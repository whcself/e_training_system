package com.csu.etrainingsystem.material.entity;

import lombok.Data;

import javax.persistence.*;

@Entity
@Data
@Table(name="reimbursement")
public class Reimbursement {

    @Id
    @Column
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;//id自增长
    private String clazz ;//名称+型号
    private String purchase_id;//申购编号
    private String pur_tname;//采购老师
    private String remib_time;//报账时间
    private String remib_remark;//报账备注
    private String remib_vert_time;//报账审核时间
    private String remib_vert_tname;//报账审核老师
    private Integer    remib_num;//报账总数
    private Boolean remib_vertify;//审核状态
    private boolean del_status;
}
