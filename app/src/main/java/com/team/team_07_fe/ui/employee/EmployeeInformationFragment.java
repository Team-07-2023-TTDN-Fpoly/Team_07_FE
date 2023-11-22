package com.team.team_07_fe.ui.employee;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.google.android.material.textfield.TextInputLayout;
import com.team.team_07_fe.R;
import com.team.team_07_fe.models.Employee;
import com.team.team_07_fe.utils.FormatHelper;


public class EmployeeInformationFragment extends Fragment {
    private TextInputLayout layout_input_id,layout_input_name,layout_input_email,
            layout_input_phone,layout_input_salary,layout_input_birthday,
            layout_input_join_date,layout_input_address,layout_input_role,layout_input_work_shift;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_employee_information, container, false);
        //
        mapping(view);
        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        if(getArguments()!=null){
         Employee data = (Employee) getArguments().getSerializable("data_employee");
         setData(data);
        }
    }
    //Gán data người dùng cho input
    private void setData(Employee employee){
        // Set lại thông tin id, nếu có trường hiển thị id
        if (layout_input_id.getEditText() != null) {
            layout_input_id.getEditText().setText(String.valueOf(employee.getEmp_id()));
        }
        // Set lại tên nhân viên
        layout_input_name.getEditText().setText(employee.getEmp_name());
        // Set lại email
        layout_input_email.getEditText().setText(employee.getEmail());
        layout_input_email.getEditText().setEnabled(false);
        // Set lại số điện thoại
        layout_input_phone.getEditText().setText(FormatHelper.formatPhoneNumber(employee.getEmp_phone()));
        // Set lại ngày sinh - bạn cần định dạng lại Date thành String
        if (employee.getEmp_birthday() != null) {
            String birthdayStr = FormatHelper.convertDatetoString(employee.getEmp_birthday());
            layout_input_birthday.getEditText().setText(birthdayStr);
        }
        if (employee.getJoin_date() != null) {
            String join_date = FormatHelper.convertDatetoString(employee.getJoin_date());
            layout_input_join_date.getEditText().setText(join_date);
        }
        // Set lại lương
        layout_input_salary.getEditText().setText(FormatHelper.convertPriceToString(employee.getBasic_salary()));
        // Set lại địa chỉ
        layout_input_address.getEditText().setText(employee.getEmp_address());

        //Set chức vụ
        layout_input_role.getEditText().setText(employee.getRole());

        //Set ca làm việc
        layout_input_work_shift.getEditText().setText(employee.getWorkShift().toString());

    }
    //mapping
    private void mapping(View view){
        layout_input_id = view.findViewById(R.id.layout_input_id);
        layout_input_name = view.findViewById(R.id.layout_input_name);
        layout_input_email = view.findViewById(R.id.layout_input_email);
        layout_input_phone = view.findViewById(R.id.layout_input_phone);
        layout_input_salary = view.findViewById(R.id.layout_input_salary);
        layout_input_birthday = view.findViewById(R.id.layout_input_birthday);
        layout_input_join_date = view.findViewById(R.id.layout_input_join_date);
        layout_input_address = view.findViewById(R.id.layout_input_address);
        layout_input_role = view.findViewById(R.id.layout_input_role);
        layout_input_work_shift = view.findViewById(R.id.layout_input_work_shift);
    }
}