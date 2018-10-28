package com.csu.etrainingsystem.purchase.entity;


import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.io.Serializable;
import java.util.Date;

@Entity
@Table(name = "purchase")
public class Purchase implements Serializable {

    @Id
    @Column
    private String clazz ;
    private String t_group_id ;
    private java.sql.Date pur_time ;
    private int pur_num;
    private boolean del_status;

    public Purchase() {
    }

    public Purchase(String clazz, String t_group_id, java.sql.Date pur_time, int pur_num, boolean del_status) {
        this.clazz = clazz;
        this.t_group_id = t_group_id;
        this.pur_time = pur_time;
        this.pur_num = pur_num;
        this.del_status = del_status;
    }

    public String getClazz() {
        return clazz;
    }

    public void setClazz(String clazz) {
        this.clazz = clazz;
    }

    public String getT_group_id() {
        return t_group_id;
    }

    public void setT_group_id(String t_group_id) {
        this.t_group_id = t_group_id;
    }

    public java.sql.Date getPur_time() {
        return pur_time;
    }

    public void setPur_time(java.sql.Date pur_time) {
        this.pur_time = pur_time;
    }

    public int getPur_num() {
        return pur_num;
    }

    public void setPur_num(int pur_num) {
        this.pur_num = pur_num;
    }

    public boolean isDel_status() {
        return del_status;
    }

    public void setDel_status(boolean del_status) {
        this.del_status = del_status;
    }
}
