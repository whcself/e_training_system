package com.csu.etrainingsystem.experiment.repository;

import com.csu.etrainingsystem.experiment.entity.Experiment;
import com.csu.etrainingsystem.material.entity.Material;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Optional;

/**
 * 由于实验记录删除不会有影响,所以可以在repository中直接操作
 */
@Repository
public interface ExperimentRepository extends JpaRepository<Experiment,Integer> {
    @Query(value="select * from experiment where experiment.exp_id=? and experiment.del_status=0",nativeQuery = true)
    Optional<Experiment> findExperimentByExp_id(int exp_id);
    @Query(value="select * from experiment where experiment.calss_time=? and experiment.del_status=0",nativeQuery = true)
    Iterable<Experiment> findExperimentByClass_time(String class_time);
    @Query(value="select * from experiment where experiment.del_status=0",nativeQuery = true)
    Iterable<Experiment> findAllExperiment();
    @Query(value="select * from experiment where experiment.s_group_id=? experiment.batch_name=? and experiment.del_status=0",nativeQuery = true)
    Iterable<Experiment> findStudentExperiment(String s_group_id,String batch_name);
    /**
     * 根据工序删除实验表,或者说就按这样的方式存?
     * @param pro_name
     */
    @Query(value = "update experiment SET experiment.del_status=1 WHERE experiment.pro_name=?",nativeQuery = true)
    @Modifying
    void deleteExperimentByPro_name(String pro_name);

    /**
     * 根据学生组删除实验记录,
     * @param s_group_id
     */
    @Query(value = "update experiment SET experiment.del_status=1 WHERE experiment.s_group_id=? and experiment.batch_name=?",nativeQuery = true)
    @Modifying
    void deleteExperimentByS_groupAndBatch_name(String s_group_id,String batch_name);
    /**
     * 根据教师组删除实验记录,
     * @param t_group_id
     */
    @Query(value = "update experiment SET experiment.del_status=1 WHERE experiment.t_group_id=?",nativeQuery = true)
    @Modifying
    void deleteExperimentByT_group(String t_group_id);
    /**
     * 根据批次删除实验记录,也就是一个批次被删除,该批次下所有的实验记录都要被删除;
     * @param batch_name
     */
    @Query(value = "update experiment SET experiment.del_status=1 WHERE experiment.batch_name=?",nativeQuery = true)
    @Modifying
    void deleteExperimentByBatch(String batch_name);
}