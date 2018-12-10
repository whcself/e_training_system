package com.csu.etrainingsystem.overwork.controller;

import com.csu.etrainingsystem.form.CommonResponseForm;
import com.csu.etrainingsystem.overwork.entity.Overwork;
import com.csu.etrainingsystem.overwork.entity.Overwork_apply;
import com.csu.etrainingsystem.overwork.service.OverworkService;
import com.csu.etrainingsystem.overwork.service.Overwork_applyService;
import com.csu.etrainingsystem.teacher.repository.TeacherRepository;
import com.csu.etrainingsystem.user.entity.User;
import com.csu.etrainingsystem.user.entity.UserRole;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpSession;
import java.sql.Time;
import java.sql.Timestamp;
import java.util.List;
import java.util.Map;

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
     * @apiNote 管理员端+教师端：查询-学生加班申请
     * @param begin    开始时间
     * @param end      结束时间
     * @param pro_name 工序名
     * @return form
     */
    // TODO-whc: 2018/11/15  权限不足的json返回，或者未登录的json返回

    @PostMapping("/getOverworkApplyByTime")
    public CommonResponseForm getOverworkApplyByTime(@RequestParam(required = false) String begin,
                                                     @RequestParam(required = false) String end,
                                                     @RequestParam(required = false) String pro_name) {
        List<Map<String,String>> overworkApplyList = overworkApplyService.getOverworkApplyByBeginAndEndTime(begin, end, pro_name);
        if (overworkApplyList.size() == 0) return CommonResponseForm.of400("查询错误");
        else return CommonResponseForm.of200("查询成功", overworkApplyList);
    }

    /**
     *
     * @apiNote 学生端，提交加班申请
     * @param begin 开始时间
     * @param pro_name 工种
     * @param duration 时长
     * @return form
     */
    // TODO-whc: 2018/11/15  权限不足的json返回，或者未登录的json返回
    @PostMapping("/addOverworkApply")
    public CommonResponseForm addOverworkApply(@RequestParam String begin,
                                               @RequestParam String pro_name,
                                               @RequestParam String duration,
                                               @RequestParam(required = false) String reason,
                                               HttpSession session){
        User user=UserRole.getUser(session);
//        if(!UserRole.hasRole(user,UserRole.STUDENT)){
//            return CommonResponseForm.of401("没有权限");
//        }
        boolean isOk= overworkApplyService.addOverworkApply(begin,pro_name,duration,reason,user);
//        if(!isOk)return CommonResponseForm.of400("提交失败");
        return CommonResponseForm.of204("提交成功");
    }

    /**
     * @apiNote 管理员端：新增教师值班
     * @param begin    开始时间 2018-10-10 22:10:10
     * @param duration 时长 默认为2，可以是2:30,2:30:12 (注意是':' 英文冒号)
     * @param pro_name 工序
     * @return form
     */
    // TODO-whc: 2018/11/15  权限不足的json返回，或者未登录的json返回

    @PostMapping("/addTeacherOverwork")
    public CommonResponseForm addTeacherOverwork(@RequestParam String begin,
                                                 @RequestParam(defaultValue = "2") String duration,
                                                 @RequestParam String pro_name,
                                                 @RequestParam String t_name,
                                                 @RequestParam String reason,
                                                 HttpSession session) {
//        User user=UserRole.getUser(session);
//        if(!UserRole.hasRole(user,UserRole.ADMIN)){
//            return CommonResponseForm.of401("没有权限");
//        }
        boolean isOk = overworkService.addTeacherOverwork(begin, pro_name, duration, t_name,reason);
        if (!isOk) return CommonResponseForm.of400("增加错误，没有该老师");
        return CommonResponseForm.of204("增加成功");
    }

    /**
     *  加班管理：修改教师值班记录
     *  注意要hidden这个id
     * @return s
     */
    @PostMapping("/updateTeacherOverwork")
    public CommonResponseForm updateTeacherOverwork(@RequestParam Integer overworkId,
                                                    @RequestParam String begin,
                                                    @RequestParam String end,
                                                    @RequestParam String pro_name,
                                                    @RequestParam String reason,
                                                    @RequestParam String tname){
        overworkService.updateOverwork(overworkId,begin,end,pro_name,reason,tname);
        return CommonResponseForm.of204("修改成功");
    }


    /**
     * @apiNote 教师值班记录
     * @param begin    开始时间
     * @param end      结束时间
     * @param pro_name 工序名
     * @return list
     *
     */
    // TODO-whc: 2018/11/15  权限不足的json返回，或者未登录的json返回

    @PostMapping("/getOverworkByTimeOrProName")
    public CommonResponseForm getOverworkByTimeOrProName(@RequestParam(required = false) String begin,
                                                         @RequestParam(required = false) String end,
                                                         @RequestParam(required = false) String pro_name,
                                                         HttpSession session) {
//        User user=UserRole.getUser(session);
//        if(UserRole.hasRole(user,UserRole.ADMIN)||UserRole.hasRole(user,UserRole.TEACHER))
        List<Overwork> overWorks = overworkService.getOverworkByTimeOrProName(begin, end, pro_name);
        if (overWorks.size() == 0) return CommonResponseForm.of400("查询失败,结果为空");
        return CommonResponseForm.of200("查询成功", overWorks);
    }

    /**
     *
     * @apiNote 学生端：我的申请
     */
    // TODO-whc: 2018/11/15  权限不足的json返回，或者未登录的json返回

    @PostMapping("/getMyOverworkApply")
    public CommonResponseForm getMyOverworkApply(HttpSession session){
        User user=UserRole.getUser(session);
        String sId=user.getAccount();
        List<Overwork_apply> overworkApplies= overworkApplyService.getMyOverworkApply(sId);
        return CommonResponseForm.of200("查询成功",overworkApplies);
    }

    @PostMapping("/deleteOverwork")
    public CommonResponseForm deleteOverwork(Integer id){
        return overworkService.deleteOverwork(id);
    }

    @PostMapping("/getTeacherOverworkFromStudent")
    public CommonResponseForm getTeacherOverworkFromStudent(){
        Timestamp now = new Timestamp(System.currentTimeMillis());
        Timestamp end7=new Timestamp(now.getTime()+3600000*24*7);

        return CommonResponseForm.of200("查询成功",overworkService.getOverworkByTimeOrProName(String.valueOf(now),String.valueOf(end7),"%"));
    }
}
