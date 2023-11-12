package com.team.team_07_fe.ui.contract;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import com.team.team_07_fe.R;
public class ContractManagerFragment extends Fragment {
    private ContractViewModel contractViewModel;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_contract_manager,container,false);

        contractViewModel =
                new ViewModelProvider(this).get(ContractViewModel.class);

        return view;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
    }
}