//package com.csu.etrainingsystem.material.repository;
//
//import com.csu.etrainingsystem.material.entity.ApplyForPurchase;
//import com.csu.etrainingsystem.material.form.PurchaseInfoForm;
//import org.springframework.data.jpa.repository.JpaRepository;
//import org.springframework.data.jpa.repository.Query;
//
//public interface PurchaseInfoRepository extends JpaRepository<PurchaseInfoForm,String> {
//    @Query(value = "select apply_num,apply_verify,pur_tname,clazz from apply_for_purchase where purchase_id=?1 and del_status=0",nativeQuery = true)
//    PurchaseInfoForm getPurchaseInfo(String pid);
//}
