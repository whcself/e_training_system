package com.csu.etrainingsystem.teacher.repository;

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

}
