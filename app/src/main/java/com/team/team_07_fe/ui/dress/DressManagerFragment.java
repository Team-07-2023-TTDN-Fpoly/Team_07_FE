package com.team.team_07_fe.ui.dress;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.lifecycle.ViewModelProvider;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.team.team_07_fe.R;

public class DressManagerFragment extends Fragment {
    private FloatingActionButton actionButton;
    private DressViewModel dressViewModel;
    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_dress_manager,container,false);
        dressViewModel =
                new ViewModelProvider(this).get(DressViewModel.class);
        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        actionButton= view.findViewById(R.id.floatingActionButton);
        actionButton.setOnClickListener(view1 -> {
            FragmentManager fragmentManager = getActivity().getSupportFragmentManager(); // hoặc getChildFragmentManager() nếu đang ở trong Fragment
            FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();



        });
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
    }
}