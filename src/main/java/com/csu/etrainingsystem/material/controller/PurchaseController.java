package com.csu.etrainingsystem.material.controller;

import com.csu.etrainingsystem.form.CommonResponseForm;
import com.csu.etrainingsystem.material.entity.Material;
import com.csu.etrainingsystem.material.entity.Purchase;
import com.csu.etrainingsystem.material.service.PurchaseService;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(value = "purchase",method = RequestMethod.POST)
public class PurchaseController {
    private final PurchaseService purchaseService;

    @Autowired
    public PurchaseController(PurchaseService purchaseService) {
        this.purchaseService = purchaseService;
    }

    @ApiOperation(value = "添加一个购买记录")
    @RequestMapping(value ="/addPurchase")
    public CommonResponseForm addPurchase(@RequestParam Purchase purchase){
        this.purchaseService.addPurchase (purchase);
        return CommonResponseForm.of204 ("获取物料成功");
    }
    @ApiOperation(value = "根据时间段查询购买记录")
    @RequestMapping(value ="/getPurchaseByTime")
    public CommonResponseForm getPurchaseByTime(@RequestParam(required = false)String start_time,@RequestParam(required = false) String end_time){

        return CommonResponseForm.of200 ("获取物料成功",this.purchaseService.getPurchaseByTime (start_time,end_time));
    }
}
