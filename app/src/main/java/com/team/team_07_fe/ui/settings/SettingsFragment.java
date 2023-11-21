package com.team.team_07_fe.ui.settings;

import androidx.lifecycle.ViewModelProvider;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.navigation.fragment.NavHostFragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import com.team.team_07_fe.LoginActivity;
import com.team.team_07_fe.MainActivity;
import com.team.team_07_fe.R;
import com.team.team_07_fe.models.Employee;
import com.team.team_07_fe.models.WorkShift;
import com.team.team_07_fe.utils.FormatHelper;

import java.time.LocalTime;
import java.util.Date;

public class SettingsFragment extends Fragment {
    private LinearLayout layout_employee_manager,layout_employee_information, layout_change_password, layout_dresstype_manager,layout_workshift_information,layout_logout;
    private SettingsViewModel mViewModel;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        View view  =inflater.inflate(R.layout.fragment_settings, container, false);
        mViewModel = new ViewModelProvider(this).get(SettingsViewModel.class);

        mapping(view);
        return view;
    }

    private void mapping(View view){
        layout_employee_manager = view.findViewById(R.id.layout_employee_manager);
        layout_employee_information = view.findViewById(R.id.layout_employee_information);
        layout_workshift_information = view.findViewById(R.id.layout_work_shift_manager);
        layout_dresstype_manager = view.findViewById(R.id.layout_dresstype_manager);
        layout_change_password = view.findViewById(R.id.layout_change_password);
        layout_logout = view.findViewById(R.id.layout_logout);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        //Chuyển sang màn hình quản lý nhân viên
        layout_employee_manager.setOnClickListener(v->{
            NavHostFragment.findNavController(SettingsFragment.this)
                    .navigate(R.id.action_navigation_settings_to_navigation_employee_manager);
        });
        //Chuyển sang màn hình xem thông tin chi tiết
        layout_employee_information.setOnClickListener(v->{
            Bundle bundle = new Bundle();
            WorkShift eveningShift = new WorkShift(2, "Ca chiều", FormatHelper.convertStringToTime("12:00"), FormatHelper.convertStringToTime("14:00"), "Làm việc buổi tối");

            Employee employee = new Employee("0", "Nguyen Van A", "0123456789", new Date(), 5000000, "Hà Nội", "Nhân viên", eveningShift, new Date(), "a@example.com","0",false);
            bundle.putSerializable("data_employee",employee);
            NavHostFragment.findNavController(SettingsFragment.this)
                    .navigate(R.id.action_navigation_settings_to_navigation_employee_information,bundle);
        });
        //chuyển sang màn hình thay đổi mật khẩu
        layout_change_password.setOnClickListener(v->{
            NavHostFragment.findNavController(SettingsFragment.this)
                    .navigate(R.id.action_navigation_settings_to_navigation_change_password);
        });
        //Đăng xuất
        layout_logout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showLogoutConfirmationDialog();
            }
        });
        layout_dresstype_manager.setOnClickListener(v->{
            NavHostFragment.findNavController(SettingsFragment.this)
                    .navigate(R.id.action_navigation_settings_to_dressTypeManagerFragment);
        });
        layout_workshift_information.setOnClickListener(v->{
            NavHostFragment.findNavController(SettingsFragment.this)
                    .navigate(R.id.action_navigation_settings_to_workShiftManagerFragment);
        });
    }
    private void showLogoutConfirmationDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
        builder.setTitle("Xác nhận đăng xuất");
        builder.setMessage("Bạn có chắc chắn muốn đăng xuất?");

        builder.setPositiveButton("Đồng ý", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                performLogout();
            }
        });

        builder.setNegativeButton("Không", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                dialogInterface.dismiss();
            }
        });

        AlertDialog dialog = builder.create();
        dialog.show();
    }
    private void performLogout() {
        Intent intent = new Intent(Intent.ACTION_MAIN);
        intent.addCategory(Intent.CATEGORY_HOME);
        startActivity(intent);
    }

}
