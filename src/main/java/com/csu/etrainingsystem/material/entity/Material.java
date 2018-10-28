package com.csu.etrainingsystem.material.entity;


import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.io.Serializable;
import java.util.Date;

@Entity
@Table(name = "material")
public class Material implements Serializable {


    @Id
    @Column
    private String clazz ;
    private int num;
    private boolean del_status;

    public Material() {
    }

    public Material(String clazz, int num, boolean del_status) {
        this.clazz = clazz;
        this.num = num;
        this.del_status = del_status;
    }

    public String getClazz() {
        return clazz;
    }

    public void setClazz(String clazz) {
        this.clazz = clazz;
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
