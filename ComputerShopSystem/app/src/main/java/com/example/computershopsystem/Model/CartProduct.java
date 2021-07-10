package com.example.computershopsystem.Model;

import java.io.Serializable;

public class CartProduct implements Serializable {
    int quantityInCart;
    Product product;

    public CartProduct() {
    }

    public CartProduct(int quantityInCart, Product product) {
        this.quantityInCart = quantityInCart;
        this.product = product;
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
