package com.team.team_07_fe;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.textfield.TextInputEditText;

public class LoginActivity extends AppCompatActivity {
    private TextInputEditText edEmail, edPass;
    private Button btnLogin;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        edEmail = findViewById(R.id.ed_email);
        edPass = findViewById(R.id.ed_pass);
        btnLogin = findViewById(R.id.btnLogin);

        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String email = edEmail.getText().toString();
                String password = edPass.getText().toString();

                if (isValidCredentials(email, password)) {
                    showToast("Đăng nhập thành công");
                    startActivity(new Intent(LoginActivity.this, MainActivity.class));
                } else {
                    showToast("Sai email hoặc mật khẩu");
                }
            }
        });
    }

    private boolean isValidCredentials(String username, String password) {

        return username.equals("admin") && password.equals("123123");
    }

    private void showToast(String message) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show();
    }
}