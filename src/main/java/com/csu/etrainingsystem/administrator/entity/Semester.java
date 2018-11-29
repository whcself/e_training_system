package com.csu.etrainingsystem.administrator.entity;

import lombok.Data;

import javax.persistence.Entity;
import javax.persistence.Id;

@Data
@Entity
public class Semester {
    @Id
    private String semesterName;
}
