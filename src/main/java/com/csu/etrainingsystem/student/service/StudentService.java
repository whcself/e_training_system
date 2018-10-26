package com.csu.etrainingsystem.student.service;

import com.csu.etrainingsystem.administrator.entity.Batch;
import com.csu.etrainingsystem.student.entity.Student;
import com.csu.etrainingsystem.student.repository.StudentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.PostMapping;

import java.util.List;
import java.util.Optional;

@Service
public class StudentService {
    private StudentRepository studentRepository;

    @Autowired
    public StudentService(StudentRepository studentRepository) {
        this.studentRepository = studentRepository;
    }

    public void addStudent(Student student){
        studentRepository.save(student);
    }

    public void deleteStudent(String id){
        studentRepository.deleteById(id);
    }

    public void updateStudent(Student student){
        studentRepository.saveAndFlush(student);
    }
    public Student getStudent(String id){
        Optional<Student> op = studentRepository.findById(id);
        return op.get();
    }
    public List<Student> getAllStudent(){
        return studentRepository.findAll();
    }

    public void addStudentFromExcel(String path){

    }
}
