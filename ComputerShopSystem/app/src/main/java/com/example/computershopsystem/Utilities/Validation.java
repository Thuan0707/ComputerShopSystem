package com.example.computershopsystem.Utilities;

public class Validation {
    String namePattern = "[a-zA-Z]+( [a-zA-Z]+)+";
    String phonePattern = "[+]*[(]{0,1}[0-9]{1,4}[)]{0,1}[-\\s\\./0-9]*";
    String strongPasswordPattern = ".*(?=.{8,})((?=.*[!@#$%^&*()\\-_=+{};:,<.>]){1})(?=.*\\d)((?=.*[a-z]){1})((?=.*[A-Z]){1}).*";

    public Validation() {
    }

    String CheckName(String name) {
        if (name.isEmpty()) {
            return "Name can not be empty";
        }
        if (!name.matches(phonePattern)) {
            return "Full Name has at least 2 words that must be in alphabet";
        }
        return null;
    }

    String CheckPhone(String phone) {
        if (phone.isEmpty()) {
            return "Phone can not be empty";
        }
        if (!phone.matches(phonePattern)) {
            return "Phone is invalid";
        }
        return null;
    }

    String CheckPassword(String password) {
        if (password.isEmpty()) {
            return "password can not be empty";
        }
        if (!password.matches(strongPasswordPattern)) {
            return "Password must has at least 8 characters. Including:\n- At least one number\n- At least one capital letters\n- At least one special characters";
        }
        return null;
    }

    String CheckConfirmPassword(String password, String confirmPassword){
        if (confirmPassword.isEmpty()){
            return "Confirm password can not be empty" ;}
        if (password.equals(confirmPassword)){
            return "Password and confirm password must be the same";
        }
        return null;
    }
}
