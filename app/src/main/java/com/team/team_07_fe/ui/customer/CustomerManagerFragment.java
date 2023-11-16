package com.team.team_07_fe.ui.customer;

import androidx.lifecycle.ViewModelProvider;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.team.team_07_fe.R;
import com.team.team_07_fe.viewmodels.CustomerViewModel;

public class CustomerManagerFragment extends Fragment {

    private CustomerViewModel mViewModel;

    public static CustomerManagerFragment newInstance() {
        return new CustomerManagerFragment();
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_customer_manager, container, false);
        mViewModel = new ViewModelProvider(this).get(CustomerViewModel.class);
        return view;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        // TODO: Use the ViewModel
    }

}