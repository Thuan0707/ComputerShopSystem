package com.example.computershopsystem.Model;

import java.util.Date;

public class Ram {
    int id;
    int capacity;
    String description;
    Date createAt;
    Date deleteAt;

    public Ram() {
    }

    public Ram(int id, int capacity, String description, Date createAt, Date deleteAt) {
        this.id = id;
        this.capacity = capacity;
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

    public int getCapacity() {
        return capacity;
    }

    public void setCapacity(int capacity) {
        this.capacity = capacity;
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
