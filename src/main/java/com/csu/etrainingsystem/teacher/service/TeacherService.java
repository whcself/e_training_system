package com.csu.etrainingsystem.teacher.service;

import com.csu.etrainingsystem.authority.TeacherAuthority;
import com.csu.etrainingsystem.teacher.entity.Teacher;
import com.csu.etrainingsystem.teacher.repository.TeacherRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;

@Service
public class TeacherService {
    private  final TeacherRepository teacherRepository;

    @Autowired
    public TeacherService(TeacherRepository teacherRepository) { this.teacherRepository = teacherRepository; }

    @Transactional
    public void addTeacher(Teacher teacher){teacherRepository.save(teacher);}

    @Transactional
    public Teacher getTeacherById(String id){return teacherRepository.getOne(id);}

    @Transactional
    public Iterable<Teacher>getAllTeacher(){return teacherRepository.findAll();}

    @Transactional
    public void updateTeacher(Teacher teacher){teacherRepository.saveAndFlush(teacher);}

    @Transactional
    public void deleteTeacherById(String id){
      Teacher teacher=teacherRepository.getOne(id);
      teacher.setDel_status(true);
      teacherRepository.saveAndFlush(teacher);
      /*
      todo:消除删除一个老师所带来的影响
       */
    }

    /**
     *
     *
     * @param tClass             String
     * @param role               String
     * @param material_privilege String 转换成int
     * @param overwork_privilege String 转换成 int
     * @return
     */
    public List<Teacher> findTeachers(String tClass, String role, String  material_privilege, String overwork_privilege) {
        switch (material_privilege) {
            case "物料登记":
                material_privilege = TeacherAuthority.MATERIAL_REGISTER;//1
                break;
            case "物料申购":
                material_privilege = TeacherAuthority.MATERIAL_BUY;//2
                break;
            case "无":
                material_privilege = TeacherAuthority.MATERIAL_NULL;
                break;
            case "all":
                material_privilege = TeacherAuthority.ALL;
                break;
        }
        switch (overwork_privilege) {
            case "加班管理":
                overwork_privilege = TeacherAuthority.OVERWORK_MANAGE;//1
                break;
            case "无":
                overwork_privilege = TeacherAuthority.OVERWORK_NULL;//0
                break;
            case "all":
                overwork_privilege = TeacherAuthority.ALL;
                break;
        }
        if(tClass.equals("all"))tClass=TeacherAuthority.ALL;
        if(role.equals("all"))role=TeacherAuthority.ALL;
        System.out.println(tClass + " " + role + " " + material_privilege + " " + overwork_privilege);
        System.out.println(teacherRepository.findTeacherByTRMO(tClass, role, material_privilege, overwork_privilege));
        return teacherRepository.findTeacherByTRMO(tClass, role, material_privilege, overwork_privilege);
    }
}
