package com.csu.etrainingsystem.score.repository;

import com.csu.etrainingsystem.score.entity.SpecialScore;
import com.csu.etrainingsystem.student.entity.SpecialStudent;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface SpScoreRepository extends JpaRepository<SpecialScore,Integer> {

    //查找该特殊学生所有工序的成绩
    @Query(value = "select * from sp_score where sp_score.sid=? and sp_score.del_status=0", nativeQuery = true)
    Iterable<SpecialScore> findSpScoreBySid(String sid);

    //根据特殊学生姓名查找成绩
    @Query(value = "select * from sp_score where sp_score.sid in (select sid from sp_student where sp_student.sname=? and sp_student.del_status=0)and sp_score.del_status=0", nativeQuery = true)
    Iterable<SpecialScore> findSpScoreBySName(String SpSname);


    //删除该特殊同学所有成绩,删除一个特殊学生时会调用
    @Query(value = "update sp_score SET sp_score.del_status=1 WHERE sp_score.sid=?", nativeQuery = true)
    @Modifying
    void deleteSpScoreBySid(String sid);

    //查找该特殊同学指定工序的成绩
    @Query(value = "select * from sp_score where sp_score.sid=? and sp_score.pro_name=? and sp_score.del_status=0", nativeQuery = true)
    SpecialScore findSpScoreBySidAndPro_name(String sid, String pro_name);

    //删除该特殊同学的指定成绩
    @Query(value = "update sp_score SET sp_score.del_status=1 WHERE sp_score.sid=? and sp_score.pro_name=?", nativeQuery = true)
    @Modifying
    void deleteSpScoreBySidAndPro_name(String sid, String pro_name);

    /*
    -ScJn
     */
    @Query(value = "select * from sp_score where sid in (select sid from sp_student where sname=? and del_status=0)",nativeQuery = true)
    List<SpecialScore> findSpStudentBySname(String sname);

    @Modifying
    @Query(value = "update sp_score set pro_score=?3 where sid=?1 and pro_name=?2",nativeQuery = true)
    void updateSpScore(String sid,String pro_name,String pro_score);



    @Modifying
    @Query(value = "update sp_student set score_lock=1 ",nativeQuery = true)
    void releaseSpScore();


}
