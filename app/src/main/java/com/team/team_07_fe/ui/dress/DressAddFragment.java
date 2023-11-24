package com.team.team_07_fe.ui.dress;

import android.os.Bundle;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.AppCompatButton;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import com.google.android.material.textfield.TextInputLayout;
import com.team.team_07_fe.R;
import com.team.team_07_fe.models.Dress;
import com.team.team_07_fe.utils.LoadingDialog;
import com.team.team_07_fe.viewmodels.DressViewModel;


public class DressAddFragment extends Fragment {
    private TextInputLayout layout_input_name, layout_input_type, layout_input_color, layout_input_size,
    layout_input_price, layout_input_des;
    private DressViewModel mViewModel;
    private AppCompatButton btn_add_item;
    private LoadingDialog loadingDialog;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_dress_add, container, false);
        mViewModel = new ViewModelProvider(requireActivity()).get(DressViewModel.class);
        loadingDialog = new LoadingDialog(requireContext());
        mapping(view);
        //mapping
        return view;
    }
    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
    }
    private void handle(View view){
        String name = layout_input_name.getEditText().getText().toString().trim();
        String type = layout_input_type.getEditText().getText().toString().trim();
        String price = layout_input_price.getEditText().getText().toString().trim();
        String color = layout_input_color.getEditText().getText().toString().trim();
        String size = layout_input_size.getEditText().getText().toString().trim();
        String des = layout_input_des.getEditText().getText().toString().trim();


        if(validateInput(name,price,type, des)){
            mViewModel.addDress(new Dress(name,type,price,color,size,des));
            Toast.makeText(requireContext(), "Thêm mới áo cưới thành công!", Toast.LENGTH_SHORT).show();
            requireActivity().onBackPressed();
        }
    }
    private void mapping(View view){

        layout_input_name = view.findViewById(R.id.layout_input_name);
        layout_input_price = view.findViewById(R.id.layout_input_price);
        layout_input_type = view.findViewById(R.id.layout_input_type);
        layout_input_color = view.findViewById(R.id.layout_input_color);
        layout_input_size = view.findViewById(R.id.layout_input_size);
        layout_input_des = view.findViewById(R.id.layout_input_des);
        btn_add_item = view.findViewById(R.id.btn_add_item);

    }
    private boolean validateInput(String name,String price,
                                  String color, String des){
        boolean isValid = true;

        if(TextUtils.isEmpty(name)){
            layout_input_name.setError("Vui lòng nhập tên áo cưới!");
            isValid = false;
        }else{
            layout_input_name.setError(null);
        }

        if(TextUtils.isEmpty(price) ){
            layout_input_price.setError("Vui lòng nhập giá tiền!");
            isValid = false;
        }else{
            layout_input_price.setError(null);
        }
        if (TextUtils.isEmpty(color)) {
            layout_input_color.setError("Vui lòng nhập màu cho áo cưới!");
            isValid = false;
        }else{
            layout_input_color.setError(null);
        }
        if(TextUtils.isEmpty(des)){
            layout_input_des.setError(null);
        }else  {
            layout_input_des.setError("Vui lòng nhập mô tả cho áo cưới!");
            isValid = false;
        }

        return isValid;
    }
}