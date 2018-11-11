package com.csu.etrainingsystem.procedure.service;


import com.csu.etrainingsystem.experiment.service.ExperimentService;
import com.csu.etrainingsystem.procedure.entity.Proced;
import com.csu.etrainingsystem.procedure.entity.ProcedId;
import com.csu.etrainingsystem.procedure.repository.ProcedureRepository;
import com.csu.etrainingsystem.score.service.ScoreService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.Optional;

@Service
public class ProcedureService {
    private final ProcedureRepository procedureRepository;
    private final ScoreService scoreService;
    private final ExperimentService experimentService;
    @Autowired
    public ProcedureService(ProcedureRepository procedureRepository, ScoreService scoreService, ExperimentService experimentService) {
        this.procedureRepository = procedureRepository;
        this.scoreService = scoreService;
        this.experimentService = experimentService;
    }

    /**
     * 复合主键,添加方法见studentgroup表或者teachergroup表
     * @param procedure
     */
    @Transactional
    public void addProcedure(Proced procedure) {
        this.procedureRepository.save(procedure);
    }

    @Transactional
    public  Iterable<Proced> getBatchProcedure(String batch_name) {

        return procedureRepository.findProcedByBatch_name(batch_name);
    }
    @Transactional
    public  Proced getProcedure(ProcedId procedId) {
        return procedureRepository.findProcedByNameAndBatch (procedId.getPro_name (),procedId.getBatch_name ()).get ();
    }
    @Transactional
    public Iterable<Proced> getAllProcedure() {
        return this.procedureRepository.findAllProced();
    }

    @Transactional
    public void  updateProcedure(Proced Procedure) {
        this.procedureRepository.saveAndFlush(Procedure);
    }
    public void deleteProcedByBatch(String batch_name){
     Iterable<Proced> proceds= this.procedureRepository.findProcedByBatch_name(batch_name);
       if(proceds!=null){
           for (Proced proced : proceds) {
               proced.setDel_status(true);
               updateProcedure(proced);
           }
       }

    }

    @Transactional
    public void  deleteProcedure(String pro_name,String batch_name) {

        ProcedId  procedId=new ProcedId (pro_name,batch_name);
        Proced  procedure=getProcedure(procedId);
        if(procedure!=null){
            procedure.setDel_status(true);
            //删除这个工序在成绩表里面的记录
            this.scoreService.deleteScoreByPro(pro_name);
            //删除这个工序在实验表的记录
            this.experimentService.deleteExperimentByPro(pro_name);
        }
       /*
       todo:消除删除一个工序所带来的影响
       即:删除这个工序的实验记录,删除成绩表中,这个工序的成绩记录,删除加班表中该工序对应的记录(加班记录先保留?)
        */
    }

    @Transactional
    public void setWeight(String batch_name,String pro_name,float weight){
        procedureRepository.setWeightByBatchNameAndProName(batch_name,pro_name,weight);
    }
}
