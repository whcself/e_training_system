package com.csu.etrainingsystem.student.service;


import com.csu.etrainingsystem.experiment.entity.Experiment;
import com.csu.etrainingsystem.experiment.service.ExperimentService;
import com.csu.etrainingsystem.overwork.service.Overwork_applyService;
import com.csu.etrainingsystem.score.entity.SpecialScore;
import com.csu.etrainingsystem.score.service.ScoreService;
import com.csu.etrainingsystem.student.entity.SpecialStudent;
import com.csu.etrainingsystem.student.entity.Student;
import com.csu.etrainingsystem.student.repository.SpStudentRepository;
import com.csu.etrainingsystem.student.repository.StudentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Optional;

import javax.transaction.Transactional;

@Service
public class StudentService {


    private final StudentRepository studentRepository;
    private final ScoreService scoreService;
    private final Overwork_applyService overwork_applyService;
    private final SpStudentRepository spStudentRepository;
    private final ExperimentService experimentService;
    @Autowired
    public StudentService(StudentRepository studentRepository, ScoreService scoreService, Overwork_applyService overwork_applyService, SpStudentRepository spStudentRepository, ExperimentService experimentService)
    { this.studentRepository = studentRepository;
        this.scoreService = scoreService;
        this.overwork_applyService = overwork_applyService;
        this.spStudentRepository = spStudentRepository;
        this.experimentService = experimentService;
    }

    @Transactional
      public void releaseScore(String batchName){
        studentRepository.releaseScore(batchName);
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
    @Transactional
    public Iterable<Student> findStudentByBatchName(String batchName){
        return studentRepository.findStudentByBatch_name(batchName);
    }
    @Transactional
    public Iterable<Student> findStudentByBatchNameAndSGroup(String batchName,String groupId){
        return studentRepository.findStudentByS_group_idAndBatch(groupId,batchName);
    }

    /**
     * 根据学号获取特殊学生
     * @param sid
     * @return
     */
    @Transactional
    public SpecialStudent findSpStudentById(String sid){
        return spStudentRepository.findSpStudentBySid (sid).get ();
    }

    /**
     * 获取所有的特殊学生
     * @return
     */
    @Transactional
    public Iterable<SpecialStudent> findAllSpStudent(){
        return spStudentRepository.findAllSpStudent ();
    }

    @Transactional
    public void addSpStudent(Student student,String template_name) {
        SpecialStudent specialStudent=new SpecialStudent (student.getSid (),student.getSname (),student.getClazz (),template_name,student.getSdept (),student.getDepart (),student.getTotal_score (),student.isDel_status (),student.isScore_lock (),student.getDegree ());

       Iterable<Experiment> experiments= experimentService.getStudentExperiment (student.getS_group_id (),student.getBatch_name ());
       Iterable<SpecialScore>specialScores =new ArrayList<SpecialScore> ();
      if(experiments!=null) {
          for (Experiment experiment : experiments) {
              SpecialScore s = new SpecialScore ();
              s.setSid (specialStudent.getSid ());
              s.setPro_name (experiment.getPro_name ());
              s.setTime_quant (experiment.getTime_quant ());
              s.setClass_time (experiment.getClass_time ());
              scoreService.addSpScore (s);
          }
      }
        spStudentRepository.save (specialStudent);
    }


    @Transactional
    public void updateSpStudent(SpecialStudent student){spStudentRepository.saveAndFlush (student); }

    @Transactional
    public void deleteSpStudentById(String sid) {
        SpecialStudent student =findSpStudentById(sid);
        if(student!=null) {
            //删除该学生的成绩记录
            //这部分等特殊分数表完成后调用
            //todo:删除学生之后也需要将对应的成绩表删除
            scoreService.deleteSpScoreBySid (sid);
            //删除这个学生申请加班的记录
            // this.overwork_applyService.deleteOverwork_apply();
            student.setDel_status(true);
            updateSpStudent(student);
        }
    }
    // TODO: 2018/11/14 教师端学生分组查询？？？
//    public List<Student> findStudentByInfo(StudentInfoForm studentInfoForm){
//        String batchName=studentInfoForm.getBatch_name();
//        String sGroup=studentInfoForm.getS_group_id();
//        String proName=studentInfoForm.getPro_name();
//
//        return studentRepository.
//    }
}