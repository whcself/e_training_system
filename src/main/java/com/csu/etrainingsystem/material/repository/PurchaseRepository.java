package com.csu.etrainingsystem.material.repository;

import com.csu.etrainingsystem.material.entity.ApplyForPurchase;
import com.csu.etrainingsystem.material.entity.Purchase;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Date;
import java.util.List;
import java.util.Optional;

@Repository
public interface PurchaseRepository extends JpaRepository<Purchase, String> {

    @Query(value = "select * from purchase where purchase_id like ?1 and clazz like ?2 and pur_tname like" +
            " ?3 and pur_time between ?4 and ?5 order by pur_time DESC ",nativeQuery = true)
    List<Purchase> findPurchasesBy4Desc(String purchase_id,
                                        String clazz,
                                        String pur_tname,
                                        String begin,
                                        String end);


}
