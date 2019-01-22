package com.csu.etrainingsystem.material.repository;

import com.csu.etrainingsystem.material.entity.ApplyForPurchase;
import com.csu.etrainingsystem.material.entity.Purchase;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Date;
import java.util.Optional;

@Repository
public interface ApplyForPurchaseRepository extends JpaRepository<ApplyForPurchase,String> {
    //自动转换为static final类型
    String APPEND_DEL_STATUS=" apply_for_purchase.del_status=0";

    @Query(value="select * from apply_for_purchase where apply_for_purchase.clazz=? and"+APPEND_DEL_STATUS,nativeQuery = true)
    Optional<ApplyForPurchase> findApplyFPchseByClazz(String clazz);
    @Query(value="select * from apply_for_purchase where"+APPEND_DEL_STATUS,nativeQuery = true)
    Iterable<ApplyForPurchase> findAllApplyFPchse();
    @Query(value="select * from apply_for_purchase where  "+APPEND_DEL_STATUS+" and apply_for_purchase.apply_time between ? and ?",nativeQuery = true)
    Iterable<ApplyForPurchase> findApplyFPchseByTime(String startTime ,String endTime);

    @Query(value="  SELECT  MAX(apply_for_purchase.apply_time) FROM apply_for_purchase where "+APPEND_DEL_STATUS,nativeQuery = true)
    Date findMaxTime();
    @Query(value="   SELECT MIN(apply_for_purchase.apply_time) FROM apply_for_purchase where "+APPEND_DEL_STATUS,nativeQuery = true)
    Date findMinTime();
    @Query(value=" SELECT MAX(purchase_id) FROM apply_for_purchase  WHERE purchase_id IN" +
            "     (SELECT purchase_id FROM apply_for_purchase WHERE purchase_id LIKE ? ) ",nativeQuery = true)
    String findMaxPurchaseId(String thisMonth);

    //需要的数据:
    //purchase_id, apply_time,apply_tname,clazz,apply_num,apply_remark,apply_vert_tname
    @Query(value="select *" +
            " from apply_for_purchase where apply_for_purchase.purchase_id=? and "+APPEND_DEL_STATUS,nativeQuery = true)
    ApplyForPurchase findApplyFPchseExcelInfos(String purchase_id);

    /**
     *   根据条件查询申购记录,如果传入的字段为空表示全部通过
     * @param apply_tname
     * @param clazz
     * @param pur_tname
     * @param purchase_id
     * @param applyStartTime
     * @param applyEndTime
     * @return
     */
    @Query(value="SELECT * FROM apply_for_purchase  WHERE apply_tname LIKE ?1 AND clazz LIKE ?2 "
            +"AND pur_tname LIKE ?3 AND apply_for_purchase.purchase_id LIKE ?4" +
            " AND apply_for_purchase.apply_time BETWEEN ?5 AND ?6 ORDER BY apply_for_purchase.apply_time DESC "
           ,nativeQuery = true)
    Iterable<ApplyForPurchase> getSelectedApplyFPchse(String apply_tname , String clazz, String pur_tname, String purchase_id, String applyStartTime, String applyEndTime);


    @Query(value = "select * from apply_for_purchase where purchase_id=?1 and del_status=0",nativeQuery = true)
    ApplyForPurchase getPurchaseInfo(String pid);
}
