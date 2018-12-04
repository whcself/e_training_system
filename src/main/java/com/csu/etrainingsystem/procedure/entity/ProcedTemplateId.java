package com.csu.etrainingsystem.procedure.entity;


import lombok.Data;

import javax.persistence.Embeddable;
import java.io.Serializable;

@Embeddable
@Data
public class ProcedTemplateId implements Serializable {
    private String template_name;
    private String pro_name;

    public ProcedTemplateId(String template_name, String pro_name) {
        this.template_name = template_name;
        this.pro_name = pro_name;
    }
    public ProcedTemplateId(){

    }
}
