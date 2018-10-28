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
    @Column
    private String applyId ;

    private String sid;
    private String reason;
    private java.sql.Date appl_time;
    private java.sql.Date ovke_time;
    private boolean del_status;

    public Overwork_apply() {
    }

    public Overwork_apply(String applyId, String sid, String reason, java.sql.Date appl_time, java.sql.Date ovke_time, boolean del_status) {
        this.applyId = applyId;
        this.sid = sid;
        this.reason = reason;
        this.appl_time = appl_time;
        this.ovke_time = ovke_time;
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

    public java.sql.Date getAppl_time() {
        return appl_time;
    }

    public void setAppl_time(java.sql.Date appl_time) {
        this.appl_time = appl_time;
    }

    public java.sql.Date getOvke_time() {
        return ovke_time;
    }

    public void setOvke_time(java.sql.Date ovke_time) {
        this.ovke_time = ovke_time;
    }

    public boolean isDel_status() {
        return del_status;
    }

    public void setDel_status(boolean del_status) {
        this.del_status = del_status;
    }
}
