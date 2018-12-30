package com.csu.etrainingsystem.overwork.entity;


import lombok.Data;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;

@Data
@Entity
@Table(name = "overwork_apply")
public class Overwork_apply implements Serializable {


    @Id
    @Column
    @GeneratedValue(strategy = GenerationType.IDENTITY)

    private Integer apply_id;
    private String sid;
    private String reason;
    private java.sql.Timestamp apply_time;
    private java.sql.Timestamp overwork_time;
    private java.sql.Timestamp overwork_time_end;
    private String pro_name;
    private boolean del_status;

}
