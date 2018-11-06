package com.csu.etrainingsystem.teacher.entity;


import javax.persistence.*;
import java.io.Serializable;
@Embeddable
@Entity
@Table(name = "t_group")
public class TeacherGroup implements Serializable {


    @EmbeddedId
    @Column//必须用复合主键
    private TeacherGroupId teacherGroupId;
    private int t_num;
    private boolean del_status;

    public TeacherGroup() {
    }

    public TeacherGroup(TeacherGroupId teacherGroupId, int t_num, boolean del_status) {
        this.teacherGroupId = teacherGroupId;
        this.t_num = t_num;
        this.del_status = del_status;
    }

    public TeacherGroupId getTeacherGroupId() {
        return teacherGroupId;
    }

    public void setTeacherGroupId(TeacherGroupId teacherGroupId) {
        this.teacherGroupId = teacherGroupId;
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
