package com.csu.etrainingsystem.material.service;

import com.csu.etrainingsystem.material.entity.Purchase;
import com.csu.etrainingsystem.material.repository.PurchaseRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PurchaseService {
    private final PurchaseRepository purchaseRepository;
    @Autowired
    public PurchaseService(PurchaseRepository purchaseRepository){
        this.purchaseRepository=purchaseRepository;
    }

    public List<Purchase> getPurchase(String purchase_id,
                                      String clazz,
                                      String pur_name,
                                      String begin,
                                      String end){

        return (List<Purchase>) purchaseRepository.findPurchasesBy4Desc(purchase_id,clazz,pur_name,begin,end);

    }
}
