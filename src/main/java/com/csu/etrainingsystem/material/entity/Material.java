package com.csu.etrainingsystem.material.entity;


import lombok.Data;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.io.Serializable;
import java.util.Date;

/**
 * -whc
 * 物料实体
 */
@Entity
@Table(name = "material")
@Data
public class Material implements Serializable {


    @Id
    @Column(length = 20)
    private String clazz ;
    private int num;
    private boolean del_status;
    public Material(String clazz, int num, boolean del_status) {
        this.clazz = clazz;
        this.num = num;
        this.del_status = del_status;
    }

}
