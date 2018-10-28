package com.csu.etrainingsystem.student.controller;


import com.csu.etrainingsystem.administrator.entity.Admin;
import com.csu.etrainingsystem.form.CommonResponseForm;
import com.csu.etrainingsystem.student.entity.Student;
import com.csu.etrainingsystem.student.service.StudentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController()
@RequestMapping(value ="/student",method = RequestMethod.POST)
public class StudentController {

    private final StudentService studentService;

    @Autowired
    public StudentController(StudentService studentService ) {
        this.studentService = studentService;
    }

    //@Autowired

    @RequestMapping(value = "/addStudent")
    public CommonResponseForm addStudent(Student student){
        studentService.save(student);
        return  CommonResponseForm.of204("学生添加增加成功");}

    @RequestMapping(value = "/getStudent/{id}")
    public CommonResponseForm getStudentById(@PathVariable("id") String id){
        return  CommonResponseForm.of200("获取学生成功",studentService.getStudentById(id)); }


    @RequestMapping(value = "/getStudent")
    public CommonResponseForm getAllStudent(){
        return  CommonResponseForm.of200("获取全部学生成功",studentService.getAllStudent()); }

    @RequestMapping(value = "/deleteStudent/{id}")
    public CommonResponseForm deleteStudentById(String id){
        studentService.deleteById(id);
        return  CommonResponseForm.of204("删除学生成功"); }


}
