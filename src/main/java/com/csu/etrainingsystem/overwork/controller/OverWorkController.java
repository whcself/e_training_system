package com.csu.etrainingsystem.overwork.controller;

import com.csu.etrainingsystem.form.CommonResponseForm;
import com.csu.etrainingsystem.overwork.entity.Overwork;
import com.csu.etrainingsystem.overwork.entity.Overwork_apply;
import com.csu.etrainingsystem.overwork.service.OverworkService;
import com.csu.etrainingsystem.overwork.service.Overwork_applyService;
import com.csu.etrainingsystem.teacher.repository.TeacherRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpSession;
import java.util.List;

@RestController
@RequestMapping("/overwork")
public class OverWorkController {
    private final Overwork_applyService overworkApplyService;
    private final OverworkService overworkService;
    private final TeacherRepository teacherRepository;

    @Autowired
    public OverWorkController(Overwork_applyService overwork_applyService, OverworkService overworkService, TeacherRepository teacherRepository) {
        this.overworkApplyService = overwork_applyService;
        this.overworkService = overworkService;
        this.teacherRepository = teacherRepository;
    }


    /**
     * @apiNote 学生加班申请
     * @param begin    开始时间
     * @param end      结束时间
     * @param pro_name 工序名
     * @return form
     */
    @PostMapping("/getOverworkApplyByTime")
    public CommonResponseForm getOverworkApplyByTime(@RequestParam(required = false) String begin,
                                                     @RequestParam(required = false) String end,
                                                     @RequestParam(required = false) String pro_name) {
        List<Overwork_apply> overworkApplyList = overworkApplyService.getOverworkApplyByBeginAndEndTime(begin, end, pro_name);
        if (overworkApplyList.size() == 0) return CommonResponseForm.of400("查询错误");
        else return CommonResponseForm.of200("查询成功", overworkApplyList);
    }

    /**
     * @apiNote 学生端，提交加班申请
     * @param begin 开始时间
     * @param pro_name 工种
     * @param duration 时长
     * @return form
     */
    @PostMapping("/addOverworkApply")
    public CommonResponseForm addOverworkApply(@RequestParam String begin,
                                               @RequestParam String pro_name,
                                               @RequestParam String duration,
                                               @RequestParam(required = false) String reason,
                                               HttpSession session){
        boolean isOk= overworkApplyService.addOverworkApply(begin,pro_name,duration,reason,session);
        if(!isOk)return CommonResponseForm.of400("提交失败");
        return CommonResponseForm.of204("提交成功");
    }

    /**
     * @apiNote 新增教师值班
     * @param begin    开始时间 2018-10-10 22:10:10
     * @param duration 时长 默认为2，可以是2:30,2:30:12 (注意是':' 英文冒号)
     * @param pro_name 工序
     * @return form
     */
    @PostMapping("/addTeacherOverwork")
    public CommonResponseForm addTeacherOverwork(@RequestParam String begin,
                                                 @RequestParam(defaultValue = "2") String duration,
                                                 @RequestParam String pro_name,
                                                 @RequestParam String t_name) {
        boolean isOk = overworkService.addTeacherOverwork(begin, pro_name, duration, t_name);
        if (!isOk) return CommonResponseForm.of400("增加错误，没有该老师");
        return CommonResponseForm.of204("增加成功");
    }


    /**
     * @apiNote 教师值班记录
     * @param begin    开始时间
     * @param end      结束时间
     * @param pro_name 工序名
     * @return list
     *
     */
    @PostMapping("/getOverworkByTimeOrProName")
    public CommonResponseForm getOverworkByTimeOrProName(@RequestParam(required = false) String begin,
                                                         @RequestParam(required = false) String end,
                                                         @RequestParam(required = false) String pro_name) {
        List<Overwork> overWorks = overworkService.getOverworkByTimeOrProName(begin, end, pro_name);
        if (overWorks.size() == 0) return CommonResponseForm.of400("查询失败,结果为空");
        return CommonResponseForm.of200("查询成功", overWorks);
    }

    /**
     *
     * @apiNote 学生端：我的申请
     */
//    @PostMapping("/getMyOverworkApply")
//    public CommonResponseForm getMyOverworkApply(HttpSession session){
//        if(session.getAttribute("role")==)
//        List<Overwork_apply> overworkApplies= overworkApplyService.getMyOverworkApply(session);
//
//        return null;
//    }
}
