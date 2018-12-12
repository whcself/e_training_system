package com.csu.etrainingsystem.score.entity;

import lombok.Data;

import javax.persistence.*;
import java.sql.Timestamp;

@Data
@Entity
@Table(name="score_update")
public class ScoreUpdate {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer score_update_id;
    private String sid;
    private Timestamp update_time;
    private String reason;
}
