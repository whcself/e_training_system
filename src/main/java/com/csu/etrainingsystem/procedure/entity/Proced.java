package com.csu.etrainingsystem.procedure.entity;


import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;

@Entity
@Table(name = "proced")
public class Proced implements Serializable {



    @EmbeddedId
    @Column
    private ProcedId proid;
    private String t_group_id;
    private float weight;
    private boolean del_status;

    public Proced() {
    }

    public Proced(ProcedId proid, String t_group_id, float weight, boolean del_status) {
        this.proid = proid;
        this.t_group_id = t_group_id;
        this.weight = weight;
        this.del_status = del_status;
    }

    public String getT_group_id() {
        return t_group_id;
    }

    public void setT_group_id(String t_group_id) {
        this.t_group_id = t_group_id;
    }

    public ProcedId getProid() {
        return proid;
    }

    public void setProid(ProcedId proid) {
        this.proid = proid;
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
