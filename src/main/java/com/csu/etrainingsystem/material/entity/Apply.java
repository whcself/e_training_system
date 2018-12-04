package com.csu.etrainingsystem.material.entity;


import lombok.Data;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.io.Serializable;
import java.util.Date;

@Data
@Entity
@Table(name = "material_apply")
public class Apply implements Serializable {
    @Id
    @Column
    private  int apply_id;
    private String apply_time;
   private String sid;
   private String tid ;
    private String sname;
   private String clazz;
   private int num;
}
