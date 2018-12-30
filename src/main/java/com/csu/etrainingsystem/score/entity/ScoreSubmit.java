package com.csu.etrainingsystem.score.entity;

import lombok.Data;

import javax.persistence.*;
import java.io.Serializable;
import java.sql.Timestamp;

@Data
@Entity
@Table(name="score_submit")
public class ScoreSubmit implements Serializable {
    @Id
    @Column
    @GeneratedValue(strategy = GenerationType.IDENTITY)

    private Integer score_submit_id;
    private String batch_name;
    private String s_group_id;
    private String pro_name;
    private String tid;
    private java.sql.Timestamp submit_time;
    private boolean del_status;
}
