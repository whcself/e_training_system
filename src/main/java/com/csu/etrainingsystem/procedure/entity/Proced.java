package com.csu.etrainingsystem.procedure.entity;


import lombok.Data;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;

@Data
@Entity
@Table(name = "proced")
public class Proced implements Serializable {



    @EmbeddedId
    @Column
    private ProcedId proid;
    private String t_group_id;
    private Float weight;
    private boolean del_status;

}
