package com.example.computershopsystem.View;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.example.computershopsystem.R;

import java.util.regex.Pattern;

public class LoginWrongActivity extends AppCompatActivity {
    private static final Pattern PASSWORD_PATTERN =
            Pattern.compile("^" +
                    //"(?=.*[0-9])" +         //at least 1 digit
                    //"(?=.*[a-z])" +         //at least 1 lower case letter
                    //"(?=.*[A-Z])" +         //at least 1 upper case letter
                    "(?=.*[a-zA-Z])" +      //any letter
                    "(?=.*[@#$%^&+=])" +    //at least 1 special character
                    "(?=\\S+$)" +           //no white spaces
                    ".{4,}" +               //at least 4 characters
                    "$");

    private EditText textPassword;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login_wrong);

        textPassword = findViewById(R.id.txtPassword);
    }
    private boolean validatePassword() {
        String passwordInput = textPassword.getText().toString().trim();
        if (passwordInput.isEmpty()) {
            textPassword.setError("Field can't be empty");
            return false;
        } else if (!PASSWORD_PATTERN.matcher(passwordInput).matches()) {
            textPassword.setError("Password too weak");
            return false;
        } else {
            textPassword.setError(null);
            return true;
        }
    }

    public void confirmInput(View v) {
        if (!validatePassword()) {
            return;
        }
        String input = "Password: " + textPassword.getText().toString();
        Toast.makeText(this, input, Toast.LENGTH_SHORT).show();
    }
}