package com.team.team_07_fe.ui.dresstype;

import android.app.AlertDialog;
import android.content.DialogInterface;
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
    private int selectedPosition = -1;
    private TextInputLayout Tenloaiao;
    private AppCompatButton btn_undo;
    private RecyclerView rvloaiao;
    private FloatingActionButton fab1;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_dress_type_manager, container, false);

        mapping(view);

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

        AppCompatButton btn_add_type = dialogView.findViewById(R.id.btn_add_type);
        ImageView backButton = dialogView.findViewById(R.id.imgback);
        TextInputLayout Tenloaiao = dialogView.findViewById(R.id.Tenloaiao);


        builder.setView(dialogView);
        AlertDialog alertDialog = builder.create();

        btn_add_type.setOnClickListener(v -> {
            String type_name = Tenloaiao.getEditText().getText().toString().trim();
            if (!type_name.isEmpty()) {
                dressTypeViewModel.addDressType(type_name);
                alertDialog.dismiss();
            } else {
                Toast.makeText(requireContext(), "Vui lòng nhập tên loại áo !", Toast.LENGTH_SHORT).show();
            }
        });

        backButton.setOnClickListener(v -> alertDialog.dismiss());
        alertDialog.setCanceledOnTouchOutside(false);

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

        dressTypeAdapter.setOnClickDeleteClickListener(this::handleShowConfirmDelete);
        dressTypeAdapter.setOnClickEditClickListener(this::handleShowUpdateDialog);
    }


    private void handleShowUpdateDialog(int position) {
        DressType dressType = dressTypeAdapter.getItem(position);
        selectedPosition = position;
        showUpdateDialog(dressType);
    }
    private void showUpdateDialog(DressType dressType) {
        AlertDialog.Builder builder = new AlertDialog.Builder(requireContext());
        LayoutInflater inflater = requireActivity().getLayoutInflater();
        View dialogView = inflater.inflate(R.layout.dialog_update_dresstype, null);

        TextInputLayout maLoaiAo = dialogView.findViewById(R.id.Ma_loai_ao);
        TextInputLayout tenLoaiAo = dialogView.findViewById(R.id.Tenloaiao);
        AppCompatButton btnUpdate = dialogView.findViewById(R.id.btn_update);
        ImageView backButton = dialogView.findViewById(R.id.imgback);
        AppCompatButton btnUndo = dialogView.findViewById(R.id.btn_undo);

        maLoaiAo.getEditText().setText(String.valueOf(dressType.getType_id()));
        tenLoaiAo.getEditText().setText(String.valueOf(dressType.getType_name()));

        builder.setView(dialogView);
        AlertDialog alertDialog = builder.create();

        AlertDialog.Builder confirmationBuilder = new AlertDialog.Builder(requireContext())
                .setTitle("Cảnh báo!")
                .setMessage("Bạn có chắc muốn cập nhật loại áo này không?")
                .setPositiveButton(R.string.yes, (dialog, which) -> {
                    // Update the dress type
                    int type_id = dressType.getType_id();
                    String type_name = tenLoaiAo.getEditText().getText().toString().trim();
                    dressTypeViewModel.updateDressType(type_id, type_name);
                    alertDialog.dismiss();
                })
                .setNegativeButton(R.string.no, (dialog, which) -> {
                    // Dismiss the confirmation dialog
                    dialog.dismiss();
                });

        btnUpdate.setOnClickListener(v -> {
            confirmationBuilder.create().show();
        });

        btnUndo.setOnClickListener(v -> {
            // Show the undo confirmation dialog
            AlertDialog.Builder undoBuilder = new AlertDialog.Builder(requireContext())
                    .setTitle("Cảnh báo!")
                    .setMessage("Bạn có chắc muốn hoàn tác không? Mọi thay đổi của bạn sẽ không được lưu.")
                    .setPositiveButton(R.string.yes, (dialog, which) -> {
                        // Get the original data for the specific DressType using the position
                        DressType originalData = dressTypeAdapter.getItem(selectedPosition);
                        maLoaiAo.getEditText().setText(String.valueOf(originalData.getType_id()));
                        tenLoaiAo.getEditText().setText(String.valueOf(originalData.getType_name()));

                        dialog.dismiss();
                    })
                    .setNegativeButton(R.string.no, (dialog, which) -> {
                        dialog.dismiss();
                    });

            undoBuilder.create().show();
        });

        backButton.setOnClickListener(v -> alertDialog.dismiss());
        alertDialog.setCanceledOnTouchOutside(false);

        alertDialog.show();
    }


    private void handleShowConfirmDelete(int position) {
        DressType dressType = dressTypeAdapter.getItem(position);

        AlertDialog.Builder builder = new AlertDialog.Builder(requireContext());
        builder.setTitle("Xác nhận xóa");
        builder.setMessage("Bạn có chắc muốn xóa loại áo này?");

        builder.setPositiveButton("Xóa", (dialog, which) -> {
            dressTypeViewModel.deleteDressType(dressType.getType_id());

            dialog.dismiss();
        });

        builder.setNegativeButton("Hủy bỏ", (dialog, which) -> {
            dialog.dismiss();
        });

        AlertDialog alertDialog = builder.create();
        alertDialog.setCanceledOnTouchOutside(false);

        alertDialog.show();
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
