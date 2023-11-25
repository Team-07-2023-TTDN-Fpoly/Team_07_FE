package com.team.team_07_fe.ui.dress;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import com.team.team_07_fe.R;

public class DressManagerFragment extends Fragment {

    private DressViewModel dressViewModel;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_dress_manager, container, false);
        dressViewModel =
                new ViewModelProvider(this).get(DressViewModel.class);
        return view;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
    }

}


