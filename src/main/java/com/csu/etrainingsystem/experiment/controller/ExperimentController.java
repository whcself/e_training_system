package com.csu.etrainingsystem.experiment.controller;

import com.csu.etrainingsystem.experiment.entity.Experiment;
import com.csu.etrainingsystem.experiment.service.ExperimentService;
import com.csu.etrainingsystem.form.CommonResponseForm;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(value = "experiment",method = RequestMethod.POST)
public class ExperimentController {
    private final ExperimentService experimentService;
     @Autowired
    public ExperimentController(ExperimentService experimentService) {
        this.experimentService = experimentService;
    }
    @RequestMapping(value ="/addExperiment")
    public CommonResponseForm addExperiment(Experiment experiment){
        this.experimentService.addExperiment (experiment);
        return CommonResponseForm.of204("添加实验成功");
    }
    @RequestMapping(value ="/getExperiment/{id}")
    public CommonResponseForm getExperiment(@PathVariable("id") int id){
         return CommonResponseForm.of200("获取成功",this.experimentService.getExperiment (id));
    }
    @RequestMapping(value ="/getExperiment/{batch_name}")
    public CommonResponseForm getExperimentByBatch(@PathVariable("batch_name") String batch_name){
        return CommonResponseForm.of200("获取成功",this.experimentService.getExperimentByBatch (batch_name));
    }
    @RequestMapping(value ="/getAllExperiment")
    public CommonResponseForm getAllExperiment(){

        return CommonResponseForm.of200("获取成功", this.experimentService.getAllExperiment ());
    }
    @RequestMapping(value ="/updateExperiment/{id}")
    public CommonResponseForm updateStudentGroup(Experiment experiment){
        this.experimentService.updateExperiment (experiment);
        return CommonResponseForm.of204("更新成功");
    }
    @RequestMapping(value ="/deleteExperiment/{id}")
    public CommonResponseForm updateStudentGroup(@PathVariable("id") int id){
        this.experimentService.deleteExperiment (id);
        return CommonResponseForm.of204("删除成功");
    }
    @ApiOperation (value = "根据模板名称查询批次为空的模板")
    @RequestMapping(value ="/getTemplate/{template_id}")
    public CommonResponseForm getTemplate(@PathVariable("template_id") String template_id){
        return CommonResponseForm.of200("查询成功",this.experimentService.getExperimentByTemplate (template_id));
    }

    @ApiOperation (value = "添加新模板或者保存模板的修改")
    @RequestMapping(value ="/addTemplate")
    public CommonResponseForm addTemplate(@RequestParam Iterable<Experiment> experiments){
        for (Experiment experiment : experiments) {
            //如果存在就更新,不存在就插入
            //experimentService
            this.experimentService.updateExperiment (experiment);
        }
        return CommonResponseForm.of204("更新模板成功");
    }

    @ApiOperation (value = "设置实验的时间段")
    @RequestMapping(value ="/allocateExperiment")
    public CommonResponseForm Template(@RequestParam String template_id,
                                       @RequestParam Iterable<Experiment> experiments,
                                       @RequestParam String batch_name){

         this.experimentService.deleteExperimentByTempAndBatch (template_id,batch_name);
        for (Experiment experiment : experiments) {
            //通过批次和模板号码以及课时获取,如果存在就先删除再写入
            //然后将实验号码设置为空,batch_nam设置为本批次,在前端中,课时已经输入,
            Integer id=null;
            experiment.setExp_id (id);
            experiment.setBatch_name (batch_name);
            experimentService.addExperiment (experiment);
        }
        return CommonResponseForm.of200("查询成功",this.experimentService.getExperimentByTemplate (template_id));
     }



}
