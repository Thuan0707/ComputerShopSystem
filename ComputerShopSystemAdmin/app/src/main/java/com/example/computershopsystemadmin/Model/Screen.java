package com.example.computershopsystemadmin.Model;

import java.io.Serializable;
import java.util.Date;

public class Screen implements Serializable {
    String id;
    String  size;
    String description;
    Date createAt;
    Date deleteAt;

    public Screen() {
    }

    public Screen(String id, String size, String description, Date createAt, Date deleteAt) {
        this.id = id;
        this.size = size;
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

    public String getSize() {
        return size;
    }

    public void setSize(String size) {
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
