package com.example.computershopsystemadmin.Model;

import java.io.Serializable;

public class CustomerAccount implements Serializable {
    String id;
    String phone;
    String  idFacebook;
    String email;
    String idGmail;
    String password;

    public CustomerAccount() {
    }

    public CustomerAccount(String id, String phone, String idFacebook, String email, String idGmail, String password) {
        this.id = id;
        this.phone = phone;
        this.idFacebook = idFacebook;
        this.email = email;
        this.idGmail = idGmail;
        this.password = password;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getIdFacebook() {
        return idFacebook;
    }

    public void setIdFacebook(String idFacebook) {
        this.idFacebook = idFacebook;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getIdGmail() {
        return idGmail;
    }

    public void setIdGmail(String idGmail) {
        this.idGmail = idGmail;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
