package com.csu.etrainingsystem.student.form;


import lombok.Data;

@Data
public class SpStudentInfoForm {
  private  String sid;
  private String sname;
  private String clazz;
  private String template_name;
    public SpStudentInfoForm(String sid, String sname, String clazz, String template_name) {
        this.sid = sid;
        this.sname = sname;
        this.clazz = clazz;
        this.template_name = template_name;
    }
}
