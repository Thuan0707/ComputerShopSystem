package com.example.computershopsystemadmin.Model;

import java.io.Serializable;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

public class Customer implements Serializable {
    String id;
    CustomerAccount customerAccount;
    String fullName;
    String  dateOfBirth;
    String address;
    int gender;
    String image;
    HashMap<String ,CreditCard> cardList;
    HashMap<String ,Order> orderList;
    HashMap<String ,Payment> list;
    Date createAt;
    Date deleteAt;

    public Customer() {
    }

    public Customer(String id, CustomerAccount customerAccount, String fullName, Date createAt) {
        this.id = id;
        this.customerAccount = customerAccount;

        this.fullName = fullName;
        this.createAt = createAt;
    }


    public Customer(String id, CustomerAccount customerAccount, String fullName, String dateOfBirth, String address, int gender, String image, HashMap<String, CreditCard> cardList, HashMap<String, Order> orderList, HashMap<String, Payment> list, Date createAt, Date deleteAt) {
        this.id = id;
        this.customerAccount = customerAccount;
        this.fullName = fullName;
        this.dateOfBirth = dateOfBirth;
        this.address = address;
        this.gender = gender;
        this.image = image;
        this.cardList = cardList;
        this.orderList = orderList;
        this.list = list;
        this.createAt = createAt;
        this.deleteAt = deleteAt;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public CustomerAccount getCustomerAccount() {
        return customerAccount;
    }

    public void setCustomerAccount(CustomerAccount customerAccount) {
        this.customerAccount = customerAccount;
    }

    public String getFullName() {
        return fullName;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    public String getDateOfBirth() {
        return dateOfBirth;
    }

    public void setDateOfBirth(String dateOfBirth) {
        this.dateOfBirth = dateOfBirth;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public int getGender() {
        return gender;
    }

    public void setGender(int gender) {
        this.gender = gender;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public HashMap<String, CreditCard> getCardList() {
        return cardList;
    }

    public void setCardList(HashMap<String, CreditCard> cardList) {
        this.cardList = cardList;
    }

    public HashMap<String, Order> getOrderList() {
        return orderList;
    }

    public void setOrderList(HashMap<String, Order> orderList) {
        this.orderList = orderList;
    }

    public HashMap<String, Payment> getList() {
        return list;
    }

    public void setList(HashMap<String, Payment> list) {
        this.list = list;
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
