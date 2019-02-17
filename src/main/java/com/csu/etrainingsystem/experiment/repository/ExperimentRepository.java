package com.csu.etrainingsystem.experiment.repository;

import com.csu.etrainingsystem.experiment.entity.Experiment;
import com.csu.etrainingsystem.material.entity.Material;
import com.csu.etrainingsystem.score.entity.Score;
import com.csu.etrainingsystem.score.entity.ScoreSubmit;
import org.hibernate.annotations.SQLUpdate;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import javax.transaction.Transactional;
import java.util.List;
import java.util.Optional;

/**
 * 由于实验记录删除不会有影响,所以可以在repository中直接操作
 */
@Repository
public interface ExperimentRepository extends JpaRepository<Experiment, Integer> {

    @Query(value = "select * from experiment where experiment.exp_id=? and experiment.del_status=0", nativeQuery = true)
    Optional<Experiment> findExperimentByExp_id(int exp_id);

    @Query(value = "insert experiment(class_time,template_id,s_group_id," +
            "pro_name,t_group_id,batch_name)values(?,?,?,?,?,?)", nativeQuery = true)
    @Modifying
    void addExp(int class_time, String template_id, String s_group_id, String pro_name, String t_group_id, String batch_name);

    @Query(value = "select * from experiment where experiment.calss_time=? and experiment.del_status=0", nativeQuery = true)
    Iterable<Experiment> findExperimentByClass_time(String class_time);

    /**
     * 获取不同的模板id
     * @return
     */
    @Query(value = "select distinct template_id from experiment where experiment.template_id is not null and experiment.del_status=0", nativeQuery = true)
    Iterable<String> findAllTemplate();


    @Query(value = "select * from experiment where experiment.del_status=0", nativeQuery = true)
    Iterable<Experiment> findAllExperiment();

    @Query(value = "select * from experiment where experiment.s_group_id=? and  experiment.batch_name=? and experiment.del_status=0", nativeQuery = true)
    Iterable<Experiment> findStudentExperiment(String s_group_id, String batch_name);

    @Query(value = "select * from experiment where experiment.batch_name=? and experiment.del_status=0", nativeQuery = true)
    Iterable<Experiment> findExperimentByBatch(String batch_name);

    @Query(value = "select * from experiment where experiment.batch_name=?" +
            " and experiment.time_quant like ?" +
            " and experiment.del_status=0", nativeQuery = true)
    Iterable<Experiment> findExperimentByBAndT(String batch_name,String time_quant);

    /**
     * 取出同一个模板中的所有实验;并且这些实验的batch_name为空,表示不属于任何批次,单纯用作模板
     *
     * @param template_id
     * @return
     */
    @Query(value = "select * from experiment where experiment.template_id=? and experiment.batch_name is null and experiment.del_status=0", nativeQuery = true)
    Iterable<Experiment> findExperimentByTemplate(String template_id);

    //     @SQLUpdate (sql = "update experiment SET experiment.del_status=1 WHERE experiment.template_id=?")
    @Query(value = "update experiment SET experiment.del_status=1 WHERE experiment.template_id=?", nativeQuery = true)
    @Modifying
    void deleteExperimentByTemplate(String template_id);

    /**
     * 根据工序删除实验表,或者说就按这样的方式存?
     *
     * @param pro_name
     */
    @Query(value = "update experiment SET experiment.del_status=1 WHERE experiment.pro_name=?", nativeQuery = true)
    @Modifying
    void deleteExperimentByPro_name(String pro_name);

    @Query(value = "update experiment SET experiment.del_status=1 WHERE experiment.template_id=?", nativeQuery = true)
    @Modifying
    void deleteExperimentTemplate(String template_id);


    /**
     * 根据学生组删除实验记录,
     *
     * @param s_group_id
     */
    @Query(value = "update experiment SET experiment.del_status=1 WHERE experiment.s_group_id=? and experiment.batch_name=?", nativeQuery = true)
    @Modifying
    void deleteExperimentByS_groupAndBatch_name(String s_group_id, String batch_name);

    /**
     * 根据教师组删除实验记录,
     *
     * @param t_group_id
     */
    @Query(value = "update experiment SET experiment.del_status=1 WHERE experiment.t_group_id=?", nativeQuery = true)
    @Modifying
    void deleteExperimentByT_group(String t_group_id);

    /**
     * 根据批次删除实验记录,也就是一个批次被删除,该批次下所有的实验记录都要被删除;
     *
     * @param batch_name
     */
    @Query(value = "update experiment SET experiment.del_status=1 WHERE experiment.batch_name=?", nativeQuery = true)
    @Modifying
    void deleteExperimentByBatch(String batch_name);


    @Query(value = "select * from experiment where batch_name like ?1 and s_group_id like ?2 and pro_name like ?3 and del_status=0", nativeQuery = true)
    List<Experiment> findExperimentByBatchOrSGroupOrProName(String batchName, String sGroup, String proName);


    /**
     * -ScJn
     * 注意，在调用此方法之前，务必将批次与模板绑定好，保证所有的组号都出现在实验表中
     * @param batchName batch
     * @return num of group
     */
    @Query(value = "select distinct s_group_id from experiment where batch_name=?1 and del_status=0",nativeQuery = true)
    List<String> getNumOfGroup(String batchName);
    @Query(value = "SELECT DISTINCT s_group_id FROM experiment WHERE batch_name=? AND del_status=0 ",nativeQuery = true)
    List<String>getSGroupsOfBatch(String batch_name);
    @Query(value = "SELECT DISTINCT pro_name FROM experiment WHERE batch_name=? AND del_status=0 ",nativeQuery = true)
    List<String>getProsOfBatch(String batch_name);
    @Query(value = "SELECT  COUNT(DISTINCT class_time) FROM experiment WHERE batch_name=? AND del_status=0 ",nativeQuery = true)
    int getClassTimeOfBatch(String batch_name);
    @Query(value = "SELECT * FROM experiment WHERE batch_name=? and s_group_id=? and  del_status=0 order by class_time asc" ,nativeQuery = true)
    List<Experiment> getExperimentOfSGroup(String batch_name,String s_group_id);
    @Query(value = "SELECT DISTINCT s_group_id FROM experiment WHERE template_id=? AND del_status=0 ",nativeQuery = true)
    List<String>getSGroupByTemplate(String template_id);
}
