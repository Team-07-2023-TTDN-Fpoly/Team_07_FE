package com.team.team_07_fe.ui.customer;

import android.app.DatePickerDialog;
import android.os.Build;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.appcompat.widget.AppCompatButton;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import android.text.TextUtils;
import android.util.Patterns;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import android.widget.DatePicker;
import android.widget.Toast;

import com.google.android.material.textfield.TextInputLayout;
import com.team.team_07_fe.MainActivity;
import com.team.team_07_fe.R;
import com.team.team_07_fe.models.Customer;
import com.team.team_07_fe.utils.FormatHelper;
import com.team.team_07_fe.utils.LoadingDialog;
import com.team.team_07_fe.viewmodels.CustomerViewModel;


import java.util.Calendar;
import java.util.Date;


public class CustomerCreateFragment extends Fragment {

    private TextInputLayout layout_input_name,layout_input_email,
            layout_input_phone,layout_input_phoneSecond,layout_input_birthday,layout_input_address;
    private CustomerViewModel mViewModel;
    private AppCompatButton btn_add_item;
    private LoadingDialog loadingDialog;

    @RequiresApi(api = Build.VERSION_CODES.O)
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_customer_create, container, false);
        mViewModel = new ViewModelProvider(requireActivity()).get(CustomerViewModel.class);
        loadingDialog = new LoadingDialog(requireContext());
        mapping(view);
        //mapping
        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        btn_add_item.setOnClickListener(this::handleAddCustomer);
        layout_input_birthday.getEditText().setOnClickListener(this::chooseDateForBirthday);

    }

    //xử lý sự kiến thêm mới
    private void handleAddCustomer(View view){
        String name = layout_input_name.getEditText().getText().toString().trim();
        String email = layout_input_email.getEditText().getText().toString().trim();
        String phone = layout_input_phone.getEditText().getText().toString().trim();
        String phoneSecond = layout_input_phoneSecond.getEditText().getText().toString().trim();
        String birthday = layout_input_birthday.getEditText().getText().toString().trim();
        String address = layout_input_address.getEditText().getText().toString().trim();


        if(validateInput(name,email,phone,phoneSecond)){
            Date formatBirthday = null;
            if (!TextUtils.isEmpty(birthday)) {
                formatBirthday = FormatHelper.convertStringtoDate(birthday);
            }
            mViewModel.addCustomer(new Customer(3,name,phone,phoneSecond,email,formatBirthday,address));
            Toast.makeText(requireContext(), "Thêm mới khách hàng thành công!", Toast.LENGTH_SHORT).show();
        }
    }

    //Chọn ngày sinh nhật
    private void chooseDateForBirthday(View view){
        Calendar calendar = Calendar.getInstance();
        int year = calendar.get(Calendar.YEAR);
        int month = calendar.get(Calendar.MONTH);
        int day = calendar.get(Calendar.DAY_OF_MONTH);

        DatePickerDialog datePickerDialog = new DatePickerDialog(
                requireContext(),
                new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                        // Xử lý ngày được chọn ở đây
                        String selectedDate = dayOfMonth + "/" + (monthOfYear + 1) + "/" + year;
                        // Ví dụ: set text cho một TextView
                        layout_input_birthday.getEditText().setText(selectedDate);
                    }
                },
                year, month, day);
        datePickerDialog.show();
    }

    private boolean validateInput(String name,String email,
                                  String phone,  String phoneSecond){
        boolean isValid = true;

        if(TextUtils.isEmpty(name)){
            layout_input_name.setError("Vui lòng nhập tên nhân viên!");
            isValid = false;
        }else{
            layout_input_name.setError(null);
        }

        if(TextUtils.isEmpty(phone) || phone.length() != 10 ||!Patterns.PHONE.matcher(phone).matches()){
            layout_input_phone.setError("Vui lòng nhập đúng định dạng!");
            isValid = false;
        }else{
            layout_input_phone.setError(null);
        }
        if (TextUtils.isEmpty(phoneSecond) || phoneSecond.length() != 10 || !Patterns.PHONE.matcher(phoneSecond).matches()) {
            layout_input_phoneSecond.setError("Vui lòng nhập đúng định dạng!");
            isValid = false;
        }else{
            layout_input_phoneSecond.setError(null);
        }
        if(TextUtils.isEmpty(email)){
            layout_input_email.setError("Vui lòng nhập email!");
            isValid = false;
        }else if(!Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
            layout_input_email.setError("Vui lòng nhập đúng định dạng email!");
            isValid = false;
        }else{
            layout_input_email.setError(null);
        }
        return isValid;
    }
    private void mapping(View view){
        layout_input_name = view.findViewById(R.id.layout_input_name);
        layout_input_email = view.findViewById(R.id.layout_input_email);
        layout_input_phone = view.findViewById(R.id.layout_input_phone);
        layout_input_phoneSecond = view.findViewById(R.id.layout_input_phoneSecond);
        layout_input_birthday = view.findViewById(R.id.layout_input_birthday);
        layout_input_address = view.findViewById(R.id.layout_input_address);
        btn_add_item = view.findViewById(R.id.btn_add_item);
    }

    @Override
    public void onResume() {
        super.onResume();
        ((MainActivity)requireActivity()).hiddenBottomBar();
    }

    @Override
    public void onStop() {
        super.onStop();
        ((MainActivity) requireActivity()).showBottomBar();
    }
}