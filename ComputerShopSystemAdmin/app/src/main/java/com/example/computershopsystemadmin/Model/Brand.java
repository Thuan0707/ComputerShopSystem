package com.example.computershopsystemadmin.Model;

import java.io.Serializable;
import java.util.Date;

public class Brand implements Serializable {
    String  id;
    String name;
    String description;
    Date createAt;
    Date deleteAt;
    public Brand() {
    }

    public Brand(String id, String name, String description, Date createAt, Date deleteAt) {
        this.id = id;
        this.name = name;
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

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
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
