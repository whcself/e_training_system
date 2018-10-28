package com.csu.etrainingsystem.teacher.service;

import com.csu.etrainingsystem.teacher.entity.Teacher;
import com.csu.etrainingsystem.teacher.repository.TeacherRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;

@Service
public class TeacherService {
    private  final TeacherRepository teacherRepository;

    @Autowired
    public TeacherService(TeacherRepository teacherRepository) { this.teacherRepository = teacherRepository; }

    @Transactional
    public void addTeacher(Teacher teacher){teacherRepository.save(teacher);}

    @Transactional
    public Teacher getTeacherById(String id){return teacherRepository.getOne(id);}

    @Transactional
    public Iterable<Teacher>getAllTeacher(){return teacherRepository.findAll();}

    @Transactional
    public void updateTeacher(Teacher teacher){teacherRepository.saveAndFlush(teacher);}

    @Transactional
    public void deleteTeacherById(String id){
      Teacher teacher=teacherRepository.getOne(id);
      teacher.setDel_status(true);
      teacherRepository.saveAndFlush(teacher);
      /*
      todo:消除删除一个老师所带来的影响
       */
    }
}
