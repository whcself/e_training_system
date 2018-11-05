package com.csu.etrainingsystem.teacher.service;

import com.csu.etrainingsystem.experiment.service.ExperimentService;
import com.csu.etrainingsystem.teacher.entity.Teacher;
import com.csu.etrainingsystem.teacher.repository.TeacherRepository;
import com.csu.etrainingsystem.teacherGroup.service.TeacherGroupService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;

@Service
public class TeacherService {
    private  final TeacherRepository teacherRepository;
    private  final TeacherGroupService teacherGroupService;
    @Autowired
    public TeacherService(TeacherRepository teacherRepository, TeacherGroupService teacherGroupService) {
        this.teacherRepository = teacherRepository;
        this.teacherGroupService = teacherGroupService;
    }

    @Transactional
    public void addTeacher(Teacher teacher){teacherRepository.save(teacher);}

    @Transactional
    public Teacher getTeacher(String id){return teacherRepository.findTeacherByTid(id);}

    @Transactional
    public Iterable<Teacher>getAllTeacher(){
        return teacherRepository.findAllTeacher();}

    @Transactional
    public void updateTeacher(Teacher teacher){teacherRepository.saveAndFlush(teacher);}

    @Transactional
    public void deleteTeacher(String tid){
      Teacher teacher=getTeacher(tid);
      teacher.setDel_status(true);
        updateTeacher(teacher);
      //删除这个老师在教师组的记录
      this.teacherGroupService.deleteTeacherGroupByTeacher(tid);
      /*
      todo:消除删除一个老师所带来的影响:
      所在教师组的记录需要被删除,实验表的提交老师需要被删除?暂定不删除,好追责

       */
    }
}
