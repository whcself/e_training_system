package com.csu.etrainingsystem.administrator.entity;

import lombok.Data;

import javax.annotation.security.DenyAll;
import javax.persistence.*;

@Data
@Entity
@Table(name = "batch")
public class Batch {

    @Id
    @Column(name = "batch_name", length = 20)
    private String batch_name;
    @Column(name = "credit")
    private Integer credit;
    @Column(name = "bat_describe")
    private String bat_describe;
    @Column(name = "del_status")
    private boolean del_status;
    private String semester_name;
    @Column(name = "begin_date")
    private String beginDate;
}