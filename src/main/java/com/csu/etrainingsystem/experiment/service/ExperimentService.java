package com.csu.etrainingsystem.experiment.service;
import com.csu.etrainingsystem.experiment.entity.Experiment;
import com.csu.etrainingsystem.experiment.repository.ExperimentRepository;
import io.swagger.models.auth.In;
import org.apache.commons.collections.IteratorUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.List;
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
    public void addExperimentByHand(Experiment experiment) {

        this.experimentRepository.addExp (experiment.getClass_time (),experiment.getTemplate_id (),
               experiment.getS_group_id () ,experiment.getPro_name (),
                experiment.getT_group_id (),experiment.getBatch_name ());
    }
    @Transactional
    public void addall(Iterable<Experiment> experiments) {
        this.experimentRepository.saveAll (experiments);
    }
    @Transactional
    public Experiment getExperiment(int id) {
        Optional<Experiment> experiment=experimentRepository.findExperimentByExp_id(id);
        return experiment.get();
    }

    /**
     * 该批次的排课
     * @param batch_name
     * @return
     */
    @Transactional
    public Iterable<Experiment> getExperimentByBatch(String batch_name) {
        return this.experimentRepository.findExperimentByBatch (batch_name);
    }

    @Transactional
    public Iterable<String>  getAllTemplate() {
        return this.experimentRepository.findAllTemplate ();
    }

    @Transactional
    public Iterable<Experiment> getAllExperiment() {
        return this.experimentRepository.findAllExperiment();
    }

    /**
     * 获取一个批次为空的模板
     * @param template_id
     * @return
     */
    @Transactional
    public Iterable<Experiment> getExperimentByTemplate(String template_id) {
        return this.experimentRepository.findExperimentByTemplate (template_id);
    }

    /**
     * 查询学生的课表,传入一个学生id,查询学生,然后找到分组和批次,根据分组和批次,查询他的排课
     * @param s_group_id
     * @param batch_name
     * @return
     */
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
    @Transactional
    public void  modifyTemplate(Iterable<Experiment> experiments) {
       //将传递过来的实验的templateid获取出来,然后删除数据库中的记录,
        //然后再更新,已经存在的就更新了,删除的因为id变化,无法恢复
        if(experiments!=null) {
            List<Experiment> experimentList = IteratorUtils.toList (experiments.iterator ());
            System.out.println (experimentList.toString ());
            Experiment experiment= experimentList.get (0);
          String   templateId =experiment.getTemplate_id ();
            System.out.println (templateId);
           // System.out.println (experiments.toString ());
          //修改模板的时候需要将之前的模板清空
          experimentRepository.deleteExperimentByTemplate (templateId);
            for (Experiment experiment1 : experiments) {
               experimentRepository.save (experiment1);
            }
        }
    }



}
