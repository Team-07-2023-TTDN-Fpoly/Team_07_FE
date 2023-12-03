package com.team.team_07_fe.ui.settings;

import android.annotation.SuppressLint;
import android.os.Bundle;

import androidx.appcompat.widget.AppCompatButton;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.google.android.material.textfield.TextInputEditText;
import com.team.team_07_fe.MainActivity;
import com.team.team_07_fe.R;


public class ChangePasswordFragment extends Fragment {

    private TextInputEditText oldPassword, newPassword, confirmPassword;


    private AppCompatButton btnChangePassword, btnCancel;

    public ChangePasswordFragment() {

    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_change_password, container, false);

        oldPassword = view.findViewById(R.id.oldPassword);
        newPassword = view.findViewById(R.id.newPassword);
        confirmPassword = view.findViewById(R.id.confirmPassword);
        btnChangePassword = view.findViewById(R.id.btn_change_password);

        btnChangePassword.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                handleChangePassword();
            }
        });

        return view;
    }


    private void handleChangePassword() {

        String oldPass = oldPassword.getText().toString();
        String newPass = newPassword.getText().toString();
        String confirmPass = confirmPassword.getText().toString();

        if (!isOldPasswordCorrect(oldPass)) {
            oldPassword.setError("Mật khẩu cũ không đúng");
            return;
        }
        if (!isNewPasswordValid(newPass, confirmPass)) {
            newPassword.setError("Mật khẩu mới và mật khẩu xác nhận không khớp");
            confirmPassword.setError("Mật khẩu mới và mật khẩu xác nhận không khớp");
            return;
        }


            Toast.makeText(getActivity(), "Mật khẩu đã được thay đổi", Toast.LENGTH_SHORT).show();
            FragmentManager fragmentManager = getFragmentManager();
            getFragmentManager().popBackStack();
    }
    private boolean isOldPasswordCorrect(String oldPassword) {
        //kiểu ta mật khẩu cũ trong database
        return true;
    }
    private boolean isNewPasswordValid(String newPassword, String confirmPassword) {
        // Kiểm tra mật khẩu mới và mật khẩu xác nhận có khớp nhau không
        return newPassword.equals(confirmPassword);
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