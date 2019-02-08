package com.csu.etrainingsystem.administrator.entity;

import lombok.Data;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Data
@Entity
@Table(name = "semester")
public class Semester {
    @Id
    private String semester_name;
    private String beginTime;

    public Semester(String semesterName){
        this.semester_name=semesterName;
    }

    public Semester(){}
}
