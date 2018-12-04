package com.csu.etrainingsystem.score.repository;

import com.csu.etrainingsystem.score.entity.ScoreSubmit;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface ScoreSubmitRepository extends JpaRepository<ScoreSubmit,Integer> {
    @Query(value = "select * from score_submit where batch_name like ?1 and s_group_id like ?2 and pro_name like ?3 and del_status=0",nativeQuery = true)
    List<ScoreSubmit> findScoreRecord(String batchName, String sGroup, String proName);

}
