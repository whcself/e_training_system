package com.csu.etrainingsystem.marking.entity;


import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.io.Serializable;

@Entity
@Table(name = "marking")
public class Marking implements Serializable {



    @Id
    @Column
    private String markId ;
    private String t_group_id;
    private String authority;
    private boolean del_status;

    public Marking() {
    }

    public Marking(String markId, String t_group_id, String authority, boolean del_status) {
        this.markId = markId;
        this.t_group_id = t_group_id;
        this.authority = authority;
        this.del_status = del_status;
    }

    public String getMarkId() {
        return markId;
    }

    public void setMarkId(String markId) {
        this.markId = markId;
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
