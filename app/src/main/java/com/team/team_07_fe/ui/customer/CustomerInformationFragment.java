package com.team.team_07_fe.ui.customer;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.google.android.material.textfield.TextInputLayout;
import com.team.team_07_fe.R;
import com.team.team_07_fe.models.Customer;
import com.team.team_07_fe.models.Employee;
import com.team.team_07_fe.utils.FormatHelper;

public class CustomerInformationFragment extends Fragment {
    private TextInputLayout layout_input_id,layout_input_name,layout_input_email,
            layout_input_phone,layout_input_phoneSecond,layout_input_birthday,layout_input_address;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_customer_information, container, false);
        //
        mapping(view);
        return view;

    }
    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        if(getArguments()!=null){
            Customer data = (Customer) getArguments().getSerializable("data_customer");
            setData(data);
        }
    }
    //Gán data cho input
    private void setData(Customer customer){ //tên, sdt1,sdt2,email,ngày sinh,địa chỉ
        // Set lại thông tin id, nếu có trường hi   ển thị id
        if (layout_input_id.getEditText() != null) {
            layout_input_id.getEditText().setText(String.valueOf(customer.getCus_id()));
        }
        // Set lại tên nhân viên
        layout_input_name.getEditText().setText(customer.getCus_name());
        // Set lại email
        layout_input_email.getEditText().setText(customer.getCus_email());
        layout_input_email.getEditText().setEnabled(false);
        // Set lại số điện thoại
        layout_input_phone.getEditText().setText(FormatHelper.formatPhoneNumber(customer.getCus_phone()));
        layout_input_phoneSecond.getEditText().setText(FormatHelper.formatPhoneNumber(customer.getCus_phoneSecond()));
        // Set lại ngày sinh - bạn cần định dạng lại Date thành String
        if (customer.getCus_wedding_date() != null) {
            String birthdayStr = FormatHelper.convertDatetoString(customer.getCus_wedding_date());
            layout_input_birthday.getEditText().setText(birthdayStr);
        }
        // Set lại địa chỉ
        layout_input_address.getEditText().setText(customer.getCus_address());


    }
    //mapping
    private void mapping(View view){
        layout_input_id = view.findViewById(R.id.layout_input_id);
        layout_input_name = view.findViewById(R.id.layout_input_name);
        layout_input_phone = view.findViewById(R.id.layout_input_phone);
        layout_input_phoneSecond = view.findViewById(R.id.layout_input_phoneSecond);
        layout_input_email = view.findViewById(R.id.layout_input_email);
        layout_input_birthday = view.findViewById(R.id.layout_input_birthday);
        layout_input_address = view.findViewById(R.id.layout_input_address);
    }
}