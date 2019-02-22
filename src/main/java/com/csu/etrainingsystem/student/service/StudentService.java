package com.csu.etrainingsystem.student.service;


import com.csu.etrainingsystem.experiment.entity.Experiment;
import com.csu.etrainingsystem.experiment.service.ExperimentService;
import com.csu.etrainingsystem.form.CommonResponseForm;
import com.csu.etrainingsystem.overwork.service.Overwork_applyService;
import com.csu.etrainingsystem.procedure.repository.ProcedTemplateRepository;
import com.csu.etrainingsystem.score.entity.SpecialScore;
import com.csu.etrainingsystem.score.service.ScoreService;
import com.csu.etrainingsystem.student.entity.SpecialStudent;
import com.csu.etrainingsystem.student.entity.Student;
import com.csu.etrainingsystem.student.form.StudentInfoForm;
import com.csu.etrainingsystem.student.repository.SpStudentRepository;
import com.csu.etrainingsystem.student.repository.StudentRepository;
import com.csu.etrainingsystem.user.entity.User;
import com.csu.etrainingsystem.user.repository.UserRepository;
import com.csu.etrainingsystem.user.service.UserService;
import com.csu.etrainingsystem.util.EncryptUtil;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

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
    private final ProcedTemplateRepository procedTemplateRepository;
    private  final UserRepository userRepository;

    @Autowired
    public StudentService(ProcedTemplateRepository procedTemplateRepository, StudentRepository studentRepository, ScoreService scoreService, Overwork_applyService overwork_applyService, SpStudentRepository spStudentRepository, ExperimentService experimentService, UserRepository userRepository) {
        this.procedTemplateRepository = procedTemplateRepository;
        this.studentRepository = studentRepository;
        this.scoreService = scoreService;
        this.overwork_applyService = overwork_applyService;
        this.spStudentRepository = spStudentRepository;
        this.experimentService = experimentService;
        this.userRepository = userRepository;
    }

    @Transactional
    public void releaseScore(String batchName) {
        studentRepository.releaseScore(batchName);
    }

    @Transactional
    public void addStudent(Student student) throws Exception {
        String id=student.getSid();
        System.out.println(id);
        if(studentRepository.findStudentBySid(id).isPresent()){
            throw new Exception("#学号重复，该学生已经存在"+id+"#");
        }
        if(spStudentRepository.findSpStudentBySid(id)!=null){
            throw new Exception("#账号重复，该学生已存在，为特殊学生"+id+"#");
        }
        User user=new User ();
        user.setRole ("student");
        user.setAccount (student.getSid ());
        user.setPwd ("e10adc3949ba59abbe56e057f20f883e");
        userRepository.save (user);
        studentRepository.save(student);
    }

    @Transactional
    public Student getStudentById(String id) {
        Optional<Student> op = studentRepository.findStudentBySid(id);
        return op.get();
    }

    public List<Student> getStudentByName(String name){
        return (List<Student>) studentRepository.findStudentBySName(name);
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
            //删除user用户
            student.setDel_status(true);
            updateStudent(student);
            //删除用户
            userRepository.delById (sid);
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
        if (batchName.equals("all")) batchName = "%";
        if (groupId.equals("all")) groupId = "%";
        return studentRepository.findStudentByS_group_idAndBatch(groupId, batchName);
    }

    public CommonResponseForm findStudentByBatchNameAndSGroupOrNameAndId(String batchName, String groupId, String sid, String sname) {
        ArrayList<Student> students;
        if (!sid.equals("all")) {
            Optional<Student> studentOptional = studentRepository.findStudentBySid(sid);
            if (studentOptional.isPresent()) {
                return CommonResponseForm.of200("查询成功", studentOptional.get());
            } else {
                return CommonResponseForm.of400("查询失败，该学号不存在");
            }
//            return studentOptional.map(student -> CommonResponseForm.of200("查询成功", student)).orElseGet(() -> CommonResponseForm.of400("查询失败，该学号不存在")); 操作骚但是看不懂，不用

        } else if (!sname.equals("all")) {
            students = (ArrayList<Student>) studentRepository.findStudentBySName(sname);
            if (students.size() == 0) {
                return CommonResponseForm.of400("查询失败，改姓名不存在");
            }
        } else {
            students = (ArrayList<Student>) findStudentByBatchNameAndSGroup(batchName, groupId);
        }

        return CommonResponseForm.of200("查询成功，共:" + students.size() + "条数据", students);

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

    public List<SpecialStudent> findSpStudentsByTemplate(String templateName){
        return spStudentRepository.findByTemplateName(templateName);
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
        //添加特殊学生
        spStudentRepository.save(specialStudent);
        Iterable<Experiment> experiments = experimentService.getStudentExperiment(student.getS_group_id(), student.getBatch_name());
        Iterable<SpecialScore> specialScores = new ArrayList<SpecialScore>();
        if (experiments != null) {
            for (Experiment experiment : experiments) {
                SpecialScore s = new SpecialScore();
                s.setSid(specialStudent.getSid());
                s.setPro_name(experiment.getPro_name());
                s.setTime_quant(experiment.getTime_quant());
                s.setClass_time(experiment.getClass_time());
                s.setTid (experiment.getTid ());
                scoreService.addSpScore(s);
            }
        }

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
            for (StudentInfoForm studentInfoForm : formList) {
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

    public List<String> getSpProName(Map<String, String> spStudent) {
        String sid = spStudent.get("sid");
        SpecialStudent specialStudent = spStudentRepository.findSpStudentBySid(sid).get();
        return procedTemplateRepository.findProByName(specialStudent.getTemplate_name());
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
            //级联删除用户
            userRepository.delById (student.getSid ());
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