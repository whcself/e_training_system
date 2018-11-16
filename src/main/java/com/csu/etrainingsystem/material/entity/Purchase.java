package com.csu.etrainingsystem.material.entity;


import lombok.Data;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.io.Serializable;
import java.util.Date;

@Entity
@Data
@Table(name = "purchase")
public class Purchase implements Serializable {

    @Id
    @Column(length = 20)
    private String clazz ;
    private String tname ;
    private Date pur_time ;
    private int pur_num;
    private boolean del_status;
}
