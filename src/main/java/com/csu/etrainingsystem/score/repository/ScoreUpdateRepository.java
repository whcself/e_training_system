package com.csu.etrainingsystem.score.repository;

import com.csu.etrainingsystem.score.entity.ScoreUpdate;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;

@Repository
public interface ScoreUpdateRepository extends JpaRepository<ScoreUpdate, Integer> {

    @Query(value = "select su.*,stu.batch_name,stu.sname,stu.clazz from score_update as su , student as stu where su.sid=stu.sid and stu.batch_name like ?1 and stu.sname like ?4 and stu.sid like ?5  and " +
            "su.update_time > ?2 and su.update_time<?3 ", nativeQuery = true)
    List<Map<String,String>> findScoreUpdate(String batchName, String begin, String end, String sname, String sid);


}
