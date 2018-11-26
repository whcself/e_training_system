package com.csu.etrainingsystem.procedure.entity;

import lombok.Data;

import javax.persistence.Entity;
import javax.persistence.Id;

@Data
@Entity
public class Proced_template {
    @Id
    private String template_name;
    private String pro_name;
    private float weight;
}

