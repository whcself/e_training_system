package com.csu.etrainingsystem.administrator.service;

import com.csu.etrainingsystem.administrator.entity.Admin;
import com.csu.etrainingsystem.administrator.entity.Batch;
import com.csu.etrainingsystem.administrator.entity.Semester;
import com.csu.etrainingsystem.administrator.repository.AdminRepository;
import com.csu.etrainingsystem.administrator.repository.BatchRepository;
import com.csu.etrainingsystem.administrator.repository.SemesterRepository;
import com.csu.etrainingsystem.experiment.service.ExperimentService;
import com.csu.etrainingsystem.procedure.service.ProcedureService;
import com.csu.etrainingsystem.student.entity.Student;
import com.csu.etrainingsystem.student.service.StudentService;
import com.csu.etrainingsystem.student.service.StudentGroupService;
import com.csu.etrainingsystem.teacher.entity.Marking;
import com.csu.etrainingsystem.teacher.service.MarkingService;
import com.csu.etrainingsystem.user.entity.User;
import com.csu.etrainingsystem.user.repository.UserRepository;
import com.csu.etrainingsystem.user.service.UserService;
import com.csu.etrainingsystem.util.ExcelPort;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.transaction.Transactional;
import java.io.*;
import java.util.ArrayList;
import java.util.Optional;

@Service
public class AdminService {
    private final AdminRepository adminRepository;
    private final BatchRepository batchRepository;
    private final StudentGroupService studentGroupService;
    private final ExperimentService experimentService;
    private final ProcedureService procedureService;
    private final MarkingService markingService;
    private final StudentService studentService;
    private final UserRepository userRepository;
private final SemesterRepository semesterRepository;
    @Autowired
    public AdminService(AdminRepository adminRepository, SemesterRepository semesterRepository, BatchRepository batchRepository, StudentGroupService studentGroupService, ExperimentService experimentService, ProcedureService procedureService, MarkingService markingService, StudentService studentService, UserRepository userRepository) {
        this.semesterRepository=semesterRepository;
        this.adminRepository = adminRepository;
        this.batchRepository = batchRepository;
        this.studentGroupService = studentGroupService;
        this.experimentService = experimentService;
        this.procedureService = procedureService;
        this.markingService = markingService;
        this.studentService = studentService;
        this.userRepository=userRepository;
    }

    /**
     * admin 的增删改查
     *
     * @param admin 管理员
     */
    @Transactional
    public void save(Admin admin) {
        User user=new User ();
        user.setRole ("admin");
        user.setAccount (admin.getAid ());
        user.setPwd ("123456");
        userRepository.save (user);
        adminRepository.save(admin);
    }

    /**
     * 删除一个管理员不会带来影响
     */
    @Transactional
    public void deleteAdmin(String id) {

        Admin admin = getAdminById(id);
        if (admin != null) {
            admin.setDel_status(true);
            updateAdmin(admin);
        }
    }

    @Transactional
    public void updateAdmin(Admin admin) {
        adminRepository.saveAndFlush(admin);
    }


    public Iterable<Admin> getAllAdmin() {
        return adminRepository.findAllAdmin();
    }

    public Admin getAdminById(String id) {
        Optional<Admin> admin = adminRepository.findAdminByAid(id);
        return admin.get();
    }

    /****batch 的增删改查****/

    @Transactional
    public void addBatch(Batch batch) {
        String semesterName=batch.getSemester_name();
        semesterRepository.save(new Semester(semesterName));

        batchRepository.save(batch);
    }

    @Transactional//假删除,先取出,修改del_status字段,写回数据库,然后调用函数,做级联操作
    public void deleteBatch(String id) {
        Batch batch = batchRepository.getOne(id);
        if (batch != null) {
            batch.setDel_status(true);
            batchRepository.saveAndFlush(batch);
            //批次中的学生组被删除
            this.studentGroupService.deleteStudentGroupByBatch(id);
            //该批次的工序被删除
            this.procedureService.deleteProcedByBatch(id);
            //该批次的实验被删除
            this.experimentService.deleteExperimentByBatch(id);
        }
    }

    public void updateBatch(Batch batch) {
        batchRepository.saveAndFlush(batch);
    }


    public Batch getBatch(String batch_name) {
        Optional<Batch> op = batchRepository.findBatchByName(batch_name);
        return op.get();
    }

    public Iterable<Batch> getAllBatch() {
        return batchRepository.findAllBatch();
    }


    /****打分权限的增删改查,调用service层更好****/

    @Transactional
    public void addMarking(Marking marking) {

        this.markingService.addMarking(marking);
    }

    /**
     * 考虑到实际情况中打分权限表id字段没有意义,所以这个改为用t_group_id查询以及查询全部
     *
     * @param t_group_id
     * @return
     */
    @Transactional
    public Iterable<Marking> getMarkingById(String t_group_id) {
        return this.markingService.getMarkingByT_group_id(t_group_id);
    }

    @Transactional
    public Iterable<Marking> getAllMarking() {
        return this.markingService.getAllMarking();
    }

    @Transactional
    public void updateMarking(Marking marking) {
        markingService.updateMarking(marking);
    }

    @Transactional
    public void deleteMarkingById(int id) {
        this.markingService.deleteMarkingById(id);
      /*
      todo:消除删除一个老师所带来的影响
       */
    }

    /**
     * -ScJn 2018.10.26
     *
     * @param batchName 2018S101/2018S201/2018S501
     * @return the students list
     * <p>
     * 2018 11.3 update:
     * @apiNote 管理员端-学生管理
     */
    public ArrayList<Student> importStudent(MultipartFile file, String batchName) {
        ArrayList<Student> students = ExcelPort.readExcel(file, batchName);
        for (Student student : students) studentService.addStudent(student);
        return students;
    }


    public void downloadTemplate(HttpServletRequest request, HttpServletResponse response,int way) throws IOException {
        OutputStream out = new BufferedOutputStream(response.getOutputStream());
        response.setHeader("Content-Disposition", "fileName=template.xlsx");
        response.setContentType("application/octet-stream;charset=UTF-8");
        String filePath;
        if(way==1)
             filePath = "C:\\doing\\template.xlsx";
        else
            filePath = "C:\\doing\\ScoreTemplate.xlsx";
//        InputStream in = new FileInputStream()

        System.out.println("*************" + filePath);
        InputStream in = new FileInputStream(filePath);
        int b;
        while ((b = in.read()) != -1) {
            out.write(b);
        }

        in.close();
        out.close();
    }


}
