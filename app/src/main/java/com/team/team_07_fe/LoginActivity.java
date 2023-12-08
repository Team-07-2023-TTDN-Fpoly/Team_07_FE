package com.team.team_07_fe;

import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.widget.Button;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.textfield.TextInputEditText;
import com.google.gson.Gson;
import com.team.team_07_fe.api.ApiClient;
import com.team.team_07_fe.api.ApiResponse;
import com.team.team_07_fe.api.service.AuthService;
import com.team.team_07_fe.models.Employee;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class LoginActivity extends AppCompatActivity {
    private AuthService authService;
    private TextInputEditText edEmail, edPass;
    private Button btnLogin;

    private Dialog loadingDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        authService = ApiClient.getClient().create(AuthService.class);

        edEmail = findViewById(R.id.ed_email);
        edPass = findViewById(R.id.ed_pass);
        btnLogin = findViewById(R.id.btnLogin);

        edPass.setOnEditorActionListener((textView, i, keyEvent) -> {
            if(i == EditorInfo.IME_ACTION_DONE){
                btnLogin.performClick();
                return true;
            }
            return false;
        });

        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String email = edEmail.getText().toString().trim();
                String password = edPass.getText().toString().trim();

                loadingDialog = loadingDialog();

                // Hiển thị dialog loading
                loadingDialog.show();

                if (email.isEmpty() || password.isEmpty()) {
                    loadingDialog.dismiss();
                    Toast.makeText(LoginActivity.this, "Vui lòng không để trống thông tin", Toast.LENGTH_SHORT).show();
                    return;
                }

                Log.i("EMAIL", email +" " + password);

                Call<ApiResponse<Employee>> call = authService.loginAccount(email, password);

                call.enqueue(new Callback<ApiResponse<Employee>>() {
                    @Override
                    public void onResponse(Call<ApiResponse<Employee>> call, Response<ApiResponse<Employee>> response) {
                        loadingDialog.dismiss();
                        if (response.isSuccessful() && response.body()!=null) {
                            Employee employee = response.body().getData();
                            Toast.makeText(LoginActivity.this, "Đăng nhập thành công", Toast.LENGTH_SHORT).show();
                            saveEmployeeInfoToSharedPreferences(employee);
                            startActivity(new Intent(LoginActivity.this, MainActivity.class));
                            finish();
                        } else {
                            // Đăng nhập thất bại, xử lý lỗi
                            if (response.code() == 401) {
                                Toast.makeText(LoginActivity.this, "Sai mật khẩu hoặc email", Toast.LENGTH_SHORT).show();
                            } else if (response.code() == 403) {
                                Toast.makeText(LoginActivity.this, "Tài khoản đã bị vô hiệu hóa", Toast.LENGTH_SHORT).show();
                            } else {
                                Toast.makeText(LoginActivity.this, "Đã xảy ra lỗi: " + response.message(), Toast.LENGTH_SHORT).show();
                            }
                        }
                    }
                    @Override
                    public void onFailure(Call<ApiResponse<Employee>> call, Throwable t) {
                        loadingDialog.dismiss();
                        Log.e("ERROR LOGIN",t.getMessage());
                    }
                });
            }
        });
    }

    private void saveEmployeeInfoToSharedPreferences(Employee employee) {
        SharedPreferences sharedPreferences = getSharedPreferences("MyNS", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        Gson gson = new Gson();
        String employeeData = gson.toJson(employee);
        editor.putString("employee",employeeData);
        editor.putString("emp_id", employee.getEmp_id());
        editor.putString("emp_name", employee.getEmp_name());
        editor.putString("emp_phone", employee.getEmp_phone());
        editor.putString("emp_birthday", String.valueOf(employee.getEmp_birthday().getTime()));
        editor.putLong("basic_salary", employee.getBasic_salary());
        editor.putString("emp_address", employee.getEmp_address());
        editor.putString("role", employee.getRole());
        editor.putString("workShift", String.valueOf(employee.getWorkShift()));
        editor.putString("join_date", String.valueOf(employee.getJoin_date().getTime()));
        editor.putString("email", employee.getEmail());
        editor.putString("auth_id", employee.getAuth_id());
        editor.putBoolean("is_disable", employee.isIs_disable());
        editor.apply();
    }
    private Dialog loadingDialog() {
        Dialog dialog = new Dialog(this);
        View view = LayoutInflater.from(this).inflate(R.layout.loading_dialog, null);
        dialog.setContentView(view);
        dialog.setCancelable(false);
        return dialog;
    }
}