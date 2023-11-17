package com.team.team_07_fe.ui.settings;

import androidx.lifecycle.ViewModelProvider;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.navigation.fragment.NavHostFragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import com.team.team_07_fe.R;
import com.team.team_07_fe.models.Employee;
import com.team.team_07_fe.models.WorkShift;

import java.time.LocalTime;
import java.util.Date;

public class SettingsFragment extends Fragment {
    private LinearLayout layout_employee_manager,layout_employee_information, layout_statistics;
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
        layout_statistics = view.findViewById(R.id.layout_statistics);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        //Chuyển sang màn hình quản lý nhân viên
        layout_employee_manager.setOnClickListener(v->{
            NavHostFragment.findNavController(SettingsFragment.this)
                    .navigate(R.id.action_navigation_settings_to_navigation_employee_manager);
        });
        //Chuyển sang màn hình thống kê
        layout_statistics.setOnClickListener(view1 -> {
            NavHostFragment.findNavController(SettingsFragment.this)
                    .navigate(R.id.action_navigation_statistics_screen_to_navigation_create_detail);
        });
        //Chuyển sang màn hình xem thông tin chi tiết
        layout_employee_information.setOnClickListener(v->{
            Bundle bundle = new Bundle();
            WorkShift eveningShift = new WorkShift(2, "Ca chiều", LocalTime.of(16, 0), LocalTime.of(0, 0), "Làm việc buổi tối");

            Employee employee = new Employee(0, "Nguyen Van A", "0123456789", new Date(), 5000000, "Hà Nội", "Nhân viên", eveningShift, new Date(), "a@example.com");
            bundle.putSerializable("data_employee",employee);
            NavHostFragment.findNavController(SettingsFragment.this)
                    .navigate(R.id.action_navigation_settings_to_navigation_employee_information,bundle);
        });
    }
}