package com.csu.etrainingsystem.student.controller;


import com.csu.etrainingsystem.form.CommonResponseForm;
import com.csu.etrainingsystem.score.service.ScoreService;
import com.csu.etrainingsystem.student.entity.SpecialStudent;
import com.csu.etrainingsystem.student.entity.Student;
import com.csu.etrainingsystem.student.form.SpStudentInfoForm;
import com.csu.etrainingsystem.student.form.StudentInfoForm;
import com.csu.etrainingsystem.student.service.StudentService;
import com.csu.etrainingsystem.user.entity.User;
import com.csu.etrainingsystem.user.service.UserService;
import io.swagger.annotations.ApiOperation;
//import org.nutz.mvc.adaptor.WhaleAdaptor;
//import org.nutz.mvc.annotation.AdaptBy;
//import org.nutz.mvc.annotation.Ok;
//import org.nutz.mvc.annotation.Param;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping(value = "/student", method = RequestMethod.POST)
public class StudentController {

    private final StudentService studentService;
    private final ScoreService scoreService;
    private final UserService userService;

    @Autowired
    public StudentController(StudentService studentService, ScoreService scoreService, UserService userService) {
        this.studentService = studentService;
        this.scoreService = scoreService;
        this.userService = userService;
    }

    /**
     * 导入一个学生就应该自动添加一个成绩表,该功能
     *
     * @param student
     * @return
     */
    @RequestMapping(value = "/addStudent")
    public CommonResponseForm addStudent(Student student) {
        studentService.addStudent (student);
        //Score score = new Score();
        //todo:在service层添加学生的同时需要添加他对应的成绩表
        // this.scoreService.addScore(score);
        return CommonResponseForm.of204 ("学生添加增加成功");
    }

    @RequestMapping(value = "/addSpStudent")
    public CommonResponseForm addSpStudent(String sid, String template_name) {
        //如果学生表里面存在这个学生,就先将其删除,然后再添加到特殊学深表
        //查询该学生的批次以及所在组的所有实验;然后将实验的时间和工序名称赋值到新的sp_score里面
        //应该配置出现异常自动回滚
        Student student = studentService.getStudentById (sid);
        if (student == null) return CommonResponseForm.of400 ("特殊学生添加失败,不存在该学生");
        //将普通学生转换成特殊学生
        studentService.addSpStudent (student, template_name);
        studentService.deleteById (student.getSid ());
        User user = this.userService.getUser (student.getSid ());
        user.setRole ("spStudent");
        this.userService.updateUser (user);
        //Score score = new Score();需要一个特殊成绩表来实现
        //todo:在service层添加特殊学生的同时需要添加他对应的成绩表;已经安排
        //this.scoreService.addScore(score);
        return CommonResponseForm.of204 ("特殊学生添加增加成功");
    }

    @RequestMapping(value = "/updateStudent")
    public CommonResponseForm updateStudent(@RequestBody Student student) {

        System.out.println (student);
        studentService.updateStudent (student);
        return CommonResponseForm.of204 ("学生更新成功");
    }

    @RequestMapping(value = "/updateSpStudent")
    public CommonResponseForm updateSpStudent(SpecialStudent student) {
        studentService.updateSpStudent (student);
        return CommonResponseForm.of204 ("特殊学生更新成功");
    }

    @RequestMapping(value = "/getStudent/{id}")
    public CommonResponseForm getStudentById(@PathVariable("id") String id) {

        Student student = studentService.getStudentById (id);
        StudentInfoForm studentInfoForm = new StudentInfoForm (student.getS_group_id (), student.getBatch_name (), student.getClazz (), student.getSname (), student.getSid ());

        return CommonResponseForm.of200 ("获取学生成功", studentInfoForm);
    }

    @ApiOperation("获取特殊学生,返回数据中sid为数据的唯一标识")
    @RequestMapping(value = "/getSpStudentById")
    public CommonResponseForm getSpStudentById(String id) {
        SpecialStudent student = studentService.findSpStudentById (id);
        SpStudentInfoForm spStudentInfoForm = new SpStudentInfoForm (student.getSid (), student.getSname (), student.getClazz (), student.getTemplate_name ());

        return CommonResponseForm.of200 ("获取特殊学生成功", spStudentInfoForm);
    }

    /**
     * -ScJn
     * @apiNote 根据模板查询特殊学生
     * @param templateName
     * @return
     */
    @RequestMapping("/getSpStudentByTemplate")
    public CommonResponseForm getSpStudentByTemplate(@RequestParam String templateName){
        ArrayList<SpecialStudent> students= (ArrayList<SpecialStudent>) studentService.findSpStudentsByTemplate(templateName);
        return CommonResponseForm.of200("共"+students.size()+"条记录",students);
    }

    @RequestMapping(value = "/getAllStudent")
    public CommonResponseForm getAllStudent() {
        Iterable<Student> students = studentService.getAllStudent ();
        List<StudentInfoForm> studentInfoForms = new ArrayList<StudentInfoForm> ();
        for (Student student : students) {
            studentInfoForms.add (new StudentInfoForm (student.getS_group_id (), student.getBatch_name (), student.getClazz (), student.getSname (), student.getSid ()));
        }
        return CommonResponseForm.of200 ("获取全部学生成功", studentInfoForms);
    }

