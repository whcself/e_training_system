package com.csu.etrainingsystem.teacher.repository;

import com.csu.etrainingsystem.teacher.entity.Marking;
import com.csu.etrainingsystem.teacher.entity.Teacher;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;

/**
 *删除一个打分权限记录不会带来影响
 */
@Repository
public interface MarkingRepository extends JpaRepository<Marking,String> {

    //查找该老师分组的权限
    @Query(value="select * from marking where marking.t_group_id=? and marking.del_status=0",nativeQuery = true)
    Iterable<Marking> findMarkingByT_group_id(String t_group_id);

     //查找所有拥有该权限的老师分组
     @Query(value="select * from marking where marking.authority=? and marking.del_status=0",nativeQuery = true)
     Iterable<Marking> findByAuthority(String authority);

     //查找所有记录
     @Query(value="select * from marking where  marking.del_status=0",nativeQuery = true)
     Iterable<Marking> findByAllMarking();

     @Query(value ="update marking SET marking.del_status=1 WHERE marking.t_group_id=?",nativeQuery=true)
     @Modifying
     void deleteMarkingByT_group_id(String  t_group_id);
     @Query(value ="update marking SET marking.del_status=1 WHERE marking.marking_id=?",nativeQuery=true)
     @Modifying
     void deleteMarkingByMark_id(int  marking_id);
}
