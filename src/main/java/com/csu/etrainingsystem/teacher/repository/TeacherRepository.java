package com.csu.etrainingsystem.teacher.repository;

import com.csu.etrainingsystem.teacher.entity.Teacher;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface TeacherRepository extends JpaRepository<Teacher,String> {

    /**
     *
     * @param tClass 教师组名称，车削组，管理教师组等
     * @param role 教师角色： 管理员，实训教师
     * @param material 物料权限：
     * @param overwork 加班管理权限： 加班管理，无
     * @return 查询所得的教师列表
     */

    @Query(value = "select * from teacher  where t_group_id=?1 and role=?2 and material_privilege=?3 and overtime=?4",nativeQuery = true)
    public List<Teacher> findTeacherByTRMO(String tClass, String role, String material, String overwork);


}