    @ApiOperation("获取所有的特殊学生,返回数据中每一條的sid为該数据的唯一标识")
    @RequestMapping(value = "/getAllSpStudent")
    public CommonResponseForm getAllSpStudent() {
        Iterable<SpecialStudent> specialStudents = studentService.findAllSpStudent ();
        List<SpStudentInfoForm> studentInfoForms = new ArrayList<SpStudentInfoForm> ();

        for (SpecialStudent student : specialStudents) {
            studentInfoForms.add (new SpStudentInfoForm (student.getSid (), student.getSname (), student.getClazz (), student.getTemplate_name ()));
        }

        return CommonResponseForm.of200 ("获取全部特殊学生成功", studentInfoForms);
    }

    @ApiOperation("根据传递过来的id数组删除学生")
    // @Ok("json")
    // @AdaptBy(type=WhaleAdaptor.class)
    @RequestMapping(value = "/deleteStudent")
    public CommonResponseForm deleteStudentById(@RequestBody String[] ids) {
        for (String id : ids) {
            this.userService.deleteUser (id);
            this.studentService.deleteById (id);
        }
        return CommonResponseForm.of204 ("删除学生成功");
    }

    @ApiOperation("根据传递过来的id数组删除特殊学生")
    @RequestMapping(value = "/deleteSpStudentById")
    public CommonResponseForm deleteSpStudentById(@RequestBody String[] ids) {
        for (String id : ids) {
            this.studentService.deleteSpStudentById (id);
            // this.studentService.deleteById (id);
        }
        return CommonResponseForm.of204 ("删除特殊学生成功");
    }


    @RequestMapping("/getStudentByBatchName")
    public CommonResponseForm getStudentByBatchName(@RequestParam String batchName) {
        return CommonResponseForm.of200 ("查询成功", studentService.findStudentByBatchName (batchName));
    }

    /**
     * -ScJn
     *
     * @apiNote 查询学生列表
     */
    @RequestMapping("/getStudent")
    public CommonResponseForm getStudent(@RequestParam(required = false) String batch_name,
                                         @RequestParam(required = false) String s_group_id) {
        return CommonResponseForm.of200 ("查询成功", studentService.findStudentByBatchNameAndSGroup (batch_name, s_group_id));
    }

    /**
     * -ScJn
     *
     * @apiNote 教师端-查询学生分组
     */
    @RequestMapping("/getStudent2")
    public CommonResponseForm getStudent2(@RequestBody StudentInfoForm form) {
        return studentService.findStudentByBatchNameAndSGroupOrNameAndId (form.getBatch_name (), form.getS_group_id (), form.getSid (), form.getSname ());
    }

    /**
     * @apiNote 我的信息
     * 根据session中的id得到自己的信息，也可通过id，name来查得信息，用于成绩列表先获取到单个学生的batch_name
     * -ScJn
     */
    @RequestMapping("/getMyInfo")
    public CommonResponseForm getMyInfo(@RequestParam(required = false) String sid,
                                        @RequestParam(required = false) String name,
                                        HttpSession session) {

        if (sid != null) {
            return CommonResponseForm.of200 ("查询成功", studentService.getStudentById (sid));
        } else if (name != null) {
            return CommonResponseForm.of200 ("查询成功", studentService.getStudentByName (name));
        } else {
            String sid2 = (String) session.getAttribute ("sid");
            return CommonResponseForm.of200 ("查询成功", studentService.getStudentById (sid2));

        }

    }

    /**
     * @apiNote 更新学生组
     * -ScJn
     */
    @RequestMapping("/updateSGroup")
    public CommonResponseForm updateSGroup(@RequestParam String sid,
                                           @RequestParam String s_group_id) {
        return studentService.updateSGroup (sid, s_group_id);
    }

    /**
     * @apiNote 下载学生列表
     * -ScJn
     */
    @RequestMapping("/downloadStudentList")
    public void downloadStudentList(@RequestBody List<StudentInfoForm> formList,
                                    HttpServletResponse response) throws Exception {
        studentService.downloadStudentList (formList, response);
    }

    /**
     * @apiNote 获取特殊学生的工序
     * -ScJn
     */
    @RequestMapping("/getSpProName")
    public CommonResponseForm getSpProName(@RequestBody Map<String, String> spStudent) {
        return CommonResponseForm.of200 ("查询成功", studentService.getSpProName (spStudent));

    }


    /**
     * -ScJn
     * @apiNote 查询学生列表
     * String s_group_id,
     * String batch_name,
     * String pro_name,
     * String sname,
     * String sid
     */
//    @RequestMapping("/getStudentByBatchNameAndSgroup")
//    public CommonResponseForm getStudentByBatchAndSgroupAndProOrNameAndId(@RequestBody StudentInfoForm studentInfoForm) {
//        return CommonResponseForm.of200("查询成功", studentService.findStudentByBatchNameAndSGroup(studentInfoForm));
//    }

//    @PostMapping("/importStudent")
//    public void dome1(HttpServletRequest request, MultipartFile file) throws Exception{
//        //file对象名记得和前端name属性值一致
//        System.out.println(file.getOriginalFilename());
//    }


}
