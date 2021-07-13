package com.example.computershopsystemadmin.Model;

import java.io.Serializable;
import java.util.Date;

public class Rom  implements Serializable {
    String id;
    String  capacity;
    String description;
    Date createAt;
    Date deleteAt;

    public Rom() {
    }

    public Rom(String id, String capacity, String description, Date createAt, Date deleteAt) {
        this.id = id;
        this.capacity = capacity;
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

    public String getCapacity() {
        return capacity;
    }

    public void setCapacity(String capacity) {
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
