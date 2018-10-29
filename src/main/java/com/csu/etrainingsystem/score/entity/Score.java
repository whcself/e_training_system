package com.csu.etrainingsystem.score.entity;


import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.io.Serializable;

@Entity
@Table(name = "score")
public class Score implements Serializable {


    @Id
    @Column(length = 20)
    private String sid;
    @Id
    @Column(length = 20)
    private String pro_name;
    private float pro_score;
    private boolean del_status;

    public Score() {
    }

    public Score(String sid, String pro_name, float pro_score, boolean del_status) {
        this.sid = sid;
        this.pro_name = pro_name;
        this.pro_score = pro_score;
        this.del_status = del_status;
    }

    public String getSid() {
        return sid;
    }

    public void setSid(String sid) {
        this.sid = sid;
    }

    public String getPro_name() {
        return pro_name;
    }

    public void setPro_name(String pro_name) {
        this.pro_name = pro_name;
    }

    public float getPro_score() {
        return pro_score;
    }

    public void setPro_score(float pro_score) {
        this.pro_score = pro_score;
    }

    public boolean isDel_status() {
        return del_status;
    }

    public void setDel_status(boolean del_status) {
        this.del_status = del_status;
    }
}
