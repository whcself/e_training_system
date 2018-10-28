package com.csu.etrainingsystem.student.service;


import com.csu.etrainingsystem.administrator.entity.Batch;

import com.csu.etrainingsystem.student.entity.Student;
import com.csu.etrainingsystem.student.repository.StudentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.PostMapping;

import java.util.List;
import java.util.Optional;

import javax.transaction.Transactional;

@Service
public class StudentService {


    private final StudentRepository studentRepository;

    @Autowired
    public StudentService(StudentRepository studentRepository) { this.studentRepository = studentRepository; }


    @Transactional
    public void save(Student student) { studentRepository.save(student); }

    @Transactional
    public Student getStudentById(String id) {
      Optional<Student> op = studentRepository.findById(id);
        return op.get(); }

    @Transactional
    public Iterable<Student> getAllStudent() { return studentRepository.findAll(); }

    @Transactional
    public void deleteById(String id) {
     Student student =studentRepository.getOne(id);
      student.setDel_status(true);
   /*
    ToDo:消除删除一个学生所带来的影响
     删除一个学生的影响:加班申请表记录删除,成绩表删除,目前还没有
   */
    }
   public void addStudentFromExcel(String path){

    }

    @Transactional
    public void updateStudent(Student student){studentRepository.saveAndFlush(student); }
}



