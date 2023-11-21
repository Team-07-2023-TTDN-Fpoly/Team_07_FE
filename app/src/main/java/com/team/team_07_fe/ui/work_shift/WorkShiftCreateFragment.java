package com.team.team_07_fe.ui.work_shift;

import android.app.AlertDialog;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.team.team_07_fe.R;

public class WorkShiftCreateFragment extends Fragment {
    Button btnAdd;
    AlertDialog.Builder builder;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_work_shift_create, container, false);
        return view;
    }
    private void mapping(View view){

        btnAdd = view.findViewById(R.id.btn_update_item_work);
        builder = new AlertDialog.Builder(requireContext());
//        btnAdd.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                builder.setTitle("Thông báo")
//                        .setMessage("")
//            }
//        });
    }
}