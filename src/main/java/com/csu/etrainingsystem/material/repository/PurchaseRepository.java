package com.csu.etrainingsystem.material.repository;

import com.csu.etrainingsystem.material.entity.Purchase;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface PurchaseRepository extends JpaRepository<Purchase,String> {

    @Query(value="select * from purchase where purchase.clazz=? and purchase.del_status=0",nativeQuery = true)
    Optional<Purchase> findPurchaseByClazz(String clazz);
    @Query(value="select * from purchase where purchase.del_status=0",nativeQuery = true)
    Iterable<Purchase> findAllPurchase();

}
