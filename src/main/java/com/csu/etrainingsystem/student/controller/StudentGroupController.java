package com.csu.etrainingsystem.student.controller;


import com.csu.etrainingsystem.form.CommonResponseForm;
import com.csu.etrainingsystem.student.entity.StudentGroup;
import com.csu.etrainingsystem.student.entity.StudentGroupId;
import com.csu.etrainingsystem.student.service.StudentGroupService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(value = "/studentGroup",method = RequestMethod.POST)
public class StudentGroupController {
    private  final StudentGroupService studentGroupService;
    @Autowired
    public StudentGroupController(StudentGroupService studentGroupService) {
        this.studentGroupService = studentGroupService;
    }

    @RequestMapping(value ="/addStudentGroup")
    public CommonResponseForm addStudentGroup(StudentGroup studentGroup){
        this.studentGroupService.addStudentGroup (studentGroup);
        return CommonResponseForm.of204("添加学生分组成功");
    }
    @RequestMapping(value ="/getStudentGroup/{s_group_id}/{batch_name}")
    public CommonResponseForm getStudentGroup(@PathVariable("s_group_id") String s_group_id,@PathVariable("batch_name") String batch_name){
        StudentGroupId studentGroupId=new StudentGroupId (s_group_id,batch_name);
        return CommonResponseForm.of200("获取学生分组成功",this.studentGroupService.getStudentGroup (studentGroupId));
    }
    @RequestMapping(value ="/getAllStudentGroup")
    public CommonResponseForm getAllStudentGroup(){
        return CommonResponseForm.of200("获取所有学生分组成功",this.studentGroupService.getAllStudentGroup ());
    }
    @RequestMapping(value ="/getStudentGroup/{batch_name}")
    public CommonResponseForm getBatchStudentGroup(@PathVariable("batch_name") String batch_name){
        return CommonResponseForm.of200("获取该批次所有学生分组成功",this.studentGroupService.getStudentGroupByBatch(batch_name));
    }
    @RequestMapping(value ="/updateStudentGroup")
    public CommonResponseForm updateStudentGroup(StudentGroup studentGroup){
        this.studentGroupService.updateStudentGroup (studentGroup);
        return CommonResponseForm.of204("更新该批次所有学生分组成功");
    }
    @RequestMapping(value ="/deleteStudentGroup/{s_group_id}/{batch_name}")
    public CommonResponseForm deleteStudentGroup(@RequestParam("s_group_id") String s_group_id, @PathVariable("batch_name") String batch_name){
        StudentGroupId studentGroupId=new StudentGroupId (s_group_id,batch_name);
        this.studentGroupService.deleteStudentGroup (studentGroupId);
        return CommonResponseForm.of204("删除学生分组成功");
    }
}
