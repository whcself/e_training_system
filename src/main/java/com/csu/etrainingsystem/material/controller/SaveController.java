package com.csu.etrainingsystem.material.controller;

import com.csu.etrainingsystem.form.CommonResponseForm;
import com.csu.etrainingsystem.material.entity.Save;
import com.csu.etrainingsystem.material.form.SaveAddForm;
import com.csu.etrainingsystem.material.form.SaveQueryForm;
import com.csu.etrainingsystem.material.service.SaveService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/save")
public class SaveController {
    private final SaveService saveService;

    @Autowired
    public SaveController(SaveService saveService) {
        this.saveService = saveService;
    }

    @PostMapping("/add")
    public CommonResponseForm add(@RequestBody SaveAddForm form){
        return saveService.addSave(form.getPid(),form.getNum(),form.getTname(),form.getRemark());
    }

    @PostMapping("/getSaveBy5")
    public CommonResponseForm getSaveBy5(@RequestBody SaveQueryForm form){
        List<Save> saveList=saveService.getSaveBy5(form.getBegin(),form.getEnd(),form.getClazz(),form.getTname(),form.getPid());
        return CommonResponseForm.of200("共"+saveList.size()+"条数据",saveList);
    }
}
