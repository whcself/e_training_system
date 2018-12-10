package com.csu.etrainingsystem.score.repository;

import com.csu.etrainingsystem.score.entity.Score;
import com.csu.etrainingsystem.score.entity.SpecialScore;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Optional;

/*
 *     private int scoreid;//无意义,自增长
 *     private String sid;
 *     private String pro_name;
 *     private float pro_score;
 *     private boolean del_status;
 */
@Repository
public interface ScoreRepository extends JpaRepository<Score, Integer> {

    @Modifying
    @Query(value = "UPDATE student INNER JOIN (SELECT newscore.sid, sum( newscore.pro_score * p.weight ) AS total_score  FROM " +
            "( SELECT s.*, stu.batch_name FROM score AS s, student AS stu WHERE s.sid = stu.sid ) AS newscore, proced AS p  " +
            "WHERE newscore.batch_name = p.batch_name AND newscore.pro_name = p.pro_name  and newscore.batch_name=? GROUP BY newscore.sid  ) total " +
            "ON student.sid = total.sid  SET student.total_score = total.total_score",nativeQuery = true)
    void executeScore(String batchName);
    //查找该同学所有工序的成绩
    @Query(value = "select * from score where score.sid=? and score.del_status=0", nativeQuery = true)
    Iterable<Score> findScoreBySid(String sid);

    //根据学生姓名查找成绩
    @Query(value = "select * from score where score.sid in (select sid from sp_student where sp_student.sname=? and del_status=0) and score.del_status=0", nativeQuery = true)
    Iterable<Score> findScoreBySName(String sName);

    //查找该同学指定工序的成绩
    @Query(value = "select * from score where score.sid=? and score.pro_name=? and score.del_status=0", nativeQuery = true)
    Score findScoreBySidAndPro_name(String sid, String pro_name);

    //查找该同学指定工序的成绩
    @Query(value = "select * from score where score.del_status=0", nativeQuery = true)
    Iterable<Score> findAllScore();


    //删除该同学所有成绩,删除一个学生时会调用
    @Query(value = "update score SET score.del_status=1 WHERE score.sid=?", nativeQuery = true)
    @Modifying
    void deleteScoreBySid(String sid);


    //删除该同学的指定成绩
    @Query(value = "update score SET score.del_status=1 WHERE score.sid=? and score.pro_name=?", nativeQuery = true)
    @Modifying
    void deleteScoreBySidAndPro_name(String sid, String pro_name);


    //删除该工序所有成绩,删除一个工序时会调用
    @Query(value = "update score SET score.del_status=1 WHERE score.pro_name=?", nativeQuery = true)
    @Modifying
    void deleteScoreByPro_name(String pro_name);



}
