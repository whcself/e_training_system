package com.csu.etrainingsystem.teacher.entity;


import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.io.Serializable;

@Entity
@Table(name = "marking")
public class Marking implements Serializable {



    @Id
    private int mark_id ;
    private String t_group_id;
    private String authority;
    private boolean del_status;

    public Marking() {
    }

    public Marking(int mark_id, String t_group_id, String authority, boolean del_status) {
        this.mark_id = mark_id;
        this.t_group_id = t_group_id;
        this.authority = authority;
        this.del_status = del_status;
    }

    public int getMark_id() {
        return mark_id;
    }

    public void setMark_id(int mark_id) {
        this.mark_id = mark_id;
    }

    public String getT_group_id() {
        return t_group_id;
    }

    public void setT_group_id(String t_group_id) {
        this.t_group_id = t_group_id;
    }

    public String getAuthority() {
        return authority;
    }

    public void setAuthority(String authority) {
        this.authority = authority;
    }

    public boolean isDel_status() {
        return del_status;
    }

    public void setDel_status(boolean del_status) {
        this.del_status = del_status;
    }
}
