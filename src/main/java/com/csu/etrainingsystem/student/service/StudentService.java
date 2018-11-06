package com.csu.etrainingsystem.student.service;


import com.csu.etrainingsystem.overwork.service.Overwork_applyService;
import com.csu.etrainingsystem.score.service.ScoreService;
import com.csu.etrainingsystem.student.entity.Student;
import com.csu.etrainingsystem.student.repository.StudentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

import javax.transaction.Transactional;

@Service
public class StudentService {


    private final StudentRepository studentRepository;
    private final ScoreService scoreService;
    private final Overwork_applyService overwork_applyService;
    @Autowired
    public StudentService(StudentRepository studentRepository, ScoreService scoreService, Overwork_applyService overwork_applyService) { this.studentRepository = studentRepository;
        this.scoreService = scoreService;
        this.overwork_applyService = overwork_applyService;
    }


    @Transactional
    public void addStudent(Student student) { studentRepository.save(student); }

    @Transactional
    public Student getStudentById(String id) {
      Optional<Student> op = studentRepository.findStudentBySid(id);
        return op.get(); }

    @Transactional
    public Iterable<Student> getAllStudent() { return studentRepository.findAllStudent(); }
    @Transactional
    public void deleteByS_group(String s_group_id,String batch_name) {
       Iterable<Student>students= this.studentRepository.findStudentByS_group_idAndBatch(s_group_id,batch_name);
        if(students!=null){
            for (Student student : students) {
                deleteById(student.getSid());
            }
        }

    }
    public void addStudentFromExcel(String path){}

    @Transactional
    public void updateStudent(Student student){studentRepository.saveAndFlush(student); }
    @Transactional
    public void deleteById(String sid) {
     Student student =getStudentById(sid);
     if(student!=null) {
         //删除该学生的成绩记录
         this.scoreService.deleteScoreBySid(sid);
         //删除这个学生申请加班的记录
         // this.overwork_applyService.deleteOverwork_apply();
         student.setDel_status(true);
         updateStudent(student);
     }
    }
    @Transactional
    public void deleteByBacth(String sid) {

    }
}



