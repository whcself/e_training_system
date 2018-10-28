package com.csu.etrainingsystem.studentGroup.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.io.Serializable;

@Entity
@Table(name = "s_group")
public class StudentGroup implements Serializable {

    @Id
    @Column
    private  String s_group_id;
    private String batch_name;
    private int num;
    private  boolean del_status;

    public StudentGroup() {
    }

    public StudentGroup(String s_group_id, String batch_name, int num, boolean del_status) {
        this.s_group_id = s_group_id;
        this.batch_name = batch_name;
        this.num = num;
        this.del_status = del_status;
    }

    public String getS_group_id() {
        return s_group_id;
    }

    public void setS_group_id(String s_group_id) {
        this.s_group_id = s_group_id;
    }

    public String getBatch_name() {
        return batch_name;
    }

    public void setBatch_name(String batch_name) {
        this.batch_name = batch_name;
    }

    public int getNum() {
        return num;
    }

    public void setNum(int num) {
        this.num = num;
    }

    public boolean isDel_status() {
        return del_status;
    }

    public void setDel_status(boolean del_status) {
        this.del_status = del_status;
    }
}
