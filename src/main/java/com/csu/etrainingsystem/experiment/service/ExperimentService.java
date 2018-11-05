package com.csu.etrainingsystem.experiment.service;
import com.csu.etrainingsystem.experiment.entity.Experiment;
import com.csu.etrainingsystem.experiment.repository.ExperimentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.Optional;

@Service
public class ExperimentService {
    private final ExperimentRepository experimentRepository;
    @Autowired
    public ExperimentService(ExperimentRepository experimentRepository) {
        this.experimentRepository = experimentRepository;
    }

    @Transactional
    public void addExperiment(Experiment experiment) {
        this.experimentRepository.save(experiment);
    }
    @Transactional
    public Experiment getExperiment(int id) {
        Optional<Experiment> experiment=experimentRepository.findExperimentByExp_id(id);
        return experiment.get();
    }
    @Transactional
    public Iterable<Experiment> getAllExperiment() {
        return this.experimentRepository.findAllExperiment();
    }
    @Transactional
    public Iterable<Experiment> getStudentExperiment(String s_group_id,String batch_name) {

        return this.experimentRepository.findStudentExperiment (s_group_id,batch_name);
    }


    @Transactional
    public void  updateExperiment(Experiment Experiment) {
        this.experimentRepository.saveAndFlush(Experiment);
    }
    @Transactional
    public void  deleteExperiment(int id) {

        Experiment experiment=getExperiment(id);
        if(experiment!=null){
            experiment.setDel_status(true);
            updateExperiment(experiment);
        }
    }

    @Transactional
    public void  deleteExperimentByPro(String pro_name) {
         this.experimentRepository.deleteExperimentByPro_name(pro_name);
    }

    @Transactional
    public void  deleteExperimentByBatch(String batch_name) {
        this.experimentRepository.deleteExperimentByBatch(batch_name);
    }

    @Transactional
    public void  deleteExperimentByS_group(String s_group_id,String batch_name) {
        this.experimentRepository.deleteExperimentByS_groupAndBatch_name (s_group_id,batch_name);
    }
    @Transactional
    public void  deleteExperimentByT_group(String t_group_id) {
        this.experimentRepository.deleteExperimentByT_group(t_group_id);
    }

}
