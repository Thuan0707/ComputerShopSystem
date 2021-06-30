package com.example.computershopsystem.Model;

import java.io.Serializable;
import java.util.Date;

public class Customer implements Serializable {
    String  id;
    CustomerAccount customerAccount;
    String email;
    String fullName;
    Date dateOfBirth;
    String address;
    int gender;
    long money;
    Date createAt;
    Date deleteAt;

    public Customer() {
    }

    public Customer(String id, CustomerAccount customerAccount, String email, String fullName, Date createAt) {
        this.id = id;
        this.customerAccount = customerAccount;
        this.email = email;
        this.fullName = fullName;
        this.createAt = createAt;
    }

    public Customer(String id, CustomerAccount customerAccount, String email, String fullName, Date dateOfBirth, String address, int gender, long money, Date createAt, Date deleteAt) {
        this.id = id;
        this.customerAccount = customerAccount;
        this.email = email;
        this.fullName = fullName;
        this.dateOfBirth = dateOfBirth;
        this.address = address;
        this.gender = gender;
        this.money = money;
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


    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getFullName() {
        return fullName;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    public Date getDateOfBirth() {
        return dateOfBirth;
    }

    public void setDateOfBirth(Date dateOfBirth) {
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

    public long getMoney() {
        return money;
    }

    public void setMoney(long money) {
        this.money = money;
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
