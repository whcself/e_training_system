package com.csu.etrainingsystem.material.form;

import lombok.Data;

@Data
public class SaveQueryForm {
    private String begin;
    private String end;
    private String clazz;
    private String tname;
    private String pid;
}
