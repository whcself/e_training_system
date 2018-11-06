package com.csu.etrainingsystem.student.repository;

import com.csu.etrainingsystem.student.entity.Student ;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Optional;

/**
 * 由于删除一个学生表记录同时要级联删除成绩表...所以只能在service层中实现
 */
@Repository
public interface StudentRepository extends JpaRepository<Student,String> {
    /*
      删除一个学生的影响:加班申请表记录删除,成绩表删除,目前还没有

     */
    @Query(value="select * from student where student.sid=? and student.del_status=0",nativeQuery = true)
    Optional<Student> findStudentBySid(String sid);
    @Query(value="select * from student where  student.del_status=0",nativeQuery = true)
    Iterable<Student> findAllStudent();

    //根据批次/分组挑选出来,然后再删除
    @Query(value="select * from student where student.s_group_id=? and student.batch_name=? and student.del_status=0",nativeQuery = true)
    Iterable<Student> findStudentByS_group_idAndBatch(String s_group_id,String batch_name);


    //根据批次挑选出来,然后再删除
    @Query(value="select * from student where student.batch_name=? and student.del_status=0",nativeQuery = true)
    Iterable<Student> findStudentByBatch_name(String batch_name);

}

