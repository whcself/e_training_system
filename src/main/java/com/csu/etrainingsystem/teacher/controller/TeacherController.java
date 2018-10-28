package com.csu.etrainingsystem.teacher.controller;

import com.csu.etrainingsystem.form.CommonResponseForm;
import com.csu.etrainingsystem.teacher.entity.Teacher;
import com.csu.etrainingsystem.teacher.service.TeacherService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import sun.plugin2.message.Message;

@RestController
@RequestMapping(value = "/teacher",method = RequestMethod.POST)
public class TeacherController {
    @Autowired
    private TeacherService teacherService;
    @RequestMapping("/addTeacher")
    public CommonResponseForm addTeacher(Teacher teacher){
        teacherService.addTeacher(teacher);
        return CommonResponseForm.of204("添加教师成功");
    }
    @RequestMapping("/getTeacher/{id}")
    public  CommonResponseForm getTeacherByid(@PathVariable("id") String id){
        return CommonResponseForm.of200("获取教师成功",teacherService.getTeacherById(id));
    }

    @RequestMapping("/getAllTeacher")
    public  CommonResponseForm getAllTeacher(){
        return CommonResponseForm.of200("获取全体教师成功",teacherService.getAllTeacher());
    }

    @RequestMapping("/deleteTeacher/{id}")
    public  CommonResponseForm deleteTeacherById(@PathVariable("id") String id){
        teacherService.deleteTeacherById(id);
        return CommonResponseForm.of204("获取全体教师成功");
    }
    @RequestMapping("/updateTeacher")
    public CommonResponseForm updateTeacher(Teacher teacher){
        teacherService.updateTeacher(teacher);
        return CommonResponseForm.of204("更新教师成功");
    }
}
