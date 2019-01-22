package com.csu.etrainingsystem.material.repository;

import com.csu.etrainingsystem.material.entity.Reimbursement;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface ReimbursementRepository extends JpaRepository<Reimbursement,Integer> {

    @Query(value = "select sum(remib_num) from reimbursement where purchase_id=?",nativeQuery = true)
    int getAllReimbNum(String pid);
}
