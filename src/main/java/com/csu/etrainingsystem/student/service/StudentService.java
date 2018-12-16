package com.csu.etrainingsystem.student.service;


import com.csu.etrainingsystem.experiment.entity.Experiment;
import com.csu.etrainingsystem.experiment.service.ExperimentService;
import com.csu.etrainingsystem.form.CommonResponseForm;
import com.csu.etrainingsystem.overwork.service.Overwork_applyService;
import com.csu.etrainingsystem.score.entity.SpecialScore;
import com.csu.etrainingsystem.score.service.ScoreService;
import com.csu.etrainingsystem.student.entity.SpecialStudent;
import com.csu.etrainingsystem.student.entity.Student;
import com.csu.etrainingsystem.student.form.StudentInfoForm;
import com.csu.etrainingsystem.student.repository.SpStudentRepository;
import com.csu.etrainingsystem.student.repository.StudentRepository;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.*;
import java.util.*;

import javax.servlet.http.HttpServletResponse;
import javax.transaction.Transactional;

@Service
public class StudentService {


    private final StudentRepository studentRepository;
    private final ScoreService scoreService;
    private final Overwork_applyService overwork_applyService;
    private final SpStudentRepository spStudentRepository;
    private final ExperimentService experimentService;

    @Autowired
    public StudentService(StudentRepository studentRepository, ScoreService scoreService, Overwork_applyService overwork_applyService, SpStudentRepository spStudentRepository, ExperimentService experimentService) {
        this.studentRepository = studentRepository;
        this.scoreService = scoreService;
        this.overwork_applyService = overwork_applyService;
        this.spStudentRepository = spStudentRepository;
        this.experimentService = experimentService;
    }

    @Transactional
    public void releaseScore(String batchName) {
        studentRepository.releaseScore(batchName);
    }

    @Transactional
    public void addStudent(Student student) {
        studentRepository.save(student);
    }

    @Transactional
    public Student getStudentById(String id) {
        Optional<Student> op = studentRepository.findStudentBySid(id);
        return op.get();
    }

    @Transactional
    public Iterable<Student> getAllStudent() {
        return studentRepository.findAllStudent();
    }

    @Transactional
    public void deleteByS_group(String s_group_id, String batch_name) {
        Iterable<Student> students = this.studentRepository.findStudentByS_group_idAndBatch(s_group_id, batch_name);
        if (students != null) {
            for (Student student : students) {
                deleteById(student.getSid());
            }
        }

    }

    public void addStudentFromExcel(String path) {
    }

    @Transactional
    public void updateStudent(Student student) {
        studentRepository.saveAndFlush(student);
    }

    @Transactional
    public void deleteById(String sid) {
        Student student = getStudentById(sid);
        if (student != null) {
            //删除该学生的成绩记录
            this.scoreService.deleteScoreBySid(sid);
            //删除这个学生申请加班的记录,由于只是记录,就不删除了
            // this.overwork_applyService.deleteOverwork_apply();
            student.setDel_status(true);
            updateStudent(student);
        }
    }

    @Transactional
    public void deleteByBacth(String sid) {

    }

    @Transactional
    public Iterable<Student> findStudentByBatchName(String batchName) {
        return studentRepository.findStudentByBatch_name(batchName);
    }

    @Transactional
    public Iterable<Student> findStudentByBatchNameAndSGroup(String batchName, String groupId) {
        if (batchName == null) batchName = "%";
        if (groupId == null) groupId = "%";
        return studentRepository.findStudentByS_group_idAndBatch(groupId, batchName);
    }

    /**
     * 根据学号获取特殊学生
     *
     * @param sid
     * @return
     */
    @Transactional
    public SpecialStudent findSpStudentById(String sid) {
        return spStudentRepository.findSpStudentBySid(sid).get();
    }

    /**
     * 获取所有的特殊学生
     *
     * @return
     */
    @Transactional
    public Iterable<SpecialStudent> findAllSpStudent() {
        return spStudentRepository.findAllSpStudent();
    }

