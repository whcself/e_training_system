package com.csu.etrainingsystem.experiment.entity;


import lombok.Data;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;
@Data
@Entity
@Table(name = "experiment")
public class Experiment implements Serializable {



    @Id
    @Column
    private  Integer exp_id ;
    private String batch_name;
    private String template_id;
    private String time_quant;
    private int    class_time;
    private String t_group_id;
    private String s_group_id;
    private String pro_name;
    private String tid;
    private java.sql.Date submit_time;
    private boolean del_status;



}
