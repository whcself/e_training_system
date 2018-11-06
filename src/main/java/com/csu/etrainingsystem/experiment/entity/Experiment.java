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
    private  int exp_id ;
    private String batch_name;
    private String time_quant;
    private String class_time;
    private String t_group_id;
    private String s_group_id;
    private String pro_name;
    private String tid;
    private java.sql.Date submit_time;
    private boolean del_status;

    public Experiment() {
    }

    public Experiment(int exp_id, String batch_name, String time_quant, String class_time, String t_group_id, String s_group_id, String pro_name, String tid, java.sql.Date submit_time, boolean del_status) {
        this.exp_id = exp_id;
        this.batch_name = batch_name;
        this.time_quant = time_quant;
        this.class_time = class_time;
        this.t_group_id = t_group_id;
        this.s_group_id = s_group_id;
        this.pro_name = pro_name;
        this.tid = tid;
        this.submit_time = submit_time;
        this.del_status = del_status;
    }

    public int getExp_id() {
        return exp_id;
    }

    public void setExp_id(int exp_id) {
        this.exp_id = exp_id;
    }

    public String getBatch_name() {
        return batch_name;
    }

    public void setBatch_name(String batch_name) {
        this.batch_name = batch_name;
    }

    public String getTime_quant() {
        return time_quant;
    }

    public void setTime_quant(String time_quant) {
        this.time_quant = time_quant;
    }

    public String getClass_time() {
        return class_time;
    }

    public void setClass_time(String class_time) {
        this.class_time = class_time;
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
