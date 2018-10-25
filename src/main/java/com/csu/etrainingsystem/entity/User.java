package com.csu.etrainingsystem.entity;

import javax.persistence.*;

@Entity
@Table(name = "tb_user")
public class User {

    @Id
    @Column(length = 20)
    private String id; //different identity means different type of id
    private String name;
    private String identity; // student, teacher ,admin
    private String username;
    private String password;

    public User() {

    }

    public User(String id, String name, String identity, String username, String password) {
        this.id = id;
        this.name = name;
        this.identity = identity;
        this.username = username;
        this.password = password;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getIdentity() {
        return identity;
    }

    public void setIdentity(String identity) {
        this.identity = identity;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}