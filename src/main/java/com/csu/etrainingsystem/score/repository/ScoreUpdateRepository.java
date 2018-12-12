package com.csu.etrainingsystem.score.repository;

import com.csu.etrainingsystem.score.entity.ScoreUpdate;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ScoreUpdateRepository extends JpaRepository<ScoreUpdate, Integer> {

    @Query(value = "select * from score_update where sid in (select sid from student where batch_name like ?1 and sname like ?4 and sid like ?5 ) and " +
            "update_time > ?2 and update_time<?3 ", nativeQuery = true)
    List<ScoreUpdate> findScoreUpdate(String batchName, String begin, String end, String sname, String sid);


}
