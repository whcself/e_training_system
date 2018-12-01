package com.csu.etrainingsystem.teacher.form;

import com.csu.etrainingsystem.teacher.entity.Teacher;
import lombok.Data;

@Data
public class TeacherForm {
    private String tid;
    private String tname;
    private String t_group_id;
    private String role;
    private int material_privilege;
    private int overtime_privilege;
    private boolean del_status;
}
