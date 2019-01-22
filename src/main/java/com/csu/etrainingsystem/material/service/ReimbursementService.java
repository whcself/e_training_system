package com.csu.etrainingsystem.material.service;

import com.csu.etrainingsystem.material.repository.ReimbursementRepository;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;

@Service
@Transactional
public class ReimbursementService {

    private  final ReimbursementRepository reimbursementRepository ;

    public ReimbursementService(ReimbursementRepository reimbursementRepository) {

        this.reimbursementRepository = reimbursementRepository;
    }

    public int getAllReimbNumByPId(String pid){
        return   reimbursementRepository.getAllReimbNum (pid);
    }
}
