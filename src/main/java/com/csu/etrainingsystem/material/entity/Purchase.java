package com.csu.etrainingsystem.material.entity;


import lombok.Data;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;

@Entity
@Data
@Table(name = "purchase")
public class Purchase implements Serializable {


    @Id
    @Column
    @GeneratedValue(strategy = GenerationType.IDENTITY)

    private Integer id;//id自增长
    private String clazz;//名称+型号
    private String purchase_id;//申购编号
    private String pur_tname;//采购老师
    private String pur_time;//采购时间
    private String pur_remark;//采购备注
    private Integer pur_num;//采购数量
    private boolean del_status;
    /**后面写sql语句会用到,先马起来

     */
}
