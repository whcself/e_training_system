package com.csu.etrainingsystem.material.controller;

import com.csu.etrainingsystem.form.CommonResponseForm;
import com.csu.etrainingsystem.material.entity.Reimbursement;
import com.csu.etrainingsystem.material.form.ReimAddForm;
import com.csu.etrainingsystem.material.form.ReimQueryForm;
import com.csu.etrainingsystem.material.service.ReimbursementService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

@RestController
@RequestMapping("/reim")
public class ReimbursementController {
    private final ReimbursementService reimbursementService;

    @Autowired
    public ReimbursementController(ReimbursementService reimbursementService) {
        this.reimbursementService = reimbursementService;
    }

    /**
     * -ScJn
     * @apiNote 查询报账记录
     */
    @PostMapping("/getReim")
    public CommonResponseForm getReim(@RequestBody ReimQueryForm form){
        List<Reimbursement> reimbursementList=reimbursementService.findBy6(form.getBegin(),form.getEnd(),form.getTName(),form.getClazz(),form.getVerify(),form.getPurchaseId());
        return CommonResponseForm.of200("共"+reimbursementList.size()+"条数据",reimbursementList);
    }

    /**
     * -ScJn
     * @apiNote 通过教师名得到类别
     */
    @PostMapping("/getClazz")
    public CommonResponseForm getClazz(@RequestParam String tName){
        return CommonResponseForm.of200("查询成功",reimbursementService.getClazzByTName2(tName));
    }

    /**
     * -ScJn
     * @apiNote 报账单
     */
    @PostMapping("/downloadReim")
    public void downloadReim(HttpServletResponse response,
                             @RequestBody List<String> reimIds) throws IOException {
        reimbursementService.downloadReim(response,reimIds);
    }

    /**
     * -ScJn
     * @apiNote 增加报账记录
     */
    @PostMapping("/add")
    public CommonResponseForm add(@RequestBody ReimAddForm form){
        return reimbursementService.add(form);
    }

    /**
     * -ScJn
     * @apiNote 查询报账额度
     * @param pid 申购号
     */
    @PostMapping("/remain")
    public CommonResponseForm remain(@RequestParam String pid){
        return CommonResponseForm.of200("查询成功",reimbursementService.remain(pid));
    }

    @PostMapping("/verify")
    public CommonResponseForm verify(@RequestParam String id,
                                     @RequestParam String tname){
        reimbursementService.verifyReim(id,tname);
        return CommonResponseForm.of204("审核完成");
    }

}