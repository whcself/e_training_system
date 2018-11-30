package com.csu.etrainingsystem.teacher.repository;

import com.csu.etrainingsystem.teacher.entity.Teacher;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;


@Repository
public interface TeacherRepository extends JpaRepository<Teacher,String> {

    /**
     * TRMO 缩写
     * 管理员端教师管理：查询
     * 使用了嵌套查询
     * @param tClass 教师组名称，车削组，管理教师组等
     * @param role 教师角色： 管理员，实训教师
     * @param material_privilege 物料权限：
     * @param overtime_privilege 加班管理权限： 加班管理，无
     *
     * @return 查询所得的教师列表
     *
     * 如果指定了教师组就调用这个，没有指定教师组的话会有多个教师组
     */
    @Query(value = "select * from teacher where role like ?2 and material_privilege like ?3 " +
            "and overtime_privilege like ?4 and del_status=0 and  tid  in(" +
            "select tid from t_group_conn where t_group_id like ?1) ",nativeQuery = true)
    List<Teacher> findTeacherByTRMO(String tClass, String role, String  material_privilege, String  overtime_privilege);

//    @Query(value = )

    @Query(value = "select * from teacher where tname=? and del_status=0",nativeQuery = true)
    Teacher findTeacherByTName(String tName);

    @Query(value="select * from teacher where teacher.tid=? and teacher.del_status=0",nativeQuery = true)
    Teacher findTeacherByTid(String tid);


    @Query(value="select * from teacher where teacher.del_status=0",nativeQuery = true)
    Iterable<Teacher> findAllTeacher();



}
