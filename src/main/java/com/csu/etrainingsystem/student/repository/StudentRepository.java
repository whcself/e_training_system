package com.csu.etrainingsystem.student.repository;

import com.csu.etrainingsystem.student.entity.Student ;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface StudentRepository extends JpaRepository<Student,String> {
    /*
      删除一个学生的影响:加班申请表记录删除,成绩表删除,目前还没有
     */
}
