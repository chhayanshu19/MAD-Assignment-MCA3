package com.example.madassigment.auth;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.example.madassigment.R;
import com.example.madassigment.home.HomeActivity;

public class LoginActivity extends AppCompatActivity {
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_login);

        EditText etEmail, etPassword;
        Button btnLogin;
        SharedPreferences sharedPreferences;

        etEmail = findViewById(R.id.et_email);
        etPassword = findViewById(R.id.et_password);
        btnLogin = findViewById(R.id.btn_login);

        sharedPreferences = getSharedPreferences("UserSession",MODE_PRIVATE);

        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString("email","user@user.com");
        editor.putString("password","user@12345");
        editor.apply();

        if(sharedPreferences.getBoolean("is_logged_in",false)){
            navigateToHome();
        }

        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String email = etEmail.getText().toString().trim();
                String password = etPassword.getText().toString().trim();

                String savedEmail = sharedPreferences.getString("email","");
                String savedPassword = sharedPreferences.getString("password","");

                if(email.equals(savedEmail) && password.equals(savedPassword)){
                    SharedPreferences.Editor sessionEditor = sharedPreferences.edit();
                    sessionEditor.putBoolean("is_logged_in",true);
                    sessionEditor.apply();

                    Toast.makeText(LoginActivity.this,"Login Successful",Toast.LENGTH_SHORT).show();
                    navigateToHome();
                }else{
                    Toast.makeText(LoginActivity.this,"Invalid Email or Password",Toast.LENGTH_SHORT).show();
                }
            }
        });

    }

    private void navigateToHome(){
        Intent intent = new Intent(LoginActivity.this, HomeActivity.class);
        startActivity(intent);
        finish();
    }
}
