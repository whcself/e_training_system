package com.csu.etrainingsystem.administrator.service;

import com.csu.etrainingsystem.administrator.entity.Admin;
import com.csu.etrainingsystem.administrator.entity.Batch;
import com.csu.etrainingsystem.administrator.repository.AdminRepository;
import com.csu.etrainingsystem.administrator.repository.BatchRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.Optional;

@Service
public class AdminService {
    private final AdminRepository adminRepository;
    private final BatchRepository batchRepository;
    @Autowired
    public AdminService(AdminRepository adminRepository,BatchRepository batchRepository) {
        this.adminRepository=adminRepository;
        this.batchRepository=batchRepository;
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
    public boolean importStudent(){
        try{

            /**/

            return true;
        }catch (Exception e){
            return false;
        }
    }
}
