package com.csu.etrainingsystem.administrator.entity;

import org.springframework.context.annotation.EnableAspectJAutoProxy;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "tb_batch")
public class Batch {

    @Id
    @Column(length = 20)
    private String id;
    private int credit;
    private String process_list;

    public Batch() {
    }

    public Batch(String id, int credit, String process_list) {
        this.id = id;
        this.credit = credit;
        this.process_list = process_list;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public int getCredit() {
        return credit;
    }

    public void setCredit(int credit) {
        this.credit = credit;
    }

    public String getProcess_list() {
        return process_list;
    }

    public void setProcess_list(String process_list) {
        this.process_list = process_list;
    }
}
