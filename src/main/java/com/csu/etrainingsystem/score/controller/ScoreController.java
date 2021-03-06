package com.csu.etrainingsystem.score.controller;

import com.csu.etrainingsystem.experiment.entity.Experiment;
import com.csu.etrainingsystem.form.CommonResponseForm;
import com.csu.etrainingsystem.score.entity.Score;
import com.csu.etrainingsystem.score.entity.ScoreSubmit;
import com.csu.etrainingsystem.score.entity.SpecialScore;
import com.csu.etrainingsystem.score.form.DegreeForm;
import com.csu.etrainingsystem.score.form.EnteringForm;
import com.csu.etrainingsystem.score.form.InputSearchForm;
import com.csu.etrainingsystem.score.service.ScoreService;
import com.csu.etrainingsystem.student.entity.SpecialStudent;
import com.csu.etrainingsystem.student.service.StudentService;
import com.csu.etrainingsystem.user.entity.User;
import com.csu.etrainingsystem.user.entity.UserRole;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

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

    /**
     * @param batch_name
     * @return
     * @apiNote 发布总成绩
     */
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
     * -ScJn
     *
     * @param batch_name 批次名
     * @param s_group_id 学生组
     * @param pro_name   工序名
     *                   学生端就传自己的id
     * @apiNote 管理员端：成绩列表 评分查询
     */
    @RequestMapping("/getScore")
    public CommonResponseForm getScoreByBatchAndTeamOrProced(@RequestParam(required = false) String batch_name,
                                                             @RequestParam(required = false) String s_group_id,
                                                             @RequestParam(required = false) String pro_name,
                                                             @RequestParam(required = false) String sId,
                                                             @RequestParam(required = false) String sName) {
        return scoreService.getScoreByBatchAndSGroupOrProName(batch_name, s_group_id, pro_name, sId, sName, false);
//        if ( == 0) {
//            return CommonResponseForm.of400("查询失败，结果为空");
//        }
    }

    /**
     * @param session s
     * @return form
     * @apiNote 学生端 我的成绩
     */
    @RequestMapping("/getMyScore")
    public CommonResponseForm getMyScore(@RequestParam(required = false) String sid, HttpSession session) {
        List<HashMap<String, String>> scoreForms;
        String role = (String) session.getAttribute("role");
        String itId=(String) session.getAttribute("id");
        if (role.equals("spStudent")) {
            return CommonResponseForm.of200("成功", scoreService.getSpScore2(itId));
        }
        if (role.equals("student")) {
            if (sid != null) {
                return scoreService.getScoreByBatchAndSGroupOrProName("all", "all", "all", sid, "all", true);
            } else {
                User user = UserRole.getUser(session);
                String sId = user.getAccount();
                return scoreService.getScoreByBatchAndSGroupOrProName("all", "all", "all", sId, "all", true);

            }
        }
        return CommonResponseForm.of400("此用户不是学生");
    }

    /**
     * @apiNote 计算特殊学生成绩
     * @param templateName
     * @return
     */
    @RequestMapping("/executeSpScore")
    public CommonResponseForm executeSpScore(@RequestParam String templateName){
        scoreService.executeSpScore(templateName);
        return CommonResponseForm.of204("计算成功");
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

    /**
     * @param batch_name
     * @param begin
     * @param end
     * @param sname
     * @param sid
     * @return
     * @apiNote 查询成绩修改记录
     */
    @PostMapping("/getScoreUpdate")
    public CommonResponseForm getScoreUpdate(@RequestParam(required = false) String batch_name,
                                             @RequestParam(required = false) String begin,
                                             @RequestParam(required = false) String end,
                                             @RequestParam(required = false) String sname,
                                             @RequestParam(required = false) String sid) {
        List<Map<String, String>> updates = scoreService.getScoreUpdate(batch_name, begin, end, sname, sid);
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

        return scoreService.setDegree(way, degreeForm);
    }

    /**
     * @param scoreForm s
     * @return s
     * @apiNote 管理员端-修改成绩
     */
    @PostMapping("/updateScore")
    public CommonResponseForm updateScore(@RequestBody Map<String, String> scoreForm,
                                          HttpSession session) {
        scoreService.updateScore2(scoreForm, true, session);
        return CommonResponseForm.of204("修改成功");
    }

    /**
     * @param scoreForm
     * @return
     * @apiNote 教师端-修改成绩
     */
    @PostMapping("/updateScore2")
    public CommonResponseForm updateScore2(@RequestBody Map<String, String> scoreForm,
                                           HttpSession session) {
        if (scoreService.updateScore2(scoreForm, false,session))
            return CommonResponseForm.of204("修改成功");
        return CommonResponseForm.of400("成绩已发布，无法修改");
    }


    /**
     * @param pro_name 工序
     * @return f
     * @apiNote 导入学生成绩
     */
    @PostMapping("/importScore")
    public CommonResponseForm importScore(@RequestParam MultipartFile file, String batch_name,
                                          @RequestParam String pro_name,
                                          HttpSession session) throws IOException {
        int flag = scoreService.importScore(file, batch_name, pro_name, session);
        if (flag == 1) {
            return CommonResponseForm.of204("导入成绩成功，部分学生不属于该批次所以未导入");
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
        return scoreService.executeScore(batch_name);

    }

    /**
     * @apiNote 下载成绩excel
     */

    @PostMapping("/scoreExcel")
    public void scoreExcel(@RequestBody List<List<Object>> data,
                           HttpServletResponse response) throws IOException {
        scoreService.downloadScoreExcel(response,data);
    }
    /**
     * -ScJn
     *
     * @param sid   di
     * @param sname sname
     * @return f
     * @apiNote 查询 特殊成绩
     */
    @PostMapping("/getSpScore")
    public CommonResponseForm getSpScore(@RequestParam(required = false) String sid,
                                         @RequestParam(required = false) String sname,
                                         @RequestParam(required = false) String templateName) {
        List<Map<String, String>> maps = scoreService.getSpScore(sid, sname, templateName, false);
        return CommonResponseForm.of200("查询成功: 共" + maps.size(), maps);
    }

    /**
     * @apiNote 修改特殊学生成绩
     */
    @PostMapping("/updateSpScore")
    public CommonResponseForm updateSpScore(@RequestParam String sid,
                                            @RequestBody HashMap<String, String> map,
                                            HttpSession session) {


        SpecialStudent spStudent;
        try {
            spStudent = studentService.findSpStudentById(sid);
        } catch (Exception e) {
            return CommonResponseForm.of400("修改失败,该学号不存在");
        }
        if (spStudent.isScore_lock() && !session.getAttribute("role").equals("管理员")) {
            return CommonResponseForm.of400("成绩已经发布，不能修改");
        } else {
            scoreService.updateSpScore(spStudent, map);
            return CommonResponseForm.of204("修改成功");
        }
    }

    /**
     * @apiNote 提交特殊学生成绩
     */
    @PostMapping("/releaseSpScore")
    public CommonResponseForm releaseSpScore(@RequestBody Map<String, String> sids) {
        scoreService.releaseSpScore(sids);
        return CommonResponseForm.of204("发布成功");
    }

    /**
     * @apiNote 录入记录
     */
    @PostMapping("/getInputInfo")
    public CommonResponseForm getInputInfo(@RequestBody InputSearchForm form) {
        List<EnteringForm> scores = scoreService.getInputInfo(form.getSId(), form.getSName(), form.getSGroup(), form.getBatchName(), form.getProName());
        return CommonResponseForm.of200("共" + scores.size() + "条", scores);
    }

    public static void main(String[] args) {


    }

}
