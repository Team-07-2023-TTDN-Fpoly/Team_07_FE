package com.team.team_07_fe.ui.dresstype;

import android.os.Bundle;

import androidx.fragment.app.DialogFragment;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.google.android.material.textfield.TextInputLayout;
import com.team.team_07_fe.R;
import com.team.team_07_fe.models.DressType;


public class UpdateDressTypeDialogFragment extends DialogFragment {

    private TextInputLayout maLoaiAoInputLayout;
    private TextInputLayout tenLoaiAoInputLayout;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.dialog_update_dresstype, container, false);

        // Retrieve DressType data from arguments
        Bundle args = getArguments();
        if (args != null) {
            DressType dressType = (DressType) args.getSerializable("data_dress_type");
            if (dressType != null) {
                // Populate the dialog views with DressType data
                maLoaiAoInputLayout.getEditText().setText(dressType.getType_id());
                tenLoaiAoInputLayout.getEditText().setText(dressType.getType_name());
            }
        }

        // ... (other code for initializing views and buttons)

        return view;
    }
}