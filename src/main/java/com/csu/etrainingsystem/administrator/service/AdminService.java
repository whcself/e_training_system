package com.csu.etrainingsystem.administrator.service;

import com.csu.etrainingsystem.administrator.entity.Admin;
import com.csu.etrainingsystem.administrator.entity.Batch;
import com.csu.etrainingsystem.administrator.repository.AdminRepository;
import com.csu.etrainingsystem.administrator.repository.BatchRepository;
import com.csu.etrainingsystem.student.entity.Student;
import com.csu.etrainingsystem.student.repository.StudentRepository;
import com.csu.etrainingsystem.util.ExcelPort;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.Optional;

@Service
public class AdminService {
    private final AdminRepository adminRepository;
    private final BatchRepository batchRepository;
    private final StudentRepository studentRepository;
    @Autowired
    public AdminService(AdminRepository adminRepository,BatchRepository batchRepository,StudentRepository studentRepository) {
        this.adminRepository=adminRepository;
        this.batchRepository=batchRepository;
        this.studentRepository=studentRepository;
    }
    @Transactional
    public Admin save(Admin admin){
        return adminRepository.save(admin);
    }
    @Transactional
    public void deleteAdmin(String id){
        adminRepository.deleteById(id);
    }
    @Transactional
    public void updateAdmin(Admin admin){
        adminRepository.saveAndFlush(admin);
    }
    public Iterable<Admin> getAllAdmin(){
        return adminRepository.findAll();
    }
    public Admin getAdminById(String id){
        Optional<Admin>op=adminRepository.findById(id);
        return op.get();
    }


    @Transactional
    public void addBatch( Batch batch){
        batchRepository.save(batch);
    }
    @Transactional
    public void deleteBatch(String id){
        batchRepository.deleteById(id);

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
    public ArrayList<Student> importStudent(String path){
        ArrayList<Student> students=ExcelPort.readExcel(path);
        for(Student student:students)studentRepository.save(student);
        return students;
    }
}
