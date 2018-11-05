package com.csu.etrainingsystem.teacherGroup.entity;

import javax.persistence.Embeddable;
import javax.persistence.EmbeddedId;
import java.io.Serializable;
import java.util.Objects;

@Embeddable
public class TeacherGroupId implements Serializable {
    private String t_group_id;
    private String tid;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        TeacherGroupId that = (TeacherGroupId) o;
        return Objects.equals(t_group_id, that.t_group_id) &&
                Objects.equals(tid, that.tid);
    }

    @Override
    public int hashCode() {

        return Objects.hash(t_group_id, tid);
    }

    public TeacherGroupId() {
    }

    public TeacherGroupId(String t_group_id, String tid) {
        this.t_group_id = t_group_id;
        this.tid = tid;
    }

    public String getT_group_id() {
        return t_group_id;
    }

    public void setT_group_id(String t_group_id) {
        this.t_group_id = t_group_id;
    }

    public String getTid() {
        return tid;
    }

    public void setTid(String tid) {
        this.tid = tid;
    }
}