    @Transactional
    public void addSpStudent(Student student, String template_name) {
        SpecialStudent specialStudent = new SpecialStudent(student.getSid(), student.getSname(), student.getClazz(), template_name, student.getSdept(), student.getDepart(), student.getTotal_score(), student.isDel_status(), student.isScore_lock(), student.getDegree());

        Iterable<Experiment> experiments = experimentService.getStudentExperiment(student.getS_group_id(), student.getBatch_name());
        Iterable<SpecialScore> specialScores = new ArrayList<SpecialScore>();
        if (experiments != null) {
            for (Experiment experiment : experiments) {
                SpecialScore s = new SpecialScore();
                s.setSid(specialStudent.getSid());
                s.setPro_name(experiment.getPro_name());
                s.setTime_quant(experiment.getTime_quant());
                s.setClass_time(experiment.getClass_time());
                scoreService.addSpScore(s);
            }
        }
        spStudentRepository.save(specialStudent);
    }

    @Transactional
    public CommonResponseForm updateSGroup(String sid, String sGroupId) {
        Optional<Student> optionalStudent = studentRepository.findStudentBySid(sid);
        if (optionalStudent.isPresent()) {
            Student student = optionalStudent.get();
            student.setS_group_id(sGroupId);
            studentRepository.save(student);
        } else {
            return CommonResponseForm.of400("该学号不存在");
        }
        return CommonResponseForm.of204("修改成功");
    }


    /**
     * 将List集合数据写入excel（单个sheet）
     */
    public void downloadStudentList(List<StudentInfoForm> formList, HttpServletResponse response) throws Exception {
        System.out.println("开始写入文件>>>>>>>>>>>>");
        HSSFWorkbook workbook = new HSSFWorkbook();
        //create sheet
        Sheet sheet = workbook.createSheet("StudentList");
        int rowIndex = 0;//标识位，用于标识sheet的行号
        String[] excelTitle = {"学号", "姓名", "班级", "批次", "组号"};
        try {
            //写表头数据
            Row titleRow = sheet.createRow(rowIndex);
            for (int i = 0; i < excelTitle.length; i++) {
                //创建表头单元格,填值
                titleRow.createCell(i).setCellValue(excelTitle[i]);
            }
            System.out.println("表头写入完成>>>>>>>>");
            rowIndex++;
            //循环写入主表数据
            for (StudentInfoForm studentInfoForm :formList) {
                //create sheet row
                Row row = sheet.createRow(rowIndex);
                //create sheet coluum(单元格)
                Cell cell0 = row.createCell(0);
                cell0.setCellValue(studentInfoForm.getSid());
                Cell cell1 = row.createCell(1);
                cell1.setCellValue(studentInfoForm.getSname());
                Cell cell2 = row.createCell(2);
                cell2.setCellValue(studentInfoForm.getClazz());
                Cell cell3 = row.createCell(3);
                cell3.setCellValue(studentInfoForm.getBatch_name());
                Cell cell4 = row.createCell(4);
                cell4.setCellValue(studentInfoForm.getS_group_id());
                rowIndex++;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        OutputStream out = new BufferedOutputStream(response.getOutputStream());
        response.setHeader("Content-Disposition", "fileName=studentList.xlsx");
        response.setContentType("application/octet-stream;charset=UTF-8");
        workbook.write(out);
        out.close();
    }

    @Transactional
    public void updateSpStudent(SpecialStudent student) {
        spStudentRepository.saveAndFlush(student);
    }

    @Transactional
    public void deleteSpStudentById(String sid) {
        SpecialStudent student = findSpStudentById(sid);
        if (student != null) {
            //删除该学生的成绩记录
            //这部分等特殊分数表完成后调用
            //todo:删除学生之后也需要将对应的成绩表删除
            scoreService.deleteSpScoreBySid(sid);
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