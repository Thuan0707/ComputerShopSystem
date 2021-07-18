package com.example.computershopsystem.Model;

import java.io.Serializable;

public class OrderProduct implements Serializable {
    String id;
    String orderID;
    int quantity;
    Product product;

    public OrderProduct() {
    }

    public OrderProduct(String id, String orderID, int quantity, Product product) {
        this.id = id;
        this.orderID = orderID;
        this.quantity = quantity;
        this.product = product;
    }

    public OrderProduct(int quantity, Product product) {
        this.quantity = quantity;
        this.product = product;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getOrderID() {
        return orderID;
    }

    public void setOrderID(String orderID) {
        this.orderID = orderID;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public Product getProduct() {
        return product;
    }

    public void setProduct(Product product) {
        this.product = product;
    }
}
