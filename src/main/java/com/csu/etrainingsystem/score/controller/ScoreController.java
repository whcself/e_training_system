package com.csu.etrainingsystem.score.controller;

import com.csu.etrainingsystem.experiment.entity.Experiment;
import com.csu.etrainingsystem.form.CommonResponseForm;
import com.csu.etrainingsystem.score.entity.ScoreSubmit;
import com.csu.etrainingsystem.score.entity.ScoreUpdate;
import com.csu.etrainingsystem.score.form.DegreeForm;
import com.csu.etrainingsystem.score.form.ScoreForm;
import com.csu.etrainingsystem.score.service.ScoreService;
import com.csu.etrainingsystem.student.service.StudentService;
import com.csu.etrainingsystem.user.entity.User;
import com.csu.etrainingsystem.user.entity.UserRole;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.stereotype.Repository;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.print.attribute.standard.Sides;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping(value = "/score", method = RequestMethod.POST)
public class ScoreController {
    private final ScoreService scoreService;
    private final StudentService studentService;

    @Autowired
    public ScoreController(ScoreService scoreService, StudentService studentService) {
        this.scoreService = scoreService;
        this.studentService = studentService;
    }

    @PostMapping("/release")
    public CommonResponseForm releaseScore(@RequestParam String batch_name) {
        studentService.releaseScore(batch_name);
        return CommonResponseForm.of204("发布成绩成功");
    }

    /*
    复合主键,主键中有两个字段,等会测试
     */
    @RequestMapping(value = "/getScore/{sid}/")
    public CommonResponseForm getScore(@PathVariable("sid") String sid) {
        return CommonResponseForm.of200("获取分数成功", scoreService.getScoreBySid(sid));
    }


    /**
     * @param batch_name 批次名
     * @param s_group_id 学生组
     * @param pro_name   工序名
     * @return 成绩列表, 上三查询，或确定工序名
     * <p>
     * 学生端就传自己的id
     * @apiNote 管理员端：成绩列表 学生端：评分查询
     */
    @RequestMapping("/getScore")
    public CommonResponseForm getScoreByBatchAndTeamOrProced(@RequestParam(required = false) String batch_name,
                                                             @RequestParam(required = false) String s_group_id,
                                                             @RequestParam(required = false) String pro_name,
                                                             @RequestParam(required = false) String sId,
                                                             @RequestParam(required = false) String sName) {
        List<HashMap<String, String>> scoreForms = scoreService.getScoreByBatchAndSGroupOrProName(batch_name, s_group_id, pro_name, sId, sName);
//        if ( == 0) {
//            return CommonResponseForm.of400("查询失败，结果为空");
//        }
        return CommonResponseForm.of200("查询成功:共" + scoreForms.size() + "条记录", scoreForms);
    }

    /**
     * @param session s
     * @return form
     * @apiNote 学生端 我的成绩
     */
    @RequestMapping("/getMyScore")
    public CommonResponseForm getMyScore(HttpSession session) {
        User user = UserRole.getUser(session);
        String sId = user.getAccount();
        List<HashMap<String, String>> scoreForms = scoreService.getScoreByBatchAndSGroupOrProName(null, null, null, sId, null);
        if (scoreForms.size() == 0) {
            return CommonResponseForm.of400("查询失败，结果为空");
        }
        return CommonResponseForm.of200("查询成功", scoreForms);

    }

    /**
     * @param batch_name 批次
     * @param s_group_id 学生组
     * @param pro_name   工序
     * @return form
     * @apiNote 成绩提交记录
     */
    @PostMapping("/getScoreRecord")
    public CommonResponseForm getScoreRecord(@RequestParam(required = false) String batch_name,
                                             @RequestParam(required = false) String s_group_id,
                                             @RequestParam(required = false) String pro_name) {
        List<ScoreSubmit> scoreSubmits = scoreService.getScoreRecord(batch_name, s_group_id, pro_name);
        return CommonResponseForm.of200("查询成功：共" + scoreSubmits.size() + "条记录", scoreSubmits);
    }

