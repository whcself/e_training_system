package com.csu.etrainingsystem.user.entity;


import lombok.Data;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Data
@Entity
@Table(name = "users")
public class User {
    @Id
    @Column(name="account",length = 20)
    private String account; //different identity means different type of id
    @Column(name="role")
    private String role; // student, teacher ,admin
    @Column(name="pwd")
    private String pwd;
    @Column(name="del_status")
    private boolean del_status;
    public User() {

    }
    public User(String account, String role, String pwd, boolean del_status){
        this.account=account;
        this.pwd =pwd;
        this.role=role;
        this.del_status=del_status;
    }


    public String getAccount() {
        return account;
    }

    public void setAccount(String account) {
        this.account = account;
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }

    public String getPwd() {
        return pwd;
    }

    public void setPwd(String pwd) {
        this.pwd = pwd;
    }

    public boolean isDel_status() {
        return del_status;
    }

    public void setDel_status(boolean del_status) {
        this.del_status = del_status;
    }
}