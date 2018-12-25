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
    private Float total_score;
    @Column(name="clazz")
    private String clazz;
    private String sdept;
    private String depart;
    private boolean score_lock;
    private boolean del_status;
    private String degree;

}

