package com.csu.etrainingsystem.student.repository;

import com.csu.etrainingsystem.student.entity.StudentGroup;
import com.csu.etrainingsystem.student.entity.StudentGroupId;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface StudentGroupRepository extends JpaRepository<StudentGroup,StudentGroupId> {
    /**
     * 跨批次查询所有的同名组
     * @param s_group_id
     * @return
     */
    @Query(value="select * from s_group where s_group.s_group_id=? and s_group.del_status=0",nativeQuery = true)
    Optional<StudentGroup> findStudentGroupByS_group_id(String s_group_id);

    /**
     * 查询指定批次的指定组
     * @param s_group_id
     * @param batch_name
     * @return
     */
    @Query(value="select * from s_group where s_group.s_group_id=? and s_group.batch_name=? and s_group.del_status=0",nativeQuery = true)
    Optional<StudentGroup> findStudentGroupByIdAndBatch(String s_group_id,String batch_name);
    /**
     * 查询所有组
     */
    @Query(value="select * from s_group where  s_group.del_status=0",nativeQuery = true)
    Iterable<StudentGroup> findAllStudentGroup();

    /**
     * 查询该批次所有分组
     * @param batch_name
     * @return
     */
    @Query(value="select * from s_group where s_group.batch_name=? and s_group.del_status=0",nativeQuery = true)
    Iterable<StudentGroup> findStudentGroupByBatch(String batch_name);

}
