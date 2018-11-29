package com.csu.etrainingsystem.teacher.controller;

import com.csu.etrainingsystem.form.CommonResponseForm;
import com.csu.etrainingsystem.teacher.entity.TeacherGroup;
import com.csu.etrainingsystem.teacher.service.TeacherGroupService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.smartcardio.CommandAPDU;

@RestController
@RequestMapping(value = "/group",method = RequestMethod.POST)
public class GroupController {
    private final TeacherGroupService teacherGroupService;

    @Autowired
    public GroupController(TeacherGroupService teacherGroupService) {
        this.teacherGroupService = teacherGroupService;
    }

    @PostMapping("/addGroup")
    public CommonResponseForm addGroup(TeacherGroup teacherGroup){
        teacherGroupService.addGroup(teacherGroup);
        return CommonResponseForm.of204("添加成功");
    }

    @PostMapping("/updateGroup")
    public CommonResponseForm updateGroup(TeacherGroup teacherGroup){
        teacherGroupService.updateGroup(teacherGroup);
        return CommonResponseForm.of204("修改成功");
    }

    @PostMapping("/delete")
    public CommonResponseForm deleteGroup(String groupName){
        teacherGroupService.deleteGroup(groupName);
        return CommonResponseForm.of204("删除成功");
    }

}
