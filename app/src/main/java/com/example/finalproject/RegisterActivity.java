package com.example.finalproject;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

public class RegisterActivity extends AppCompatActivity {
    private EditText FirstName,LastName, Username, Password,ConfirmPassword;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        setupViews();
    }

    private void setupViews(){
        FirstName = findViewById(R.id.txt_register_first_name);
        LastName = findViewById(R.id.txt_register_last_name);
        Username = findViewById(R.id.txt_register_username);
        Password = findViewById(R.id.txt_register_password);
        ConfirmPassword = findViewById(R.id.txt_register_confirm_password);
    }



    public void btn_register(View view) {

        String firstNameText = FirstName.getText().toString();
        String lastNameText = LastName.getText().toString();
        String usernameText = Username.getText().toString();
        String passwordText = Password.getText().toString();
        String confirmPasswordText = ConfirmPassword.getText().toString();

        if (firstNameText.trim().isEmpty() || lastNameText.trim().isEmpty() || usernameText.trim().isEmpty() || passwordText.trim().isEmpty() || confirmPasswordText.trim().isEmpty()) {
            showToast("Please fill in all fields");
            return;
        }

        if (!passwordText.equals(confirmPasswordText)) {
            showToast("Password and Confirm Password do not match");
            return;
        }

        clsUser.isExist(this, usernameText, new clsUser.ExistCallback() {
            @Override
            public void onExistCheck(boolean exists) {
                if (exists) {
                    showToast("You already registered");
                } else {
                    // User does not exist, proceed to registration
                    clsUser.addUser(RegisterActivity.this, firstNameText, lastNameText,usernameText, passwordText);

                    Intent intent = new Intent(RegisterActivity.this, MainActivity.class);
                    startActivity(intent);
                    finish();
                }
            }
        });
    }

    private void showToast(String message) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show();
    }
}