    @PostMapping("/getScoreUpdate")
    public CommonResponseForm getScoreUpdate(@RequestParam(required = false) String batch_name,
                                             @RequestParam(required = false) String begin,
                                             @RequestParam(required = false) String end,
                                             @RequestParam(required = false) String sname,
                                             @RequestParam(required = false) String sid) {
        List<ScoreUpdate> updates = scoreService.getScoreUpdate(batch_name, begin, end, sname, sid);
        return CommonResponseForm.of200("查询成功共：" + updates.size() + "条记录", updates);
    }

    /**
     * @param batch_name
     * @param s_group_id
     * @param pro_name
     * @return
     */
    // TODO: 2018/12/4 实验表里面有提交时间，觉得有点混乱
    @PostMapping("/getExperiment")
    public CommonResponseForm getScoreSubmitRecord(@RequestParam(required = false) String batch_name,
                                                   @RequestParam(required = false) String s_group_id,
                                                   @RequestParam(required = false) String pro_name) {
        List<Experiment> experiments = scoreService.getScoreSubmitRecord(batch_name, s_group_id, pro_name);
        if (experiments.size() == 0) {
            return CommonResponseForm.of400("查询失败，结果为空");
        } else return CommonResponseForm.of200("查询成功", experiments);

    }

    /**
     * @param way        方式
     * @param degreeForm form
     * @return f
     * @apiNote 管理员端-设置等级
     */
    @PostMapping("/setDegree")
    public CommonResponseForm setDegree(@RequestParam(required = false) String way,
                                        @RequestBody DegreeForm degreeForm) {

        scoreService.setDegree(way, degreeForm);
        return CommonResponseForm.of204("设置等级成功");
    }

    /**
     * @param scoreForm s
     * @return s
     * @apiNote 管理员端-修改成绩
     */
    @PostMapping("/updateScore")
    public CommonResponseForm updateScore(@RequestBody Map<String, String> scoreForm) {
        if (scoreService.updateScore2(scoreForm))
            return CommonResponseForm.of204("修改成功");
        return CommonResponseForm.of400("成绩已发布，无法修改");
    }

    /**
     * @param pro_name 工序
     * @return f
     * @apiNote 导入学生成绩
     */
    @PostMapping("/importScore")
    public CommonResponseForm importScore(@RequestParam MultipartFile file, String batch_name, @RequestParam String pro_name) throws IOException {
        int flag = scoreService.importScore(file, batch_name, pro_name);
        if (flag == 1) {
            return CommonResponseForm.of204("导入成绩成功，部分导入失败，不属于该批次");
        } else if (flag == 2) {
            return CommonResponseForm.of204("部分导入失败，id不正确");
        }
        return CommonResponseForm.of204("导入成绩成功");

    }

    /**
     * @apiNote 计算总成绩
     */
    @PostMapping("/executeScore")
    public CommonResponseForm executeScore(@RequestParam String batch_name) {
        scoreService.executeScore(batch_name);
        return CommonResponseForm.of204("计算成功");

    }

    /**
     * @apiNote 查询 特殊成绩
     * @param sid di
     * @param sname sname
     * @return f
     */
    @PostMapping("/getSpScore")
    public CommonResponseForm getSpScore(@RequestParam(required = false) String sid,
                                         @RequestParam(required = false) String sname) {
        List<Map<String, String>> maps=scoreService.getSpScore(sid,sname);
        return CommonResponseForm.of200("查询成功: 共"+maps.size(),maps);
    }

    /**
     * @apiNote  修改特殊学生成绩
     */
    @PostMapping("/updateSpScore")
    public CommonResponseForm updateSpScore(@RequestParam String sid,
                                            @RequestBody HashMap<String,String> map){
        if(scoreService.updateSpScore(sid,map)){
            return CommonResponseForm.of204("修改成功");
        }
        return CommonResponseForm.of204("修改失败,可能是学号或者是工序名出错了");
    }


}
