package com.csu.etrainingsystem.administrator.controller;

import com.csu.etrainingsystem.administrator.entity.Batch;
import com.csu.etrainingsystem.administrator.repository.BatchRepository;
import com.csu.etrainingsystem.administrator.service.AdminService;
import com.csu.etrainingsystem.administrator.service.BatchService;
import com.csu.etrainingsystem.form.CommonResponseForm;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(value="/batch",method = RequestMethod.POST)
public class BatchController {

    private final AdminService adminService;
    private final BatchService batchService;


    @Autowired
    public BatchController(AdminService adminService,BatchService batchService){
        this.adminService=adminService;
        this.batchService=batchService;
    }


    @RequestMapping(value="/addBatch")
    public CommonResponseForm addBatch(Batch batch){
        adminService.addBatch(batch);
        return  CommonResponseForm.of200("添加批次成功",batch);
    }

    @PostMapping("/getBatchBySemesterName")
    public CommonResponseForm getBatchBySemesterName(String semester_name){
        return CommonResponseForm.of200("查找成功",batchService.getBatchBySemester(semester_name));
    }

    @RequestMapping(value="/deleteBatch/{id}")
    public @ResponseBody CommonResponseForm deleteBatch(@PathVariable String id){

        adminService.deleteBatch(id);

        return CommonResponseForm.of204("删除批次成功");
    }


    @RequestMapping(value="/updateBatch")
    public CommonResponseForm updateBatch(Batch batch){
        adminService.updateBatch(batch);
        return CommonResponseForm.of204("更新批次成功");
    }

    @RequestMapping(value="/getBatch/{id}")
    public CommonResponseForm getBatch(@PathVariable String  id){
        return CommonResponseForm.of200("获取批次成功",adminService.getBatch(id));
    }

    @RequestMapping(value="/getAllBatch")
    public CommonResponseForm getAllBatch(){
        return CommonResponseForm.of200("获取所有批次成功",adminService.getAllBatch());
    }

    @ApiOperation ("格式:  semester_name:春季学期"+" incrementId:12")
    @PostMapping("/getAllSemesterName")
    public CommonResponseForm getAllSemesterName(){
        return CommonResponseForm.of200("获取学期名成功",batchService.getAllSemesterName());
    }
    @PostMapping("/addSemester")
    public CommonResponseForm addSemester(@RequestParam String semesterName){
        batchService.addSemester(semesterName);
        return CommonResponseForm.of204("添加成功");
    }

    @PostMapping("/updateSemesterName")
    public CommonResponseForm updateSemesterName(@RequestParam String old,
                                                 @RequestParam String semesterName){
        batchService.updateSemesterName(old,semesterName);
        return CommonResponseForm.of204("更新成功");
    }
    @PostMapping("/deleteSemester")
    public CommonResponseForm deleteSemester(@RequestParam String semesterName){
        batchService.deleteSemester(semesterName);
        return CommonResponseForm.of204("删除成功");
    }
}
