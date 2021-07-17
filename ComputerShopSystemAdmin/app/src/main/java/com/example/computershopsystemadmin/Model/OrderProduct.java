package com.example.computershopsystemadmin.Model;

import java.io.Serializable;

public class OrderProduct implements Serializable {
    String id;
    String orderID;
    int quantityInCart;
    Product product;

    public OrderProduct() {
    }

    public OrderProduct(String id, String orderID, int quantityInCart, Product product) {
        this.id = id;
        this.orderID = orderID;
        this.quantityInCart = quantityInCart;
        this.product = product;
    }

    public OrderProduct(int quantityInCart, Product product) {
        this.quantityInCart = quantityInCart;
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

    public int getQuantityInCart() {
        return quantityInCart;
    }

    public void setQuantityInCart(int quantityInCart) {
        this.quantityInCart = quantityInCart;
    }

    public Product getProduct() {
        return product;
    }

    public void setProduct(Product product) {
        this.product = product;
    }
}
