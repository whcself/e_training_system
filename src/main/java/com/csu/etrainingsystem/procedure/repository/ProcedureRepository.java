package com.csu.etrainingsystem.procedure.repository;

import com.csu.etrainingsystem.procedure.entity.Proced;
import com.csu.etrainingsystem.procedure.entity.ProcedId;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.jpa.repository.query.Procedure;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface ProcedureRepository extends JpaRepository<Proced,ProcedId> {
    /**
     * 跨批次查找工序名
     */
    @Query(value="select * from proced where  proced.del_status=0",nativeQuery = true)
    Iterable<Proced> findAllProced();
    @Query(value="select * from proced where proced.batch_name=? and proced.del_status=0",nativeQuery = true)
    Iterable<Proced> findProcedByBatch_name(String batch_name);

    /**
     * 查找同一批次的所有工序
     * @param batch_name
     * @return
     */
    @Query(value="select * from proced where proced.batch_name=? and proced.del_status=0",nativeQuery = true)
    Iterable<Proced> findBatchProced(String batch_name);

    /**
     * 查找指定批次/指定工序
     * @param pro_name
     * @param batch_name
     * @return
     */
    @Query(value="select * from proced where  proced.pro_name=? and proced.batch_name=? and proced.del_status=0",nativeQuery = true)
    Optional<Proced> findProcedByNameAndBatch(String pro_name,String batch_name);

}
