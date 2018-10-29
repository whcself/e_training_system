package com.csu.etrainingsystem.overwork_apply.entity;


import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.io.Serializable;
import java.util.Date;

@Entity
@Table(name = "overwork_apply")
public class Overwork_apply implements Serializable {


    @Id
    @Column(length = 20)
    private String applyId ;

    private String sid;
    private String reason;
    private java.sql.Date apply_time;
    private java.sql.Date overwork_time;
    private boolean del_status;

    public Overwork_apply() {
    }

    public Overwork_apply(String applyId, String sid, String reason, java.sql.Date apply_time, java.sql.Date overwork_time, boolean del_status) {
        this.applyId = applyId;
        this.sid = sid;
        this.reason = reason;
        this.apply_time = apply_time;
        this.overwork_time = overwork_time;
        this.del_status = del_status;
    }

    public String getApplyId() {
        return applyId;
    }

    public void setApplyId(String applyId) {
        this.applyId = applyId;
    }

    public String getSid() {
        return sid;
    }

    public void setSid(String sid) {
        this.sid = sid;
    }

    public String getReason() {
        return reason;
    }

    public void setReason(String reason) {
        this.reason = reason;
    }

    public java.sql.Date getApply_time() {
        return apply_time;
    }

    public void setApply_time(java.sql.Date apply_time) {
        this.apply_time = apply_time;
    }

    public java.sql.Date getOverwork_time() {
        return overwork_time;
    }

    public void setOverwork_time(java.sql.Date overwork_time) {
        this.overwork_time = overwork_time;
    }

    public boolean isDel_status() {
        return del_status;
    }

    public void setDel_status(boolean del_status) {
        this.del_status = del_status;
    }
}
