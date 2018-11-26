package com.csu.etrainingsystem.procedure.controller;


import com.csu.etrainingsystem.form.CommonResponseForm;
import com.csu.etrainingsystem.procedure.entity.Proced;
import com.csu.etrainingsystem.procedure.entity.ProcedId;
import com.csu.etrainingsystem.procedure.entity.Proced_template;
import com.csu.etrainingsystem.procedure.service.ProcedureService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping(value = "/proced", method = RequestMethod.POST)
public class ProcedureController {
    private final ProcedureService procedureService;

    @Autowired
    public ProcedureController(ProcedureService procedureService) {
        this.procedureService = procedureService;
    }

    @RequestMapping(value = "/addProced")
    public CommonResponseForm addProced(Proced proced) {
        if (procedureService.getProcedure(proced.getProid()) != null) return CommonResponseForm.of204("工序已存在");
        else {
            this.procedureService.addProcedure(proced);
            return CommonResponseForm.of204("工序添加增加成功");
        }
    }

    @RequestMapping(value = "/updateProced")
    public CommonResponseForm updateProced(Proced proced) {
        this.procedureService.updateProcedure(proced);
        return CommonResponseForm.of204("工序添加增加成功");
    }

    @RequestMapping(value = "/getProced/{pro_name}/{batch_name}")
    public CommonResponseForm getProced(@PathVariable("pro_name") String pro_name, @PathVariable("batch_name") String batch_name) {
        ProcedId procedId = new ProcedId(pro_name, batch_name);
        return CommonResponseForm.of200("工序获取成功", procedureService.getProcedure(procedId));
    }

    /**
     * 获取指定批次的所有工序
     *
     * @param batch_name
     * @return list of 工序
     * 修改一次，之前掉错了方法 -ScJn
     */
    @RequestMapping("/getBatchProced/{batch_name}")
    public CommonResponseForm getBatchProced(@PathVariable("batch_name") String batch_name) {
        return CommonResponseForm.of200("工序获取成功", procedureService.getBatchProcedure(batch_name));
    }

    @RequestMapping(value = "/getAllProced")
    public CommonResponseForm getAllProced() {
        return CommonResponseForm.of200("工序获取成功", procedureService.getAllProcedure());
    }

    @RequestMapping(value = "/deleteProced/{pro_name}/{batch_name}")
    public CommonResponseForm deleteProced(@PathVariable("pro_name") String pro_name, @PathVariable("batch_name") String batch_name) {
        procedureService.deleteProcedure(pro_name, batch_name);
        return CommonResponseForm.of204("工序删除成功");
    }

    /**
     * @apiNote 管理员端-设置权重
     */
    @RequestMapping("/setWeight")
    public CommonResponseForm setWeight(@RequestBody List<Map<String, String>> weightForm) {
        for (Map<String, String> form : weightForm) {
            String batchName = form.get("batch_name");
            String proName=form.get("pro_name");
            Float weight = Float.valueOf(form.get("weight"));
            procedureService.setWeight(batchName,proName,weight);
        }

        return CommonResponseForm.of204("设置成功");
    }

    /**
     * @apiNote 管理员端-增加权重模板
     * @return form
     */
    @PostMapping("/addTemplate")
    public CommonResponseForm addTemplate(@RequestBody Proced_template template){
        procedureService.addTemplate(template);
        return CommonResponseForm.of204("添加成功");
    }

    /**
     * @apiNote 管理员端-权重模板绑定
     * @param batch_name 批次名
     * @param template_name 模板名
     * @return form
     */
    @PostMapping("/band")
    public CommonResponseForm band( @RequestParam String batch_name,@RequestParam String template_name){
        try {
            procedureService.band(batch_name, template_name);
        }catch (Exception e){
            return CommonResponseForm.of400("绑定失败");
        }
        return CommonResponseForm.of204("绑定成功");
    }

}