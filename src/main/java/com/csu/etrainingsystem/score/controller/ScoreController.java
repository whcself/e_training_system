package com.csu.etrainingsystem.score.controller;

import com.csu.etrainingsystem.form.CommonResponseForm;
import com.csu.etrainingsystem.score.service.ScoreService;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.print.attribute.standard.Sides;

@Controller
public class ScoreController {
    private final ScoreService scoreService;

    public ScoreController(ScoreService scoreService) {
        this.scoreService = scoreService;
    }

     /*
     复合主键,主键中有两个字段,等会测试
      */
    @RequestMapping(value="/getScore/{sid}/")
    public CommonResponseForm getBatch(@PathVariable("sid") String sid){
        return CommonResponseForm.of200("获取分数成功",scoreService.getScoreBySid(sid));
    }

}
