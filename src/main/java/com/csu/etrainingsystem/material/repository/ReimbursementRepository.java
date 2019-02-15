package com.csu.etrainingsystem.material.repository;

import com.csu.etrainingsystem.material.entity.Reimbursement;
import com.csu.etrainingsystem.material.form.ReimAddForm;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.List;

@Repository
public interface ReimbursementRepository extends JpaRepository<Reimbursement,Integer> {

    @Query(value = "select sum(remib_num) from reimbursement where purchase_id=?1 and del_status=0 ",nativeQuery = true)
    Integer getAllReimbNum(String pid);

    @Query(value = "select * from reimbursement where remib_time between ?1 and " +
            "  ?2 and pur_tname like ?3 and clazz like ?4 and remib_vertify like ?5 " +
            " and purchase_id like ?6 and del_status=0 order by remib_time desc ",nativeQuery = true)
    List<Reimbursement> findBy6(String begin,
                                String end,
                                String tName,
                                String clazz,
                                String verify,
                                String purchaseId);

    @Query(value = "select * from reimbursement where id=?1 and del_status=0",nativeQuery = true)
    Reimbursement findById(String id);


    @Query(value = "select sum(remib_num) from reimbursement where purchase_id=?1 and del_status=0",nativeQuery = true)
    Integer getSumReimByPId(String pid);



    @Query(value = "select apply_num from apply_for_purchase where purchase_id=?1 and del_status=0",nativeQuery = true)
    int getSumApplyByPId(String pid);

    @Modifying
    @Query(value = "update reimbursement set remib_vertify=1 ,remib_vert_time=?1, remib_vert_tname=?2 where id=?3 and del_status=0",nativeQuery = true)
    void verifyReim(String time,
                    String tname,
                    String id);

    @Modifying
    @Query(value = "update reimbursement set del_status=1 where id=?1",nativeQuery = true)
    void delete2(String id);

}
