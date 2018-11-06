package com.csu.etrainingsystem.student.entity;


import javax.persistence.Embeddable;
import java.io.Serializable;
import java.util.Objects;

@Embeddable
public class StudentGroupId implements Serializable {
    private  String s_group_id;
    private String batch_name;

    public StudentGroupId() {
    }

    public StudentGroupId(String s_group_id, String batch_name) {
        this.s_group_id = s_group_id;
        this.batch_name = batch_name;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass () != o.getClass ()) return false;
        StudentGroupId that = (StudentGroupId) o;
        return Objects.equals (s_group_id, that.s_group_id) &&
                Objects.equals (batch_name, that.batch_name);
    }

    @Override
    public int hashCode() {

        return Objects.hash (s_group_id, batch_name);
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
}
