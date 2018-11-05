package com.csu.etrainingsystem.studentGroup.entity;

import javax.persistence.*;
import java.io.Serializable;

@Entity
@Table(name = "s_group")
public class StudentGroup implements Serializable {

    @EmbeddedId
    @Column
    private StudentGroupId studentGroupId;
    private int num;
    private  boolean del_status;

    public StudentGroup() {
    }

    public StudentGroup(StudentGroupId studentGroupId, int num, boolean del_status) {
        this.studentGroupId = studentGroupId;
        this.num = num;
        this.del_status = del_status;
    }

    public StudentGroupId getStudentGroupId() {
        return studentGroupId;
    }

    public void setStudentGroupId(StudentGroupId studentGroupId) {
        this.studentGroupId = studentGroupId;
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
