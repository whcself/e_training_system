package com.csu.etrainingsystem.student.controller;


import com.csu.etrainingsystem.form.CommonResponseForm;
import com.csu.etrainingsystem.score.entity.Score;
import com.csu.etrainingsystem.score.service.ScoreService;
import com.csu.etrainingsystem.student.entity.SpecialStudent;
import com.csu.etrainingsystem.student.entity.Student;
import com.csu.etrainingsystem.student.form.SpStudentInfoForm;
import com.csu.etrainingsystem.student.form.StudentInfoForm;
import com.csu.etrainingsystem.student.service.StudentService;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping(value = "/student", method = RequestMethod.POST)
public class StudentController {

    private final StudentService studentService;
    private final ScoreService scoreService;

    @Autowired
    public StudentController(StudentService studentService, ScoreService scoreService) {
        this.studentService = studentService;
        this.scoreService = scoreService;
    }

    /**
     * 导入一个学生就应该自动添加一个成绩表,该功能
     *
     * @param student
     * @return
     */
    @RequestMapping(value = "/addStudent")
    public CommonResponseForm addStudent(Student student) {
        studentService.addStudent(student);
        //Score score = new Score();
        //todo:在service层添加学生的同时需要添加他对应的成绩表
       // this.scoreService.addScore(score);
        return CommonResponseForm.of204("学生添加增加成功");
    }
    @RequestMapping(value = "/addSpStudent")
    public CommonResponseForm addSpStudent(String sid,String template_name) {
       //如果学生表里面存在这个学生,就先将其删除,然后再添加到特殊学深表
       //查询该学生的批次以及所在组的所有实验;然后将实验的时间和工序名称赋值到新的sp_score里面
        Student student=studentService.getStudentById (sid);
        if(student==null)return CommonResponseForm.of400 ("特殊学生添加失败,不存在该学生");
        System.out.println (student+template_name);
        studentService.deleteById (student.getSid ());
        studentService.addSpStudent (student,template_name);
        //Score score = new Score();需要一个特殊成绩表来实现
        //todo:在service层添加特殊学生的同时需要添加他对应的成绩表;已经安排
        //this.scoreService.addScore(score);
        return CommonResponseForm.of204("特殊学生添加增加成功");
    }
    @RequestMapping(value = "/updateStudent")
    public CommonResponseForm updateStudent(Student student) {
        studentService.updateStudent(student);
        return CommonResponseForm.of204("学生更新成功");
    }
    @RequestMapping(value = "/updateSpStudent")
    public CommonResponseForm updateSpStudent(SpecialStudent student) {
        studentService.updateSpStudent (student);
        return CommonResponseForm.of204("特殊学生更新成功");
    }

    @RequestMapping(value = "/getStudent/{id}")
    public CommonResponseForm getStudentById(@PathVariable("id") String id) {

       Student student= studentService.getStudentById(id);
        StudentInfoForm studentInfoForm =new StudentInfoForm (student.getS_group_id (),student.getBatch_name (),student.getClazz (),student.getSname (),student.getSid ());

        return CommonResponseForm.of200("获取学生成功",studentInfoForm);
    }
    @ApiOperation ("获取特殊学生,返回数据中sid为数据的唯一标识")
    @RequestMapping(value = "/getSpStudentById/{id}")
    public CommonResponseForm getSpStudentById(@PathVariable("id") String id) {
        SpecialStudent student=studentService.findSpStudentById (id);
        SpStudentInfoForm spStudentInfoForm=new SpStudentInfoForm (student.getSid (),student.getSname (),student.getClazz (),student.getTemplate_name ());

        return CommonResponseForm.of200("获取特殊学生成功", spStudentInfoForm);
    }
    @RequestMapping(value = "/getAllStudent")
    public CommonResponseForm getAllStudent() {
       Iterable<Student> students=studentService.getAllStudent();
        List<StudentInfoForm> studentInfoForms=new ArrayList<StudentInfoForm> ();
        for (Student student : students) {
            studentInfoForms.add (new StudentInfoForm (student.getS_group_id (),student.getBatch_name (),student.getClazz (),student.getSname (),student.getSid ()));
        }
        return CommonResponseForm.of200("获取全部学生成功",studentInfoForms);
    }

    @ApiOperation ("获取所有的特殊学生,返回数据中每一條的sid为該数据的唯一标识")
    @RequestMapping(value = "/getAllSpStudent")
    public CommonResponseForm getAllSpStudent() {
       Iterable<SpecialStudent> specialStudents = studentService.findAllSpStudent ();
        List<SpStudentInfoForm> studentInfoForms=new ArrayList<SpStudentInfoForm> ();

        for (SpecialStudent student : specialStudents) {
            studentInfoForms.add (new SpStudentInfoForm (student.getSid (),student.getSname (),student.getClazz (),student.getTemplate_name ()));
        }

        return CommonResponseForm.of200("获取全部特殊学生成功",studentInfoForms );
    }
    @ApiOperation ("根据传递过来的id数组删除学生")
    @RequestMapping(value = "/deleteStudent")
    public CommonResponseForm deleteStudentById(@RequestParam(required = false) List<String> ids) {

        for (String id : ids) {
            this.studentService.deleteById (id);
        }
        return CommonResponseForm.of204("删除学生成功");
    }
    @ApiOperation ("根据传递过来的id数组删除特殊学生")
    @RequestMapping(value = "/deleteSpStudentById")
    public CommonResponseForm deleteSpStudentById(@RequestParam(required = false) List<String> ids) {



        for (String id : ids) {
            this.studentService.deleteSpStudentById (id);
        }
        return CommonResponseForm.of204("删除特殊学生成功");
    }


    /**
     * -ScJn
     * 重要，学生分组板块，只根据批次
     *
     * @param batchName
     * @return
     */
    @RequestMapping("/getStudentByBatchName")
    public CommonResponseForm getStudentByBatchName(@RequestParam String batchName) {
        return CommonResponseForm.of200("查询成功", studentService.findStudentByBatchName(batchName));
    }

    /**
     * -ScJn
     * 重要 学生分组板块，根据批次和组名
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

    @PostMapping("/importStudent")
    public void dome1(HttpServletRequest request, MultipartFile file) throws Exception{
        //file对象名记得和前端name属性值一致
        System.out.println(file.getOriginalFilename());
    }



}
