package com.csu.etrainingsystem.procedure.repository;

import com.csu.etrainingsystem.procedure.entity.Proced;
import com.csu.etrainingsystem.procedure.entity.ProcedId;
import org.hibernate.annotations.SQLUpdate;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.jpa.repository.query.Procedure;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;
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

    @Query(value = "select distinct pro_name from proced where del_status=0",nativeQuery = true)
    List<String>findAllProced2();

    /**
     * 设置权重
     *
     */
    @Modifying
    @Query(value = "update proced set weight=?3 where batch_name=?1 and pro_name=?2",nativeQuery = true)
    void setWeightByBatchNameAndProName(String batch_name,String pro_name,Float weight);


    @Query(value = "select distinct template_name from proced_template where del_status=0",nativeQuery = true)
    Iterable<String>findAllTemplateName();

    @Query(value = "select * from proced_template where template_name=?1 and del_status=0",nativeQuery = true)
    List<Map<String,Float>> findTemplateItemByName(String name);

    @Modifying
    @Query(value = "update proced_template set del_status=1 where template_name=?1 ",nativeQuery = true)
    void deleteTemplate(String name);
    /**
     * 不管删除了，只需要得到老师组名
     * @param proName pro
     * @return t_group_id
     */
    @Query(value="select distinct t_group_id from proced where pro_name=?1 and del_status=0",nativeQuery = true)
    String getTGroupByProName(String proName);

    @Query(value = "select * from proced where template_name=?1 and del_status=0",nativeQuery = true)
    Iterable<Proced> findTemplateByTemplate_name(String templateName);

//    @Query(value = "update proced set del_status=1 where batch_name=?1 and  ")

    @Modifying
    @Query(value = "update proced set pro_name=?3 where pro_name=?2 and t_group_id=?1",nativeQuery = true)
    void updateProcedFromGroup(String groupName,String old,String newName);


    @Modifying
    @Query(value = "update proced set del_status=1 where t_group_id=?1 and pro_name=?2",nativeQuery = true)
    void deleteProcedFromGroup(String groupName,String proName);

    @Modifying
    @Query(value = "update proced set del_status=1 where batch_name=?1 ",nativeQuery = true)
    void deleteProced(String batchName);
}
