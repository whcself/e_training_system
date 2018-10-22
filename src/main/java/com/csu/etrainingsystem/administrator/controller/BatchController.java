package com.csu.etrainingsystem.administrator.controller;

import com.csu.etrainingsystem.administrator.entity.Batch;
import com.csu.etrainingsystem.administrator.repository.BatchRepository;
import com.csu.etrainingsystem.administrator.service.AdminService;
import com.csu.etrainingsystem.form.CommonResponseForm;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/batch")
public class BatchController {

    private final AdminService adminService;

    @Autowired
    public BatchController(AdminService adminService){
        this.adminService=adminService;
    }


    @PostMapping("/")
    public CommonResponseForm addBatch(Batch batch){
        adminService.addBatch(batch);
        return  CommonResponseForm.of200("添加批次成功",batch);

    }

    @DeleteMapping("/{id}")
    public @ResponseBody CommonResponseForm deleteBatch(@PathVariable String id){

        adminService.deleteBatch(id);

        return CommonResponseForm.of204("删除批次成功");
    }


    @PutMapping("/")
    public CommonResponseForm updateBatch(Batch batch){
        adminService.updateBatch(batch);
        return CommonResponseForm.of204("更新批次成功");
    }

    @GetMapping("/{id}")
    public CommonResponseForm getBatch(@PathVariable String  id){
        return CommonResponseForm.of200("获取批次成功",adminService.getBatch(id));
    }

    @GetMapping("/")
    public CommonResponseForm getAllBatch(){
        return CommonResponseForm.of200("获取所有批次成功",adminService.getAllBatch());
    }

}
