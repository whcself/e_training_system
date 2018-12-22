package com.csu.etrainingsystem.student.service;


import com.csu.etrainingsystem.experiment.repository.ExperimentRepository;
import com.csu.etrainingsystem.experiment.service.ExperimentService;
import com.csu.etrainingsystem.form.CommonResponseForm;
import com.csu.etrainingsystem.student.entity.Student;
import com.csu.etrainingsystem.student.entity.StudentGroup;
import com.csu.etrainingsystem.student.repository.StudentGroupRepository;
import com.csu.etrainingsystem.student.repository.StudentRepository;
import com.csu.etrainingsystem.student.service.StudentService;
import com.csu.etrainingsystem.student.entity.StudentGroupId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;
import java.util.Optional;

@Service
public class StudentGroupService {
    private final StudentGroupRepository studentGroupRepository;
    private final StudentService studentService;
    private final ExperimentService experimentService;
    private final ExperimentRepository experimentRepository;
    private final StudentRepository studentRepository;

    @Autowired
    public StudentGroupService(StudentRepository studentRepository, ExperimentRepository experimentRepository, StudentGroupRepository studentGroupRepository, StudentService studentService, ExperimentService experimentService) {
        this.studentRepository = studentRepository;
        this.experimentRepository = experimentRepository;
        this.studentGroupRepository = studentGroupRepository;
        this.studentService = studentService;
        this.experimentService = experimentService;
    }

    /**
     * 复合主键添加方式:studentGroupId.s_group_id
     * studentGroupId.batch_name
     *
     * @param studentGroup
     */
    @Transactional
    public void addStudentGroup(StudentGroup studentGroup) {
        this.studentGroupRepository.save(studentGroup);
    }

    //复合主键
    @Transactional
    public StudentGroup getStudentGroup(StudentGroupId studentGroupId) {
        Optional<StudentGroup> studentGroup = studentGroupRepository.findStudentGroupByIdAndBatch(studentGroupId.getS_group_id(), studentGroupId.getBatch_name());
        return studentGroup.get();
    }

    @Transactional
    public Iterable<StudentGroup> getAllStudentGroup() {
        return this.studentGroupRepository.findAllStudentGroup();
    }

    @Transactional
    public void updateStudentGroup(StudentGroup studentGroup) {
        this.studentGroupRepository.saveAndFlush(studentGroup);
    }

    @Transactional
    public void deleteStudentGroupByBatch(String batch_name) {
        Iterable<StudentGroup> studentGroups = this.studentGroupRepository.findStudentGroupByBatch(batch_name);
        if (studentGroups != null) {
            for (StudentGroup studentGroup : studentGroups) {
                studentGroup.setDel_status(true);
                updateStudentGroup(studentGroup);
            }
        }
    }

    @Transactional
    public Iterable<StudentGroup> getStudentGroupByBatch(String batch_name) {
        return this.studentGroupRepository.findStudentGroupByBatch(batch_name);
    }

    @Transactional
    public void deleteStudentGroup(StudentGroupId studentGroupId) {
        //删除学生表中该批次该组中的学生
        this.studentService.deleteByS_group(studentGroupId.getS_group_id(), studentGroupId.getBatch_name());
        //删除该批次该组学生所参与的实验记录,
        this.experimentService.deleteExperimentByS_group(studentGroupId.getS_group_id(), studentGroupId.getBatch_name());
    }


    /**
     * -ScJn
     *
     * 在学生分组的时候会将对应的学生组实体建立
     *
     * @param batchName batch
     */
    @Transactional
    public CommonResponseForm groupStudent(String batchName) {
        int groupNum, studentNum;
        List<String> groups;
        List<Student> students;
        groups = experimentRepository.getNumOfGroup(batchName);
        groupNum = groups.size();
        students = (List<Student>) studentRepository.findStudentByBatch_name(batchName);
        studentNum = students.size();
        if(groupNum == 0||studentNum==0)
            return CommonResponseForm.of400("出错了，请检查批次名是否有问题,批次是否已经绑定好了模板");

        for(String groupId : groups){
            StudentGroupId groupId1=new StudentGroupId(groupId,batchName);
            studentGroupRepository.save(new StudentGroup(groupId1,30,false));
        }
        for (int i = 0; i < studentNum; i++) {
            Student student = students.get(i);
            student.setS_group_id(groups.get(i % groupNum));
            try {
                studentRepository.save(student);
            }catch (Exception e){
                return CommonResponseForm.of400("分组失败");
            }
        }
        return CommonResponseForm.of204("分组成功");
    }
}
