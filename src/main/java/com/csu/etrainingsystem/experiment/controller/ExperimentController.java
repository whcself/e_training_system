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
     * 获取模板中的实验
     * @param template_id
     * @return
     */
    @ApiOperation (value = "根据模板名称查询批次为空的模板")
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

    /**
     *
     * @param experiments
     * 该参数有两种情况:1该实验在数据库中不存在,是新模板中的实验
     *                2该实验在数据库中已经存在,是将原有模板中的实验进行修改
     * @param studentGroups
     *                创建学生分组,该分组没有批次,是独立于批次的,当批次和模板绑定的时候再分配批次
     *
     * @return
     */
    @ApiOperation (value = "添加新模板")
    @RequestMapping(value ="/addTemplate")
    public CommonResponseForm addTemplate(@RequestParam Iterable<Experiment> experiments,
                                          @RequestParam Iterable<StudentGroup>studentGroups){

        //这里的学生组不具有批次,以及人数;
        for (StudentGroup studentGroup : studentGroups) {
            this.studentGroupService.updateStudentGroup (studentGroup);
        }
        //这里的实验具有模板号,具有学生组(该学生组没有批次,没有学生),具有课时;不具有批次,和时间段
        //由于外键关系,学生组必须先加入
        for (Experiment experiment : experiments) {
            this.experimentService.updateExperiment (experiment);
        }

        return CommonResponseForm.of204("更新模板成功");
    }

    @ApiOperation (value = "绑定模板")
    @RequestMapping(value ="/bundleTemplate")
    public CommonResponseForm bundleTemplate(
                                       @RequestParam Iterable<Experiment> experiments,
                                       @RequestParam String batch_name){
        //如果是再次绑定,就需要将之前的实验删掉
        //并且在绑定模板的时候,将分组也绑定好
        this.experimentService.deleteExperimentByBatch (batch_name);
        StudentGroupId studentGroupId=new StudentGroupId();
        for (Experiment experiment : experiments) {
            String sGroupId=experiment.getS_group_id ();
            studentGroupId.setBatch_name (batch_name);
            studentGroupId.setS_group_id (sGroupId);
            StudentGroup studentGroup=new StudentGroup ();
            studentGroup.setStudentGroupId (studentGroupId);
            studentGroupService.addStudentGroup (studentGroup);

            //然后将实验号码设置为空,batch_nam设置为本批次,模板设置为空
            Integer id=null;
            experiment.setExp_id (id);
            //设置为空,脱离模板
            experiment.setTemplate_id (null);
            experiment.setBatch_name (batch_name);
            experimentService.addExperiment (experiment);
        }
        return CommonResponseForm.of204("实验安排成功成功");
     }

    @ApiOperation (value = "修改模板")
    @RequestMapping(value ="/modifyTemplate")
    public CommonResponseForm modifyTemplate(
            @RequestParam Iterable<Experiment> experiments){
        //如果是再次绑定,就需要将之前的实验删掉,之前的实验是指的两部分,第一
        //模板中的实验,第二,绑定的批次的实验,不过第二种不用,上面已经判断了
        //那么需要有一个根据模板号删除实验的函数,先删除再添加;
         //并且该函数是一个事务类型的函数
        experimentService.modifyTemplate (experiments);
        return CommonResponseForm.of204("实验安排成功成功");
    }

/**ok
 * 添加新模板:需要异步判断是否模板名已经存在
 * 同理:其他板块也需要
 * 在添加新模板的同时也需要添加新的学生组,如果存在就更新(实际上不会有任何修改),如果不存在就添加
 *ok
 * 绑定模板:也即是将具有该模板号(并且批次为空的)为空的实验赋予批次号,为保证模板得纯洁性,赋予了
 * 批次的实验id设置为空,也即是自增长,永远不重复,并且将实验的模板id取消,表示该实验已经脱离模板
 *然后获取该这些实验各自的学生分组,查找学生分组然后赋予其批次号
 *
 * 模板再次绑定:或者说一次绑定的时候就可以判断
 * 如何确定已经绑定过?塞选该批次的实验,如果不为空,就表示已经绑定过,先删除再绑定
 *
 *
 *
 * 模板修改:考虑修改会对原有的课时删改,也就是删除某些实验;导致写回数据库的时候;有些实验没有被覆盖
 * 那就选出来后就都删掉好了;等待写回,如果没有保存修改怎么办?也就是不写回,比如断网
 * 那就在save函数里面判断,采取回滚的机制,就行了
 *
 * 为模板赋予确定的时间,也就是修改模板的特定属性而已,直接update就行了
 *
 */

}
