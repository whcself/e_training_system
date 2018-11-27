package com.csu.etrainingsystem.teacher.entity;


import lombok.Data;

import javax.persistence.*;
import java.io.Serializable;

@Data
@Embeddable
@Entity
@Table(name = "t_group_conn")
public class TeacherAndGroup implements Serializable {


    @EmbeddedId
    @Column//必须用复合主键
    private TeacherGroupId teacherGroupId;
    private int column1;
    private boolean del_status;

}
