package com.example.computershopsystemadmin.Model;

import java.util.Date;

public class Payment {
    String id;
    String idCustomer;
    Date date;
    double total;
    String description;

    public Payment(String id, String idCustomer, Date date, double total, String description) {

        this.id = id;
        this.idCustomer = idCustomer;
        this.date = date;
        this.total = total;
        this.description = description;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getIdCustomer() {
        return idCustomer;
    }

    public void setIdCustomer(String idCustomer) {
        this.idCustomer = idCustomer;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public double getTotal() {
        return total;
    }

    public void setTotal(double total) {
        this.total = total;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}
