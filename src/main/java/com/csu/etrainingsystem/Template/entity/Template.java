package com.csu.etrainingsystem.Template.entity;


import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.io.Serializable;
import java.util.Date;

@Entity
@Table(name = "template")
public class Template implements Serializable {

    @Id
    @Column
    private String tempId ;
    private String s_group_id ;
    private String temp_name ;
    private int class_time;
    private String pro_name;
    private boolean del_status;

    public Template() {
    }

    public Template(String tempId, String s_group_id, String temp_name, int class_time, String pro_name, boolean del_status) {
        this.tempId = tempId;
        this.s_group_id = s_group_id;
        this.temp_name = temp_name;
        this.class_time = class_time;
        this.pro_name = pro_name;
        this.del_status = del_status;
    }

    public String getTempId() {
        return tempId;
    }

    public void setTempId(String tempId) {
        this.tempId = tempId;
    }

    public String getS_group_id() {
        return s_group_id;
    }

    public void setS_group_id(String s_group_id) {
        this.s_group_id = s_group_id;
    }

    public String getTemp_name() {
        return temp_name;
    }

    public void setTemp_name(String temp_name) {
        this.temp_name = temp_name;
    }

    public int getClass_time() {
        return class_time;
    }

    public void setClass_time(int class_time) {
        this.class_time = class_time;
    }

    public String getPro_name() {
        return pro_name;
    }

    public void setPro_name(String pro_name) {
        this.pro_name = pro_name;
    }

    public boolean isDel_status() {
        return del_status;
    }

    public void setDel_status(boolean del_status) {
        this.del_status = del_status;
    }
}
