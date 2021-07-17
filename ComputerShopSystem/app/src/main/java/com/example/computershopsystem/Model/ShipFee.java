package com.example.computershopsystem.Model;

import java.util.Date;

public class ShipFee {
    int id;
    int distance;
    double fee;
    Date createAt;
    Date deleteAt;

    public ShipFee() {
    }

    public ShipFee(int id, int distance, double fee, Date createAt, Date deleteAt) {
        this.id = id;
        this.distance = distance;
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

    public int getDistance() {
        return distance;
    }

    public void setDistance(int distance) {
        this.distance = distance;
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
