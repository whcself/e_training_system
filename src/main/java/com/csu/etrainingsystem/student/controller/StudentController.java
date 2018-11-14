package com.csu.etrainingsystem.student.controller;


import com.csu.etrainingsystem.form.CommonResponseForm;
import com.csu.etrainingsystem.score.entity.Score;
import com.csu.etrainingsystem.score.service.ScoreService;
import com.csu.etrainingsystem.student.entity.Student;
import com.csu.etrainingsystem.student.service.StudentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController()
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
        Score score = new Score();
        this.scoreService.addScore(score);
        return CommonResponseForm.of204("学生添加增加成功");
    }

    @RequestMapping(value = "/updateStudent")
    public CommonResponseForm updateStudent(Student student) {
        studentService.updateStudent(student);
        return CommonResponseForm.of204("学生更新成功");
    }

    @RequestMapping(value = "/getStudent/{id}")
    public CommonResponseForm getStudentById(@PathVariable("id") String id) {
        return CommonResponseForm.of200("获取学生成功", studentService.getStudentById(id));
    }


    @RequestMapping(value = "/getAllStudent")
    public CommonResponseForm getAllStudent() {
        return CommonResponseForm.of200("获取全部学生成功", studentService.getAllStudent());
    }

    @RequestMapping(value = "/deleteStudent/{id}")
    public CommonResponseForm deleteStudentById(@PathVariable("id") String id) {
        System.out.println("删除学生:" + id);
        studentService.deleteById(id);
        return CommonResponseForm.of204("删除学生成功");
    }

    /**
     * -ScJn
     * 重要，学生分组板块，只根据批次
     * @param batchName
     * @return
     */
    @RequestMapping("/getStudentByBatchName")
    public CommonResponseForm getStudentByBatchName(@RequestParam String batchName){
        return CommonResponseForm.of200("查询成功",studentService.findStudentByBatchName(batchName));
    }

    /**
     * -ScJn
     * 重要 学生分组板块，根据批次和组名
     * @param groupId
     * @param batchName
     * @return
     */
    @RequestMapping("/getStudentByBatchNameAndSgroup")
    public CommonResponseForm getStudentByBatchNameAndSgroup(@RequestParam String groupId,
                                                             @RequestParam String batchName){
        return CommonResponseForm.of200("查询成功",studentService.findStudentByBatchNameAndSGroup(batchName,groupId));
    }


}
