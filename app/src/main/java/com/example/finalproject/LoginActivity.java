package com.example.finalproject;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

public class LoginActivity extends AppCompatActivity {

    private EditText txtUsernameLogin, txtPasswordLogin;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        setupViews();

    }

    private void setupViews(){
        txtUsernameLogin = findViewById(R.id.txtUsernameLogin);
        txtPasswordLogin = findViewById(R.id.txtPasswordLogin);
    }




    public void btn_Login(View view) {
        String username = txtUsernameLogin.getText().toString();
        String password = txtPasswordLogin.getText().toString();


        if (username.trim().isEmpty() || password.trim().isEmpty()) {
            showToast("Please fill in all fields");
            return;
        }

        clsUser.checkByUsernameAndPassword(this, username, password, new clsUser.ExistCallbackForLogin() {
            @Override
            public void onExistCheck(boolean exists, clsUser user) {
                if (exists && user != null) {
                    clsCurrentUser.CurrentUser = user;
                    showToast("Login successful for user: " + clsCurrentUser.CurrentUser.getFirstname());
                } else {
                    // User does not exist or login failed
                    showToast("Invalid username or password");
                }
            }
        });
    }

    private void showToast(String message) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show();
    }


}