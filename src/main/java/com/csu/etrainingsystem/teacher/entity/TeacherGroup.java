package com.csu.etrainingsystem.teacher.entity;

import lombok.Data;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Data
@Entity
public class TeacherGroup {
    @Id
    private String tGroupId;
}
