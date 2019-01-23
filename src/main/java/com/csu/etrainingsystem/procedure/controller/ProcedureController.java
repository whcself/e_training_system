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
        procedureService.addProcedure(proced);
        return CommonResponseForm.of204("工序添加增加成功");
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
     * @param batch_name 批次
     * @return list of 工序
     * 修改一次，之前掉错了方法 -ScJn
     * @apiNote 权重管理：获取指定批次的所有工序
     */
    @RequestMapping("/getBatchProced/{batch_name}")
    public CommonResponseForm getBatchProced(@PathVariable("batch_name") String batch_name) {
        List<Proced> proceds = (List<Proced>) procedureService.getBatchProcedure(batch_name);
        return CommonResponseForm.of200("查询成功：共" + proceds.size(), proceds);
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
            String proName = form.get("pro_name");
            Float weight = Float.valueOf(form.get("weight"));
            procedureService.setWeight(batchName, proName, weight);
        }

        return CommonResponseForm.of204("设置成功");
    }

    /**
     * @return form
     * @apiNote 管理员端-增加权重模板
     */
    @PostMapping("/addTemplate")
    public CommonResponseForm addTemplate(@RequestParam String templateName,
                                          @RequestBody Map<String, Float> template) {
        procedureService.addTemplate(templateName, template);
        return CommonResponseForm.of204("添加成功");
    }

//    @PostMapping("/updateTemplate")
//    public CommonResponseForm updateTemplate(@RequestParam String templateName,
//                                             @RequestBody  Map<String,Float> template){
//
//    }

    /**
     * @return form
     * @apiNote 权重管理 权重管理-查询所有权重模板名
     */
    @PostMapping("/findAllTemplate")
    public CommonResponseForm findAllTemplateName() {
        List<String> names = (List<String>) procedureService.findAllTemplateName();
        return CommonResponseForm.of200("查询成功:共" + names.size(), names);
    }

    /**
     * @param name 名字
     * @apiNote 权重管理 根据模板名字查的所有的条目
     */
    @PostMapping("/findTemplateItemByName")
    public CommonResponseForm findTemplateItemByName(String name) {

        List<Map<String, Float>> items = procedureService.findTemplateItemByName(name);
        return CommonResponseForm.of200("查询成功:共" + items.size(), items);

    }

    /**
     * @apiNote 权重管理 删除权重模板
     */
    @PostMapping("/deleteTemplate")
    public CommonResponseForm deleteTemplate(String name) {
        return procedureService.deleteTemplate(name);
    }


    /**
     * @param batch_name    批次名
     * @param template_name 模板名
     * @return form
     * @apiNote 管理员端-权重模板绑定
     */
    @PostMapping("/band")
    public CommonResponseForm band(@RequestParam String batch_name, @RequestParam String template_name) {
//        try {
            procedureService.band(batch_name, template_name);
//        } catch (Exception e) {
//            return CommonResponseForm.of400("绑定失败");
//        }
        return CommonResponseForm.of204("绑定成功");
    }

    /**
     * @param groupName g
     * @param proName   p
     * @return f
     * @apiNote 增加工序名到教师组
     */
    @PostMapping("/addProcedToGroup")
    public CommonResponseForm addProcedToGroup(@RequestParam String groupName,
                                               @RequestParam String proName) {
        procedureService.addProcedToGroup(groupName, proName);
        return CommonResponseForm.of204("增加成功");
    }

    /**
     * @param groupName g
     * @param old       o
     * @param newName   n
     * @apiNote 修改教师分组下的工序名
     */
    @PostMapping("/updateProcedFromGroup")
    public CommonResponseForm updateProcedFromGroup(@RequestParam String groupName,
                                                    @RequestParam String old,
                                                    @RequestParam String newName) {
        procedureService.updateProcedFromGroup(groupName, old, newName);
        return CommonResponseForm.of204("修改成功");
    }

    /**
     * @param groupName g
     * @param pro_name  p
     * @apiNote 在教师组下删除工序
     */
    @PostMapping("/deleteProcedFromGroup")
    public CommonResponseForm deleteProcedFromGroup(@RequestParam String groupName,
                                                    @RequestParam String pro_name) {
        procedureService.deleteProcedFromGroup(groupName, pro_name);
        return CommonResponseForm.of204("删除成功");
    }


}