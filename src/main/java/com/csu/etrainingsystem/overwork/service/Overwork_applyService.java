package com.csu.etrainingsystem.overwork_apply.service;
import com.csu.etrainingsystem.overwork.entity.Overwork_apply;
import com.csu.etrainingsystem.overwork.repository.Overwork_applyRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.Optional;

@Service
public class Overwork_applyService {
    private final Overwork_applyRepository overwork_applyRepository;
    @Autowired
    public Overwork_applyService(Overwork_applyRepository overwork_applyRepository) {
        this.overwork_applyRepository = overwork_applyRepository;
    }

    @Transactional
    public void addOverwork_apply(Overwork_apply overwork_apply) {
        this.overwork_applyRepository.save(overwork_apply);
    }
    @Transactional
    public Overwork_apply getOverwork_apply(int apply_id) {
        Optional<Overwork_apply> overwork_apply=overwork_applyRepository.findOverwork_applyByApply_id(apply_id);
        return overwork_apply.get();
    }
    @Transactional
    public Iterable<Overwork_apply> getAllOverwork_apply() {
        return this.overwork_applyRepository.findAllOverwork_apply();
    }

    @Transactional
    public void  updateOverwork_apply(Overwork_apply Overwork_apply) {
        this.overwork_applyRepository.saveAndFlush(Overwork_apply);
    }
    @Transactional
    public void  deleteOverwork_apply(int apply_id) {

          Overwork_apply overwork_apply=getOverwork_apply(apply_id);
          if(overwork_apply!=null){
              overwork_apply.setDel_status(true);
              updateOverwork_apply(overwork_apply);

          }
        /*
       todo:消除删除一个加班申请所带来的影响
       即:似乎无影响,需要有一个根据学号删除申请的记录,但是并没有多大作用,先留白
        */
    }

}
