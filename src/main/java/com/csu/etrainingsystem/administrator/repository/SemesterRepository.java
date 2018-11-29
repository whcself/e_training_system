package com.csu.etrainingsystem.administrator.repository;

import com.csu.etrainingsystem.administrator.entity.Semester;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

public interface SemesterRepository extends JpaRepository<Semester,String> {

    @Modifying
    @Query(value = "update semester set semester_name=?1 where semester_name=?2",nativeQuery = true)
    void updateSemester(String newName,String oldName);

}
