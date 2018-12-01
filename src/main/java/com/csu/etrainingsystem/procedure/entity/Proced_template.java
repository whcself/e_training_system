package com.csu.etrainingsystem.procedure.entity;

import lombok.Data;

import javax.naming.Name;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Data
@Entity
@Table(name = "proced_template")
public class Proced_template {
    @Id
    private String template_name;
    private String pro_name;
    private float weight;
}

