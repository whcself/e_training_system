package com.csu.etrainingsystem.user.service;

import com.csu.etrainingsystem.form.CommonResponseForm;
import com.csu.etrainingsystem.teacher.repository.GroupRepository;
import com.csu.etrainingsystem.teacher.repository.TeacherRepository;
import com.csu.etrainingsystem.user.entity.User;
import com.csu.etrainingsystem.administrator.entity.Admin;
import com.csu.etrainingsystem.administrator.service.AdminService;
import com.csu.etrainingsystem.student.entity.Student;
import com.csu.etrainingsystem.student.service.StudentService;
import com.csu.etrainingsystem.teacher.entity.Teacher;
import com.csu.etrainingsystem.teacher.service.TeacherService;
import com.csu.etrainingsystem.user.entity.UserRole;
import com.csu.etrainingsystem.user.form.UserPwdForm;
import com.csu.etrainingsystem.user.repository.UserRepository;
import org.apache.tomcat.util.buf.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpSession;
import javax.transaction.Transactional;
import java.util.HashMap;
import java.util.List;

@Service
public class UserService {
    private final UserRepository userRepository;
    private final AdminService adminService;
    private final StudentService studentService;
    private final TeacherService teacherService;
    private final TeacherRepository teacherRepository;
    private final GroupRepository groupRepository;


    @Autowired
    public UserService(GroupRepository groupRepository,TeacherRepository teacherRepository,UserRepository userRepository, AdminService adminService, StudentService studentService, TeacherService teacherService) {
        this.groupRepository=groupRepository;
        this.teacherRepository=teacherRepository;
        this.userRepository = userRepository;
        this.adminService = adminService;
        this.studentService = studentService;

        this.teacherService = teacherService;
    }

    @Transactional
    public void changePassword(HttpSession session, String newPassword) {
        User user = (User) session.getAttribute("user");
        user.setPwd(newPassword);
        userRepository.saveAndFlush(user);
    }

    @Transactional
    public CommonResponseForm cPassword(HttpSession session, String old, String pwd) {

        User user = UserRole.getUser(session);
        if (user == null) {
            return CommonResponseForm.of400("用户未登录");
        } else if (old.equals(user.getPwd())) {
            user.setPwd(pwd);
            userRepository.save(user);
            return CommonResponseForm.of204("修改成功");
        } else {
            return CommonResponseForm.of400("旧密码错误");
        }

    }

    public CommonResponseForm getInfo(HttpSession session){
        HashMap<String,String> map=new HashMap<>();
        User user=UserRole.getUser(session);
        String role=user.getRole();
        String id=user.getAccount();
        map.put("id",id);

        if(role.equals("teacher")||role.equals("admin")){
            Teacher teacher=teacherRepository.findTeacherByTid(id);
            List<String> tGroups=groupRepository.getAllGroupByTId(id);
            String StrGroups=StringUtils.join(tGroups, ',');
            int material=teacher.getMaterial_privilege();
            int overwork=teacher.getOvertime_privilege();
            String role2=teacher.getRole();
            map.put("加班权限", String.valueOf(overwork));
            map.put("物料权限",String .valueOf(material));
            map.put("角色",role2);
            map.put("姓名",teacher.getTname());
            map.put("教师组",StrGroups);
        }else if(role.equals("student")){
            Student student=studentService.getStudentById(id);
            map.put("班级",student.getClazz());
            map.put("姓名",student.getSname());
            map.put("组号",student.getS_group_id());
            map.put("批次",student.getBatch_name());
        }
        return CommonResponseForm.of200("查询成功",map);

    }

    @Transactional
    public CommonResponseForm changePwd(UserPwdForm[] forms) {

        try {
            for (UserPwdForm form : forms) {
                User user = userRepository.findUserByAccount(form.getId());
                user.setPwd(form.getPwd());
                userRepository.save(user);
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
        return CommonResponseForm.of204("修改成功 共修改：" + forms.length + "条");

    }

    @Transactional
    public User getUser(String account) {

        return userRepository.findUserByAccount(account);
    }

    @Transactional
    public void addUser(User user) {
        this.userRepository.save(user);
    }

    @Transactional
    public void updateUser(User user) {
        this.userRepository.saveAndFlush(user);
    }

    /**
     * 删除一个用户会同时删除一个管理员或者老师或者学生
     *
     * @param id
     */
    @Transactional
    public void deleteUser(String id) {
        User user = getUser(id);
        //如果已经删除或者用户不存在就返回
        if (user == null) return;
        user.setDel_status(true);
        updateUser(user);
        if (user.getRole().equals("admin")) {
            Admin admin = this.adminService.getAdminById(id);
            if (admin != null) {
                this.adminService.deleteAdmin(id);
            }
        }
        if (user.getRole().equals("teacher")) {
            Teacher teacher = this.teacherService.getTeacher(id);
            if (teacher != null) {
                String[] ids = new String[1];
                ids[0] = id;
                this.teacherService.deleteTeacher(ids);
            }
        }
        if (user.getRole().equals("student")) {
            Student student = this.studentService.getStudentById(id);
            if (student != null) {
                this.studentService.deleteById(id);
            }
        }
    }
}
