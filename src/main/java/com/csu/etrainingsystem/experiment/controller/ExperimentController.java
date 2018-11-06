package com.csu.etrainingsystem.experiment.controller;

import com.csu.etrainingsystem.experiment.entity.Experiment;
import com.csu.etrainingsystem.experiment.service.ExperimentService;
import com.csu.etrainingsystem.form.CommonResponseForm;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

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
}
