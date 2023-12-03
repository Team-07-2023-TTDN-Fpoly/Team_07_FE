package com.team.team_07_fe.ui.contract.contractcreate;


import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.AppCompatButton;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.fragment.NavHostFragment;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.textfield.TextInputLayout;
import com.team.team_07_fe.MainActivity;
import com.team.team_07_fe.R;
import com.team.team_07_fe.models.Customer;
import com.team.team_07_fe.ui.contract.ContractHandleViewModel;
import com.team.team_07_fe.utils.FormatHelper;
import com.team.team_07_fe.viewmodels.CustomerViewModel;

import java.util.ArrayList;
import java.util.List;


public class ContractCreateFragment extends Fragment {
    private TextInputLayout layout_input_customer,layout_input_customer_name,layout_input_customer_phone,
    layout_input_createAt,layout_input_endAt,layout_input_total,layout_input_prepay,layout_input_discount;
    private TextView tv_choose_dress;
    private TextView tv_employee_name;
    private AutoCompleteTextView dropdown_customer;
    private RecyclerView recyclerView;
    private AppCompatButton btn_create_item;
    private CustomerViewModel customerViewModel;
    private ContractHandleViewModel contractHandleViewModel;

    private ArrayAdapter<Customer> customerArrayAdapter;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_contract_create, container, false);
        mapping(view);
        customerViewModel = new ViewModelProvider(requireActivity()).get(CustomerViewModel.class);
        contractHandleViewModel = new ViewModelProvider(requireActivity()).get(ContractHandleViewModel.class);
        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        customerViewModel.getAllCustomer(null);
        observeDataCustomer();



        handleClick();
    }
    /**
     * Xử lý sự kiện của các phần nút nhấn
     */
    private void handleClick(){
        tv_choose_dress.setOnClickListener(view -> {
            NavHostFragment.findNavController(ContractCreateFragment.this).navigate(R.id.action_navigation_contract_create_to_navigation_contract_choose_dress);
        });
        //Lựa chọn cho customer
        dropdown_customer.setOnItemClickListener((adapterView, view1, i, l) -> {
            contractHandleViewModel.setSelectCustomer(customerArrayAdapter.getItem(i));
        });

        btn_create_item.setOnClickListener(view -> {
            if(validateInput()){
                Toast.makeText(requireActivity(), "Check!", Toast.LENGTH_SHORT).show();
            }
        });
    }

    /**
     * Kiểm tra dữ liệu các thành phần khi nhấn button "thêm mới"
     * - Chọn khách hàng, chọn ngày tạo hợp đồng, ngày kết thúc
     * - Danh sách các áo cưới được chọn
     *
     * @return boolean
     * - Trả về 'true' nếu tất cả các điều kiện xác nhận đều được thoả mãn
     * - ngược lại trả về 'false'.
     * @author NghiaTc
     */
    private boolean validateInput(){
        boolean isValid = true;
        if(contractHandleViewModel.getSelectCustomer().getValue() == null){
            layout_input_customer.setError("Vui lòng chọn khách hàng!");
            layout_input_customer.getEditText().requestFocus();
            isValid = false;
        }else{
            layout_input_customer.setError(null);
        }

        String createAt = layout_input_createAt.getEditText().getText().toString().trim();
        String endAt = layout_input_endAt.getEditText().getText().toString().trim();

        if(createAt.isEmpty()){
            layout_input_createAt.setError("Chọn lại!");
            layout_input_createAt.getEditText().requestFocus();
            isValid = false;
        }else if(endAt.isEmpty()){
            layout_input_endAt.setError("Chọn lại!");
            layout_input_endAt.getEditText().requestFocus();
            isValid = false;
        }else if(FormatHelper.convertStringtoDate(endAt).compareTo(FormatHelper.convertStringtoDate(createAt))<=0){
            layout_input_endAt.setError("Chọn lại!");
            layout_input_endAt.getEditText().requestFocus();
            isValid = false;
        }else{
            layout_input_createAt.setError(null);
            layout_input_endAt.setError(null);
        }
        return isValid;
    }
    /**
     * @desc kiểm tra lấy dữ liệu từ danh sách khách hàng
     * Lưu trữ dữ liệu vào 1 viewmodel mới
     */
    private void observeDataCustomer(){
        customerViewModel.getCustomerList().observe(getViewLifecycleOwner(),customers -> {
            if(customers!=null){
                contractHandleViewModel.setListCustomer(customers);
            }
        });

        contractHandleViewModel.getListCustomer().observe(getViewLifecycleOwner(), customers -> {
            if(customers!=null){
                setCustomerAdapter(customers);
            }
        });

        contractHandleViewModel.getSelectCustomer().observe(getViewLifecycleOwner(), customer -> {
            if(customer!=null){
                layout_input_customer_phone.getEditText().setText(FormatHelper.formatPhoneNumber(customer.getCus_phone()));
                layout_input_customer_name.getEditText().setText(customer.getCus_name());
            }
        });
    }
    /**
    * @desc mapping layout
    */
    private void mapping(View view){
        layout_input_customer = view.findViewById(R.id.layout_input_customer);
        dropdown_customer = view.findViewById(R.id.dropdown_customer);
        layout_input_customer_phone = view.findViewById(R.id.layout_input_customer_phone);
        layout_input_customer_name = view.findViewById(R.id.layout_input_customer_name);
        layout_input_createAt = view.findViewById(R.id.layout_input_createAt);
        layout_input_endAt = view.findViewById(R.id.layout_input_endAt);
        tv_employee_name = view.findViewById(R.id.tv_employee_name);
        tv_choose_dress = view.findViewById(R.id.tv_choose_dress);
        recyclerView = view.findViewById(R.id.recyclerView);
        layout_input_total = view.findViewById(R.id.layout_input_total);
        layout_input_discount = view.findViewById(R.id.layout_input_discount);
        layout_input_prepay = view.findViewById(R.id.layout_input_prepay);
        btn_create_item = view.findViewById(R.id.btn_create_item);
    }

    /**
     * @param customerList
     * Thực hiện việc load lại danh sách customer
     */
    private void setCustomerAdapter(List<Customer> customerList){
        customerArrayAdapter = new ArrayAdapter<>(requireActivity(), android.R.layout.simple_dropdown_item_1line,customerList);
        dropdown_customer.setAdapter(customerArrayAdapter);
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