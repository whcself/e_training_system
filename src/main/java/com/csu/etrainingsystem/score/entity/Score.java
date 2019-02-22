package com.csu.etrainingsystem.score.entity;


import lombok.Data;

import javax.persistence.*;
import java.io.Serializable;

@Entity
@Table(name = "score")
@Data
public class Score implements Serializable {

    @Id
    @Column
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer scoreid;//无意义,自增长
    private String sid;
    private String pro_name;
    private Float pro_score;
    private String tname;  //提交老师
    private String enter_time;
    private boolean del_status;




}
/**
 * 需要在导入学生的时候创建学生的成绩表?
 */