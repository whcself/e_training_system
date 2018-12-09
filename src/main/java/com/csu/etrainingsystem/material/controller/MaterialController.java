package com.csu.etrainingsystem.material.controller;


import com.csu.etrainingsystem.administrator.entity.Admin;
import com.csu.etrainingsystem.administrator.service.AdminService;
import com.csu.etrainingsystem.form.CommonResponseForm;
import com.csu.etrainingsystem.material.entity.Apply;
import com.csu.etrainingsystem.material.entity.Material;
import com.csu.etrainingsystem.material.service.MaterialService;
import com.csu.etrainingsystem.material.service.PurchaseService;
import com.csu.etrainingsystem.teacher.entity.Teacher;
import com.csu.etrainingsystem.teacher.service.TeacherService;
import com.csu.etrainingsystem.user.entity.User;
import io.swagger.annotations.ApiOperation;
import org.apache.shiro.subject.SimplePrincipalCollection;
import org.apache.shiro.subject.support.DefaultSubjectContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpSession;
import java.text.SimpleDateFormat;
import java.util.Date;

@RestController
@RequestMapping(value = "/material",method = RequestMethod.POST)
public class MaterialController {
    private final MaterialService materialService;
    private final TeacherService teacherService;
    private  final PurchaseService purchaseService;
    private final AdminService adminService;
    @Autowired
    public MaterialController(MaterialService materialService, TeacherService teacherService, PurchaseService purchaseService, AdminService adminService) {
        this.materialService = materialService;
        this.teacherService = teacherService;
        this.purchaseService = purchaseService;
        this.adminService = adminService;
    }

    @ApiOperation (value = "查询所有物料")
    @RequestMapping(value ="/getAllMaterial")
    public CommonResponseForm getAllMaterial(){
        return CommonResponseForm.of200 ("获取物料成功",this.materialService.getAllMaterial ());
    }

    @ApiOperation (value = "查询所有物料")
    @RequestMapping(value ="/deleteMaterial")
    public CommonResponseForm deleteMaterial(String clazz){
        this.materialService.deleteMaterial (clazz);
        return CommonResponseForm.of204 ("获取物料成功");
    }
    @ApiOperation (value = "查询所有存量大于0的物料")
    @RequestMapping(value ="/getMaterialsNotEmpty")
    public CommonResponseForm getMaterialsNotEmpty(){

       return CommonResponseForm.of200 ("获取存量大于0的物料成功",this.materialService.getMaterialNotEmpty ());
    }

    @ApiOperation (value = "派出物料,需要在物料表里面新增加申请记录")
    @RequestMapping(value ="/decrMaterialNum")
    public CommonResponseForm decrMaterialNum(HttpSession session,
                                              @RequestParam(required = false) int num,
                                              @RequestParam String clazz,
                                              @RequestParam String sid,
                                              @RequestParam String sname){

        //派出物料,也就是减少物料数量
        Material material= this.materialService.getMaterial (clazz);
        if(material.getNum ()-num<0)return CommonResponseForm.of400 ("物料数量不足");
       material.setNum (material.getNum ()-num);
       this.materialService.updateMaterial (material);
       //新增物料申请记录
        SimpleDateFormat format=new SimpleDateFormat("yyyy-MM-dd");
        String time= format.format (new Date ());
        Apply apply=new Apply ();
        apply.setApply_time (time);
        apply.setClazz (clazz);
        apply.setNum (num);
        apply.setSid (sid);
        apply.setSname (sname);
        //todo:需要判断是管理员还是老师
        String tid="";
        if(session.getAttribute(DefaultSubjectContext.PRINCIPALS_SESSION_KEY)!=null){
        SimplePrincipalCollection spc=(SimplePrincipalCollection)session.getAttribute(DefaultSubjectContext.PRINCIPALS_SESSION_KEY);
            User user=(User)spc.getPrimaryPrincipal ();
            if(user.getRole ().equals ("teacher")) {
                Teacher teacher = teacherService.getTeacher (user.getAccount ());
                if (teacher != null)
                    tid = teacher.getTid ();
            }
          else if(user.getRole ().equals ("admin")) {
                Admin admin = adminService.getAdminById (user.getAccount ());
                if (admin != null)
                    tid = admin.getAid ();
            }
        }
        apply.setTid (tid);
        materialService.addAply (apply);
        return CommonResponseForm.of204 ("派出物料成功");
    }


    @ApiOperation (value = "获取申请记录")
    @RequestMapping(value ="/getApplys")
    public CommonResponseForm getApplys(@RequestParam(required = false) String clazz,
                                        @RequestParam(required = false)String sid,
                                        @RequestParam(required = false)String sname ,
                                        @RequestParam(required = false)Date startTime,//起始时间
                                        @RequestParam(required = false)Date endTime//截止时间
                                        ){
        return CommonResponseForm.of200("查询记录成功",materialService.getAplyBySidAndSnameAndClazzAndTime (sid,sname,clazz,startTime,endTime));
    }


}
