package com.example.computershopsystem.Model;

import java.util.Date;

public class Screen {
    int id;
    int size;
    String description;
    Date createAt;
    Date deleteAt;

    public Screen() {
    }

    public Screen(int id, int size, String description, Date createAt, Date deleteAt) {
        this.id = id;
        this.size = size;
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

    public int getSize() {
        return size;
    }

    public void setSize(int size) {
        this.size = size;
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
