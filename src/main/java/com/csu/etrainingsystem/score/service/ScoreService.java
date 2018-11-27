package com.csu.etrainingsystem.score.service;

import com.csu.etrainingsystem.experiment.entity.Experiment;
import com.csu.etrainingsystem.experiment.repository.ExperimentRepository;
import com.csu.etrainingsystem.score.entity.Score;
import com.csu.etrainingsystem.score.form.DegreeForm;
import com.csu.etrainingsystem.score.form.ScoreForm;
import com.csu.etrainingsystem.score.repository.ScoreRepository;
import com.csu.etrainingsystem.student.entity.Student;
import com.csu.etrainingsystem.student.repository.StudentRepository;
import com.csu.etrainingsystem.student.service.StudentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
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
    public Score getScoreBySidAndPro(String sid, String pro_name) {
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
     *
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
        List<Student> students = new ArrayList<>();

        //根据所传的参数，先确定学生,没有学好就看批次和组号
        if (sId != null) {
            students = new ArrayList<>();
            Optional<Student> student = studentRepository.findStudentBySid(sId);
            student.ifPresent(students::add);
        } else if (sName != null) {
            students = (List<Student>) studentRepository.findStudentBySName(sName);
        } else if (sGroup != null || batchName != null) {
            if (sGroup == null) sGroup = "%";
            if (batchName == null) batchName = "%";
            students = (List<Student>) studentRepository.findStudentByS_group_idAndBatch(sGroup, batchName);
        }
        for (Student student : students) {
            if (proName != null) {
                scores = (Iterable<Score>) scoreRepository.findScoreBySidAndPro_name(student.getSid(), proName);
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
     * @param batchName 批次名
     * @param sGroup    学生组
     * @param proName   工序名
     * @return 实验列表
     */
    public List<Experiment> getScoreSubmitRecord(String batchName, String sGroup, String proName) {
        if (batchName == null) batchName = "%";
        if (sGroup == null) sGroup = "%";
        if (proName == null) proName = "%";
        return experimentRepository.findExperimentByBatchOrSGroupOrProName(batchName, sGroup, proName);
    }

    /**
     * 等级
     */
    public void setDegree(String way, DegreeForm degreeForm) {
        float great = Float.parseFloat(degreeForm.getGreat());
        float good = Float.parseFloat(degreeForm.getGood());
        float middle = Float.parseFloat(degreeForm.getMiddle());
        float pass = Float.parseFloat(degreeForm.getPass());
        float notPass = Float.parseFloat(degreeForm.getNotPass());
        String batchName = degreeForm.getBatchName();
        List<Student> students = (List<Student>) studentRepository.findStudentByBatch_nameAndOrder(batchName);
        int studentNum = students.size();
        if (way.equals("percent")) {
            int i = 0;
            for (Student student : students) {
                if (i < studentNum * great) student.setDegree("优");
                else if (i <= studentNum * (good + great)) student.setDegree("良");
                else if (i <= studentNum * (good + great + middle)) student.setDegree("中");
                else if (i <= studentNum * (good + great + middle + pass)) student.setDegree("及格");
                else student.setDegree("不及格");
                i++;
                studentRepository.save(student);
            }
        } else if (way.equals("score")) {
            for (Student student : students) {
                float score = student.getTotal_score();
                if (score >= great) {
                    student.setDegree("优");
                } else if (score >= good) {
                    student.setDegree("良");
                } else if (score >= middle) {
                    student.setDegree("中");
                } else if (score >= pass) {
                    student.setDegree("及格");
                } else {
                    student.setDegree("不及格");
                }
                studentRepository.save(student);

            }

        }
    }

    /**
     * 修改成绩
     *
     * 修改的话，以前就有记录了
     * 2中类别的分数，分开来执行，一个用studentRepo
     * 一个用scoreRepo
     */
    public void updateScore2(Map<String, String> scoreForm) {
        String sid = scoreForm.get("sid");
        System.out.println("*****" + sid);
        Optional<Student> op = studentRepository.findStudentBySid(sid);
        if (op.isPresent()) {
            Student student = op.get();
            System.out.println("*******" + sid + " " + student.getSname());
            for (String itemName : scoreForm.keySet()) {
                System.out.println("****" + itemName);
                if (itemName.equals("sid")) continue;
                if (itemName.equals("degree")) student.setDegree(scoreForm.get(itemName));
                if (itemName.equals("total_score")) student.setTotal_score(Float.parseFloat(scoreForm.get(itemName)));
                else updateScoreInScore(sid, itemName, Float.parseFloat(scoreForm.get(itemName)));
            }
        }


        // 管理组打分项
//        if (scoreForm.getAddScore() != 0.0f) {
//            updateScoreInScore(sid, "加分", scoreForm.getAddScore());
//        }
//        if (scoreForm.getReport() != 0.0f) {
//            updateScoreInScore(sid, "报告", scoreForm.getReport());
//        }
//
//        if (scoreForm.getComputer() != 0.0f) {
//            updateScoreInScore(sid, "上机", scoreForm.getComputer());
//        }
//
//        if (scoreForm.getAttendance() != 0.0f) {
//            updateScoreInScore(sid, "考勤", scoreForm.getAttendance());
//        }
//        if (scoreForm.getCarDesign() != 0.0f) {
//            updateScoreInScore(sid, "小车设计", scoreForm.getCarDesign());
//        }
//        if (scoreForm.getCarMake() != 0.0f) {
//            updateScoreInScore(sid, "小车制造", scoreForm.getCarMake());
//        }
//
//
//        /**********学生表中的项********/
//        if (scoreForm.getDegree() != null) {
//            student.setDegree(scoreForm.getDegree());
//        }
//
//        if (scoreForm.getTotalScore() != 0.0f) {
//            student.setTotal_score(scoreForm.getTotalScore());
//
//        }
//
//        //其他工序项
//        if (scoreForm.getChexue() != 0.0f) {
//            updateScoreInScore(sid, "车削", scoreForm.getChexue());
//        }
//        if (scoreForm.getZhuzao() != 0.0f) {
//            updateScore();
//        }
//        if (scoreForm.getCimatron() != 0.0f) {
//            updateScoreInScore(sid, "Cimatron", scoreForm.getCimatron());
//        }
//        if (scoreForm.getDuanya() != 0.0f) {
//            updateScoreInScore(sid, "锻压", scoreForm.getDuanya());
//        }
//
//
//        if (scoreForm.getHanjie() != 0.0f) {
//            updateScoreInScore(sid, "焊接", scoreForm.getHanjie());
//        }
//        if (scoreForm.getHeat() != 0.0f) {
//            updateScoreInScore(sid, "热处理", scoreForm.getHeat());
//
//        }
//        if (scoreForm.getJiguang() != 0.0f) {
//            updateScoreInScore(sid, "激光", scoreForm.getJiguang());
//        }
//        if (scoreForm.getMoxue() != 0.0f) {
//            updateScoreInScore(sid, "磨削", scoreForm.getMoxue());
//        }
//        if (scoreForm.getProcess() != 0.0f) {
//            updateScoreInScore(sid, "加工中心", scoreForm.getProcess());
//        }
//        if (scoreForm.getQiangong() != 0.0f) {
//            updateScoreInScore(sid, "钳工", scoreForm.getQiangong());
//        }
//        if (scoreForm.getRapidprototyping() != 0.0f) {
//            updateScoreInScore(sid, "快速成型", scoreForm.getRapidprototyping());
//        }
//        if (scoreForm.getShukongche() != 0.0f) {
//            updateScoreInScore(sid, "数控车", scoreForm.getShukongche());
//        }
//        if (scoreForm.getShukongchefangzheng() != 0.0f) {
//            updateScoreInScore(sid, "数控车仿真", scoreForm.getShukongchefangzheng());
//        }
//        if (scoreForm.getXianqiege() != 0.0f) {
//            updateScoreInScore(sid, "线切割", scoreForm.getXianqiege());
//        }
//        if (scoreForm.getXixue() != 0.0f) {
//            updateScoreInScore(sid, "铣削", scoreForm.getXixue());
//        }
    }

    private void updateScoreInScore(String sid, String proName, float sco) {

        Score score = scoreRepository.findScoreBySidAndPro_name(sid, proName);
        if (score != null) {
            score.setPro_score(sco);
        } else {
            score = new Score();
            score.setSid(sid);
            score.setPro_score(sco);
            score.setPro_name(proName);
        }
        scoreRepository.save(score);

    }

//    private void updateScoreInStudent(String sid,String ScoreName,float sco){

//
//    }
}
