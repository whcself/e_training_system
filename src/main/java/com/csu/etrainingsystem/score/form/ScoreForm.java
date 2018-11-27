package com.csu.etrainingsystem.score.form;

import lombok.Data;
import lombok.Setter;

import java.util.Map;

//铸造/数控车/Cimatron/激光切割/加工中心/热处理/焊接/快速成型/锻压/铣削/磨削/车削/钳工/线切割/数控车仿真/激光切割
@Data
@Setter
public class ScoreForm {
    private String batchName;
    private String sGroupId;
    private String stuName;
    private String sid;
    private float zhuzao;
    private float shukongche;
    private float cimatron;
    private float jiguang;
    private float process;
    private float heat;
    private float hanjie;
    private float rapidprototyping;
    private float duanya;
    private float xixue;
    private float moxue;
    private float chexue;
    private float qiangong;
    private float xianqiege;
    private float shukongchefangzheng;
    private float attendance;//考勤
    private float computer;
    private float report;
    private float carMake;
    private float carDesign;
    private float addScore;
    private Map<String,Float> items;
    private float totalScore;
    private String degree;

}
