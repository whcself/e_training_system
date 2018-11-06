package com.csu.etrainingsystem.studentGroup.service;


import com.csu.etrainingsystem.experiment.service.ExperimentService;
import com.csu.etrainingsystem.student.entity.StudentGroup;
import com.csu.etrainingsystem.student.repository.StudentGroupRepository;
import com.csu.etrainingsystem.student.service.StudentService;
import com.csu.etrainingsystem.student.entity.StudentGroupId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.Optional;

@Service
public class StudentGroupService {
    private final StudentGroupRepository studentGroupRepository;
    private  final StudentService studentService;
    private final ExperimentService experimentService;
    @Autowired
    public StudentGroupService(StudentGroupRepository studentGroupRepository, StudentService studentService, ExperimentService experimentService) {
        this.studentGroupRepository = studentGroupRepository;
        this.studentService = studentService;
        this.experimentService = experimentService;
    }

    /**
     * 复合主键添加方式:studentGroupId.s_group_id
     *                studentGroupId.batch_name
     * @param studentGroup
     */
    @Transactional
    public void addStudentGroup(StudentGroup studentGroup) {
        this.studentGroupRepository.save(studentGroup);
    }
     //复合主键
    @Transactional
    public StudentGroup getStudentGroup(StudentGroupId studentGroupId) {
        Optional<StudentGroup> studentGroup=studentGroupRepository.findStudentGroupByIdAndBatch (studentGroupId.getS_group_id (),studentGroupId.getBatch_name ());
        return studentGroup.get();
    }
    @Transactional
    public Iterable<StudentGroup> getAllStudentGroup() {
        return this.studentGroupRepository.findAllStudentGroup();
    }

    @Transactional
    public void  updateStudentGroup(StudentGroup studentGroup) {
        this.studentGroupRepository.saveAndFlush(studentGroup);
    }
    @Transactional
    public void  deleteStudentGroupByBacth(String batch_name) {
       Iterable<StudentGroup> studentGroups =this.studentGroupRepository.findStudentGroupByBatch(batch_name);
        if (studentGroups!=null){
            for (StudentGroup studentGroup : studentGroups) {
                studentGroup.setDel_status(true);
                updateStudentGroup(studentGroup);
            }
        }
    }
    @Transactional
    public Iterable<StudentGroup>  getStudentGroupByBacth(String batch_name) {
       return this.studentGroupRepository.findStudentGroupByBatch(batch_name);
    }
    @Transactional
    public void  deleteStudentGroup(StudentGroupId studentGroupId) {
        //删除学生表中该批次该组中的学生
        this.studentService.deleteByS_group(studentGroupId.getS_group_id (),studentGroupId.getBatch_name ());
        //删除该批次该组学生所参与的实验记录,
        this.experimentService.deleteExperimentByS_group(studentGroupId.getS_group_id (),studentGroupId.getBatch_name ());
    }
}
