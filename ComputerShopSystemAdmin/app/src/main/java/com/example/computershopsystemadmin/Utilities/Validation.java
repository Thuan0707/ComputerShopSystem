package com.example.computershopsystemadmin.Utilities;

public class Validation {
    String namePattern = "[a-zA-Z]+( [a-zA-Z]+)+";
    String phonePattern = "([\\+84|84|0]+(3|5|7|8|9|1[2|6|8|9]))+([0-9]{8})\\b";
    String strongPasswordPattern = "^(?=.*[a-z])(?=.*[A-Z])(?=.*\\d)(?=.*[@$!%*?&])[A-Za-z\\d@$!%*?&]{8,}$";
   public boolean isValid=true;
    public Validation() {
    }
    public boolean isValid() {
        return isValid;
    }

    public void setValid(boolean valid) {
        isValid = valid;
    }


    public String CheckName(String name) {
        if (name.isEmpty()) {
            isValid=false;
            return "Name can not be empty";
        }
        if (!name.matches(namePattern)) {
            isValid=false;
            return "Full Name has at least 2 words that must be in alphabet";
        }
        return null;
    }
    public String  checkEmail(String email){
        if (!email.matches("^([a-zA-Z0-9_\\-\\.]+)@((\\[[0-9]{1,3}\\.[0-9]{1,3}\\.[0-9]{1,3}\\.)|(([a-zA-Z0-9\\-]+\\.)+))([a-zA-Z]{2,4}|[0-9]{1,3})(\\]?)$"))
            return "Email is invalid";
        return null ;
    }

    public String CheckPhone(String phone) {
        if (phone.isEmpty()) {
            isValid=false;
            return "Phone can not be empty";
        }
        if (!phone.matches(phonePattern)) {
            isValid=false;
            return "Phone is invalid";
        }
        return null;
    }

    public String CheckPassword(String password) {
        if (password.isEmpty()) {
            isValid=false;
            return "password can not be empty";
        }
        if (!password.matches(strongPasswordPattern)) {
            isValid=false;
            return "Password must has at least 8 characters. Including:\n- At least one number\n- At least one capital letters\n- At least one special characters";
        }
        return null;
    }

    public String CheckConfirmPassword(String password, String confirmPassword){
        if (confirmPassword.isEmpty()){
            isValid=false;
            return "Confirm password can not be empty" ;}
        if (!password.equals(confirmPassword)){
            isValid=false;
            return "Password and confirm password must be the same";
        }
        return null;
    }
   public String CheckAddress(String address){
        if (address.isEmpty()){
            isValid=false;
            return "Address can not null";

        }
        return null;
    }
}
