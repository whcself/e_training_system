package com.csu.etrainingsystem.teacher.controller;

import com.csu.etrainingsystem.form.CommonResponseForm;
import com.csu.etrainingsystem.teacher.entity.TeacherGroup;
import com.csu.etrainingsystem.teacher.entity.TeacherGroupId;
import com.csu.etrainingsystem.teacher.service.TeacherGroupService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(value = "/teacherGroup",method = RequestMethod.POST)
public class TeacherGroupController {
    private TeacherGroupService teacherGroupService;

    @Autowired
    public TeacherGroupController(TeacherGroupService teacherGroupService) {
        this.teacherGroupService = teacherGroupService;
    }


    /**
     *   复合主键添加方式 TeacherGroupId.t_group_id
     *                  TeacherGroupId.tid
     * @param teacherGroup
     * @return
     */
    @RequestMapping("/addTeacherGroup")
    public CommonResponseForm addTeacherGroup(TeacherGroup teacherGroup){
        System.out.println ("新增加教师组:"+teacherGroup);
        this.teacherGroupService.addTeacherGroup (teacherGroup);
        return CommonResponseForm.of204("添加教师组成功");
    }
    @RequestMapping("/getTeacherGroup/{tid}/{t_group_id}")
    public  CommonResponseForm getTeacherGroup(@PathVariable("tid") String tid,@PathVariable("t_group_id") String t_group_id){


        TeacherGroupId id=new  TeacherGroupId (tid,t_group_id);
        System.out.println (id.getT_group_id ());
        return CommonResponseForm.of200("获取该老师的教师组成功",teacherGroupService.getTeacherGroup (id));
    }
//x-www-form-urlencoded
    @RequestMapping("/getAllTeacherGroup")
    public  CommonResponseForm getAllTeacheGroup(){
        return CommonResponseForm.of200("获取全体教师成组成功",teacherGroupService.getAllTeacherGroup ());
    }

    @RequestMapping("/deleteTeacherGroup/{tid}/{t_group_id}")
    public  CommonResponseForm deleteTeacher(@PathVariable("tid") String tid,@PathVariable("t_group_id") String t_group_id){
        TeacherGroupId id=new TeacherGroupId (tid,t_group_id);
       teacherGroupService.deleteTeacherGroup (id);
        return CommonResponseForm.of204("删除教师组记录成功成功");
    }
    @RequestMapping("/updateTeacherGroup")
    public CommonResponseForm updateTeacherGroup(TeacherGroup teacherGroup){
       this.teacherGroupService.updateTeacherGroup (teacherGroup);
        return CommonResponseForm.of204("更新教师组记录成功");
    }
}
