package com.csu.etrainingsystem.material.controller;

import com.csu.etrainingsystem.administrator.entity.Admin;
import com.csu.etrainingsystem.administrator.service.AdminService;
import com.csu.etrainingsystem.form.CommonResponseForm;
import com.csu.etrainingsystem.material.entity.ApplyForPurchase;
import com.csu.etrainingsystem.material.form.ApplyFPchseForm;
import com.csu.etrainingsystem.material.service.*;
import com.csu.etrainingsystem.teacher.entity.Teacher;
import com.csu.etrainingsystem.teacher.service.TeacherService;
import com.csu.etrainingsystem.user.entity.User;
import com.csu.etrainingsystem.util.UserUtils;
import io.swagger.annotations.ApiOperation;
//import org.apache.shiro.subject.SimplePrincipalCollection;
//import org.apache.shiro.subject.support.DefaultSubjectContext;
import org.apache.poi.hssf.usermodel.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.*;

@RestController
@RequestMapping(value = "/applyFPchse",method = RequestMethod.POST)
public class ApplyPurchaseController {
    private final ApplyForPurchaseService applyForPurchaseService;
    private final TeacherService teacherService;
    private  final MaterialService materialService;
    private final PurchaseService  purchaseService;
    private final ReimbursementService reimbursementService;
    private final SaveService saveService;
    private final AdminService adminService;
    @Autowired
    public ApplyPurchaseController(ApplyForPurchaseService applyForPurchaseService, TeacherService teacherService, MaterialService materialService, PurchaseService purchaseService, ReimbursementService reimbursementService, SaveService saveService, AdminService adminService) {
        this.applyForPurchaseService = applyForPurchaseService;
        this.teacherService = teacherService;
        this.materialService = materialService;
        this.purchaseService = purchaseService;
        this.reimbursementService = reimbursementService;
        this.saveService = saveService;
        this.adminService = adminService;
    }

    /**
     *
     * 添加一个申购记录
     * @param num
     * @param clazz
     * @param session
     * @return
     */
    @ApiOperation(value = "添加一个申购记录")
    @RequestMapping(value ="/addApplyFPchse")
    public CommonResponseForm addApplyFPchse(@RequestParam(required = false) Integer num,
                                               @RequestParam(required = false) String clazz,
                                             @RequestParam(required = false) String apply_remark
                                               ,HttpSession session
    ){
        User user=UserUtils.getHttpSessionUser (session);
        String tname="";
        if(user.getRole ().equals ("teacher")||user.getRole ().equals ("admin")) {
            Teacher teacher = teacherService.getTeacher (user.getAccount ());
            if (teacher != null)
                tname = teacher.getTname ();
        }
        else {
            return CommonResponseForm.of400 ("用户操作异常,请检查权限");
        }
        ApplyForPurchase applyForPurchase=new ApplyForPurchase();
        applyForPurchase.setApply_tname (tname);
        //申购时间
        SimpleDateFormat format=new SimpleDateFormat("yyyy-MM-dd HH:mm");
        String time= format.format (new Date ());
        applyForPurchase.setApplyTime (time);
        applyForPurchase.setApply_num (num);
        applyForPurchase.setClazz (clazz);
        applyForPurchase.setApply_remark (apply_remark);
        applyForPurchase.setPurchase_id (this.applyForPurchaseService.GeneratePurchaseId ());
        this.applyForPurchaseService.addApplyFPchse (applyForPurchase);
        //判断需要申购的物料是否存在,如果不存在,也买进来
        //todo:在购买部分需要到采购部分完成
        return CommonResponseForm.of204 ("物料申购成功");

    }
    @ApiOperation(value = "删除申购记录")
    @RequestMapping(value ="/deleteApplyFPchse")
    public CommonResponseForm deleteApplyFPchse(String purchase_id){
        this.applyForPurchaseService.deleteApplyFPchseById (purchase_id);
        return CommonResponseForm.of204 ("删除申购记录成功");
    }
    @ApiOperation(value = "根据时间段查询申购记录")
    @RequestMapping(value ="/getApplyFPchseByTime")
    public CommonResponseForm getApplyFPchseByTime(@RequestParam(required = false)String start_time,@RequestParam(required = false) String end_time){

        return CommonResponseForm.of200 ("获取物料申购记录成功",this.applyForPurchaseService.getPurchaseByTime (start_time,end_time));
    }
    @ApiOperation(value = "查询所有申购记录,默认显示所有")
    @RequestMapping(value ="/getAllApplyFPchse")
    public CommonResponseForm getAllApplyFPchse(){
       Iterable<ApplyForPurchase>purchases= this.applyForPurchaseService.getAllApplyFPchse ();
        return CommonResponseForm.of200 ("获取所有物料申购记录成功",ApplyFPchseForm.wrapForm (purchases,this.purchaseService,this.reimbursementService,this.saveService));
    }

