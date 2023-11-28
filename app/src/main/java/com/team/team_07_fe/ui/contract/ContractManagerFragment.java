package com.team.team_07_fe.ui.contract;

import android.os.Bundle;
import android.service.controls.actions.FloatAction;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.fragment.NavHostFragment;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.team.team_07_fe.R;
import com.team.team_07_fe.ui.work_shift.WorkShiftManagerFragment;

public class ContractManagerFragment extends Fragment {
    private FloatingActionButton fab;

    private ContractViewModel contractViewModel;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_contract_manager, container, false);
        mapping(view);
        contractViewModel =
                new ViewModelProvider(this).get(ContractViewModel.class);

        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        fab.setOnClickListener(this::handleNavigateCreateForm);


    }

    private void handleNavigateCreateForm(View view) {
        NavHostFragment.findNavController(ContractManagerFragment.this)
                .navigate(R.id.action_navigation_contract_to_contractCreateFragment);
    }

    private void mapping(View view) {
        fab = view.findViewById(R.id.fab);
    }

    ;
}