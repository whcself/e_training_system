package com.csu.etrainingsystem.administrator.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.io.Serializable;

//create the admin persistence layer
@Entity
@Table(name = "tb_admin")
public class Admin implements Serializable {
    @Id
    @Column(length = 20)
    private String aId;
    private String name;
    private String phone;

    public Admin(){

    }
    public Admin(String aId, String name, String phone) {
        this.aId = aId;
        this.name = name;
        this.phone = phone;
    }

    public String getaId() {
        return aId;
    }

    public void setaId(String aId) {
        this.aId = aId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }
}
