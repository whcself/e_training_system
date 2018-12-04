package com.csu.etrainingsystem.material.repository;

import com.csu.etrainingsystem.material.entity.Purchase;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Date;
import java.util.List;
import java.util.Optional;

@Repository
public interface PurchaseRepository extends JpaRepository<Purchase,String> {

    @Query(value="select * from purchase where purchase.clazz=? and purchase.del_status=0",nativeQuery = true)
    Optional<Purchase> findPurchaseByClazz(String clazz);
    @Query(value="select * from purchase where purchase.del_status=0",nativeQuery = true)
    Iterable<Purchase> findAllPurchase();
    @Query(value="select * from purchase where purchase.pur_time between ? and ?",nativeQuery = true)
    Iterable<Purchase> findPurchaseByTime(String startTime ,String endTime);

    @Query(value="  SELECT  MAX(purchase.pur_time) FROM purchase where purchase.del_status=0",nativeQuery = true)
    List<Date> findMaxTime();
    @Query(value="   SELECT MIN(purchase.pur_time) FROM purchase where purchase.del_status=0",nativeQuery = true)
    List<Date> findMinTime();
    @Query(value="select * from purchase where" +
            " purchase.tname like ?  and purchase.clazz like?"
            +" and  purchase.pur_time between ? and ? "
            ,nativeQuery = true)
    Iterable<Purchase> getSelectedPurchase(String tname ,String clazz,String startTime,String endTime);


}
