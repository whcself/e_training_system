package com.csu.etrainingsystem.administrator.entity;

import org.springframework.context.annotation.EnableAspectJAutoProxy;

import javax.persistence.*;

@Entity
@Table(name = "tb_batch")
public class Batch {

    @Id
    @Column(length = 20)
    private String bId;
    private int credit;
    private String process_list;

    public Batch() {
    }

    public Batch(String bId, int credit, String process_list) {
        this.bId = bId;
        this.credit = credit;
        this.process_list = process_list;
    }

    public String getbId() {
        return bId;
    }

    public void setbId(String bId) {
        this.bId = bId;
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
