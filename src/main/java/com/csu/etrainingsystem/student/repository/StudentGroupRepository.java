package com.csu.etrainingsystem.student.repository;

import com.csu.etrainingsystem.teacher.entity.Teacher;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface StudentGroupRepository extends JpaRepository<Teacher,String> {
}
