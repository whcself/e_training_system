package com.csu.etrainingsystem.teacher.repository;

import com.csu.etrainingsystem.procedure.entity.Proced;
import com.csu.etrainingsystem.teacher.entity.TeacherGroup;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface GroupRepository extends JpaRepository<TeacherGroup,String> {

    @Modifying
    @Query(value = "update teacher_group set del_status=1 where t_group_id=?1",nativeQuery = true)
    void deleteGroup(String name);

    @Modifying
    @Query(value = "update teacher_group set t_group_id=?2 where t_group_id=?1 and del_status=0",nativeQuery = true)
    void updateGroup(String old,String newName);

    @Query(value = "select * from teacher_group where del_status=0",nativeQuery = true)
    Iterable<TeacherGroup>getAllGroupName();

    @Query(value = "select pro_name from proced where del_status=0 and t_group_id=?1 and batch_name=\"conn\"",nativeQuery = true)
    Iterable<String>getProcedByGroup(String groupName);
}
