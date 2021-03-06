
package com.csu.etrainingsystem.user.controller;//package com.csu-engineer-train-front.etrainingsystem.controller;


import com.csu.etrainingsystem.administrator.service.AdminService;
import com.csu.etrainingsystem.form.CommonResponseForm;

import com.csu.etrainingsystem.user.entity.User;

import com.csu.etrainingsystem.user.form.UserPwdForm;
import com.csu.etrainingsystem.user.service.UserService;

import com.csu.etrainingsystem.student.service.StudentService;
import com.csu.etrainingsystem.teacher.service.TeacherService;
import com.sun.javafx.collections.MappingChange;
import org.apache.shiro.crypto.hash.SimpleHash;
import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpSession;

@RestController
@RequestMapping(value="/user",method = RequestMethod.POST)
public class UserController {
    private final UserService userService;
    private final AdminService adminService;
    private final StudentService studentService;
    private final TeacherService teacherService;
    @Autowired
    public UserController(UserService userService, AdminService adminService, StudentService studentService, TeacherService teacherService) {
        this.userService = userService;
        this.adminService = adminService;
        this.studentService = studentService;
        this.teacherService = teacherService;
    }

    @PostMapping("/addUser")
    public CommonResponseForm addUser(@RequestBody User Users[]){
        for (User user : Users) {
            userService.addUser (user);
        }
        return CommonResponseForm.of204 ("添加用户成功");
    }
    @PostMapping("/getUser/{id}")
    public CommonResponseForm getUser(@PathVariable("id") String id){
        User user=userService.getUser(id);
        return CommonResponseForm.of200("获取成功",user);
    }
    @PostMapping("/deleteUser/{id}")
    public CommonResponseForm deleteUser(@PathVariable("id") String id){
        userService.deleteUser(id);
        return CommonResponseForm.of204("删除成功");
    }

    @PostMapping("/updateUser")
    public CommonResponseForm updateUser(User user){
        userService.updateUser(user);
        return CommonResponseForm.of204("更新成功");
    }
    @PostMapping("/change")
    public CommonResponseForm changePassword(HttpSession session,String newPassword){
        userService.changePassword(session,newPassword);
        return CommonResponseForm.of204("修改密码成功");
    }

    /**
     * @apiNote 根据ids，重置密码
     * @param forms 包括id和密码
     */
    @PostMapping("/changePwd")
    public CommonResponseForm changePwd(@RequestBody UserPwdForm[] forms){
        return userService.changePwd(forms);
    }

    /**
     * @apiNote 修改密码
     * @param session session
     * @param old 旧密码
     * @param pwd 新密码
     */
    @PostMapping("/changePwd2")
    public CommonResponseForm changePwd2(HttpSession session,String old,String pwd){
        return userService.cPassword(session,old,pwd);
    }

    /**
     * @apiNote 我的信息
     * @param session session
     */
    @PostMapping("/getInfo")
    public CommonResponseForm getInfo(HttpSession session){
        return userService.getInfo(session);

    }


}