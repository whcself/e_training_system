package com.csu.etrainingsystem.student.repository;


import com.csu.etrainingsystem.student.entity.SpecialStudent;
import com.csu.etrainingsystem.student.entity.Student;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface SpStudentRepository extends JpaRepository<SpecialStudent,String> {


    //根据学号查找特殊学生
    @Query(value="select * from sp_student where sp_student.sid=? and sp_student.del_status=0",nativeQuery = true)
    Optional<SpecialStudent> findSpStudentBySid(String sid);
    //查找所有特殊学生
    @Query(value="select * from sp_student where  sp_student.del_status=0",nativeQuery = true)
    Iterable<SpecialStudent> findAllSpStudent();
    //根据姓名查找学生
    @Query(value = "select * from sp_student where sp_student.sname=? and sp_student.del_status=0",nativeQuery = true)
    Iterable<SpecialStudent> findSpStudentBySName(String sName);
    @Query(value = "select * from sp_student where del_status=0",nativeQuery = true)
    List<SpecialStudent> findAll();

    @Query(value = "select * from sp_student where template_name=? and del_status=0",nativeQuery = true)
    List<SpecialStudent> findByTemplateName(String templateName);
}
