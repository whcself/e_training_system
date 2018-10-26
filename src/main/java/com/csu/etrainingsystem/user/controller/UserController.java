//package com.csu.etrainingsystem.user.controller;
//
//import com.csu.etrainingsystem.administrator.entity.Admin;
//import com.csu.etrainingsystem.administrator.service.AdminService;
//import com.csu.etrainingsystem.user.entity.User;
//import com.csu.etrainingsystem.form.CommonResponseForm;
//import com.csu.etrainingsystem.user.service.UserService;
//import com.csu.etrainingsystem.student.service.StudentService;
//import com.csu.etrainingsystem.teacher.service.TeacherService;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.web.bind.annotation.GetMapping;
//import org.springframework.web.bind.annotation.PathVariable;
//import org.springframework.web.bind.annotation.PostMapping;
//import org.springframework.web.bind.annotation.RestController;
//
//import javax.servlet.http.HttpSession;
//
//@RestController("/user")
//public class UserController {
//    private final UserService userService;
//    private final AdminService adminService;
//    private final StudentService studentService;
//    private final TeacherService teacherService;
//    @Autowired
//    public UserController(UserService userService, AdminService adminService, StudentService studentService, TeacherService teacherService) {
//        this.userService = userService;
//        this.adminService = adminService;
//        this.studentService = studentService;
//        this.teacherService = teacherService;
//    }
//
//
//    @GetMapping("/{id}")
//    public CommonResponseForm getUserById(@PathVariable String id){
//        User user=userService.getUser(id);
//        return CommonResponseForm.of200("获取成功",user);
//    }
//    @PostMapping("/change")
//    public CommonResponseForm changePassword(HttpSession session,String newPassword){
//        userService.changePassword(session,newPassword);
//        return CommonResponseForm.of204("修改密码成功");
//    }
//
//
//}
