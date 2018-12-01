package com.csu.etrainingsystem.teacher.entity;

import lombok.Data;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;


@Data
@Entity
@Table(name="teacher_group")
public class TeacherGroup {
    @Column
    @Id
    private String t_group_id;
    boolean del_status;
}
