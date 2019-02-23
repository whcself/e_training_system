package com.csu.etrainingsystem.score.service;

import com.csu.etrainingsystem.experiment.entity.Experiment;
import com.csu.etrainingsystem.experiment.repository.ExperimentRepository;
import com.csu.etrainingsystem.form.CommonResponseForm;
import com.csu.etrainingsystem.score.entity.Score;
import com.csu.etrainingsystem.score.entity.ScoreSubmit;
import com.csu.etrainingsystem.score.entity.ScoreUpdate;
import com.csu.etrainingsystem.score.entity.SpecialScore;
import com.csu.etrainingsystem.score.form.DegreeForm;
import com.csu.etrainingsystem.score.form.EnteringForm;
import com.csu.etrainingsystem.score.form.ScoreForm;
import com.csu.etrainingsystem.score.repository.ScoreRepository;
import com.csu.etrainingsystem.score.repository.ScoreSubmitRepository;
import com.csu.etrainingsystem.score.repository.ScoreUpdateRepository;
import com.csu.etrainingsystem.score.repository.SpScoreRepository;
import com.csu.etrainingsystem.student.entity.SpecialStudent;
import com.csu.etrainingsystem.student.entity.Student;
import com.csu.etrainingsystem.student.repository.SpStudentRepository;
import com.csu.etrainingsystem.student.repository.StudentRepository;
import com.csu.etrainingsystem.util.TimeUtil;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellType;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
//import org.apache.shiro.crypto.hash.Hash;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpSession;
import javax.transaction.Transactional;
import java.io.*;
import java.sql.Timestamp;
import java.text.DecimalFormat;
import java.util.*;

@Service
public class ScoreService {
    private final ScoreRepository scoreRepository;
    private final StudentRepository studentRepository;
    private final ExperimentRepository experimentRepository;
    private final SpStudentRepository spStudentRepository;
    private final ScoreSubmitRepository scoreSubmitRepository;
    private final SpScoreRepository spScoreRepository;
    private final ScoreUpdateRepository scoreUpdateRepository;

    @Autowired
    public ScoreService(ScoreUpdateRepository scoreUpdateRepository, ScoreSubmitRepository scoreSubmitRepository, ScoreRepository scoreRepository, StudentRepository studentRepository, ExperimentRepository experimentRepository, SpStudentRepository spStudentRepository, SpScoreRepository spScoreRepository) {
        this.scoreUpdateRepository = scoreUpdateRepository;
        this.scoreSubmitRepository = scoreSubmitRepository;
        this.scoreRepository = scoreRepository;
        this.studentRepository = studentRepository;
        this.experimentRepository = experimentRepository;
        this.spStudentRepository = spStudentRepository;
        this.spScoreRepository = spScoreRepository;
    }

    @Transactional
    public void addScore(Score score) {
        this.scoreRepository.save(score);
    }

    @Transactional
    public void addSpScore(SpecialScore specialScore) {
        this.spScoreRepository.save(specialScore);
    }


    @Transactional
    public CommonResponseForm executeScore(String batch_name) {
        if (scoreRepository.checkBand(batch_name) != 0) {
            scoreRepository.executeScore(batch_name);
            return CommonResponseForm.of204("计算成功");
        } else return CommonResponseForm.of400("该批次未绑定权重模板，无法计算");
    }

    @Transactional
    public Iterable<Score> getScoreBySid(String sid) {
        return scoreRepository.findScoreBySid(sid);
    }

    @Transactional
    public Iterable<SpecialScore> getSpScoreBySid(String sid) {
        return spScoreRepository.findSpScoreBySid(sid);
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
        Iterable<Score> scores = scoreRepository.findScoreBySid(sid);
        if (scores != null) this.scoreRepository.deleteScoreBySid(sid);
    }

    @Transactional
    public void deleteSpScoreBySid(String sid) {
        Iterable<SpecialScore> scores = spScoreRepository.findSpScoreBySid(sid);
        if (scores != null) this.spScoreRepository.deleteSpScoreBySid(sid);
    }

