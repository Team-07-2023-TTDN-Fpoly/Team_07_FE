package com.team.team_07_fe.dialog;

import android.app.AlertDialog;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import com.google.android.material.textfield.TextInputLayout;
import com.team.team_07_fe.R;
import com.team.team_07_fe.ui.employee.EmployeeManagerFragment;
import com.team.team_07_fe.utils.LoadingDialog;
import com.team.team_07_fe.viewmodels.AuthViewModel;
import com.team.team_07_fe.viewmodels.EmployeeViewModel;

public class AdminChangePasswordDialog {
    private TextInputLayout layout_input_new_password,layout_input_check_password;
    private AlertDialog dialog;
    private LoadingDialog loadingDialog;
    public AdminChangePasswordDialog(Context context, EmployeeManagerFragment fragment, AuthViewModel viewModel, String auth_id) {
        View view = LayoutInflater.from(context).inflate(R.layout.dialog_admin_change_password, null);
        initializeViews(view);
        loadingDialog = new LoadingDialog(context);

        AlertDialog.Builder builder = new AlertDialog.Builder(context).setView(view)
                .setCancelable(false)
                .setPositiveButton(R.string.update_item, null)
                .setNegativeButton(R.string.cancel_item, (dialog, which) -> {
                    dialog.dismiss();
                });

        dialog = builder.create();
        dialog.setOnShowListener(dialogInterface -> {
            Button button = dialog.getButton(AlertDialog.BUTTON_POSITIVE);
            button.setOnClickListener(view1 -> {
                String inputNewPass = layout_input_new_password.getEditText().getText().toString().trim();
                String inputCheckPass = layout_input_check_password.getEditText().getText().toString().trim();

                if (validInput(inputNewPass, inputCheckPass)) {
                    AlertDialog.Builder newBuilder = new AlertDialog.Builder(context)
                            .setTitle("Thông báo!")
                            .setMessage("Bạn có chắc muốn cập nhật mật khẩu cho nhân viên này không? " +
                                    "Mọi thông tin trước đó sẽ không được lưu.")
                            .setPositiveButton(R.string.yes,(dialog, which) -> {
                                loadingDialog.show();
                                viewModel.adminChangePassword(auth_id,inputNewPass);
                            })
                            .setNegativeButton(R.string.no,((dialog, which) -> {
                                dialog.dismiss();
                            }));
                    newBuilder.create().show();
                }
            });
        });
        viewModel.getDataMessage().observe(fragment.getViewLifecycleOwner(),s -> {
            if(s!=null){
                loadingDialog.dismiss();
                dialog.dismiss();
                Toast.makeText(context, s, Toast.LENGTH_SHORT).show();
                viewModel.setDataMessage(null);
            }
        });
        viewModel.getErrorMessage().observe(fragment.getViewLifecycleOwner(),s -> {
            if(s!=null){
                loadingDialog.dismiss();
                Toast.makeText(context, s, Toast.LENGTH_SHORT).show();
                viewModel.setErrorMessage(null);
            }
        });
    }
    //
    private void initializeViews(View view){
        //mapping
        layout_input_new_password = view.findViewById(R.id.layout_input_new_password);
        layout_input_check_password = view.findViewById(R.id.layout_input_check_password);

    }

    //Kiểm tra dữ liệu nhập vào
    public boolean validInput(String newPassword,String checkPassword){
        boolean isValid = true;

        if(newPassword.length() < 6){
            layout_input_new_password.setError("Mật khẩu từ 6 kí tự trở lên!");
            isValid = false;
        } else {
            layout_input_new_password.setError(null);
        }

         if(!checkPassword.equals(newPassword)){
             layout_input_check_password.setError("Mật khẩu không trùng khớp!");
            isValid = false;
        } else {
             layout_input_check_password.setError(null);
        }

        return isValid;
    }

    public void show(){
        dialog.show();
    }
    public void dismiss(){
        dialog.dismiss();
    }
}
