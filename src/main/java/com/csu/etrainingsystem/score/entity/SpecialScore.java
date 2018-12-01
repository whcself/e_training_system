package com.csu.etrainingsystem.score.entity;

import lombok.Data;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.io.Serializable;
import java.util.Date;

@Data
@Table(name = "sp_score")
@Entity
public class SpecialScore  implements Serializable {

    @Column
    @Id
    private int sp_scoreid;//无意义,自增长
    private String tid;
    private String sid;
    private String pro_name;
    private float pro_score;
    private Date time;//对于特殊学生,查询上课时间是在成绩表里面实现的吗,一开始就指定他的课程

}