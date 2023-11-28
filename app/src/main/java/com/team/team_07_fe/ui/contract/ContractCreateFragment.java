package com.team.team_07_fe.ui.contract;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.navigation.fragment.NavHostFragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.team.team_07_fe.R;


public class ContractCreateFragment extends Fragment {

   private TextView themmoi;



    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {

        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_contract_create, container, false);
        mapping(view);
        return view;
        // Inflate the layout for this fragment

    }
    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        themmoi.setOnClickListener(this::handleNavigateCreateForm);


    }

    private void handleNavigateCreateForm(View view) {
        NavHostFragment.findNavController(ContractCreateFragment.this)
                .navigate(R.id.action_contractCreateFragment2_to_chonAoFragment);
    }
    private void mapping(View view){
        themmoi = view.findViewById(R.id.themmoi);

    }

}
