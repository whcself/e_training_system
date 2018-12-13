package com.csu.etrainingsystem.teacher.entity;


import lombok.Data;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.io.Serializable;

@Data
@Entity
@Table(name = "marking")
public class Marking implements Serializable {



    @Id
    @Column
    private Integer mark_id ;//没有实际意义,只是唯一标识,自动增长
    private String t_group_id;
    private String authority;
    private boolean del_status;


}
