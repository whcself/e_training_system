package com.csu.etrainingsystem.material.entity;

import lombok.Data;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Data
@Table(name = "apply_for_purchase")
public class ApplyForPurchase {
    @Id
    @Column
    private String purchase_id;//申购编号
    private String clazz ;//名称+型号
    private String apply_tname ;//申购老师
    private String apply_vert_tname;//申购审核老师
    private String apply_vert_time;//申购审核时间
    private String apply_time ;//申购时间
    private String apply_remark ;//申购备注
    private Integer    apply_num;//申购数量
    private Boolean apply_verify;//申购审核状态
    private String pur_tname;//采购老师
    private boolean del_status;
}
