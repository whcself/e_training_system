package com.csu.etrainingsystem.administrator.form;

import lombok.Data;

@Data
public class SemesterForm  {

    private String semester_name;
    private int incrementId;

    public SemesterForm(String semester_name, int incrementId) {
        this.semester_name = semester_name;
        this.incrementId = incrementId;
    }
}
