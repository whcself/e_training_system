package com.csu.etrainingsystem.score.entity;


import javax.persistence.*;
import java.io.Serializable;

@Entity
@Table(name = "score")
public class Score implements Serializable {

    @Id
    @Column
    private int scoreid;//无意义,自增长
    private String sid;
    private String pro_name;
    private float pro_score;
    private boolean del_status;

    public Score() {
    }

    public Score(int scoreid, String sid, String pro_name, float pro_score, boolean del_status) {
        this.scoreid = scoreid;
        this.sid = sid;
        this.pro_name = pro_name;
        this.pro_score = pro_score;
        this.del_status = del_status;
    }

    public int getScoreid() {
        return scoreid;
    }

    public void setScoreid(int scoreid) {
        this.scoreid = scoreid;
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
/**
 * 需要在导入学生的时候创建学生的成绩表?
 */