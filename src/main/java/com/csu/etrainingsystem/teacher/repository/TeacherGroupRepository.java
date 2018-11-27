package com.csu.etrainingsystem.teacher.repository;

import com.csu.etrainingsystem.teacher.entity.TeacherAndGroup;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface TeacherGroupRepository extends JpaRepository<TeacherAndGroup,String > {

    @Query(value="select * from t_group where t_group.tid=? and t_group.t_group_id=? and t_group.del_status=0",nativeQuery = true)
    TeacherAndGroup findTeacherGroupById(String tid, String t_group_id);

    @Query(value="select * from t_group where t_group.del_status=0",nativeQuery = true)
    Iterable<TeacherAndGroup> findAllTeacherGroup();

    /**
     * 如果tid对应的教师被删除,那么对应的分组记录也应该被删除,但是分组依然存在,
     * @param tid
     * @return
     */
    @Query(value="UPDATE t_group SET t_group.del_status=1 WHERE t_group.tid=?",nativeQuery=true)
    @Modifying
    void DeleteTeacherGroupByTidSQL(String tid);
}
