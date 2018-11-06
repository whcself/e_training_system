package com.csu.etrainingsystem.score.repository;

import com.csu.etrainingsystem.score.entity.Score;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface ScoreRepository extends JpaRepository<Score,Integer> {

    //查找该同学所有工序的成绩
    @Query(value="select * from score where score.sid=? and score.del_status=0",nativeQuery = true)
    Iterable<Score> findScoreBySid(String sid);

    //查找该同学指定工序的成绩
    @Query(value="select * from score where score.sid=? and score.pro_name=? and score.del_status=0",nativeQuery = true)
    Iterable<Score> findScoreBySidAndPro_name(String sid, String pro_name);

    //查找该同学指定工序的成绩
    @Query(value="select * from score where score.del_status=0",nativeQuery = true)
    Iterable<Score> findAllScore();
    //删除该同学所有成绩,删除一个学生时会调用
    @Query(value = "update score SET score.del_status=1 WHERE score.sid=?",nativeQuery = true)
    @Modifying
    void deleteScoreBySid(String sid);
    //删除该同学的指定成绩
    @Query(value = "update score SET score.del_status=1 WHERE score.sid=? and score.pro_name=?" ,nativeQuery = true)
    @Modifying
    void deleteScoreBySidAndPro_name(String sid,String pro_name);
    //删除该同学所有成绩,删除一个学生时会调用
    @Query(value = "update score SET score.del_status=1 WHERE score.pro_name=?",nativeQuery = true)
    @Modifying
    void deleteScoreByPro_name(String pro_name);
}
