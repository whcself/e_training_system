package com.csu.etrainingsystem.score.controller;

import com.csu.etrainingsystem.form.CommonResponseForm;
import com.csu.etrainingsystem.score.entity.SpecialScore;
import com.csu.etrainingsystem.score.service.ScoreService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(value = "/specialScore",method = RequestMethod.POST)
public class SpecialScoreController {
    private final ScoreService scoreService;

    @Autowired
    public SpecialScoreController(ScoreService scoreService) {
        this.scoreService = scoreService;
    }

    /**
     * 获取某个特殊学生的课程
     * @param sid
     * @return
     */
    @RequestMapping("/getClassBySid")
    public CommonResponseForm getScoreAndClassById(String sid){
        return CommonResponseForm.of200 ("查询成功",this.scoreService.getSpScoreBySid(sid));
    }

    /**
     * 删除某个特殊学生的某几个课程
     * @param scoreIds
     * @return
     */
    @RequestMapping("/deleteClass")
    public CommonResponseForm deleteScoreAndClassByScoreId(@RequestBody String[] scoreIds){
        for (String scoreId : scoreIds) {
            this.scoreService.deleteSpScoreByscoreId (scoreId);
        }
        return CommonResponseForm.of204 ("删除成功");
    }

    /**
     * 删除某个特殊学生的所有课程,
     * @param sid
     * @return
     */
    @RequestMapping("/deleteAllClassBySid")
    public CommonResponseForm deleteAllClassBySid( String sid){
        this.scoreService.deleteSpScoreBySid (sid);
        return CommonResponseForm.of204 ("删除成功");
    }


    /**
     * 添加某个特殊学生的课程
     * @return
     */
    @RequestMapping("/addClass")
    public CommonResponseForm addClass(String sid, String proName,Integer classTime,String timeQuant){
        SpecialScore specialScore=new SpecialScore ();
        specialScore.setSid (sid);
        specialScore.setPro_name (proName);
        specialScore.setClass_time (classTime);
        specialScore.setTime_quant (timeQuant);
        this.scoreService.addSpScore (specialScore);
        return CommonResponseForm.of204 ("添加成功");
    }


}
