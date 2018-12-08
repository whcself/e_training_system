package com.csu.etrainingsystem.material.controller;

import com.csu.etrainingsystem.administrator.entity.Admin;
import com.csu.etrainingsystem.administrator.service.AdminService;
import com.csu.etrainingsystem.form.CommonResponseForm;
import com.csu.etrainingsystem.material.entity.Material;
import com.csu.etrainingsystem.material.entity.Purchase;
import com.csu.etrainingsystem.material.service.MaterialService;
import com.csu.etrainingsystem.material.service.PurchaseService;
import com.csu.etrainingsystem.teacher.entity.Teacher;
import com.csu.etrainingsystem.teacher.service.TeacherService;
import com.csu.etrainingsystem.user.entity.User;
import io.swagger.annotations.ApiOperation;
import org.apache.shiro.subject.SimplePrincipalCollection;
import org.apache.shiro.subject.support.DefaultSubjectContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpSession;
import java.text.SimpleDateFormat;
import java.util.Date;

@RestController
@RequestMapping(value = "/purchase",method = RequestMethod.POST)
public class PurchaseController {
    private final PurchaseService purchaseService;
    private final TeacherService teacherService;
    private  final MaterialService materialService;
    private final AdminService adminService;
    @Autowired
    public PurchaseController(PurchaseService purchaseService, TeacherService teacherService, MaterialService materialService, AdminService adminService) {
        this.purchaseService = purchaseService;
        this.teacherService = teacherService;
        this.materialService = materialService;
        this.adminService = adminService;
    }

    /**
     * 需要做两件事情:
     * 1 添加一个申购记录
     * 2 添加物料数量
     * @param num
     * @param clazz
     * @param session
     * @return
     */
    @ApiOperation(value = "添加一个购买记录")
    @RequestMapping(value ="/addPurchase")
    public CommonResponseForm increMaterialNum(@RequestParam(required = false) int num,
                                               @RequestParam String clazz,
                                               HttpSession session){
        SimplePrincipalCollection simplePrincipalCollection=(SimplePrincipalCollection)session.getAttribute(DefaultSubjectContext.PRINCIPALS_SESSION_KEY);
        User user=(User)simplePrincipalCollection.getPrimaryPrincipal ();
        String tname="";
       // System.out.println (user.getRole ());
        if(user.getRole ().equals ("teacher")) {
            Teacher teacher = teacherService.getTeacher (user.getAccount ());
            if (teacher != null)
                tname = teacher.getTname ();
        }
        else if(user.getRole ().equals ("admin")) {
            Admin admin = adminService.getAdminById (user.getAccount ());
            if (admin != null)
                tname = admin.getAid ();
        }
        Purchase purchase=new Purchase();
        purchase.setTname (tname);
        //申购时间
        SimpleDateFormat format=new SimpleDateFormat("yyyy-MM-dd");
        String time= format.format (new Date ());
        purchase.setPur_time (time);
        purchase.setPur_num (num);
        purchase.setClazz (clazz);
        this.purchaseService.addPurchase (purchase);
        //判断需要申购的物料是否存在,如果不存在,也买进来
        Material material;
        if(materialService.getMaterial (clazz)!=null){
                material=materialService.getMaterial (clazz);
               material.setNum (material.getNum ()+num);
             this.materialService.updateMaterial (material);
        }
        else {
            material = new Material ();
            material.setClazz (clazz);
            material.setNum (num);
            this.materialService.addMaterial (material);
        }

        return CommonResponseForm.of204 ("补充物料成功");
    }
    @ApiOperation(value = "根据时间段查询购买记录")
    @RequestMapping(value ="/getPurchaseByTime")
    public CommonResponseForm getPurchaseByTime(@RequestParam(required = false)String start_time,@RequestParam(required = false) String end_time){

        return CommonResponseForm.of200 ("获取物料成功",this.purchaseService.getPurchaseByTime (start_time,end_time));
    }
    @ApiOperation(value = "查询所有购买记录,默认显示所有?")
    @RequestMapping(value ="/getAllPurchase")
    public CommonResponseForm getAllPurchase(){

        return CommonResponseForm.of200 ("获取物料成功",this.purchaseService.getAllPurchase ());
    }
    @ApiOperation (value = "获取申请记录")
    @RequestMapping(value ="/getSelectedPurchase")
    public CommonResponseForm getSelectedPurchase(
                                        @RequestParam(required = false) String clazz,
                                        @RequestParam(required = false)String tname ,
                                        @RequestParam(required = false)Date startTime,//起始时间
                                        @RequestParam(required = false)Date endTime//截止时间
    )
    {
        return CommonResponseForm.of200("查询记录成功",purchaseService.getSelectedPurchase (tname,clazz,startTime,endTime));
    }



}