    /**
     * 根据各种条件查询申请记录,基本所有数据都在这里,需要前端筛选
     * @param apply_tname
     * @param clazz
     * @param startTime
     * @param endTime
     * @return
     */
    @ApiOperation (value = "根据条件获取申购记录")
    @RequestMapping(value ="/getSelectedPurchase")
    public CommonResponseForm getSelectedPurchase(
                                        @RequestParam(required = false)String apply_tname ,//申购人
                                        @RequestParam(required = false) String clazz,//种类
                                        @RequestParam(required = false)String startTime,//起始时间
                                        @RequestParam(required = false)String endTime,//截止时间
                                        @RequestParam(required = false)String pur_tname,//采购人
                                        @RequestParam(required = false)String purchase_id,//申购编号
                                        @RequestParam(required = false)Boolean apply_verify
                                        //申购审核状态
                                                 )
    {
       Iterable<ApplyForPurchase> purchases= applyForPurchaseService.getSelectedApplyFPchse (apply_verify,apply_tname,clazz,startTime,endTime,pur_tname,purchase_id);
       return CommonResponseForm.of200("查询记录成功",ApplyFPchseForm.wrapForm (purchases,this.purchaseService,this.reimbursementService,this.saveService));
    }

    @ApiOperation ("获取所有具有相应物料权限用户的名字(管理员返回id)")
    @RequestMapping(value ="/getAllNameByAuthType")
    public CommonResponseForm getAllPurchaserByType(int type){
        return  CommonResponseForm.of200 ("获取记录成功",this.applyForPurchaseService.getAllAuthedName (type));
    }
    @ApiOperation ("审核申购")
    @RequestMapping(value ="/ApplyVertify")
    public CommonResponseForm ApplyVertify(String purchase_id,String purchase_tname,int apply_num ,HttpSession session ){


        ApplyForPurchase applyForPurchase= this.applyForPurchaseService.getApplyFPchse (purchase_id);
        if (applyForPurchase.getApply_verify())return CommonResponseForm.of400 ("该申购已经审核");
        applyForPurchase.setApply_num (apply_num);
        applyForPurchase.setPur_tname (purchase_tname);
        applyForPurchase.setApply_verify(true);
        User user= UserUtils.getHttpSessionUser (session);
        //管理员才有权限审核或者统一一下
        applyForPurchase.setApply_vert_tname (user.getAccount ());
        applyForPurchase.setApply_vert_tname (teacherService.getTeacher (user.getAccount ()).getTname ());
        this.applyForPurchaseService.updateApplyFPchse (applyForPurchase);
        return  CommonResponseForm.of204 ("申购审核成功");
    }

