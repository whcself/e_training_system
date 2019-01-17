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
    @Column
    private int purchase_id;
    private String clazz ;//名称+型号
    private String apply_tname ;
    private String pur_tname;
    private String pur_time ;//申购时间
    private int apply_num;//申购数量
    private int pur_num;//采购数量
    private int save_num;//入库数量
    private String remark;//备注
    private String vert_tname;//审核老师姓名
    private int remib_num;//报账总数
    private boolean vert_status;//审核状态
    private boolean del_status;
}
