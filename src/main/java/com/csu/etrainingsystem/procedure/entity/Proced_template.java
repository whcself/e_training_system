package com.csu.etrainingsystem.procedure.entity;

import lombok.Data;

import javax.naming.Name;
import javax.persistence.*;

@Data
@Entity
@Table(name = "proced_template")
public class Proced_template {
    @EmbeddedId
    @Column
    private ProcedTemplateId procedTemplateId;
    private float weight;
}

