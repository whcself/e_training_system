package com.csu.etrainingsystem.score.form;

import lombok.Data;
import lombok.Setter;

import java.util.Map;

//铸造/数控车/Cimatron/激光切割/加工中心/热处理/焊接/快速成型/锻压/铣削/磨削/车削/钳工/线切割/数控车仿真/激光切割
@Data
public class ScoreForm {
    private String batchName;
    private String sGroupId;
    private String stuName;
    private String sid;
    private Float zhuzao;
    private Float shukongche;
    private Float cimatron;
    private Float jiguang;
    private Float process;
    private Float heat;
    private Float hanjie;
    private Float rapidprototyping;
    private Float duanya;
    private Float xixue;
    private Float moxue;
    private Float chexue;
    private Float qiangong;
    private Float xianqiege;
    private Float shukongchefangzheng;
    private Float attendance;//考勤
    private Float computer;
    private Float report;
    private Float carMake;
    private Float carDesign;
    private Float addScore;
    private Map<String,Float> items;
    private Float totalScore;
    private String degree;

}
