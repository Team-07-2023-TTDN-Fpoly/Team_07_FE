package com.team.team_07_fe.ui.dresstype;

import android.app.AlertDialog;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.LayoutInflater;
import com.team.team_07_fe.models.DressType;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import java.lang.String;

import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.AppCompatButton;
import androidx.appcompat.widget.AppCompatEditText;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.fragment.NavHostFragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.textfield.TextInputLayout;
import com.google.android.material.textview.MaterialTextView;
import com.team.team_07_fe.R;
import com.team.team_07_fe.adapter.DressTypeAdapter;
import com.team.team_07_fe.models.DressType;
import com.team.team_07_fe.models.Employee;
import com.team.team_07_fe.utils.FormatHelper;

import java.util.Date;
import java.util.List;

public class DressTypeManagerFragment extends Fragment {

    private DressTypeViewModel dressTypeViewModel;
    private DressTypeAdapter dressTypeAdapter;
    private TextInputLayout Tenloaiao;

    private RecyclerView rvloaiao;
    private FloatingActionButton fab1;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_dress_type_manager, container, false);

        //
        mapping(view);
        //
        dressTypeViewModel = new ViewModelProvider(requireActivity()).get(DressTypeViewModel.class);
        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        initialAdapter();
        fab1.setOnClickListener(this::showAddTypeDialog);

        observeViewModel();
    }

    private void showAddTypeDialog(View view) {
        AlertDialog.Builder builder = new AlertDialog.Builder(requireContext());
        LayoutInflater inflater = requireActivity().getLayoutInflater();
        View dialogView = inflater.inflate(R.layout.dialog_add_dresstype, null);

        // Find views in the custom dialog layout
        AppCompatButton btn_add_type = dialogView.findViewById(R.id.btn_add_type);
        ImageView backButton = dialogView.findViewById(R.id.imgback);
        TextInputLayout Tenloaiao = dialogView.findViewById(R.id.Tenloaiao);


        builder.setView(dialogView);
        AlertDialog alertDialog = builder.create();

        btn_add_type.setOnClickListener(v -> {
            // Handle adding the new dress type here
            String type_name = Tenloaiao.getEditText().getText().toString().trim();
            if (!type_name.isEmpty()) {
                dressTypeViewModel.addDressType(type_name);
                alertDialog.dismiss();
            } else {
                // Show an error message if the dress type name is empty
                Toast.makeText(requireContext(), "Vui lòng nhập tên loại áo !", Toast.LENGTH_SHORT).show();
            }
        });

        backButton.setOnClickListener(v -> alertDialog.dismiss());

        alertDialog.show();
    }

    private void mapping(View view){
        rvloaiao = view.findViewById(R.id.rvloaiao);
        fab1 = view.findViewById(R.id.fab1);
        Tenloaiao = view.findViewById(R.id.Tenloaiao);
    }

    private void initialAdapter(){
        dressTypeAdapter = new DressTypeAdapter(requireContext(),dressTypeViewModel.getEmployeeList().getValue());
        rvloaiao.setLayoutManager(new LinearLayoutManager(requireContext()));
        rvloaiao.setAdapter(dressTypeAdapter);

        dressTypeAdapter.setOnClickDeleteClickListener(this::handleShowConfirmDisable);
        dressTypeAdapter.setOnClickEditClickListener(this::handleShowUpdateDialog);
    }
    private void handleShowUpdateDialog(int position) {
        DressType dressType = dressTypeAdapter.getItem(position);
        showUpdateDialog(dressType);
    }
    private void showUpdateDialog(DressType dressType) {
        AlertDialog.Builder builder = new AlertDialog.Builder(requireContext());
        LayoutInflater inflater = requireActivity().getLayoutInflater();
        View dialogView = inflater.inflate(R.layout.dialog_update_dresstype, null);

        // Find views in the custom dialog layout
        TextInputLayout maLoaiAo = dialogView.findViewById(R.id.Ma_loai_ao);
        TextInputLayout tenLoaiAo = dialogView.findViewById(R.id.Tenloaiao);
        AppCompatButton btnUpdate = dialogView.findViewById(R.id.btn_update);
        ImageView backButton = dialogView.findViewById(R.id.imgback);
        AppCompatButton btnUndo = dialogView.findViewById(R.id.btn_undo);

        // Set initial values
        maLoaiAo.getEditText().setText(String.valueOf(dressType.getType_id()));
        tenLoaiAo.getEditText().setText(String.valueOf(dressType.getType_name()));

        builder.setView(dialogView);
        AlertDialog alertDialog = builder.create();

        btnUpdate.setOnClickListener(v -> {
            // Handle updating the dress type data here
            int type_id = dressType.getType_id();
            String type_name = tenLoaiAo.getEditText().getText().toString().trim();


            dressTypeViewModel.updateDressType(type_id, type_name);

            // Dismiss the dialog
            alertDialog.dismiss();
        });

        backButton.setOnClickListener(v -> alertDialog.dismiss());

        alertDialog.show();
    }


    private void handleShowConfirmDisable(int position1) {
    }



    private void observeViewModel(){
        dressTypeViewModel.getEmployeeList().observe(getViewLifecycleOwner(), new Observer<List<DressType>>() {
            @Override
            public void onChanged(List<DressType> dressTypes) {
                dressTypeAdapter.setList(dressTypes);
                Toast.makeText(requireContext(), "Thao tác thành công!", Toast.LENGTH_SHORT).show();
            }
        });
    }
}