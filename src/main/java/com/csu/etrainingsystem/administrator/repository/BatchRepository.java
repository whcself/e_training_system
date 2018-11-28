package com.csu.etrainingsystem.administrator.repository;

import com.csu.etrainingsystem.administrator.entity.Admin;
import com.csu.etrainingsystem.administrator.entity.Batch;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Optional;


@Repository
public interface BatchRepository extends JpaRepository<Batch,String> {

    @Query(value="select * from batch where batch.batch_name=? and batch.del_status=0",nativeQuery = true)
    Optional<Batch> findBatchByName(String batch_name);
    @Query(value="select * from batch where batch.del_status=0",nativeQuery = true)
    Iterable<Batch> findAllBatch();

    @Query(value = "select * from batch where semester_name like ?1 and del_status=0", nativeQuery = true)
    Batch findBatchBySemester_name(String semesterName);
}
