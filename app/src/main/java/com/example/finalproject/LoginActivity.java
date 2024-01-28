package com.example.finalproject;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.view.View;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Toast;

public class LoginActivity extends AppCompatActivity {

    private EditText txtUsernameLogin, txtPasswordLogin;
    private CheckBox rememberMeCheckBox;

    private SharedPreferences prefs;
    public static SharedPreferences.Editor editor;

    private static final String FLAG = "FLAG";
    private static final String PREF_USERNAME = "USERNAME";
    private static final String PREF_PASSWORD = "PASSWORD";
    private boolean flag = false;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        setupViews();
        setupSharedPrefs();
        checkPrefs();

    }
    private void setupSharedPrefs() {

        prefs= PreferenceManager.getDefaultSharedPreferences(this);
        editor = prefs.edit();
    }

    private void checkPrefs() {
        flag = prefs.getBoolean(FLAG, false);

        if(flag){
            txtUsernameLogin.setText(prefs.getString(PREF_USERNAME, ""));
            txtPasswordLogin.setText(prefs.getString(PREF_PASSWORD, ""));
            rememberMeCheckBox.setChecked(true);
        }
    }

    private void setupViews(){
        txtUsernameLogin = findViewById(R.id.txtUsernameLogin);
        txtPasswordLogin = findViewById(R.id.txtPasswordLogin);
        rememberMeCheckBox = findViewById(R.id.rememberMeCheckBox);

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

                    if (rememberMeCheckBox.isChecked()) {
                        if (!flag) {
                            editor.putString(PREF_USERNAME, username);
                            editor.putString(PREF_PASSWORD, password);
                            editor.putBoolean(FLAG, true);
                            editor.commit();
                        }
                    } else {
                        editor.remove(PREF_USERNAME);
                        editor.remove(PREF_PASSWORD);
                        editor.putBoolean(FLAG, false);
                        editor.commit();
                    }

                    Intent intent = new Intent(LoginActivity.this, Successful_MainActivity.class);
                    intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                    startActivity(intent);
                    finish();
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