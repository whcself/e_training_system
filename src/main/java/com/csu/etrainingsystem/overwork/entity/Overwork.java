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
    @Column
    private String ovrkeId ;
    private String pro_name;
    private java.sql.Date ovkeTime;
    private boolean del_status;

    public Overwork() {
    }

    public Overwork(String ovrkeId, String pro_name, java.sql.Date ovkeTime, boolean del_status) {
        this.ovrkeId = ovrkeId;
        this.pro_name = pro_name;
        this.ovkeTime = ovkeTime;
        this.del_status = del_status;
    }

    public String getOvrkeId() {
        return ovrkeId;
    }

    public void setOvrkeId(String ovrkeId) {
        this.ovrkeId = ovrkeId;
    }

    public String getPro_name() {
        return pro_name;
    }

    public void setPro_name(String pro_name) {
        this.pro_name = pro_name;
    }

    public java.sql.Date getOvkeTime() {
        return ovkeTime;
    }

    public void setOvkeTime(java.sql.Date ovkeTime) {
        this.ovkeTime = ovkeTime;
    }

    public boolean isDel_status() {
        return del_status;
    }

    public void setDel_status(boolean del_status) {
        this.del_status = del_status;
    }
}
