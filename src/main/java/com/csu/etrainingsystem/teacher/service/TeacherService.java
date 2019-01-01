package com.csu.etrainingsystem.teacher.service;

import com.csu.etrainingsystem.authority.TeacherAuthority;
import com.csu.etrainingsystem.teacher.entity.Teacher;
import com.csu.etrainingsystem.teacher.entity.TeacherAndGroup;
import com.csu.etrainingsystem.teacher.entity.TeacherGroupId;
import com.csu.etrainingsystem.teacher.repository.T_Group_ConnRepository;
import com.csu.etrainingsystem.teacher.repository.TeacherRepository;
import com.csu.etrainingsystem.user.entity.User;
import com.csu.etrainingsystem.user.repository.UserRepository;
import com.csu.etrainingsystem.user.service.UserService;
import com.csu.etrainingsystem.util.EncryptUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;
import java.util.Map;

@Service
public class TeacherService {
    private  final TeacherRepository teacherRepository;
    private  final TeacherGroupService teacherGroupService;
    private final T_Group_ConnRepository tGroupConnRepository;
    private final UserRepository userRepository;
    @Autowired
    public TeacherService(TeacherRepository teacherRepository, TeacherGroupService teacherGroupService, T_Group_ConnRepository tGroupConnRepository,UserRepository userRepository) {
        this.teacherRepository = teacherRepository;
        this.teacherGroupService = teacherGroupService;
        this.tGroupConnRepository = tGroupConnRepository;
        this.userRepository=userRepository;
    }

    @Transactional
    public void addTeacher(Teacher teacher,String t_group_id ){
        //添加教师,同时指定分组
        teacherRepository.save(teacher);
        if(t_group_id!=null){
        TeacherAndGroup teacherAndGroup=new TeacherAndGroup ();
        TeacherGroupId teacherGroupId=new TeacherGroupId ();
        teacherGroupId.setTid (teacher.getTid ());
        teacherGroupId.setT_group_id (t_group_id);
        teacherAndGroup.setTeacherGroupId (teacherGroupId);
        User user=new User();
        user.setAccount (teacher.getTid ());
        user.setPwd (EncryptUtil.encrypt ("123456"));
        user.setRole ("teacher");
       this.userRepository.save (user);
        tGroupConnRepository.save (teacherAndGroup);

        }
    }

    @Transactional
    public Teacher getTeacher(String id){return teacherRepository.findTeacherByTid(id);}

    @Transactional
    public Iterable<Teacher>getAllTeacher(){
        return teacherRepository.findAllTeacher();}

    @Transactional
    public void updateTeacher(Teacher teacher,String t_group_id){
        tGroupConnRepository.modifyTeacherGroupByTidSQL (t_group_id,teacher.getTid ());
        teacherRepository.saveAndFlush(teacher);
    }

    @Transactional
    public void deleteTeacher(String[] tids){
        for (String tid : tids) {
            Teacher teacher=getTeacher(tid);
            teacher.setDel_status(true);
            updateTeacher(teacher,null);
            //删除这个老师在teachergroup中的记录
            this.tGroupConnRepository.DeleteTeacherGroupByTidSQL (tid);
        }

      /*
      todo:消除删除一个老师所带来的影响:
      所在教师组的记录需要被删除,实验表的提交老师需要被删除?暂定不删除,好追责

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
    public List<Map<String, String>> findTeachers(String tClass, String role, String  material_privilege, String overwork_privilege) {
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
        if (role.equals("all")) role = TeacherAuthority.ALL;
        if (tClass.equals("all")) {
            return teacherRepository.findTeacherByRMO(role,material_privilege,overwork_privilege);
        } else {
            System.out.println(tClass + " " + role + " " + material_privilege + " " + overwork_privilege);
            return teacherRepository.findTeacherByTRMO(tClass, role, material_privilege, overwork_privilege);
        }
    }

//    public List<Teacher> findTeachersByGroup(String groupName){
//        teacherRepository.
//    }
}
