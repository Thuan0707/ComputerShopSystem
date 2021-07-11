package com.example.computershopsystem.Model;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

public class Customer implements Serializable {
    String id;
    CustomerAccount customerAccount;
    String fullName;
    String  dateOfBirth;
    String address;
    int gender;
    List<CreditCard> cardList;
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

    public Customer(String id, CustomerAccount customerAccount, String fullName, String dateOfBirth, String address, int gender, List<CreditCard> cardList, Date createAt, Date deleteAt) {
        this.id = id;
        this.customerAccount = customerAccount;
        this.fullName = fullName;
        this.dateOfBirth = dateOfBirth;
        this.address = address;
        this.gender = gender;
        this.cardList = cardList;
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

    public List<CreditCard> getCardList() {
        return cardList;
    }

    public void setCardList(List<CreditCard> cardList) {
        this.cardList = cardList;
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
