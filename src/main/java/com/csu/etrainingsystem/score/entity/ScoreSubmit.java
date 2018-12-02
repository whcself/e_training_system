package com.csu.etrainingsystem.score.entity;

import lombok.Data;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.sql.Timestamp;

@Data
@Entity
@Table(name="score_submit")
public class ScoreSubmit {
    @Id
    private Integer score_submit_id;
    private String batch_name;
    private String s_group_id;
    private String pro_name;
    private String tid;
    private Timestamp submit_time;
}
