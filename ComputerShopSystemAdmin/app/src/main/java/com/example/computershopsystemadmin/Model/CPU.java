package com.example.computershopsystemadmin.Model;

import java.io.Serializable;
import java.util.Date;

public class CPU  implements Serializable {
    String id;
    String series;
    String description;
    Date createAt;
    Date deleteAt;

    public CPU() {
    }

    public CPU(String id, String series, String description, Date createAt, Date deleteAt) {
        this.id = id;
        this.series = series;
        this.description = description;
        this.createAt = createAt;
        this.deleteAt = deleteAt;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getSeries() {
        return series;
    }

    public void setSeries(String series) {
        this.series = series;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Date getCreateAt() {
        return createAt;
    }

    public void setCreateAt(Date createAt) {
        this.createAt = createAt;
    }

    public Date getDeleteAt() {
        return deleteAt;
    }

    public void setDeleteAt(Date deleteAt) {
        this.deleteAt = deleteAt;
    }
}
