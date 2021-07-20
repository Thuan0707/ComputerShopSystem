package com.example.computershopsystem.Model;

import java.util.Date;

public class ShipFee {
    int id;
    String  city;
    double fee;
    Date createAt;
    Date deleteAt;

    public ShipFee() {
    }

    public ShipFee(int id, String city, double fee, Date createAt, Date deleteAt) {
        this.id = id;
        this.city = city;
        this.fee = fee;
        this.createAt = createAt;
        this.deleteAt = deleteAt;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public double getFee() {
        return fee;
    }

    public void setFee(double fee) {
        this.fee = fee;
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
