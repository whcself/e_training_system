package com.csu.etrainingsystem.teacher.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.io.Serializable;

@Entity
@Table(name = "teacher")
public class Teacher implements Serializable {

    @Id
    @Column(name = "tid")
    private String tid;
    private String t_group_id;
    private String tname;
    private String role;
    private int material_privilege;
    private int overtime_privilege;
    private boolean del_status;

    public Teacher() { }

    public Teacher(String tid, String t_group_id, String tname, String role, int material_privilege, int overtime_privilege, boolean del_status) {
        this.tid = tid;
        this.t_group_id = t_group_id;
        this.tname = tname;
        this.role = role;
        this.material_privilege = material_privilege;
        this.overtime_privilege = overtime_privilege;
        this.del_status = del_status;
    }

    public String getTid() { return tid; }

    public void setTid(String tid) { this.tid = tid; }

    public String getT_group_id() { return t_group_id; }

    public void setT_group_id(String t_group_id) { this.t_group_id = t_group_id; }

    public String getTname() { return tname; }

    public void setTname(String tname) { this.tname = tname; }

    public String getRole() { return role; }

    public void setRole(String role) { this.role = role; }

    public int getMaterial_privilege() { return material_privilege; }

    public void setMaterial_privilege(int material_privilege) { this.material_privilege = material_privilege; }

    public int getOvertime_privilege() {
        return overtime_privilege;
    }

    public void setOvertime_privilege(int overtime_privilege) {
        this.overtime_privilege = overtime_privilege;
    }

    public boolean isDel_status() { return del_status; }

    public void setDel_status(boolean del_status) { this.del_status = del_status; }
}
