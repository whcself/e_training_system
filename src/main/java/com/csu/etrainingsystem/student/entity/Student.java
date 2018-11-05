package com.csu.etrainingsystem.student.entity;

import javax.persistence.*;
import java.io.Serializable;

@Entity
@Table(name = "student")
public class Student implements Serializable {
    @Id
    @Column
    private String sid;
    @Column(name = "s_group_id")
    private  String s_group_id;
    private String batch_name;
    private String  sname;
    @Column(name="clazz")
    private String clazz;
    private String sdept;
    private String depart;
    private String exm_score;
    private String total_score;
    private boolean del_status;
    private String degree;
    private String attend_score;
    private String pluses_score;
    private String report_score;
    private String car_design_score;
    private String car_prod_score;
    private String car_compet_score;

    public Student() {
    }

    public Student(String sid, String s_group_id, String batch_name, String sname, String clazz, String sdept, String depart, String exm_score, String total_score, boolean del_status, String degree, String attend_score, String pluses_score, String report_score, String car_design_score, String car_prod_score, String car_compet_score) {
        this.sid = sid;
        this.s_group_id = s_group_id;
        this.batch_name = batch_name;
        this.sname = sname;
        this.clazz = clazz;
        this.sdept = sdept;
        this.depart = depart;
        this.exm_score = exm_score;
        this.total_score = total_score;
        this.del_status = del_status;
        this.degree = degree;
        this.attend_score = attend_score;
        this.pluses_score = pluses_score;
        this.report_score = report_score;
        this.car_design_score = car_design_score;
        this.car_prod_score = car_prod_score;
        this.car_compet_score = car_compet_score;
    }

    public String getSid() {
        return sid;
    }

    public void setSid(String sid) {
        this.sid = sid;
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

    public String getSname() {
        return sname;
    }

    public void setSname(String sname) {
        this.sname = sname;
    }

    public String getClazz() {
        return clazz;
    }

    public void setClazz(String clazz) {
        this.clazz = clazz;
    }

    public String getSdept() {
        return sdept;
    }

    public void setSdept(String sdept) {
        this.sdept = sdept;
    }

    public String getDepart() {
        return depart;
    }

    public void setDepart(String depart) {
        this.depart = depart;
    }

    public String getExm_score() {
        return exm_score;
    }

    public void setExm_score(String exm_score) {
        this.exm_score = exm_score;
    }

    public String getTotal_score() {
        return total_score;
    }

    public void setTotal_score(String total_score) {
        this.total_score = total_score;
    }

    public boolean getDel_status() {
        return del_status;
    }

    public void setDel_status(boolean del_status) {
        this.del_status = del_status;
    }

    public String getDegree() {
        return degree;
    }

    public void setDegree(String degree) {
        this.degree = degree;
    }

    public String getAttend_score() {
        return attend_score;
    }

    public void setAttend_score(String attend_score) {
        this.attend_score = attend_score;
    }

    public String getPluses_score() {
        return pluses_score;
    }

    public void setPluses_score(String pluses_score) {
        this.pluses_score = pluses_score;
    }

    public String getReport_score() {
        return report_score;
    }

    public void setReport_score(String report_score) {
        this.report_score = report_score;
    }

    public String getCar_design_score() {
        return car_design_score;
    }

    public void setCar_design_score(String car_design_score) {
        this.car_design_score = car_design_score;
    }

    public String getCar_prod_score() {
        return car_prod_score;
    }

    public void setCar_prod_score(String car_prod_score) {
        this.car_prod_score = car_prod_score;
    }

    public String getCar_compet_score() {
        return car_compet_score;
    }

    public void setCar_compet_score(String car_compet_score) {
        this.car_compet_score = car_compet_score;
    }
}

