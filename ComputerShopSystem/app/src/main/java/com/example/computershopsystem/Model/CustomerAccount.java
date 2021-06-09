package com.example.computershopsystem.Model;

public class CustomerAccount {
    int id;
    String phone;
    String  idFacebook;
    String idGmail;
    String password;

    public CustomerAccount() {
    }

    public CustomerAccount(int id, String phone, String idFacebook, String idGmail, String password) {
        this.id = id;
        this.phone = phone;
        this.idFacebook = idFacebook;
        this.idGmail = idGmail;
        this.password = password;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
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
