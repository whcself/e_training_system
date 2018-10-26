package com.csu.etrainingsystem.student.controller;

import com.csu.etrainingsystem.form.CommonResponseForm;
import com.csu.etrainingsystem.student.entity.Student;
import com.csu.etrainingsystem.student.repository.StudentRepository;
import com.csu.etrainingsystem.student.service.StudentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/student")
public class StudentController {
    StudentService studentService;

    @Autowired
    public StudentController(StudentService studentService) {
        this.studentService = studentService;
    }

    @PostMapping("/addStudent")
    public CommonResponseForm addStudent(Student student){
        studentService.addStudent(student);
        return  CommonResponseForm.of204("增加学生成功");
    }
    @PostMapping("/deleteStudent/{id}")
    public CommonResponseForm deleteStudent(@PathVariable String id){
        studentService.deleteStudent(id);
        return CommonResponseForm.of204("删除学生成功");
    }
    @PostMapping("/updateStudent")
    public CommonResponseForm updateStudent(Student student){
        studentService.updateStudent(student);
        return CommonResponseForm.of204("更新学生成功");
    }
    @PostMapping("/getStudent/{id}")
    public CommonResponseForm getStudent(String id){
        return CommonResponseForm.of200("获取学生成功",studentService.getStudent(id));
    }
    @PostMapping("/getAllStudent")
    public CommonResponseForm getAllStudent(){
        return CommonResponseForm.of200("获取学生列表成功",studentService.getAllStudent());
    }
}
