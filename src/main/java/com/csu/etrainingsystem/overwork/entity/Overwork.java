package com.csu.etrainingsystem.overwork.entity;


import lombok.Data;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.io.Serializable;
import java.util.Date;

@Data
@Entity
@Table(name = "overwork")
public class Overwork implements Serializable {


    @Id
    @Column
    private int overwork_id ;
    private String pro_name;
    private java.sql.Timestamp overwork_time;
    private java.sql.Timestamp overwork_time_end;
    private String tname;
    private String reason;
    private boolean del_status;
}
