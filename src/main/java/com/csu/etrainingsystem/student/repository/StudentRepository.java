package com.csu.etrainingsystem.student.repository;

import com.csu.etrainingsystem.student.entity.Student;
import org.springframework.data.jpa.repository.JpaRepository;

public interface StudentRepository extends JpaRepository<Student,String> {
}
