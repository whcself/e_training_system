package com.csu.etrainingsystem.experiment.controller;

import com.csu.etrainingsystem.experiment.entity.Experiment;
import com.csu.etrainingsystem.experiment.service.ExperimentService;
import com.csu.etrainingsystem.form.CommonResponseForm;
import com.csu.etrainingsystem.student.entity.Student;
import com.csu.etrainingsystem.student.entity.StudentGroup;
import com.csu.etrainingsystem.student.entity.StudentGroupId;
import com.csu.etrainingsystem.student.service.StudentGroupService;
import com.csu.etrainingsystem.student.service.StudentService;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping(value = "experiment",method = RequestMethod.POST)
public class ExperimentController {
    private final ExperimentService experimentService;
    private final StudentService studentService;
    private final StudentGroupService studentGroupService;
     @Autowired
    public ExperimentController(ExperimentService experimentService, StudentService studentService, StudentGroupService studentGroupService) {
        this.experimentService = experimentService;
         this.studentService = studentService;
         this.studentGroupService = studentGroupService;
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
    @RequestMapping(value ="/getAllTemplate")
    public CommonResponseForm getAllTemplate(){
        return CommonResponseForm.of200("获取成功",this.experimentService.getAllTemplate ());
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
    //更新实验表,在将实验排课完成之后写回的时候调用
    @RequestMapping(value ="/updateExperiment/")
    public CommonResponseForm updateExperiment(Iterable<Experiment> experiments){
        for (Experiment experiment : experiments) {
            this.experimentService.updateExperiment (experiment);
        }

        return CommonResponseForm.of204("更新成功");
    }
    @RequestMapping(value ="/deleteExperiment/{id}")
    public CommonResponseForm deleteExperiment(@PathVariable("id") int id){
        this.experimentService.deleteExperiment (id);
        return CommonResponseForm.of204("删除成功");
    }

    /**
     * 获取模板中的实验
     * @param template_id
     * @return
     */
    @ApiOperation (value = "根据模板名称查询模板")
    @RequestMapping(value ="/getTemplate")
    public CommonResponseForm getTemplate( String template_id){
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

    /**
     *
     * @param experiments
     * 该参数有两种情况:1该实验在数据库中不存在,是新模板中的实验
     *                2该实验在数据库中已经存在,是将原有模板中的实验进行修改
     *
     * @return
     */
    @ApiOperation (value = "添加新模板")
    @RequestMapping(value ="/addTemplate")
    public CommonResponseForm addTemplate(@RequestBody List<Experiment> experiments){

//        这里的实验具有模板号,具有学生组id;后面经过绑定之后将学生组id和批次传递过去给俊贤即可
//        判断模板号是否为空以及是否重复;需要添加,不能加入既没有模板又没有批次的实验
        for (Experiment experiment : experiments) {
            if(experiment.getTemplate_id ()!=null){
                // Integer exp_id=new Integer (null);
             //   experiment.setExp_id (exp_id);
                //experimentService.addExperiment (experiment);
                 experimentService.addExperimentByHand (experiment);
            }


        }
        return CommonResponseForm.of204("更新模板成功");
    }

    @ApiOperation (value = "绑定模板")
    @RequestMapping(value ="/bundleTemplate")
    public CommonResponseForm bundleTemplate(
                                       @RequestParam String template_id,
                                       @RequestParam String batch_name){
        //如果是再次绑定,就需要将之前的实验删掉
        //并且在绑定模板的时候,将分组也绑定好
        this.experimentService.deleteExperimentByBatch (batch_name);
        Experiment exp=new Experiment ();
       Iterable<Experiment> experiments= experimentService.getExperimentByTemplate (template_id);
        for (Experiment experiment : experiments) {
            //然后将实验号码设置为空,batch_nam设置为本批次,模板设置为空
           //设置为空,脱离模板
           //     experiment.setExp_id (null);
            //添加到批次
            exp.setBatch_name (batch_name);
            exp.setDel_status (experiment.isDel_status ());
            exp.setClass_time (experiment.getClass_time ());
            exp.setPro_name (experiment.getPro_name ());
            exp.setS_group_id (experiment.getS_group_id ());
            exp.setT_group_id (experiment.getT_group_id ());
            exp.setSubmit_time (experiment.getSubmit_time ());
            exp.setTid (experiment.getTid ());
            exp.setTime_quant (experiment.getTime_quant ());
            experimentService.addExperimentByHand (exp);
            System.out.println (exp.toString ());
        }
        return CommonResponseForm.of204("实验绑定成功");
     }

    @ApiOperation (value = "修改模板")
    @RequestMapping(value ="/modifyTemplate")
    public CommonResponseForm modifyTemplate(
            @RequestBody Iterable<Experiment> experiments){
        //如果是再次绑定,就需要将之前的实验删掉,之前的实验是指的两部分,第一
        //模板中的实验,第二,绑定的批次的实验,不过第二种不用,上面绑定函数已经判断了
        //那么需要有一个根据模板号删除实验的函数,先删除再添加;
         //并且该函数是一个事务类型的函数
        experimentService.modifyTemplate (experiments);
        return CommonResponseForm.of204("模板修改成功");
    }

/**ok
 * 添加新模板:需要异步判断是否模板名已经存在
 * 同理:其他板块也需要
 * 在添加模板的时候添加分组(已经取消外键)
 *ok
 * 绑定模板:也即是将具有该模板号(并且批次为空的)为空的实验赋予批次号,为保证模板得纯洁性,赋予了
 * 批次的实验id设置为空,也即是自增长,永远不重复,并且将实验的模板id取消,表示该实验已经脱离模板
 *然后获取该这些实验各自的学生分组,查找学生分组然后赋予其批次号
 *ok
 * 模板再次绑定:或者说一次绑定的时候就可以判断
 * 如何确定已经绑定过?塞选该批次的实验,如果不为空,就表示已经绑定过,先删除再绑定
 *
 *
 * ok
 * 模板修改:考虑修改会对原有的课时删改,也就是删除某些实验;导致写回数据库的时候;有些实验没有被覆盖
 * 那就选出来后就都删掉好了;等待写回,如果没有保存修改怎么办?也就是不写回,比如断网
 * 那就在save函数里面判断,采取回滚的机制,就行了
 *
 *
 *ok
 *给学生排课表:这个时候就不再是对模板进行操作,而是对已经确定下来的实验进行操作
 * 具体方法:
 * 将传递过去的实验根据课时分组;
 *然后更新即可
 *
 *
 *
 */

}
