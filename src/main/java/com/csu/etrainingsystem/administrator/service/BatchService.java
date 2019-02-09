package com.csu.etrainingsystem.administrator.service;

import com.csu.etrainingsystem.administrator.entity.Batch;
import com.csu.etrainingsystem.administrator.entity.Semester;
import com.csu.etrainingsystem.administrator.form.SemesterForm;
import com.csu.etrainingsystem.administrator.repository.BatchRepository;
import com.csu.etrainingsystem.administrator.repository.SemesterRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.List;

@Repository
public class BatchService {
    private BatchRepository batchRepository;
    private SemesterRepository semesterRepository;

    @Autowired
    public BatchService(BatchRepository batchRepository, SemesterRepository semesterRepository) {
        this.batchRepository = batchRepository;
        this.semesterRepository = semesterRepository;
    }

    public List<Batch> getBatchBySemester(String semesterName) {
        return batchRepository.findBatchBySemester_name(semesterName);
    }

    public List<SemesterForm> getAllSemesterName() {
      List<Object[]> semesters= batchRepository.findAllSemesterName();
       List<SemesterForm> semesterForms=new ArrayList<SemesterForm> ();
       int index=0;
        for (Object[] semester : semesters) {
            semesterForms.add (new SemesterForm ((String)semester[0],(String)semester[1],index++));
        }
        return semesterForms;
    }

    public void addSemester(String semesterName) {
        Batch batch = new Batch();
        batch.setSemester_name(semesterName);
        batch.setBatch_name("semester");
        batchRepository.save(batch);
    }

    @Transactional
    public void updateSemesterName(String old, String semesterName) {
//        semesterRepository.save(new Semester(semesterName));
        batchRepository.updateSemesterName(old, semesterName);
    }

    @Transactional
    public void deleteSemester(String semesterName) {
        batchRepository.deleteSemester(semesterName);
    }

    public Iterable<String>getAllSGroup(String batchName){
       return batchRepository.getAllSGroup(batchName);
    }

    @Transactional
    public void updateBeginDate(String beginDate,String semesterName){
        batchRepository.updateBeginDate(beginDate,semesterName);
    }

}
