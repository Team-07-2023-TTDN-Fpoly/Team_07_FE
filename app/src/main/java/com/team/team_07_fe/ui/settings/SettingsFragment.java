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

public class SettingsFragment extends Fragment {
    private LinearLayout layout_employee_manager;
    private SettingsViewModel mViewModel;

    public static SettingsFragment newInstance() {
        return new SettingsFragment();
    }

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
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        layout_employee_manager.setOnClickListener(v->{
            NavHostFragment.findNavController(SettingsFragment.this)
                    .navigate(R.id.action_navigation_settings_to_navigation_employee_manager);
        });
    }
}