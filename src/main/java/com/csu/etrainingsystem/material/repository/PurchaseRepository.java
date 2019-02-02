package com.csu.etrainingsystem.material.repository;

import com.csu.etrainingsystem.material.entity.Purchase;
import com.csu.etrainingsystem.material.form.PurchaseInfoForm;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Repository
public interface PurchaseRepository extends JpaRepository<Purchase, Integer> {

    @Query(value = "select * from purchase where purchase_id like ?1 and clazz like ?2 and pur_tname like" +
            " ?3 and pur_time between ?4 and ?5 and del_status=0 order by pur_time DESC ",nativeQuery = true)
    List<Purchase> findPurchasesBy4Desc(String purchase_id,
                                        String clazz,
                                        String pur_tname,
                                        String begin,
                                        String end);

    @Query(value = "select * from purchase where id like ?1 and del_status=0 order by pur_time desc ",nativeQuery = true)
    Purchase findByPId(String pid);


    @Query(value = "select pru_num from purchase where purchase_id = ? ",nativeQuery = true)
    int findApplyNumByPId(String pId);

    @Query(value = "select sum(pur_num) from purchase where purchase_id=? and del_status=0 group by purchase_id", nativeQuery = true)
    Integer getAllPurNum(String pid);

    @Query(value = "select apply_num from apply_for_purchase where purchase_id=? and del_status=0",nativeQuery = true)
    int getApplyNumByPid(String pid);

    @Query(value = "select apply_verify from apply_for_purchase where purchase_id=?1 and del_status=0",nativeQuery = true)
    boolean getApplyVerify(String pid);

    @Query(value = "select distinct pur_tname from purchase where purchase_id=?1 and del_status=0", nativeQuery = true)
    String getPurTNameByPId(String pid);

    @Query(value = "select distinct clazz from purchase where pur_tname=?1 and del_status=0", nativeQuery = true)
    List<String> getClazzByTName(String tName);

    @Query(value = "select distinct clazz from purchase where purchase_id=?1 and del_status=0",nativeQuery = true)
    String getClazzByPId(String pid);

    @Modifying
    @Query(value = "update purchase set del_status=1 where id=?1",nativeQuery = true)
    void delete2(String id);

    @Modifying
    @Query(value = "update purchase set pur_num=?2 where id=?1",nativeQuery = true)
    void updateNum(Integer id,Integer num);
}
