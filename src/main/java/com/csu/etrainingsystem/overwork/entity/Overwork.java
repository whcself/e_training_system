package com.csu.etrainingsystem.overwork.entity;


import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.io.Serializable;
import java.util.Date;

@Entity
@Table(name = "overwork")
public class Overwork implements Serializable {


    @Id
    @Column(length = 20)
    private String overwork_id ;
    private String pro_name;
    private java.sql.Date overwork_time;
    private boolean del_status;

    public Overwork() {
    }

    public Overwork(String overwork_id, String pro_name, java.sql.Date overwork_time, boolean del_status) {
        this.overwork_id = overwork_id;
        this.pro_name = pro_name;
        this.overwork_time = overwork_time;
        this.del_status = del_status;
    }

    public String getOverwork_id() {
        return overwork_id;
    }

    public void setOverwork_id(String overwork_id) {
        this.overwork_id = overwork_id;
    }

    public String getPro_name() {
        return pro_name;
    }

    public void setPro_name(String pro_name) {
        this.pro_name = pro_name;
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
