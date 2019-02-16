package com.csu.etrainingsystem.teacher.controller;

import com.csu.etrainingsystem.form.CommonResponseForm;
import com.csu.etrainingsystem.teacher.entity.Teacher;
import com.csu.etrainingsystem.teacher.service.TeacherService;
import com.csu.etrainingsystem.user.entity.User;
import com.csu.etrainingsystem.user.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping(value = "/teacher",method = RequestMethod.POST)
public class TeacherController {

    private TeacherService teacherService;
    private UserService userService;

    @Autowired
    public TeacherController(TeacherService teacherService,UserService userService) {
        this.teacherService = teacherService;
        this.userService=userService;
    }


    /**
     * @apiNote 教师管理-查询教师
     * @param tClass 教师组
     * @param role 角色
     * @return list
     */
    @PostMapping("/findTeachers")
    public CommonResponseForm findTeachers(@RequestParam(required = false) String tClass,
                                           @RequestParam(required = false) String role,
                                           @RequestParam(required = false) String material_privilege,
                                           @RequestParam(required = false) String overwork_privilege) {

        try {
            List<Map<String,String>> teachers = teacherService.findTeachers(tClass, role, material_privilege, overwork_privilege);
            return CommonResponseForm.of200("查询成功", teachers);
        } catch (Exception e) {
            return CommonResponseForm.of400("查询错误");
        }
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
        for (String id : ids) {
            userService.deleteUser (id);
        }
        teacherService.deleteTeacher (ids);
        return CommonResponseForm.of204("删除教师成功");
    }
    @RequestMapping("/updateTeacher")
    public CommonResponseForm updateTeacher(Teacher teacher,@RequestParam(required = false) String t_group_id){
//        System.out.println (teacher.toString ()+"新的教師組是："+t_group_id);
//        if (t_group_id.equals ("0"))return CommonResponseForm.of400("请选择教师组");
        teacherService.updateTeacher(teacher);
        return CommonResponseForm.of204("更新教师成功");
    }

    @PostMapping("/getMaterialAuth")
    public CommonResponseForm getMaterialAuth(){
        return CommonResponseForm.of200("成功",new String[]{"物料登记","物料申购","申购审核","物料采购","报账审核","入库"});
    }

    @PostMapping("/getOverWorkAuth")
    public CommonResponseForm getOverWorkAuth(){
        return CommonResponseForm.of200("成功",new String[]{});
    }
}
