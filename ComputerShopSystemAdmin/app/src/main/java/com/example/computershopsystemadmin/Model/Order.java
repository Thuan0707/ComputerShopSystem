package com.example.computershopsystemadmin.Model;

import java.io.Serializable;
import java.util.List;

public class Order implements Serializable {
    String id;
    String idCustomer;
    String name;
    String orderDate;
    ShipFee shipFee;
    String ShipDate;
    String addesss;
    String numberPhone;
    List<OrderProduct> orderProductList;
    String note;
    CreditCard creditCard;
    Voucher voucher;
    public Order() {
    }

    public Order(String id, String idCustomer, String name, String orderDate, ShipFee shipFee, String shipDate, String addesss, String numberPhone, List<OrderProduct> orderProductList, String note, CreditCard creditCard, Voucher voucher) {
        this.id = id;
        this.idCustomer = idCustomer;
        this.name = name;
        this.orderDate = orderDate;
        this.shipFee = shipFee;
        ShipDate = shipDate;
        this.addesss = addesss;
        this.numberPhone = numberPhone;
        this.orderProductList = orderProductList;
        this.note = note;
        this.creditCard = creditCard;
        this.voucher = voucher;
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

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getOrderDate() {
        return orderDate;
    }

    public void setOrderDate(String orderDate) {
        this.orderDate = orderDate;
    }

    public ShipFee getShipFee() {
        return shipFee;
    }

    public void setShipFee(ShipFee shipFee) {
        this.shipFee = shipFee;
    }

    public String getShipDate() {
        return ShipDate;
    }

    public void setShipDate(String shipDate) {
        ShipDate = shipDate;
    }

    public String getAddesss() {
        return addesss;
    }

    public void setAddesss(String addesss) {
        this.addesss = addesss;
    }

    public String getNumberPhone() {
        return numberPhone;
    }

    public void setNumberPhone(String numberPhone) {
        this.numberPhone = numberPhone;
    }

    public List<OrderProduct> getOrderProductList() {
        return orderProductList;
    }

    public void setOrderProductList(List<OrderProduct> orderProductList) {
        this.orderProductList = orderProductList;
    }

    public String getNote() {
        return note;
    }

    public void setNote(String note) {
        this.note = note;
    }

    public CreditCard getCreditCard() {
        return creditCard;
    }

    public void setCreditCard(CreditCard creditCard) {
        this.creditCard = creditCard;
    }

    public Voucher getVoucher() {
        return voucher;
    }

    public void setVoucher(Voucher voucher) {
        this.voucher = voucher;
    }
}
