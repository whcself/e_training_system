/**
 * 物料先放一放,这里出现了一点问题,字段有需要修改的地方
 */
package com.csu.etrainingsystem.purchase.service;

import com.csu.etrainingsystem.material.entity.Purchase;
import com.csu.etrainingsystem.material.repository.PurchaseRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.Optional;


@Service
public class PurchaseService {
    private final PurchaseRepository purchaseRepository;
    @Autowired
    public PurchaseService(PurchaseRepository purchaseRepository) {
        this.purchaseRepository = purchaseRepository;
    }

    @Transactional
    public void addPurchase(Purchase purchase) {
        this.purchaseRepository.save(purchase);
    }
    @Transactional
    public Purchase getPurchase(String clazz) {
        Optional<Purchase> purchase=purchaseRepository.findPurchaseByClazz(clazz);
        return purchase.get();
    }
    @Transactional
    public Iterable<Purchase> getAllPurchase() {
        return this.purchaseRepository.findAllPurchase();
    }

    @Transactional
    public void  updatePurchase(Purchase Purchase) {
        this.purchaseRepository.saveAndFlush(Purchase);
    }
    @Transactional
    public void  deletePurchase(String id) {


    }

}
