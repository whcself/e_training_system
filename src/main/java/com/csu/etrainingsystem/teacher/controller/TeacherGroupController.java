package com.csu.etrainingsystem.teacher.controller;

import com.csu.etrainingsystem.form.CommonResponseForm;
import com.csu.etrainingsystem.teacher.entity.TeacherAndGroup;
import com.csu.etrainingsystem.teacher.entity.TeacherGroupId;
import com.csu.etrainingsystem.teacher.service.TeacherGroupService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 *
 * todo:teachergroup中的东西全部修改成t_group_conn里面的东西
 */
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
     * @param teacherAndGroup
     * @return
     */
    @RequestMapping("/addTeacherGroup")
    public CommonResponseForm addTeacherGroup(TeacherAndGroup teacherAndGroup){
        System.out.println ("新增加教师组:"+ teacherAndGroup);
        this.teacherGroupService.addTGroupConn(teacherAndGroup);
        return CommonResponseForm.of204("添加教师组成功");
    }
    @RequestMapping("/getTeacherGroup/{tid}/{t_group_id}")
    public  CommonResponseForm getTeacherGroup(@PathVariable("tid") String tid,@PathVariable("t_group_id") String t_group_id){


        TeacherGroupId id=new  TeacherGroupId (tid,t_group_id);
        System.out.println (id.getT_group_id ());
        return CommonResponseForm.of200("获取该老师的教师组成功",teacherGroupService.getTGroupConnByGroupIdAndTid (id));
    }
//x-www-form-urlencoded
    @RequestMapping("/getAllTeacherGroup")
    public  CommonResponseForm getAllTeacheGroup(){
        return CommonResponseForm.of200("获取全体教师成组成功",teacherGroupService.getAllTeacherGroup ());
    }

    @RequestMapping("/deleteTeacherGroup/{tid}/{t_group_id}")
    public  CommonResponseForm deleteTeacher(@PathVariable("tid") String tid,@PathVariable("t_group_id") String t_group_id){
        TeacherGroupId id=new TeacherGroupId (t_group_id,tid);
      int index= teacherGroupService.deleteTeacherGroup (id);
      return index==1? CommonResponseForm.of204("删除教师组记录成功成功"):CommonResponseForm.of400("记录不存在");
    }
    @RequestMapping("/updateTeacherGroup")
    public CommonResponseForm updateTeacherGroup(TeacherAndGroup teacherAndGroup){
       this.teacherGroupService.updateTGroupConn (teacherAndGroup);
        return CommonResponseForm.of204("更新教师组记录成功");
    }

    @RequestMapping("/updateTeacherGroup2")
    public CommonResponseForm updateTeacherGroup2(TeacherAndGroup teacherAndGroup,String newGroup){
        teacherGroupService.updateTGroupConn (teacherAndGroup,newGroup);
        return CommonResponseForm.of204("更新教师组记录成功");
    }

    @PostMapping("/getTGroup")
    public CommonResponseForm getTGroup(@RequestParam String tid){
        return CommonResponseForm.of200("查询成功",teacherGroupService.getTGroup(tid));
    }
}
