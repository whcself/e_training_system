package com.csu.etrainingsystem.administrator.repository;

import com.csu.etrainingsystem.administrator.entity.Batch;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;


@Repository
public interface BatchRepository extends JpaRepository<Batch,String> {
/*
  删除一个批次所带来的影响:批次中的学生删除,批次中的学生组被删除,该批次的工序被删除
  在下面的语句中消除该影响
 */
/*
UPDATE batch SET del_status=0 WHERE batch_name='201801'
UPDATE s_group,batch SET s_group.del_status=batch.del_status WHERE s_group.batch_name=batch.batch_name
 */
    //根据batch_name修改学生的状态
    @Query(value="UPDATE student,batch SET student.del_status=batch.del_status WHERE student.batch_name=?",nativeQuery=true)
    @Modifying //需要执行一个更新操作
    void deleteStudentByBatchStatusSQL(String name);
//    @Query(value="UPDATE student,batch SET student.del_status=batch.del_status WHERE student.batch_name=?",nativeQuery=true)
//    @Modifying //需要执行一个更新操作
//    void deleteStudentByBatchStatusSQL(String name);
}
