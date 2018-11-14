package com.csu.etrainingsystem.score.service;

import com.csu.etrainingsystem.experiment.entity.Experiment;
import com.csu.etrainingsystem.experiment.repository.ExperimentRepository;
import com.csu.etrainingsystem.form.CommonResponseForm;
import com.csu.etrainingsystem.score.entity.Score;
import com.csu.etrainingsystem.score.form.ScoreForm;
import com.csu.etrainingsystem.score.form.ScoreSubmitForm;
import com.csu.etrainingsystem.score.repository.ScoreRepository;
import com.csu.etrainingsystem.student.entity.Student;
import com.csu.etrainingsystem.student.repository.StudentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Optional;

@Service
public class ScoreService {
    private final ScoreRepository scoreRepository;
    private final StudentRepository studentRepository;
    private final ExperimentRepository experimentRepository;

    @Autowired
    public ScoreService(ScoreRepository scoreRepository, StudentRepository studentRepository, ExperimentRepository experimentRepository) {
        this.scoreRepository = scoreRepository;
        this.studentRepository = studentRepository;
        this.experimentRepository = experimentRepository;
    }

    @Transactional
    public void addScore(Score score) {
        this.scoreRepository.save(score);
    }

    @Transactional
    public Iterable<Score> getScoreBySid(String sid) {
        return scoreRepository.findScoreBySid(sid);
    }

    @Transactional
    public Iterable<Score> getScoreBySidAndPro(String sid, String pro_name) {
        return scoreRepository.findScoreBySidAndPro_name(sid, pro_name);
    }

    @Transactional
    public Iterable<Score> getAllScore() {
        return this.scoreRepository.findAllScore();
    }

    @Transactional
    public void updateScore(Score Score) {
        this.scoreRepository.saveAndFlush(Score);
    }

    @Transactional
    public void deleteScoreBySid(String sid) {
        this.scoreRepository.deleteScoreBySid(sid);
    }

    @Transactional
    public void deleteScoreBySidAndPro(String sid, String pro_name) {
        this.scoreRepository.deleteScoreBySidAndPro_name(sid, pro_name);
    }

    @Transactional
    public void deleteScoreByPro(String pro_name) {
        this.scoreRepository.deleteScoreByPro_name(pro_name);
    }


    /**
     * 重要
     * @param batchName 批次
     * @param sGroup    学生组
     * @param proName   工序名
     * @return list scoreForm 分数表单，对应于前端的需求
     * 如果 proName 非空，就查询学号，跟proName的score，空的话就查询学号的score
     */
    public List<ScoreForm> getScoreByBatchAndSGroupOrProName(String batchName, String sGroup, String proName,
                                                             String sId, String sName) {
        List<ScoreForm> scoreForms = new ArrayList<>();

        Iterable<Score> scores;
        List<Student> students;

        //根据所传的参数，先确定学生
        if (sGroup != null || batchName != null) {
            if (sGroup == null) sGroup = "%";
            if (batchName == null) batchName = "%";
            students = (List<Student>) studentRepository.findStudentByS_group_idAndBatch(sGroup, batchName);
        } else if (sId != null) {
            students = new ArrayList<>();
            Optional<Student> student = studentRepository.findStudentBySid(sId);
            student.ifPresent(students::add);
        } else {
            students = (List<Student>) studentRepository.findStudentBySName(sName);
        }
        for (Student student : students) {
            if (proName != null) {
                scores = scoreRepository.findScoreBySidAndPro_name(student.getSid(), proName);
            } else {
                scores = scoreRepository.findScoreBySid(student.getSid());
            }
            ScoreForm scoreForm = new ScoreForm();
            scoreForm.setStuName(student.getSname());
            scoreForm.setBatchName(student.getBatch_name());
            scoreForm.setSGroupId(student.getS_group_id());
            for (Score score : scores) {
                scoreForm = setScoreForScoreForm(score, scoreForm);
            }
            scoreForms.add(scoreForm);
        }
        return scoreForms;
    }


    ////铸造/数控车/Cimatron/激光切割/加工中心/热处理/焊接/快速成型/锻压/铣削/磨削/车削/钳工/线切割/数控车仿真/
    private ScoreForm setScoreForScoreForm(Score score, ScoreForm scoreForm) {
        String proName = score.getPro_name();
        float proScore = score.getPro_score();
        switch (proName) {
            case "铸造":
                scoreForm.setZhuzao(proScore);
                break;
            case "数控车":
                scoreForm.setShukongche(proScore);
                break;
            case "Cimatron":
                scoreForm.setCimatron(proScore);
                break;
            case "激光切割":
                scoreForm.setJiguang(proScore);
                break;
            case "加工中心":
                scoreForm.setProcess(proScore);
                break;
            case "热处理":
                scoreForm.setHeat(proScore);
                break;
            case "焊接":
                scoreForm.setHanjie(proScore);
                break;
            case "快速成型":
                scoreForm.setRapidprototyping(proScore);
                break;
            case "锻压":
                scoreForm.setDuanya(proScore);
                break;
            case "磨削":
                scoreForm.setMoxue(proScore);
            case "车削":
                scoreForm.setChexue(proScore);
                break;
            case "钳工":
                scoreForm.setQiangong(proScore);
                break;
            case "铣削":
                scoreForm.setXixue(proScore);
                break;
            case "上机":
                scoreForm.setComputer(proScore);
                break;
            case "考勤":
                scoreForm.setAttendance(proScore);
                break;
            case "小车制造":
                scoreForm.setCarMake(proScore);
                break;
            case "实习报告":
                scoreForm.setReport(proScore);
                break;
            case "小车设计":
                scoreForm.setCarDesign(proScore);
                break;
            case "加分":
                scoreForm.setAddScore(proScore);
            case "总成绩":
                scoreForm.setTotalScore(proScore);
                break;

        }
        return scoreForm;
    }

    /**
     *
     * @param batchName 批次名
     * @param sGroup 学生组
     * @param proName 工序名
     * @return 实验列表
     */
    public List<Experiment> getScoreSubmitRecord(String batchName, String sGroup, String proName) {
        if(batchName==null)batchName="%";
        if(sGroup==null)sGroup="%";
        if(proName==null)proName="%";
        return experimentRepository.findExperimentByBatchOrSGroupOrProName(batchName, sGroup, proName);
    }

}
