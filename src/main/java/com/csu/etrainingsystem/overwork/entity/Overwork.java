package com.csu.etrainingsystem.overwork.entity;


import lombok.Data;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;

@Data
@Entity
@Table(name = "overwork")
public class Overwork implements Serializable {


    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer overwork_id ;
    private String pro_name;
    private java.sql.Timestamp overwork_time;
    private java.sql.Timestamp overwork_time_end;
    private String tname;
    private String reason;
    private boolean del_status;
}
