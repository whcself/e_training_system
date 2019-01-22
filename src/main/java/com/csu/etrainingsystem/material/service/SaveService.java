package com.csu.etrainingsystem.material.service;

import com.csu.etrainingsystem.material.repository.SaveRepository;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;


@Service
@Transactional
public class SaveService {

    private final SaveRepository saveRepository;

    public SaveService(SaveRepository saveRepository) {
        this.saveRepository = saveRepository;
    }
    public int getAllSaveNum(String pid){
        return   saveRepository.getAllSaveNum (pid);
    }
}
