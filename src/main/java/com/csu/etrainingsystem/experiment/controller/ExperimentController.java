package com.csu.etrainingsystem.experiment.controller;

import com.csu.etrainingsystem.experiment.entity.Experiment;
import com.csu.etrainingsystem.experiment.service.ExperimentService;
import com.csu.etrainingsystem.form.CommonResponseForm;
import com.csu.etrainingsystem.student.entity.Student;
import com.csu.etrainingsystem.student.service.StudentService;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(value = "experiment",method = RequestMethod.POST)
public class ExperimentController {
    private final ExperimentService experimentService;
    private final StudentService studentService;
     @Autowired
    public ExperimentController(ExperimentService experimentService, StudentService studentService) {
        this.experimentService = experimentService;
         this.studentService = studentService;
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
    @ApiOperation ("根据批次选择实验,用于后面展示")
    @RequestMapping(value ="/getExperiment/{batch_name}")
    public CommonResponseForm getExperimentByBatch(@PathVariable("batch_name") String batch_name){
        return CommonResponseForm.of200("获取成功",this.experimentService.getExperimentByBatch (batch_name));
    }
    @RequestMapping(value ="/getAllExperiment")
    public CommonResponseForm getAllExperiment(){

        return CommonResponseForm.of200("获取成功", this.experimentService.getAllExperiment ());
    }
    @RequestMapping(value ="/updateExperiment/")
    public CommonResponseForm updateExperiment(Experiment experiment){
        this.experimentService.updateExperiment (experiment);
        return CommonResponseForm.of204("更新成功");
    }
    @RequestMapping(value ="/deleteExperiment/{id}")
    public CommonResponseForm deleteExperiment(@PathVariable("id") int id){
        this.experimentService.deleteExperiment (id);
        return CommonResponseForm.of204("删除成功");
    }

    /**
     * 获取模板中的实验;
     * @param template_id
     * @return
     */
    @ApiOperation (value = "根据模板名称查询批次为空的模板,学生分组的时候复用,点击弹出下拉框的填充内容")
    @RequestMapping(value ="/getTemplate/{template_id}")
    public CommonResponseForm getTemplate(@PathVariable("template_id") String template_id){
        return CommonResponseForm.of200("查询成功",this.experimentService.getExperimentByTemplate (template_id));
    }

    @ApiOperation (value = "根据学生号查询实验,返回结果为该学生的课表安排")
    @RequestMapping(value ="/getClass")
    public CommonResponseForm getExperimentByStudentGroup(@RequestParam(required = false) String sid){
        String s_group="";
        String batch_name="";
        if(sid!=null){
            Student student= this.studentService.getStudentById (sid);
            if(student!=null){s_group=student.getS_group_id ();
        batch_name=student.getBatch_name ();
            }
        }

        return CommonResponseForm.of200("查询成功",this.experimentService.getStudentExperiment (s_group,batch_name));
    }


    @ApiOperation (value = "添加新模板或者保存模板的修改")
    @RequestMapping(value ="/addTemplate")
    public CommonResponseForm addTemplate(@RequestParam Iterable<Experiment> experiments){
        for (Experiment experiment : experiments) {
            //如果存在就更新,不存在就插入
            this.experimentService.updateExperiment (experiment);
        }
        return CommonResponseForm.of204("更新模板成功");
    }

    @ApiOperation (value = "设置实验的时间段,也就是学生分组里面的排课")
    @RequestMapping(value ="/allocateExperiment")
    public CommonResponseForm Template(
                                       @RequestParam Iterable<Experiment> experiments,
                                       @RequestParam String batch_name){
        /**
         * 将以前的记录删除,需不需要脱离模板???
         */
        this.experimentService.deleteExperimentByBatch (batch_name);
        for (Experiment experiment : experiments) {
            //通过批次和模板号码以及课时获取,如果存在就先删除再写入
            //然后将实验号码设置为空,batch_nam设置为本批次,在前端中,课时已经输入,
            Integer id=null;
            experiment.setExp_id (id);
            //设置为空,脱离模板
            experiment.setTemplate_id (null);
            experiment.setBatch_name (batch_name);
            experimentService.addExperiment (experiment);
        }
        return CommonResponseForm.of204("实验安排成功成功");
     }



}
