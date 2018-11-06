package com.csu.etrainingsystem.procedure.entity;


import javax.persistence.Embeddable;
import java.io.Serializable;
import java.util.Objects;

@Embeddable
public class ProcedId implements Serializable {
    private String pro_name ;
    private String batch_name;

    public ProcedId() {
    }

    public ProcedId(String pro_name, String batch_name) {
        this.pro_name = pro_name;
        this.batch_name = batch_name;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass () != o.getClass ()) return false;
        ProcedId procedId = (ProcedId) o;
        return Objects.equals (pro_name, procedId.pro_name) &&
                Objects.equals (batch_name, procedId.batch_name);
    }

    @Override
    public int hashCode() {

        return Objects.hash (pro_name, batch_name);
    }

    public String getBatch_name() {
        return batch_name;
    }

    public void setBatch_name(String batch_name) {
        this.batch_name = batch_name;
    }

    public String getPro_name() {
        return pro_name;
    }

    public void setPro_name(String pro_name) {
        this.pro_name = pro_name;
    }
}
