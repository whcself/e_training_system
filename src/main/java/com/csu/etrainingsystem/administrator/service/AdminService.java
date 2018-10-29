package com.csu.etrainingsystem.administrator.service;

import com.csu.etrainingsystem.administrator.entity.Admin;
import com.csu.etrainingsystem.administrator.entity.Batch;
import com.csu.etrainingsystem.administrator.repository.AdminRepository;
import com.csu.etrainingsystem.administrator.repository.BatchRepository;
import com.csu.etrainingsystem.student.entity.Student;
import com.csu.etrainingsystem.student.repository.StudentRepository;
import com.csu.etrainingsystem.teacher.entity.Marking;
import com.csu.etrainingsystem.teacher.entity.Teacher;
import com.csu.etrainingsystem.teacher.repository.MarkingRepository;
import com.csu.etrainingsystem.util.ExcelPort;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.Optional;

@Service
public class AdminService {
    private final AdminRepository adminRepository;
    private final BatchRepository batchRepository;
    private final StudentRepository studentRepository;
    private final MarkingRepository markingRepository;

    @Autowired
    public AdminService(AdminRepository adminRepository, BatchRepository batchRepository, StudentRepository studentRepository, MarkingRepository markingRepository) {
        this.adminRepository = adminRepository;
        this.batchRepository = batchRepository;
        this.studentRepository = studentRepository;
        this.markingRepository = markingRepository;
    }


    /**
     * admin 的增删改查
     * @param admin 管理员
     */
    @Transactional
    public void save(Admin admin){
       adminRepository.save(admin);
    }

    @Transactional
    public void deleteAdmin(String id){
        adminRepository.deleteById(id);
    }

    @Transactional
    public void updateAdmin(Admin admin){
        adminRepository.saveAndFlush(admin);
    }

    public Iterable<Admin> getAllAdmin(){ return adminRepository.findAll(); }

    public Admin getAdminById(String id){
        Optional<Admin>op=adminRepository.findById(id);
        return op.get();
    }


    /****batch 的增删改查****/

    @Transactional
    public void addBatch( Batch batch){
        batchRepository.save(batch);
    }

    @Transactional//假删除,先取出,修改del_status字段,写回数据库,然后调用函数,做级联操作
    public void deleteBatch(String id){
        Batch batch = batchRepository.getOne(id);
        batch.setDel_status(true);
        batchRepository.saveAndFlush(batch);
        batchRepository.deleteStudentByBatchStatusSQL(batch.getBatch_name());

        // TODO: 2018/10/27 消除删除一个批次所带来的影响:如下 
        //删除一个批次所带来的影响:批次中的学生删除,批次中的学生组被删除,该批次的工序被删除
        //在下面的语句中消除该影响
    }

    public void updateBatch(Batch batch){
        batchRepository.saveAndFlush(batch);
    }

    public Batch getBatch(String id){
        Optional<Batch> op = batchRepository.findById(id);
        return op.get();
    }

    public Iterable<Batch>getAllBatch(){
        return batchRepository.findAll();
    }


    /****打分权限的增删改查****/

    @Transactional
    public void addMarking(Marking marking){
        markingRepository.save(marking);
    }

    @Transactional
    public Marking getMarkingById(String id){return markingRepository.getOne(id);}

    @Transactional
    public Iterable<Marking>getAllMarking(){return markingRepository.findAll();}

    @Transactional
    public void updateMarking(Marking marking){markingRepository.saveAndFlush(marking);}

    @Transactional
    public void deleteMarkingById(String id){
        Marking marking=markingRepository.getOne(id);
        marking.setDel_status(true);
        markingRepository.saveAndFlush(marking);
      /*
      todo:消除删除一个老师所带来的影响
       */
    }

    /**
     *
     * @param path 文件路径，绝对
     * @return 学生列表
     */
    public ArrayList<Student> importStudent(String path){
        ArrayList<Student> students=ExcelPort.readExcel(path);
        for(Student student:students)studentRepository.save(student);
        return students;
    }


}
