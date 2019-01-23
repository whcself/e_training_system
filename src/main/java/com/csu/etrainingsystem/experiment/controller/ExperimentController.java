package com.csu.etrainingsystem.experiment.controller;

import com.csu.etrainingsystem.experiment.entity.Experiment;
import com.csu.etrainingsystem.experiment.service.ExperimentService;
import com.csu.etrainingsystem.form.CommonResponseForm;
import com.csu.etrainingsystem.student.entity.Student;
import com.csu.etrainingsystem.student.service.StudentGroupService;
import com.csu.etrainingsystem.student.service.StudentService;
import io.swagger.annotations.ApiOperation;
import org.apache.poi.hssf.usermodel.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.*;

@RestController
@RequestMapping(value = "experiment", method = RequestMethod.POST)
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

    @RequestMapping(value = "/addExperiment")
    public CommonResponseForm addExperiment(Experiment experiment) {
        this.experimentService.addExperiment (experiment);
        return CommonResponseForm.of204 ("添加实验成功");
    }

    @RequestMapping(value = "/getExperimentByBAndW")
    public CommonResponseForm getExperimentByBAndW( String batch_name,String time_quant) {

        return CommonResponseForm.of200 ("获取成功", this.experimentService.getExperimentByBAndW (batch_name,time_quant));
    }
    @RequestMapping(value = "/getAllTemplate")
    public CommonResponseForm getAllTemplate( HttpServletResponse response) {
        String key = UUID.randomUUID().toString();
        Cookie cookie=new Cookie ("TT_token",key);
        response.addCookie (cookie);
        return CommonResponseForm.of200 ("获取成功", this.experimentService.getAllTemplate ());
    }
    @RequestMapping(value = "/getExperimentByProAndBatch")
    public CommonResponseForm getExperimentByProAndBatch(String batch_name,String pro_name) {
        return CommonResponseForm.of200 ("获取成功", this.experimentService.getExperimentByProAndBatch (pro_name,batch_name));
    }
    @ApiOperation("根据批次选择实验,用于后面展示")
    @RequestMapping(value = "/getExperimentByBatch")
    public CommonResponseForm getExperimentByBatch( String batch_name) {
        return CommonResponseForm.of200 ("获取成功", this.experimentService.getExperimentByBatch (batch_name));
    }

    @RequestMapping(value = "/getAllExperiment")
    public CommonResponseForm getAllExperiment() {

        return CommonResponseForm.of200 ("获取成功", this.experimentService.getAllExperiment ());
    }

    //更新实验表,在将实验排课完成之后写回的时候调用
    @RequestMapping(value = "/updateExperiment")
    public CommonResponseForm updateExperiment(@RequestBody Experiment[] experiments) {
        for (Experiment experiment : experiments) {
            this.experimentService.updateExperiment (experiment);
        }

        return CommonResponseForm.of204 ("更新成功");
    }

    /**
     * 获取模板中的实验
     *
     * @param template_id
     * @return
     */
    @ApiOperation(value = "根据模板名称查询模板")
    @RequestMapping(value = "/getTemplate")
    public CommonResponseForm getTemplate(String template_id) {
        return CommonResponseForm.of200 ("查询成功", this.experimentService.getExperimentByTemplate (template_id));
    }
    @ApiOperation(value = "根据模板名称查询模板")
    @RequestMapping(value = "/getSGroupByTemplate")
    public CommonResponseForm getSGroupByTemplate(String template_id) {
        return CommonResponseForm.of200 ("查询成功", this.experimentService.getSGroupByTemplate(template_id));
    }


    @ApiOperation(value = "根据学生号查询实验,返回结果为该学生的课表安排")
    @RequestMapping(value = "/getClass")
    public CommonResponseForm getExperimentByStudentGroup(@RequestParam(required = false) String sid) {
        String s_group = "";
        String batch_name = "";
        if (sid != null) {
            Student student = this.studentService.getStudentById (sid);
            if (student != null) {
                s_group = student.getS_group_id ();
                batch_name = student.getBatch_name ();
            }
        }

        return CommonResponseForm.of200 ("查询成功", this.experimentService.getStudentExperiment (s_group, batch_name));
    }

    /**
     * @param experiments 该参数有两种情况:1该实验在数据库中不存在,是新模板中的实验
     *                    2该实验在数据库中已经存在,是将原有模板中的实验进行修改
     * @return
     */
    @ApiOperation(value = "添加新模板")
    @RequestMapping(value = "/addTemplate")
    public CommonResponseForm addTemplate(@RequestBody List<Experiment> experiments) {
//        这里的实验具有模板号,具有学生组id;后面经过绑定之后将学生组id和批次传递过去给俊贤即可
//        判断模板号是否为空以及是否重复;需要添加,不能加入既没有模板又没有批次的实验
        for (Experiment experiment : experiments) {
            if (experiment.getTemplate_id () != null) {
                // Integer exp_id=new Integer (null);
                //   experiment.setExp_id (exp_id);
                //experimentService.addExperiment (experiment);
                experimentService.addExperimentByHand (experiment);
            }


        }
        return CommonResponseForm.of204 ("更新模板成功");
    }

    @ApiOperation(value = "绑定模板")
    @RequestMapping(value = "/bundleTemplate")
    public CommonResponseForm bundleTemplate(
            @RequestParam String template_id,
            @RequestParam String batch_name) {
        //如果是再次绑定,就需要将之前的实验删掉
        //并且在绑定模板的时候,将分组也绑定好
        this.experimentService.deleteExperimentByBatch (batch_name);
        Experiment exp = new Experiment ();
        Iterable<Experiment> experiments = experimentService.getExperimentByTemplate (template_id);
        for (Experiment experiment : experiments) {
            //然后将实验号码设置为空,batch_nam设置为本批次,模板设置为空
            //设置为空,脱离模板
            //experiment.setExp_id (null);
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
        return CommonResponseForm.of204 ("实验绑定成功");
    }

    @ApiOperation(value = "修改模板")
    @RequestMapping(value = "/modifyTemplate")
    public CommonResponseForm modifyTemplate(
            @RequestBody Iterable<Experiment> experiments) {
        //如果是再次绑定,就需要将之前的实验删掉,之前的实验是指的两部分,第一
        //模板中的实验,第二,绑定的批次的实验,不过第二种不用,上面绑定函数已经判断了
        //那么需要有一个根据模板号删除实验的函数,先删除再添加;
        //并且该函数是一个事务类型的函数
        experimentService.modifyTemplate (experiments);
        return CommonResponseForm.of204 ("模板修改成功");
    }

    @ApiOperation(value = "删除模板")
    @RequestMapping(value = "/deleteTemplate")
    public CommonResponseForm deleteTemplate(String template_id) {
        experimentService.deleteExperimentTemplate (template_id);
        return CommonResponseForm.of204 ("模板删除成功");
    }

    /**
     * 获取工序排课表
     * @param response
     * @param batch_name
     * @throws IOException
     */
    @RequestMapping(value = "/ExcelDownloads01")
public void ExcelDownloads01(HttpServletResponse response,String batch_name) throws IOException {
    HSSFWorkbook workbook = new HSSFWorkbook();
    HSSFSheet sheet = workbook.createSheet("信息表");
    //headers表示excel表中第一行的表头
    List<String> headers=experimentService.getSGroupsOfBatch (batch_name);
    System.out.println ("学生分组名"+headers.toString ());
    headers.add (0,"课时\\组号");
    String fileName = "工序排课"  + ".xls";//设置要导出的文件的名字
    //新增数据行，并且设置单元格数据
    HSSFRow row = sheet.createRow(0);
    //在excel表中添加表头
    for(int i=0;i<headers.size ();i++){
        HSSFCell cell = row.createCell(i);
        HSSFRichTextString text = new HSSFRichTextString(headers.get (i));
        cell.setCellValue(text);
    }
    //根据学生分组分类实验;
    Map<String,List<Experiment>> experiments=new HashMap<> ();
    for (int i=1;i<headers.size ();i++){
        experiments.put (headers.get (i),experimentService.getExperimentOfSGroup (batch_name,headers.get (i)));

    }
    //获取还需要所有的课时
    int rowNum=experimentService.getClassTimeOfBatch (batch_name);
    System.out.println (rowNum);
    List<HSSFRow> rows=new ArrayList<> ();
    for (int i=1;i<=rowNum;i++){
        HSSFRow r = sheet.createRow(i);
        r.createCell (0).setCellValue (i);
        rows.add (r);
    }
    for (int i=1;i<headers.size ();i++) {
        //比如A组的实验
        List<Experiment> exps=experiments.get (headers.get (i));
        for (Experiment exp : exps) {
            rows.get (exp.getClass_time ()-1).createCell (i).setCellValue (exp.getPro_name ());
        }

    }
    response.setContentType("application/octet-stream");
    response.setHeader("Content-disposition", "attachment;filename=" + fileName);
    response.flushBuffer();
    workbook.write(response.getOutputStream());
}

    /**
     * 获取工序排课表
     * @param response
     * @throws IOException
     */
    @RequestMapping(value = "/ExcelDownload")
    public void ExcelDownloads02(HttpServletResponse response,@RequestBody String[][] datum) throws IOException {
        int rowLength=0;
        int colLength=0;
        HSSFWorkbook workbook = new HSSFWorkbook();
        HSSFSheet sheet = workbook.createSheet("信息表");
        String fileName = "信息"  + ".xls";
        if (datum!=null){
            rowLength=datum.length;//多少行
            colLength=datum[0].length;//多少列
        }
        if (rowLength==0||colLength==0)return ;
        for (int i=0;i<rowLength;i++){
            HSSFRow r = sheet.createRow(i);
            for (int j=0;j<colLength;j++){
                r.createCell (j).setCellValue (datum[i][j]);
                 }
              }
        response.setContentType("application/octet-stream");
        response.setHeader("Content-disposition", "attachment;filename=" + fileName);
        response.flushBuffer();
        workbook.write(response.getOutputStream());
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
