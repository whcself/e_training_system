package com.csu.etrainingsystem.material.controller;

import com.csu.etrainingsystem.form.CommonResponseForm;
import com.csu.etrainingsystem.material.entity.Purchase;
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

@RestController
@RequestMapping(value = "purchase",method = RequestMethod.POST)
public class PurchaseController {
    private final PurchaseService purchaseService;
    private final TeacherService teacherService;

    @Autowired
    public PurchaseController(PurchaseService purchaseService, TeacherService teacherService) {
        this.purchaseService = purchaseService;
        this.teacherService = teacherService;
    }

    @ApiOperation(value = "添加一个购买记录")
    @RequestMapping(value ="/addPurchase")
    public CommonResponseForm addPurchase(@RequestParam Purchase purchase, HttpSession session){
        SimplePrincipalCollection simplePrincipalCollection=(SimplePrincipalCollection)session.getAttribute(DefaultSubjectContext.PRINCIPALS_SESSION_KEY);
        User user=(User)simplePrincipalCollection.getPrimaryPrincipal ();
        Teacher teacher=teacherService.getTeacher (user.getAccount ());
        if(teacher!=null)purchase.setTname (teacher.getTname ());
        this.purchaseService.addPurchase (purchase);
        return CommonResponseForm.of204 ("添加物料购买申请成功成功");
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
}
