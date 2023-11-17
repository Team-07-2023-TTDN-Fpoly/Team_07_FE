package com.team.team_07_fe.fragment;

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
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.DatePicker;
import android.widget.Toast;

import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;
import com.team.team_07_fe.MainActivity;
import com.team.team_07_fe.R;
import com.team.team_07_fe.models.Customer;
import com.team.team_07_fe.ui.customer.CustomerViewDetail;
import com.team.team_07_fe.ui.customer.CustomerViewModel;
import com.team.team_07_fe.ui.dress.CreateDetailScreen;
import com.team.team_07_fe.utils.FormatHelper;
import com.team.team_07_fe.utils.LoadingDialog;

import java.util.Calendar;
import java.util.Date;


public class CreateDetailFragment extends Fragment {

    private TextInputLayout create_detail_input_date,
            create_detail_input_name, create_detail_input_money, create_detail_input_text;
    private CustomerViewDetail mViewDetail;
    private AppCompatButton btn_add_detail;
    private LoadingDialog loadingDialog;

    //    private View view;
    @RequiresApi(api = Build.VERSION_CODES.O)
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_create_detail, container, false);
//        mViewDetail = new ViewModelProvider(requireActivity()).get(CustomerViewDetail.class);
//        loadingDialog = new LoadingDialog(requireContext());
//
//        mapping(view);
//
//        return view;
    }

    private void AddDetailCustomer(View view) {
        String date = create_detail_input_date.getEditText().getText().toString().trim();
        String name = create_detail_input_name.getEditText().getText().toString().trim();
        String money = create_detail_input_money.getEditText().getText().toString().trim();
        String text = create_detail_input_text.getEditText().getText().toString().trim();

        if (valueInput(date, name, money, text)) {

        }
        mViewDetail.addDetail(new Customer(4, date, name, money, text));
        Toast.makeText(requireContext(), "Thêm mới khách hàng thành công!", Toast.LENGTH_SHORT).show();
    }


    private void dateforcreatedetail(View view){
        Calendar calendar = Calendar.getInstance();
        int year = calendar.get(Calendar.YEAR);
        int month = calendar.get(Calendar.MONTH);
        DatePickerDialog monthYearPickerDialog = new DatePickerDialog(requireContext(),
                new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker view, int selectedYear,
                                          int selectedMonth, int dayOfMonth) {
                        String selectedMonthYear = (selectedMonth + 1) + " / " + selectedYear;

                    }
                }, year, month, 1); // 1: Ngày mặc định, chúng ta chỉ quan tâm đến tháng và năm

        // Chỉ hiển thị tháng và năm
        monthYearPickerDialog.getDatePicker().findViewById(getResources().getIdentifier("day", "id", "android")).setVisibility(View.GONE);

        monthYearPickerDialog.show();
    }
    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        btn_add_detail.setOnClickListener(this::AddDetailCustomer);
        create_detail_input_date.getEditText().setOnClickListener(this::dateforcreatedetail);

    }
    private boolean valueInput(String date, String name,
                               String money, String text) {
        boolean isvalid = true;
        if (TextUtils.isEmpty(date)) {
            create_detail_input_date.setError("Không để trống dữ liệu!");
            isvalid = false;
        } else {
            create_detail_input_date.setError(null);
        }
        if (TextUtils.isEmpty(name)) {
            create_detail_input_name.setError("Không để trống dữ liệu!");
            isvalid = false;
        } else {
            create_detail_input_name.setError(null);
        }
        if (TextUtils.isEmpty(money)) {
            create_detail_input_money.setError("Không để trống dữ liệu!");
            isvalid = false;
        } else {
            create_detail_input_money.setError(null);
        }
        if (TextUtils.isEmpty(text)) {
            create_detail_input_text.setError("Không để trống dữ liệu!");
            isvalid = false;
        } else {
            create_detail_input_text.setError(null);
        }
        return isvalid;
    }
    private void mapping(View view){
        create_detail_input_date = view.findViewById(R.id.create_detail_input_date);
        create_detail_input_name = view.findViewById(R.id.create_detail_input_name);
        create_detail_input_money = view.findViewById(R.id.create_detai_input_money);
        create_detail_input_text = view.findViewById(R.id.create_detail_input_text);
        btn_add_detail = view.findViewById(R.id.btn_add_detail);
    }

}