package com.example.computershopsystem.Model;

import java.io.Serializable;

public class CustomerAccount implements Serializable {
    String id;
    String phone;
    String  idFacebook;
    String gmail;
    String idGmail;

    public CustomerAccount() {
    }

    public CustomerAccount(String id, String phone, String idFacebook, String gmail, String idGmail) {
        this.id = id;
        this.phone = phone;
        this.idFacebook = idFacebook;
        this.gmail = gmail;
        this.idGmail = idGmail;
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

    public String getGmail() {
        return gmail;
    }

    public void setGmail(String gmail) {
        this.gmail = gmail;
    }

    public String getIdGmail() {
        return idGmail;
    }

    public void setIdGmail(String idGmail) {
        this.idGmail = idGmail;
    }

}
