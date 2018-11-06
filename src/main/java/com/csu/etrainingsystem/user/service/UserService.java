package com.csu.etrainingsystem.user.service;

import com.csu.etrainingsystem.user.entity.User;
import com.csu.etrainingsystem.administrator.entity.Admin;
import com.csu.etrainingsystem.administrator.service.AdminService;
import com.csu.etrainingsystem.student.entity.Student;
import com.csu.etrainingsystem.student.service.StudentService;
import com.csu.etrainingsystem.teacher.entity.Teacher;
import com.csu.etrainingsystem.teacher.service.TeacherService;
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

    public void changePassword(HttpSession session, String newPassword) {
        User user = (User) session.getAttribute ("user");
        user.setPwd (newPassword);
        userRepository.saveAndFlush (user);
    }

    @Transactional
    public User getUser(String account) {

        return userRepository.findUserByAccount (account);
    }

    @Transactional
    public void addUser(User user) {
        this.userRepository.save (user);
    }

    @Transactional
    public void updateUser(User user) {
        this.userRepository.saveAndFlush (user);
    }

    /**
     * 删除一个用户会同时删除一个管理员或者老师或者学生
     *
     * @param id
     */
    @Transactional
    public void deleteUser(String id) {
        User user = getUser (id);
        //如果已经删除或者用户不存在就返回
        if (user == null) return;
        user.setDel_status (true);
        updateUser (user);
        if (user.getRole ().equals ("admin")) {
            Admin admin = this.adminService.getAdminById (id);
            if (admin != null) {
                this.adminService.deleteAdmin (id);
            }
        }
        if (user.getRole ().equals ("teacher")) {
            Teacher teacher = this.teacherService.getTeacher (id);
            if (teacher != null) {
                this.teacherService.deleteTeacher (id);
            }
        }
        if (user.getRole ().equals ("student")) {
            Student student = this.studentService.getStudentById (id);
            if (student != null) {
                this.studentService.deleteById (id);
            }
        }
    }
}
