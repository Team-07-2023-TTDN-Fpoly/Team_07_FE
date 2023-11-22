package com.team.team_07_fe.ui.dress;

import android.app.AlertDialog;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.AppCompatButton;
import androidx.core.widget.ImageViewCompat;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import com.google.android.material.textfield.TextInputLayout;
import com.team.team_07_fe.R;
import com.team.team_07_fe.models.Customer;
import com.team.team_07_fe.models.Dress;
import com.team.team_07_fe.models.Employee;
import com.team.team_07_fe.utils.FormatHelper;
import com.team.team_07_fe.utils.LoadingDialog;

import java.util.Date;


public class DressUpdateFragment extends Fragment {
    private TextInputLayout layout_input_id, layout_input_name, layout_input_type, layout_input_color,
            layout_input_size, layout_input_price, layout_input_des;
    private DressViewModel mViewModel;
    private AppCompatButton btn_reloadDress, btn_updateDress;
    private ImageViewCompat layout_image;
    private Dress originalData = null;
    private LoadingDialog loadingDialog;


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_update_dress, container, false);
        mViewModel = new ViewModelProvider(requireActivity()).get(DressViewModel.class);
        loadingDialog = new LoadingDialog(requireContext());
//        mapping (view);
        return (view);
    }

//    @Override
//    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
//        super.onViewCreated(view, savedInstanceState);
//        if(getArguments()!=null){
//            originalData = (Dress) getArguments().getSerializable("data_customer");
//            setData(originalData);
//        }
//        //Click button
//        btn_reloadDress.setOnClickListener(this::handleReloadData);
//        btn_updateDress.setOnClickListener(this::handleUpdateData);
//    }
//    private void handleReloadData(View view){
//        if(originalData!=null){
//            AlertDialog.Builder builder = new AlertDialog.Builder(requireContext())
//                    .setTitle("Cảnh báo!")
//                    .setMessage("Bạn có chắc với quyết định hoàn tác này không? " +
//                            "Mọi thay đổi của bạn sẽ không được lưu.")
//                    .setPositiveButton(R.string.yes,(dialog, which) -> {
//                        setData(originalData);
//                        dialog.dismiss();
//                    })
//                    .setNegativeButton(R.string.no,((dialog, which) -> {
//                        dialog.dismiss();
//                    }));
//            builder.create().show();
//        }
//    }
//    private void handleUpdateData(View view){
//        String name = layout_input_name.getEditText().getText().toString().trim();
//        String id = layout_input_id.getEditText().getText().toString().trim();
//        String type = layout_input_type.getEditText().getText().toString().trim();
//        String color = layout_input_color.getEditText().getText().toString().trim();
//        String size = layout_input_size.getEditText().getText().toString().trim();
//        String price = layout_input_price.getEditText().getText().toString().trim();
//        String des = layout_input_des.getEditText().getText().toString().trim();
//
//
//
//
//        if(validateInput(name,id,price)){
//
//            Employee employeeRequest = new Employee(name,id,type,color,size,price,des);
//            showDialogConfirmUpdate(id,employeeRequest);
//        }
//        private void showDialogConfirmUpdate(String id, Dress dressRequest){
//            AlertDialog.Builder builder = new AlertDialog.Builder(requireContext())
//                    .setTitle("Thông báo!")
//                    .setMessage("Bạn có chắc muốn cập nhật áo cưới này không? " +
//                            "Mọi thông tin trước đó sẽ không được lưu.")
//                    .setPositiveButton(R.string.yes,(dialog, which) -> {
//                        mViewModel.updateEmployee(Integer.parseInt(id),dressRequest);
//                        refreshFragment();
//                        dialog.dismiss();
//                    })
//                    .setNegativeButton(R.string.no,((dialog, which) -> {
//                        dialog.dismiss();
//                    }));
//            builder.create().show();
//        }
//    }
}