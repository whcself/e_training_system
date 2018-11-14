package com.csu.etrainingsystem.score.controller;

import com.csu.etrainingsystem.experiment.entity.Experiment;
import com.csu.etrainingsystem.form.CommonResponseForm;
import com.csu.etrainingsystem.score.form.ScoreForm;
import com.csu.etrainingsystem.score.service.ScoreService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.stereotype.Repository;
import org.springframework.web.bind.annotation.*;

import javax.print.attribute.standard.Sides;
import java.util.List;

@RestController
@RequestMapping(value = "/score", method = RequestMethod.POST)
public class ScoreController {
    private final ScoreService scoreService;

    @Autowired
    public ScoreController(ScoreService scoreService) {
        this.scoreService = scoreService;
    }

    /*
    复合主键,主键中有两个字段,等会测试
     */
    @RequestMapping(value = "/getScore/{sid}/")
    public CommonResponseForm getScore(@PathVariable("sid") String sid) {
        return CommonResponseForm.of200("获取分数成功", scoreService.getScoreBySid(sid));
    }


    /**
     * @apiNote 管理员端：成绩列表 学生端：评分查询
     * @param batch_name 批次名
     * @param s_group_id 学生组
     * @param pro_name   工序名
     * @return 成绩列表, 上三查询，或确定工序名
     *
     * 学生端就传自己的id
     */
    @RequestMapping("/getScore")
    public CommonResponseForm getScoreByBatchAndTeamOrProced(@RequestParam(required = false) String batch_name,
                                                             @RequestParam(required = false) String s_group_id,
                                                             @RequestParam(required = false) String pro_name,
                                                             @RequestParam(required = false) String sId,
                                                             @RequestParam(required = false) String sName) {
        List<ScoreForm> scoreForms = scoreService.getScoreByBatchAndSGroupOrProName(batch_name, s_group_id, pro_name, sId, sName);
        if ( scoreForms.size() == 0) {
            return CommonResponseForm.of400("查询失败，结果为空");
        }
        return CommonResponseForm.of200("查询成功", scoreForms);
    }

    /**
     * @apiNote 成绩提交记录
     * @param batch_name 批次
     * @param s_group_id 学生组
     * @param pro_name 工序
     * @return form
     */
    @PostMapping("/getScoreRecord")
    public CommonResponseForm getScoreSubmitRecord(@RequestParam(required = false) String batch_name,
                                                   @RequestParam(required = false) String s_group_id,
                                                   @RequestParam(required = false) String pro_name){
        List<Experiment> experiments=scoreService.getScoreSubmitRecord(batch_name,s_group_id,pro_name);
        if(experiments.size()==0){
            return CommonResponseForm.of400("查询失败，结果为空");
        }else return CommonResponseForm.of200("查询成功",experiments);

    }

}
