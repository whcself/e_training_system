package com.csu.etrainingsystem.student.entity;

import lombok.Data;

import javax.persistence.*;
import java.io.Serializable;

@Data
@Entity
@Table(name = "student")
public class Student implements Serializable {
    @Id
    @Column(length = 20)
    private String sid;
    @Column(name = "s_group_id")
    private  String s_group_id;
    private String batch_name;
    private String  sname;
    @Column(name="clazz")
    private String clazz;
    private String sdept;
    private String depart;
    private Float exm_score;
    private Float total_score;
    private Integer score_lock;
    private boolean del_status;
    private String degree;
    private Float attend_score;
    private Float pluses_score;
    private Float report_score;
    private Float car_design_score;
    private Float car_prod_score;
    private Float car_compet_score;

   }

