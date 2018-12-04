package com.csu.etrainingsystem.procedure.service;


import com.csu.etrainingsystem.experiment.service.ExperimentService;
import com.csu.etrainingsystem.form.CommonResponseForm;
import com.csu.etrainingsystem.procedure.entity.Proced;
import com.csu.etrainingsystem.procedure.entity.ProcedId;
import com.csu.etrainingsystem.procedure.entity.ProcedTemplateId;
import com.csu.etrainingsystem.procedure.entity.Proced_template;
import com.csu.etrainingsystem.procedure.repository.ProcedTemplateRepository;
import com.csu.etrainingsystem.procedure.repository.ProcedureRepository;
import com.csu.etrainingsystem.score.service.ScoreService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;
import java.util.Map;

@Service
public class ProcedureService {
    private final ProcedureRepository procedureRepository;
    private final ProcedTemplateRepository procedTemplateRepository;
    private final ScoreService scoreService;
    private final ExperimentService experimentService;
    String 胡俊贤="123";

    @Autowired
    public ProcedureService(ProcedTemplateRepository procedTemplateRepository,ProcedureRepository procedureRepository, ScoreService scoreService, ExperimentService experimentService) {
        this.procedureRepository = procedureRepository;
        this.procedTemplateRepository=procedTemplateRepository;
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
    public void addProcedToGroup(String groupName,String proName) {
        Proced proced = new Proced();
        proced.setT_group_id(groupName);
        proced.setProid(new ProcedId(proName,"conn"));
        procedureRepository.save(proced);
    }

    @Transactional
    public void updateProcedFromGroup(String groupName,String old,String newName){
        procedureRepository.updateProcedFromGroup(groupName,old,newName);
    }
    @Transactional
    public void deleteProcedFromGroup(String groupName,String proName){
        procedureRepository.deleteProcedFromGroup(groupName,proName);
    }

    /**
     *
     * @param batch_name 批次名
     * @param pro_name 工序名
     * @param weight 权重值
     * update必须要@Transactional， 要不然报错
     */
    @Transactional
    public void setWeight(String batch_name,String pro_name,float weight){
        procedureRepository.setWeightByBatchNameAndProName(batch_name,pro_name,weight);
    }

    /**
     * 管理员端-增加权重模板
     * @param form template
     */
    @Transactional
    public void addTemplate(String templateName,Map<String,Float> form) {
        for(String proName:form.keySet()){

            Proced_template template=new Proced_template();
            template.setProcedTemplateId(new ProcedTemplateId(templateName,proName));
            template.setWeight(form.get(proName));

            procedTemplateRepository.save(template);

        }
    }

    @Transactional
    public Iterable<String>findAllTemplateName(){
        return procedureRepository.findAllTemplateName();
    }

    @Transactional
    public List<Map<String,Float>>findTemplateItemByName(String name){
        return procedureRepository.findTemplateItemByName(name);
    }

    @Transactional
    public CommonResponseForm deleteTemplate(String name){
        procedureRepository.deleteTemplate(name);
        return CommonResponseForm.of204("删除成功");
    }
    @Transactional
    public void band(String batchName,String templateName){
        List<Proced_template>templates= (List<Proced_template>) procedTemplateRepository.findByTemplateName(templateName);
        for(Proced_template template:templates){
            Proced proced=new Proced();
            proced.setWeight(template.getWeight());
            String tGroupName=procedureRepository.getTGroupByProName(template.getProcedTemplateId().getPro_name());
            proced.setT_group_id(tGroupName);
            proced.setProid(new ProcedId(template.getProcedTemplateId().getPro_name(),batchName));
            procedureRepository.save(proced);
        }

    }

//    /**
//     * 加打分项，与老师组
//     */
//    @Transactional
//    public void addProced()
}
