package com.csu.etrainingsystem.teacher.controller;

import com.csu.etrainingsystem.form.CommonResponseForm;
import com.csu.etrainingsystem.teacher.entity.TeacherGroup;
import com.csu.etrainingsystem.teacher.service.TeacherGroupService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(value = "/group",method = RequestMethod.POST)
public class GroupController {
    private final TeacherGroupService teacherGroupService;

    @Autowired
    public GroupController(TeacherGroupService teacherGroupService) {
        this.teacherGroupService = teacherGroupService;
    }

    @PostMapping("/addGroup")
    public CommonResponseForm addGroup(@RequestBody TeacherGroup teacherGroup){
        teacherGroupService.addGroup(teacherGroup);
        return CommonResponseForm.of204("添加成功");
    }

    @PostMapping("/updateGroup")
    public CommonResponseForm updateGroup(@RequestParam String old,
                                          @RequestParam String newName){
        teacherGroupService.updateGroup(old,newName);
        return CommonResponseForm.of204("修改成功");
    }

    @PostMapping("/delete")
    public CommonResponseForm deleteGroup(@RequestParam String groupName){
        teacherGroupService.deleteGroup(groupName);
        return CommonResponseForm.of204("删除成功");
    }

    @PostMapping("/getAllGroup")
    public CommonResponseForm getAllGroupName(){
        return CommonResponseForm.of200("获取成功",teacherGroupService.getAllGroupName());
    }

    @PostMapping("/getProcedByGroup")
    public CommonResponseForm getProcedByGroup(@RequestParam String groupName){
        return CommonResponseForm.of200("获取成功",teacherGroupService.getProcedByGroup(groupName));
    }

}
