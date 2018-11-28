package com.csu.etrainingsystem.administrator.service;

import com.csu.etrainingsystem.administrator.entity.Batch;
import com.csu.etrainingsystem.administrator.repository.BatchRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

@Repository
public class BatchService {
    private BatchRepository batchRepository;

    @Autowired
    public BatchService(BatchRepository batchRepository) {
        this.batchRepository = batchRepository;
    }

    public Batch getBatchBySemester(String semesterName){
        return batchRepository.findBatchBySemester_name(semesterName);
    }
}
