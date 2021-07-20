package com.example.computershopsystemadmin.Model;

import java.io.Serializable;

public class Voucher implements Serializable {
    String  id;
    String code;
    double discount;
    String name;
    String description;
    String createAt;
    String deleteAt;

    public Voucher() {
    }

    public Voucher(String id, String code, double discount, String name, String description) {
        this.id = id;
        this.code = code;
        this.discount = discount;
        this.name = name;
        this.description = description;
    }

    public Voucher(String id, String code, double discount, String name, String description, String createAt, String deleteAt) {
        this.id = id;
        this.code = code;
        this.discount = discount;
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

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public double getDiscount() {
        return discount;
    }

    public void setDiscount(double discount) {
        this.discount = discount;
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

    public String getCreateAt() {
        return createAt;
    }

    public void setCreateAt(String createAt) {
        this.createAt = createAt;
    }

    public String getDeleteAt() {
        return deleteAt;
    }

    public void setDeleteAt(String deleteAt) {
        this.deleteAt = deleteAt;
    }
}
