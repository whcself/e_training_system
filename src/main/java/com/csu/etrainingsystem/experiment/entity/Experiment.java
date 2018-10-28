package com.csu.etrainingsystem.experiment.entity;


import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.io.Serializable;
import java.util.Date;

@Entity
@Table(name = "experiment")
public class Experiment implements Serializable {

    @Id
    @Column
    private String expId ;
    private String t_group_id;
    private String s_group_id;
    private String time_quant;
    private String pro_name;
    private String tid;
    private java.sql.Date submit_time;
    private boolean del_status;

    public Experiment() {
    }

    public Experiment(String expId, String t_group_id, String s_group_id, String time_quant, String pro_name, String tid, java.sql.Date submit_time, boolean del_status) {
        this.expId = expId;
        this.t_group_id = t_group_id;
        this.s_group_id = s_group_id;
        this.time_quant = time_quant;
        this.pro_name = pro_name;
        this.tid = tid;
        this.submit_time = submit_time;
        this.del_status = del_status;
    }

    public String getExpId() {
        return expId;
    }

    public void setExpId(String expId) {
        this.expId = expId;
    }

    public String getT_group_id() {
        return t_group_id;
    }

    public void setT_group_id(String t_group_id) {
        this.t_group_id = t_group_id;
    }

    public String getS_group_id() {
        return s_group_id;
    }

    public void setS_group_id(String s_group_id) {
        this.s_group_id = s_group_id;
    }

    public String getTime_quant() {
        return time_quant;
    }

    public void setTime_quant(String time_quant) {
        this.time_quant = time_quant;
    }

    public String getPro_name() {
        return pro_name;
    }

    public void setPro_name(String pro_name) {
        this.pro_name = pro_name;
    }

    public String getTid() {
        return tid;
    }

    public void setTid(String tid) {
        this.tid = tid;
    }

    public java.sql.Date getSubmit_time() {
        return submit_time;
    }

    public void setSubmit_time(java.sql.Date submit_time) {
        this.submit_time = submit_time;
    }

    public boolean isDel_status() {
        return del_status;
    }

    public void setDel_status(boolean del_status) {
        this.del_status = del_status;
    }
}
