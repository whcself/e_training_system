package com.csu.etrainingsystem.material.entity;

import lombok.Data;

import javax.persistence.*;

@Entity
@Data
@Table(name = "save")
public class Save {
    @Id
    @Column
    @GeneratedValue(strategy = GenerationType.IDENTITY)

    private Integer id;//id自增长
    private String clazz ;//名称+型号
    private String purchase_id;//申购编号
    private Integer    save_num;//入库数量
    private String save_time;//入库时间
    private String save_tname;//入库老师
    private String save_remark;//入库备注
    private boolean del_status;
}
