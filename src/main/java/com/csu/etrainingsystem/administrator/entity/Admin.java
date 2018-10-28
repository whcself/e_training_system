package com.csu.etrainingsystem.administrator.entity;

import javax.persistence.*;
import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import java.io.Serializable;

//create the admin persistence layer
@Entity
@Table(name = "admin")
public class Admin implements Serializable {
    @Id
    @Column(name="aid")
    private String adminId;
    @Column(name="del_status")
    private boolean del_status;
    public Admin(){

    }
    public Admin(String adminId,boolean del_status) {
        this.adminId = adminId;
        this.del_status=del_status;
    }

    public String getAdminId() {
        return adminId;
    }

    public void setAdminId(String adminId) {
        this.adminId = adminId;
    }

    public boolean isDel_status() {
        return del_status;
    }

    public void setDel_status(boolean del_status) {
        this.del_status = del_status;
    }
}
