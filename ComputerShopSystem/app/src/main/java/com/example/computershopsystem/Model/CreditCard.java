package com.example.computershopsystem.Model;

import com.google.firebase.database.PropertyName;

import java.io.Serializable;
import java.util.Date;

public class CreditCard implements Serializable {


    @PropertyName("id")
    String id;

    @PropertyName("money")
    String money;

    @PropertyName("cardNumber")
    String cardNumber;

    @PropertyName("expirationDate")
    String expirationDate;

    @PropertyName("cardHolder")
    String cardHolder;

    @PropertyName("createAt")
    Date createAt;

    @PropertyName("deleteAt")
    Date deleteAt;

    public CreditCard(String id, String money, String cardNumber, String expirationDate, String cardHolder, Date createAt, Date deleteAt) {
        this.id = id;
        this.money = money;
        this.cardNumber = cardNumber;
        this.expirationDate = expirationDate;
        this.cardHolder = cardHolder;
        this.createAt = createAt;
        this.deleteAt = deleteAt;
    }

    public CreditCard() {
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getMoney() {
        return money;
    }

    public void setMoney(String money) {
        this.money = money;
    }

    public String getCardNumber() {
        return cardNumber;
    }

    public void setCardNumber(String cardNumber) {
        this.cardNumber = cardNumber;
    }

    public String getExpirationDate() {
        return expirationDate;
    }

    public void setExpirationDate(String expirationDate) {
        this.expirationDate = expirationDate;
    }

    public String getCardHolder() {
        return cardHolder;
    }

    public void setCardHolder(String cardHolder) {
        this.cardHolder = cardHolder;
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