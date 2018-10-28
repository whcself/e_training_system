package com.csu.etrainingsystem.teacherGroup.entity;


import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.io.Serializable;

@Entity
@Table(name = "t_group")
public class TeacherGroup implements Serializable {


    @Id
    @Column
    private String t_group_id;
    private int t_num;
    private boolean del_status;

    public TeacherGroup() {
    }

    public TeacherGroup(String t_group_id, int t_num, boolean del_status) {
        this.t_group_id = t_group_id;
        this.t_num = t_num;
        this.del_status = del_status;
    }

    public String getT_group_id() {
        return t_group_id;
    }

    public void setT_group_id(String t_group_id) {
        this.t_group_id = t_group_id;
    }

    public int getT_num() {
        return t_num;
    }

    public void setT_num(int t_num) {
        this.t_num = t_num;
    }

    public boolean isDel_status() {
        return del_status;
    }

    public void setDel_status(boolean del_status) {
        this.del_status = del_status;
    }
}
