package com.team.team_07_fe.ui.settings;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.appcompat.widget.AppCompatButton;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.fragment.NavHostFragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.google.android.material.textfield.TextInputEditText;
import com.team.team_07_fe.LoginActivity;
import com.team.team_07_fe.MainActivity;
import com.team.team_07_fe.R;
import com.team.team_07_fe.api.ApiClient;
import com.team.team_07_fe.api.ApiResponse;
import com.team.team_07_fe.api.service.AuthService;
import com.team.team_07_fe.models.Employee;
import com.team.team_07_fe.ui.home.HomeFragment;
import com.team.team_07_fe.utils.LoadingDialog;
import com.team.team_07_fe.viewmodels.AuthViewModel;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;


public class ChangePasswordFragment extends Fragment {
    private TextInputEditText edOldPassword, edNewPassword, edCheckPassword;
    private AppCompatButton btnChangePassword;
    private AuthViewModel authViewModel;
    private Dialog loadingDialog;
    private AuthService authService;


    public ChangePasswordFragment() {

    }
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_change_password, container, false);

        edOldPassword = view.findViewById(R.id.oldPassword);
        edNewPassword = view.findViewById(R.id.newPassword);
        edCheckPassword = view.findViewById(R.id.checkPassword);
        btnChangePassword = view.findViewById(R.id.btn_change_password);

        authViewModel = new ViewModelProvider(this).get(AuthViewModel.class);

        authService = ApiClient.getClient().create(AuthService.class);
        btnChangePassword.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                handleChangePassword();
            }
        });

        return view;
    }

    private void handleChangePassword() {
        SharedPreferences sharedPreferences = getActivity().getSharedPreferences("MyNS", Context.MODE_PRIVATE);
        String auth_id = sharedPreferences.getString("auth_id", "");
        String oldPass = edOldPassword.getText().toString().trim();
        String newPass = edNewPassword.getText().toString().trim();
        String checkPass = edCheckPassword.getText().toString().trim();

        loadingDialog = loadingDialog();

        // Hiển thị dialog loading

        if (isValidate(oldPass, newPass, checkPass)) {
            loadingDialog.show();
            AlertDialog.Builder builder = new AlertDialog.Builder(requireContext())
                    .setTitle("Thông báo")
                    .setMessage("Bạn có chắc muốn thay đổi mật khẩu không?")
                    .setPositiveButton("Có", (dialog, which) -> {
                        loadingDialog.show();
                        authViewModel.changePassword(auth_id, oldPass, newPass, checkPass);
                        observeViewModel();
                    })
                    .setNegativeButton("Không", (dialog, which) -> {
                        loadingDialog.dismiss();
                    });
            builder.create().show();
        }
        }


    private boolean isValidate(String oldPass, String newPass, String checkPass){
        boolean isValid = true;
        if (oldPass.isEmpty() || newPass.isEmpty() || checkPass.isEmpty()) {
            Toast.makeText(getActivity(), "Vui lòng không để trống thông tin", Toast.LENGTH_SHORT).show();
            isValid = false;
        }
        if (!oldPass.equals(edOldPassword.getText().toString())) {
            Toast.makeText(getActivity(), "Mật khẩu cũ không chính xác", Toast.LENGTH_SHORT).show();
            isValid = false;
        }
        if (newPass.length() < 6) {
            Toast.makeText(getActivity(), "Mật khẩu phải từ 6 kí tự trở lên", Toast.LENGTH_SHORT).show();
            isValid = false;
        }

        if (!checkPass.equals(newPass)) {
            Toast.makeText(getActivity(), "Mật khẩu không trùng khớp", Toast.LENGTH_SHORT).show();
            isValid = false;
        }
        return isValid;
    }
    private void observeViewModel() {
        authViewModel.getDataMessage().observe(getViewLifecycleOwner(), message -> {
            if (message != null) {
                loadingDialog.dismiss();
                NavHostFragment.findNavController(this).popBackStack();
                Toast.makeText(requireContext(), message, Toast.LENGTH_SHORT).show();
                authViewModel.setDataMessage(null);

                if (message.equals("Thay đổi mật khẩu thành công")) {
                    edOldPassword.setText("");
                    edNewPassword.setText("");
                    edCheckPassword.setText("");
                }

            }
        });

        authViewModel.getErrorMessage().observe(getViewLifecycleOwner(), errorMessage -> {
            if (errorMessage != null) {
                loadingDialog.dismiss();
                Toast.makeText(requireContext(), errorMessage, Toast.LENGTH_SHORT).show();
                authViewModel.setErrorMessage(null);
            }
        });
    }

    private Dialog loadingDialog() {
        Dialog dialog = new Dialog(getActivity());
        View view = LayoutInflater.from(getActivity()).inflate(R.layout.loading_dialog, null);
        dialog.setContentView(view);
        dialog.setCancelable(false);
        return dialog;
    }
    @Override
    public void onResume() {
        super.onResume();
        ((MainActivity)requireActivity()).hiddenBottomBar();
    }

    @Override
    public void onStop() {
        super.onStop();
        ((MainActivity) requireActivity()).showBottomBar();
    }
}