    /**
     * 这个接口需要修改
     * @param session
     * @return
     */
    @ApiOperation ("查询该用户是是否有权限")
    @RequestMapping(value ="/getPurchaser")
    public CommonResponseForm JustifyAuthority(HttpSession session){
        int type;
        User user=UserUtils.getHttpSessionUser (session);
        if(user.getRole ().equals ("teacher")||user.getRole ().equals ("admin")){
           type=this.teacherService.getTeacher (user.getAccount ()).getMaterial_privilege ();
        }
        else{
            type=0;
        }
        return  CommonResponseForm.of200 ("获取物料权限成功",type);
    }
    /**
     * 申购单
     * @param response
     * @param purchase_ids
     * @throws IOException
     */
    @RequestMapping(value = "/ExcelDownloads")
    public void ExcelDownloads01(HttpServletResponse response,
                                 @RequestBody List<String>purchase_ids) throws IOException {
        List<ApplyForPurchase>applyFPchses=this.applyForPurchaseService.getExcelInfos (purchase_ids);
        HSSFWorkbook workbook = new HSSFWorkbook();
        HSSFSheet sheet = workbook.createSheet("信息表");
        List<String> headers=new ArrayList<> ();
        headers.add ("申购日期");headers.add ("申购人");headers.add ("物料种类");
        headers.add ("申购数量");headers.add ("申购备注");headers.add ("审核人");
        String fileName = "申购单"  + ".xls";//设置要导出的文件的名字
        //新增数据行，并且设置单元格数据
        HSSFRow row = sheet.createRow(0);
        //在excel表中添加表头
        for(int i=0;i<headers.size ();i++){
            HSSFCell cell = row.createCell(i);
            HSSFRichTextString text = new HSSFRichTextString(headers.get (i));
            cell.setCellValue(text);
        }
        int rowNum=purchase_ids.size ();
        System.out.println (rowNum);
//        List<HSSFRow> rows=new ArrayList<> ();
        for (int i=1;i<=rowNum;i++){
            HSSFRow r = sheet.createRow(i);
            r.createCell (0).setCellValue (applyFPchses.get (i-1).getApplyTime ());
            r.createCell (1).setCellValue (applyFPchses.get (i-1).getApply_tname ());
            r.createCell (2).setCellValue (applyFPchses.get (i-1).getClazz ());
            r.createCell (3).setCellValue (applyFPchses.get (i-1).getApply_num ());
            r.createCell (4).setCellValue (applyFPchses.get (i-1).getApply_remark ());
            r.createCell (5).setCellValue (applyFPchses.get (i-1).getApply_vert_tname ());
//            rows.add (r);
        }

        response.setContentType("application/octet-stream");
        response.setHeader("Content-disposition", "attachment;filename=" + fileName);
        response.flushBuffer();
        workbook.write(response.getOutputStream());
    }

    @RequestMapping(value = "/ExcelDownloads01")
    public void ExcelDownloads02(HttpServletResponse response,
                                 @RequestBody List<String>purchase_ids) throws IOException {
        List<ApplyForPurchase>applyFPchses=this.applyForPurchaseService.getExcelInfos (purchase_ids);
        System.out.println (applyFPchses);
        List<ApplyFPchseForm>forms=ApplyFPchseForm.wrapForm (applyFPchses,this.purchaseService,this.reimbursementService,this.saveService);
        HSSFWorkbook workbook = new HSSFWorkbook();
        HSSFSheet sheet = workbook.createSheet("信息表");
        List<String> headers=new ArrayList<> ();
        headers.add ("申购编号");headers.add ("申购日期");headers.add ("申购人");
        headers.add ("物料种类");headers.add ("申购数量");headers.add ("申购备注");
        headers.add ("审核状态");headers.add ("审核人");headers.add ("采购总数");
        headers.add ("报账总数");headers.add ("采购人");headers.add ("入库总数");
        String fileName = "工序排课"  + ".xls";//设置要导出的文件的名字
        //新增数据行，并且设置单元格数据
        HSSFRow row = sheet.createRow(0);
        //在excel表中添加表头
        for(int i=0;i<headers.size ();i++){
            HSSFCell cell = row.createCell(i);
            HSSFRichTextString text = new HSSFRichTextString(headers.get (i));
            cell.setCellValue(text);
        }
        int rowNum=forms.size ();
        System.out.println (rowNum);
//        List<HSSFRow> rows=new ArrayList<> ();
        for (int i=1;i<=rowNum;i++){
            HSSFRow r = sheet.createRow(i);
            r.createCell (0).setCellValue (forms.get (i-1).getPurchase_id ());
            r.createCell (1).setCellValue (forms.get (i-1).getApply_time ());
            r.createCell (2).setCellValue (forms.get (i-1).getApply_tname ());
            r.createCell (3).setCellValue (forms.get (i-1).getClazz ());
            r.createCell (4).setCellValue (forms.get (i-1).getApply_num ());
            r.createCell (5).setCellValue (forms.get (i-1).getApply_remark ());
            r.createCell (6).setCellValue (forms.get (i-1).getApply_vertify ());
            r.createCell (7).setCellValue (forms.get (i-1).getApply_vert_tname ());
            r.createCell (8).setCellValue (forms.get (i-1).getPur_num ());
            r.createCell (9).setCellValue (forms.get (i-1).getRemib_num ());
            r.createCell (10).setCellValue (forms.get (i-1).getPur_tname ());
            r.createCell (11).setCellValue (forms.get (i-1).getSave_num ());

        }

        response.setContentType("application/octet-stream");
        response.setHeader("Content-disposition", "attachment;filename=" + fileName);
        response.flushBuffer();
        workbook.write(response.getOutputStream());
    }
}
