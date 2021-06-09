package com.example.computershopsystem.Model;

import java.util.Date;

public class CPU {
    int id;
    String series;
    String description;
    Date createAt;
    Date deleteAt;

    public CPU() {
    }

    public CPU(int id, String series, String description, Date createAt, Date deleteAt) {
        this.id = id;
        this.series = series;
        this.description = description;
        this.createAt = createAt;
        this.deleteAt = deleteAt;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
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
