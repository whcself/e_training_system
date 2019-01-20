package com.csu.etrainingsystem.material.controller;

import com.csu.etrainingsystem.form.CommonResponseForm;
import com.csu.etrainingsystem.material.entity.Purchase;
import com.csu.etrainingsystem.material.service.PurchaseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping( value = "/purchase")
public class PurchaseController {

    private final PurchaseService purchaseService;

    @Autowired
    public PurchaseController(PurchaseService purchaseService) {
        this.purchaseService = purchaseService;
    }

    @PostMapping("/getPurchase")
    public CommonResponseForm getPurchase(String purchase_id,
                                          String clazz,
                                          String pur_tname,
                                          String begin,
                                          String end){

        List<Purchase> purchases=purchaseService.getPurchase(purchase_id,clazz, pur_tname, begin, end);
        return CommonResponseForm.of200("共"+purchases.size()+"条记录",purchases);
    }
}
