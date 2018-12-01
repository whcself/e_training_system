package com.csu.etrainingsystem.score.entity;

import lombok.Data;

import javax.persistence.Entity;
import javax.persistence.Table;
import java.sql.Timestamp;

@Data
@Entity
@Table(name="score_update")
public class ScoreUpdate {
    private String sid;
    private Timestamp update_time;
    private String reason;
}
