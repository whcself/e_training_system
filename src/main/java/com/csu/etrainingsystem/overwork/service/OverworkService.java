package com.csu.etrainingsystem.overwork.service;
import com.csu.etrainingsystem.overwork.entity.Overwork;
import com.csu.etrainingsystem.overwork.repository.OverworkRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.Optional;

@Service
public class OverworkService {
    private final OverworkRepository overworkRepository;
    @Autowired
    public OverworkService(OverworkRepository overworkRepository) {
        this.overworkRepository = overworkRepository;
    }

    @Transactional
    public void addOverwork(Overwork overwork) {
        this.overworkRepository.save(overwork);
    }
    @Transactional
    public Overwork getOverwork(int id) {
        Optional<Overwork> overwork=overworkRepository.findOverworkByOverwork_id(id);
        return overwork.get();
    }
    @Transactional
    public Iterable<Overwork> getAllOverwork() {
        return this.overworkRepository.findAllOverwork();
    }

    @Transactional
    public void  updateOverwork(Overwork Overwork) {
        this.overworkRepository.saveAndFlush(Overwork);
    }
    @Transactional
    public void  deleteOverwork(int id) {
        Overwork overwork=getOverwork(id);
        if (overwork!=null){
            overwork.setDel_status(true);
            updateOverwork(overwork);
        }

       /*
       todo:消除删除一个加班记录所带来的影响,似乎没有影响(常常会删除)
       即:似乎无影响
        */
    }

}
