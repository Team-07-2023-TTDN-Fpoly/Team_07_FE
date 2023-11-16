package com.team.team_07_fe.ui.settings;

import android.annotation.SuppressLint;
import android.os.Bundle;

import androidx.appcompat.widget.AppCompatButton;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.google.android.material.textfield.TextInputEditText;
import com.team.team_07_fe.R;


public class ChangePasswordFragment extends Fragment {

    private TextInputEditText  newPassword, confirmPassword;


    private AppCompatButton btnChangePassword, btnCancel;

    public ChangePasswordFragment() {

    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_change_password, container, false);

        newPassword = view.findViewById(R.id.newPassword);
        confirmPassword = view.findViewById(R.id.confirmPassword);
        btnChangePassword = view.findViewById(R.id.btn_change_password);
        btnCancel = view.findViewById(R.id.btn_cancel);


        btnChangePassword.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                handleChangePassword();
            }
        });

        return view;
    }


    private void handleChangePassword() {

        String newPass = newPassword.getText().toString();
        String confirmPass = confirmPassword.getText().toString();
        
            Toast.makeText(getActivity(), "Mật khẩu đã được thay đổi", Toast.LENGTH_SHORT).show();


    }
}