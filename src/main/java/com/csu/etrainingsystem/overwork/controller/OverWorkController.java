package com.csu.etrainingsystem.overwork.controller;

import com.csu.etrainingsystem.form.CommonResponseForm;
import com.csu.etrainingsystem.overwork.entity.Overwork_apply;
import com.csu.etrainingsystem.overwork.repository.Overwork_applyRepository;
import com.csu.etrainingsystem.overwork.service.OverworkService;
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
    private final OverworkService overworkService;

    @Autowired
    public OverWorkController(Overwork_applyService overwork_applyService, OverworkService overworkService) {
        this.overworkapplyService = overwork_applyService;
        this.overworkService = overworkService;
    }


    /**
     * @param begin    开始时间
     * @param end      结束时间
     * @param pro_name 工序名
     * @return form
     */
    @PostMapping("/getOverworkByTime")
    public CommonResponseForm getOverworkByTime(@RequestParam(required = false) String begin,
                                                @RequestParam(required = false) String end,
                                                @RequestParam(required = false) String pro_name) {
        List<Overwork_apply> overworkApplyList = overworkapplyService.getOverworkApplyByBeginAndEndTime(begin, end, pro_name);
        if (overworkApplyList.size() == 0) return CommonResponseForm.of400("查询错误");
        else return CommonResponseForm.of200("查询成功", overworkApplyList);
    }

    /**
     * 新增教师值班
     * @param begin 开始时间 2018-10-10 22:10:10
     * @param duration 时长 默认为2，可以是2:30,2:30:12 (注意是':' 英文冒号)
     * @param pro_name 工序
     * @return form
     */
    @PostMapping("/addTeacherOverwork")
    public CommonResponseForm addTeacherOverwork(@RequestParam String begin,
                                                 @RequestParam(defaultValue = "2") String duration,
                                                 @RequestParam String pro_name,
                                                 @RequestParam String t_name) {
        return CommonResponseForm.of200("增加成功",overworkService.addTeacherOverwork(begin,pro_name,duration,t_name));

    }
}
