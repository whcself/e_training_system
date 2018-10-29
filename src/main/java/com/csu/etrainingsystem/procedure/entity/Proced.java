package com.csu.etrainingsystem.procedure.entity;


import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.io.Serializable;
import java.util.Date;

@Entity
@Table(name = "proced")
public class Proced implements Serializable {



    @Id
    @Column(length = 20)
    private String pro_name ;
    private String t_group_id;
    private String batch_name;
    private float weight;
    private boolean del_status;

    public Proced() {
    }

    public Proced(String pro_name, String t_group_id, String batch_name, float weight, boolean del_status) {
        this.pro_name = pro_name;
        this.t_group_id = t_group_id;
        this.batch_name = batch_name;
        this.weight = weight;
        this.del_status = del_status;
    }

    public String getPro_name() {
        return pro_name;
    }

    public void setPro_name(String pro_name) {
        this.pro_name = pro_name;
    }

    public String getT_group_id() {
        return t_group_id;
    }

    public void setT_group_id(String t_group_id) {
        this.t_group_id = t_group_id;
    }

    public String getBatch_name() {
        return batch_name;
    }

    public void setBatch_name(String batch_name) {
        this.batch_name = batch_name;
    }

    public float getWeight() {
        return weight;
    }

    public void setWeight(float weight) {
        this.weight = weight;
    }

    public boolean isDel_status() {
        return del_status;
    }

    public void setDel_status(boolean del_status) {
        this.del_status = del_status;
    }
}
