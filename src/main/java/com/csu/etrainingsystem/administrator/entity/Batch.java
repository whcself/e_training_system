package com.csu.etrainingsystem.administrator.entity;

import javax.persistence.*;

@Entity
@Table(name = "batch")
public class Batch {

    @Id
    @Column(name="batch_name")
    private String batch_name;
    @Column(name="credit")
    private int credit;
    @Column(name="bat_describe")
    private String bat_describe;
    @Column(name="del_status")
    private  boolean del_status;

    public Batch() {
    }

    public Batch(String batch_name, int credit, String bat_describe) {
        this.batch_name = batch_name;
        this.credit = credit;
        this.bat_describe = bat_describe;
    }

    public String getBatch_name() {
        return batch_name;
    }

    public void setBatch_name(String batch_name) {
        this.batch_name = batch_name;
    }

    public int getCredit() {
        return credit;
    }

    public void setCredit(int credit) {
        this.credit = credit;
    }

    public String getBat_describe() {
        return bat_describe;
    }

    public void setBat_describe(String bat_describe) {
        this.bat_describe = bat_describe;
    }

    public boolean isDel_status() {
        return del_status;
    }

    public void setDel_status(boolean del_status) {
        this.del_status = del_status;
    }
}
