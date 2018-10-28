package com.csu.etrainingsystem.teacher.repository;

import com.csu.etrainingsystem.teacher.entity.Teacher;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TeacherRepository extends JpaRepository<Teacher,String> {
}
