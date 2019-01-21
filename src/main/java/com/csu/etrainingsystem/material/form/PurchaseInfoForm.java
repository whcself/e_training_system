package com.csu.etrainingsystem.material.form;

import lombok.Data;

import javax.persistence.Id;

@Data
public class PurchaseInfoForm {
    @Id
    private String pur_tname;
    private String clazz;
    private Integer apply_num;
    private Boolean apply_verify;
}
