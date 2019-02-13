package com.csu.etrainingsystem.administrator.form;

import lombok.Data;

@Data
public class SemesterForm  {

    private String semester_name;
    private String date;
    private int incrementId;

    public SemesterForm(String semester_name,String date, int incrementId) {
        this.date=date;
        this.semester_name = semester_name;
        this.incrementId = incrementId;
    }
}
