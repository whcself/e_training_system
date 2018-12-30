package com.csu.etrainingsystem.user.service;

import com.csu.etrainingsystem.form.CommonResponseForm;
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
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpSession;
import javax.transaction.Transactional;

@Service
public class UserService {
    private final UserRepository userRepository;
    private final AdminService adminService;
    private final StudentService studentService;
    private final TeacherService teacherService;

    @Autowired
    public UserService(UserRepository userRepository, AdminService adminService, StudentService studentService, TeacherService teacherService) {
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
