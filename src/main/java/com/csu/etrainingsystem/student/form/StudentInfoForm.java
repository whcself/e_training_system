package com.csu.etrainingsystem.student.form;

import lombok.Data;

@Data
public class StudentInfoForm {
    private String s_group_id;
    private String batch_name;
    private String clazz;
    private String sname;
    private String sid;

    public StudentInfoForm(String s_group_id, String batch_name, String clazz, String sname, String sid) {
        this.s_group_id = s_group_id;
        this.batch_name = batch_name;
        this.clazz = clazz;
        this.sname = sname;
        this.sid = sid;
    }
}
