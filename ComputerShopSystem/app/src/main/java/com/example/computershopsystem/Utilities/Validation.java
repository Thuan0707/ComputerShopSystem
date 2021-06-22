package com.example.computershopsystem.Utilities;

public class Validation {
    String namePattern = "[a-zA-Z]+( [a-zA-Z]+)+";
    String phonePattern = "([\\+84|84|0]+(3|5|7|8|9|1[2|6|8|9]))+([0-9]{8})\\b";
    String strongPasswordPattern = "^(?=.*[a-z])(?=.*[A-Z])(?=.*\\d)(?=.*[@$!%*?&])[A-Za-z\\d@$!%*?&]{8,}$";

    public Validation() {
    }

    public String CheckName(String name) {
        if (name.isEmpty()) {
            return "Name can not be empty";
        }
        if (!name.matches(namePattern)) {
            return "Full Name has at least 2 words that must be in alphabet";
        }
        return null;
    }

    public String CheckPhone(String phone) {
        if (phone.isEmpty()) {
            return "Phone can not be empty";
        }
        if (!phone.matches(phonePattern)) {
            return "Phone is invalid";
        }
        return null;
    }

    public String CheckPassword(String password) {
        if (password.isEmpty()) {
            return "password can not be empty";
        }
        if (!password.matches(strongPasswordPattern)) {
            return "Password must has at least 8 characters. Including:\n- At least one number\n- At least one capital letters\n- At least one special characters";
        }
        return null;
    }

    public String CheckConfirmPassword(String password, String confirmPassword){
        if (confirmPassword.isEmpty()){
            return "Confirm password can not be empty" ;}
        if (password.equals(confirmPassword)){
            return "Password and confirm password must be the same";
        }
        return null;
    }
}
