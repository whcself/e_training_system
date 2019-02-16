package com.csu.etrainingsystem.teacher.repository;

import com.csu.etrainingsystem.teacher.entity.TeacherAndGroup;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface T_Group_ConnRepository extends JpaRepository<TeacherAndGroup,String > {

    @Query(value="select * from t_group_conn where tid=? and t_group_id=? and del_status=0",nativeQuery = true)
    TeacherAndGroup findTeacherGroupById(String tid, String t_group_id);

    @Query(value="select * from t_group_conn where t_group_conn.del_status=0",nativeQuery = true)
    Iterable<TeacherAndGroup> findAllTeacherGroup();

    /**
     * 如果tid对应的教师被删除,那么对应的分组记录也应该被删除,但是分组依然存在,
     * @param tid
     * @return
     */
    @Modifying
    @Query(value="update t_group_conn set del_status=1 where tid=?1 and del_status=0",nativeQuery=true)
    void deleteTeacherGroupByTidSQL(String tid);


    @Query(value="insert into t_group_conn(t_group_id,tid)values (?,?)",nativeQuery=true)
    @Modifying
    void modifyTeacherGroupByTidSQL(String t_group_id,String tid);

    @Query(value = "select t_group_id from t_group_conn where del_status=0 and tid=?1",nativeQuery = true)
    List<String> findTGroup(String tid);
}
