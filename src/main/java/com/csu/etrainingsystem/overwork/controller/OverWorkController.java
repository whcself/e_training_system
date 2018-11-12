package com.csu.etrainingsystem.overwork.controller;

import com.csu.etrainingsystem.form.CommonResponseForm;
import com.csu.etrainingsystem.overwork.entity.Overwork_apply;
import com.csu.etrainingsystem.overwork.repository.Overwork_applyRepository;
import com.csu.etrainingsystem.overwork.service.Overwork_applyService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/overwork")
public class OverWorkController {
    private final Overwork_applyService overworkapplyService;

    @Autowired
    public OverWorkController(Overwork_applyService overwork_applyService) {
        this.overworkapplyService = overwork_applyService;
    }


    /**
     *
     * @param begin 开始时间
     * @param end 结束时间
     * @param pro_name 工序名
     * @return form
     */
    @PostMapping("/getOverworkByTime")
    public CommonResponseForm getOverworkByTime(@RequestParam(required = false) String begin,
                                                @RequestParam(required = false) String end,
                                                @RequestParam(required = false) String pro_name){
        List<Overwork_apply> overworkApplyList=overworkapplyService.getOverworkApplyByBeginAndEndTime(begin,end,pro_name);
        if(overworkApplyList.size()==0)return CommonResponseForm.of400("查询错误");
        else return CommonResponseForm.of200("查询成功",overworkApplyList);
    }
}
