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
    public boolean importStudent(){
        try{

            /**/

            return true;
        }catch (Exception e){
            return false;
        }
    }
}