    @Transactional
    public void deleteSpScoreByscoreId(String scoreId) {
        this.spScoreRepository.deleteSpScoreByScoreId(scoreId);
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
    public CommonResponseForm getScoreByBatchAndSGroupOrProName(String batchName, String sGroup, String proName,
                                                                           String sId, String sName,boolean isStudent) {

        List<HashMap<String, String>> scoreForms = new ArrayList<>();

        List<Student> students = getStudents(sId, sName, sGroup, batchName);
        for (Student student : students) {
            if(isStudent&&student.isScore_lock()){
                return CommonResponseForm.of400("成绩未发布");
            }

            List<Score> scores = getScore(proName, student);
            System.out.println("***" + student.getSid());
            HashMap<String, String> scoreForm = new HashMap<>();
            scoreForm.put("sname", student.getSname());
            scoreForm.put("sid", student.getSid());
            scoreForm.put("batch_name", student.getBatch_name());
            scoreForm.put("s_group_id", student.getS_group_id());
            scoreForm.put("total_score", String.valueOf(student.getTotal_score()));
            scoreForm.put("degree", student.getDegree());
            scoreForm.put("release", student.isScore_lock() ? "已发布" : "未发布");
            scoreForm.put("clazz",student.getClazz());

            for (Score score : scores) {
                scoreForm.put(score.getPro_name(), String.valueOf(score.getPro_score()));

//                scoreForm = setScoreForScoreForm(score, scoreForm);
            }
            scoreForms.add(scoreForm);
        }
        return CommonResponseForm.of200("查询成功:共" + scoreForms.size() + "条记录", scoreForms);
    }

    public List<EnteringForm> getInputInfo(String sId, String sName, String sGroup, String batchName, String proName) {
        List<Student> students = getStudents(sId, sName, sGroup, batchName);
        List<Score> inputForm = new ArrayList<>();
        List<EnteringForm> enteringForms=new ArrayList<>();
        for (Student student : students) {
            List<Score> scores=getScore(proName, student);
            String batchName2=student.getBatch_name();
            String sGroup2=student.getBatch_name();
            String sName2=student.getSname();

            for(Score score:scores){
                EnteringForm enteringForm=new EnteringForm();
                enteringForm.setScore(score.getPro_score());
                enteringForm.setBatchName(batchName2);
                enteringForm.setSGroup(sGroup2);
                enteringForm.setEnterTime(score.getEnter_time());
                enteringForm.setProced(score.getPro_name());
                enteringForm.setSid(score.getSid());
                enteringForm.setSName(sName2);
                enteringForm.setTName(score.getTname());
                enteringForms.add(enteringForm);
            }
        }
        return enteringForms;
    }

    private List<Score> getScore(String proName, Student student) {
        List<Score> scores = new ArrayList<>();
        if (!proName.equals("all")) {
            Score score = scoreRepository.findScoreBySidAndPro_name(student.getSid(), proName);
            if (score != null)
                scores.add(score);
        } else {
            scores = (ArrayList<Score>) scoreRepository.findScoreBySid(student.getSid());
        }
        return scores;
    }

    private List<Student> getStudents(String sId, String sName, String sGroup, String batchName) {

        List<Student> students;

        //根据所传的参数，先确定学生,没有学好就看批次和组号
        if (!sId.equals("all")) {
            students = new ArrayList<>();
            Optional<Student> student = studentRepository.findStudentBySid(sId);
            student.ifPresent(students::add);
        } else if (!sName.equals("all")) {
            students = (List<Student>) studentRepository.findStudentBySName(sName);
        } else {
            if (sGroup.equals("all")) sGroup = "%";
            if (batchName.equals("all")) batchName = "%";
            students = (List<Student>) studentRepository.findStudentByS_group_idAndBatch(sGroup, batchName);

        }
        return students;
    }

    @Transactional
    public List<ScoreForm> getSpScoreBySidOrSname(String sId, String sName) {
        List<ScoreForm> scoreForms = new ArrayList<>();

        Iterable<SpecialScore> scores;
        List<SpecialStudent> students = new ArrayList<SpecialStudent>();

        //根据所传的参数，先确定学生
        if (sId != null) {
            students = new ArrayList<>();
            Optional<SpecialStudent> student = spStudentRepository.findSpStudentBySid(sId);
            student.ifPresent(((ArrayList<SpecialStudent>) students)::add);
        } else if (sName != null) {
            students = (List<SpecialStudent>) spStudentRepository.findSpStudentBySName(sName);
        }
        for (SpecialStudent student : students) {
            scores = this.spScoreRepository.findSpScoreBySid(student.getSid());
            ScoreForm scoreForm = new ScoreForm();
            scoreForm.setStuName(student.getSname());
            for (SpecialScore score : scores) {
                //  scoreForm = setScoreForScoreForm(score, scoreForm);
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

    public List<ScoreSubmit> getScoreRecord(String batchName, String sGroup, String proName) {

        if (batchName == null) batchName = "%";
        if (sGroup == null) sGroup = "%";
        if (proName == null) proName = "%";
        return scoreSubmitRepository.findScoreRecord(batchName, sGroup, proName);
    }

    public List<Map<String, String>> getScoreUpdate(String batchName, String begin, String end, String sname, String sid) {
        sid = toAll(sid);
        sname = toAll(sname);
        batchName = toAll(batchName);
        if (begin == null) begin = "1999-10-10";
        if (end == null) end = "2999-10-10";
        System.out.println(sid + " " + sname + " " + batchName + " " + begin + " " + end);
        return scoreUpdateRepository.findScoreUpdate(batchName, begin, end, sname, sid);

    }

    //    public CommonResponseForm addTeacherOverwork(@RequestParam String begin,
//                                                 @RequestParam(defaultValue = "2") String duration,
//                                                 @RequestParam String pro_name,
//                                                 @RequestParam String t_name,
//                                                 @RequestParam String reason,
//                                                 HttpSession session)
    private String toAll(String para) {
        if (para == null) return "%";
        else return para;
    }

    /**
     * 等级
     */
    @Transactional
    public CommonResponseForm setDegree(String way, DegreeForm degreeForm) {
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
                Float score = student.getTotal_score();
                if (score == null) {
                    score = 0.0F;
                }
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
        return CommonResponseForm.of204("评定成功");

    }

    /**
     * 修改的话，以前就有记录了
     * 2中类别的分数，分开来执行，一个用studentRepo
     * 一个用scoreRepo
     *
     * 对应的录入信息也会更新
     */
    public boolean updateScore2(Map<String, String> scoreForm, boolean isAdmin, HttpSession session) {
        String sid = scoreForm.get("sid");
        Optional<Student> op = studentRepository.findStudentBySid(sid);
        String tName = scoreForm.get("tName");
        // if the form does not have the tName, use the session attribute
        if(tName==null){
            tName= (String) session.getAttribute("name");
        }
        if (op.isPresent()) {
            Student student = op.get();
            if (student.isScore_lock() && !isAdmin) return false; // 如果是老师，并且锁了，就return
            System.out.println("*******" + sid + " " + student.getSname());
            for (String itemName : scoreForm.keySet()) {
                System.out.println("****" + itemName);
                switch (itemName) {
                    case "sid":
                    case "reason":
                    case "tName":
                        continue;
                    case "degree":
                        student.setDegree(scoreForm.get(itemName));
                        break;
                    case "total_score":
                        student.setTotal_score(Float.parseFloat(scoreForm.get(itemName)));
                        break;
                    default:
                        updateScoreInScore(sid, itemName, Float.parseFloat(scoreForm.get(itemName)),tName);
                        break;
                }
            }


        }
        if (isAdmin) {
            String reason = scoreForm.get("reason");
            ScoreUpdate update = new ScoreUpdate();
            update.setReason(reason);
            update.setSid(sid);
            update.setTName(tName);
            update.setUpdate_time(new Timestamp(System.currentTimeMillis()));
            scoreUpdateRepository.save(update);
        }

        return true;


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


    public void addScore(String sid, String proName, Float score) {
        Score score1 = new Score();
        score1.setPro_score(score);
        score1.setSid(sid);
        score1.setPro_name(proName);
        scoreRepository.save(score1);
    }

    /**
     * -ScJn
     *
     * @param contactFile 导入成绩的xslx表
     * @param batchName   指定的批次名
     * @param proName     工序
     * @return 状态位
     */
    @Transactional
    public int importScore(MultipartFile contactFile, String batchName, String proName,HttpSession session) throws IOException {

        int flag = 0;


        File convFile = new File(contactFile.getOriginalFilename());
        contactFile.transferTo(convFile.getAbsoluteFile()); // 如果不是绝对路径就会找不到
        FileInputStream file = new FileInputStream(convFile);

        //Create Workbook instance holding reference to .xlsx file
        XSSFWorkbook workbook = new XSSFWorkbook(file);

        //Get first/desired sheet from the workbook
        XSSFSheet sheet = workbook.getSheetAt(0);
        //Iterate through each rows one by one
        for (Row row : sheet) {
            if (row.getRowNum() == 0) continue;
            Cell idCell = row.getCell(0);// id column
            Cell scoreCell = row.getCell(2);// score column
            String id = null;
            String value = null;

            if (idCell == null || scoreCell == null) {
                continue;
            }
            if (idCell.getCellType() == CellType.STRING) {
                id = idCell.getStringCellValue();
            } else if (idCell.getCellType() == CellType.NUMERIC) {
                id = new DecimalFormat("#").format(idCell.getNumericCellValue()); // significant code
            }
            if (scoreCell.getCellType() == CellType.STRING) {
                value = scoreCell.getStringCellValue();
            } else if (idCell.getCellType() == CellType.NUMERIC) {
                value = new DecimalFormat("#").format(idCell.getNumericCellValue()); // significant code
            }


            Optional<Student> optionalStudent = studentRepository.findStudentBySid(id);
            if (optionalStudent.isPresent()) {

                Student student = optionalStudent.get();
                if (!student.getBatch_name().equals(batchName)) {
                    flag = 1;  //不属于该批次
                    continue;
                }
                String name= (String) session.getAttribute("name");

                Score newScore = new Score();
                newScore.setPro_name(proName);
                newScore.setPro_score(Float.valueOf(value));
                newScore.setSid(id);
                newScore.setTname(name);
                newScore.setEnter_time(TimeUtil.getZoneTime());
                scoreRepository.save(newScore);
            } else {
                flag = 2;
            }

        }
        file.close();
        return flag;

    }


    /**
     * 如果有这个对应sid，proName的score，就更新，如果没有就增加
     * 所以也可以用于打分，而用于打分的时候就必须要加入录入记录，也就是更新这个时间和老师
     * @param sid sid
     * @param proName proName
     * @param sco score
     */
    private void updateScoreInScore(String sid, String proName, float sco,String tName) {

        Score score = scoreRepository.findScoreBySidAndPro_name(sid, proName);
        if (score != null) {
            score.setPro_score(sco);
        } else {
            score = new Score();
            score.setSid(sid);
            score.setPro_score(sco);
            score.setPro_name(proName);
            score.setEnter_time(TimeUtil.getZoneTime());
            score.setTname(tName);
        }
        scoreRepository.save(score);

    }

    public List<Map<String, String>> getSpScore(String sid, String sname, String templateName) {
        List<Map<String, String>> maps = new ArrayList<>();
        List<SpecialStudent> spStudents = new ArrayList<>();
        /* 拿到所有的学生,所有或者具体一个 */
        if (sid == null && sname == null && templateName == null) {
            spStudents = spStudentRepository.findAll();
        } else if (sid != null) {
            Optional<SpecialStudent> optionalSpecialStudent = spStudentRepository.findSpStudentBySid(sid);

            if (optionalSpecialStudent.isPresent()) {
                spStudents.add(optionalSpecialStudent.get());
            } else {
                return null;
            }
        } else if (sname != null) {
            spStudents = (List<SpecialStudent>) spStudentRepository.findSpStudentBySName(sname);
        } else {
            spStudents = spStudentRepository.findByTemplateName(templateName);
        }
        //*************************
        for (SpecialStudent student : spStudents) {
            Map<String, String> map = new HashMap<>();
            List<SpecialScore> scores = null;
            scores = (List<SpecialScore>) spScoreRepository.findSpScoreBySid(student.getSid());
            /* 一个学生的所有分数信息 */
            map.put("姓名", student.getSname());
            map.put("学号", student.getSid());
            map.put("等级", student.getDegree());
            map.put("总成绩", String.valueOf(student.getTotal_score()));
            map.put("发布情况", student.isScore_lock() ? "已发布" : "未发布");
            System.out.println(map.get("发布情况") + "********");
            for (SpecialScore score : scores) {
                map.put(score.getPro_name(), String.valueOf(score.getPro_score()));
            }
            //***********************
            maps.add(map);
        }
        return maps;
    }

    @Transactional
    public boolean updateSpScore(SpecialStudent specialStudent, HashMap<String, String> map) {

        boolean flag = true;
        String sid = map.get("sid");
        String tName = map.get("tName");
        for (String key : map.keySet()) {
            switch (key) {
                case "等级":
                    specialStudent.setDegree(map.get(key));
                    break;
                case "总成绩":
                    specialStudent.setTotal_score(Float.parseFloat(map.get(key)));
                    break;
                case "reason":
                case "tName":
                case "sid":
                    continue;
                default:
                    try {
                        spScoreRepository.updateSpScore(specialStudent.getSid(), key, map.get(key));
                    } catch (Exception e) {
                        flag = false;
                        e.printStackTrace();
                    }
                    break;
            }
            String reason = map.get("reason");
            ScoreUpdate update = new ScoreUpdate();
            update.setSid(sid);
            update.setReason(reason);
            update.setTName(tName);
            update.setUpdate_time(new Timestamp(System.currentTimeMillis()));
            scoreUpdateRepository.saveAndFlush(update);

        }
        return flag;
    }

    @Transactional
    public void releaseSpScore(Map<String, String> map) {

        String sids = map.get("sid");
        for (String sid : sids.split(",")) {
            spScoreRepository.releaseSpScore(sid);

        }
    }
}
