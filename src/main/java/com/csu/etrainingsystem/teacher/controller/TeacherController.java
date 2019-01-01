package com.csu.etrainingsystem.teacher.controller;

import com.csu.etrainingsystem.form.CommonResponseForm;
import com.csu.etrainingsystem.teacher.entity.Teacher;
import com.csu.etrainingsystem.teacher.service.TeacherService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(value = "/teacher",method = RequestMethod.POST)
public class TeacherController {

    private TeacherService teacherService;

    @Autowired
    public TeacherController(TeacherService teacherService) {
        this.teacherService = teacherService;
    }



    @RequestMapping("/addTeacher")
    public CommonResponseForm addTeacher(Teacher teacher,
                                         @RequestParam(required = false) String t_group_id){

        teacherService.addTeacher(teacher,t_group_id);
        return CommonResponseForm.of204("添加教师成功");
    }
    @RequestMapping("/getTeacher/{id}")
    public  CommonResponseForm getTeacherById(@PathVariable("id") String id){
        return CommonResponseForm.of200("获取教师成功",teacherService.getTeacher(id));
    }

    @RequestMapping("/getAllTeacher")
    public  CommonResponseForm getAllTeacher(){
        return CommonResponseForm.of200("获取全体教师成功",teacherService.getAllTeacher());
    }

    @RequestMapping("/deleteTeacher")
    public  CommonResponseForm deleteTeacherById(@RequestBody String[] ids){
        teacherService.deleteTeacher(ids);
        return CommonResponseForm.of204("删除教师成功");
    }
    @RequestMapping("/updateTeacher")
    public CommonResponseForm updateTeacher(@RequestBody Teacher teacher,@RequestBody String t_group_id){
        if (t_group_id.equals ("0"))return CommonResponseForm.of400("请选择教师组");
        teacherService.updateTeacher(teacher,t_group_id);
        return CommonResponseForm.of204("更新教师成功");
    }
}
