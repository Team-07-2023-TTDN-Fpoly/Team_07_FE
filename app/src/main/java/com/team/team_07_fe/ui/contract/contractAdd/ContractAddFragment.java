package com.team.team_07_fe.ui.contract.contractAdd;


import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;

import com.google.android.material.textfield.TextInputLayout;
import com.team.team_07_fe.MainActivity;
import com.team.team_07_fe.R;
import com.team.team_07_fe.models.Customer;
import com.team.team_07_fe.viewmodels.CustomerViewModel;

import java.util.ArrayList;


public class ContractAddFragment extends Fragment {
    private TextInputLayout layout_input_customer;
    private AutoCompleteTextView dropdown_customer;
    private CustomerViewModel customerViewModel;
    private ContractAddViewModel contractAddViewModel;

    private ArrayAdapter<Customer> customerArrayAdapter;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_contract_add, container, false);
        mapping(view);
        customerViewModel = new ViewModelProvider(requireActivity()).get(CustomerViewModel.class);
        contractAddViewModel = new ViewModelProvider(requireActivity()).get(ContractAddViewModel.class);
        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        customerViewModel.getAllCustomer(null);
        observeDataCustomer();

        dropdown_customer.setOnItemClickListener((adapterView, view1, i, l) -> {
            contractAddViewModel.setSelectCustomer(customerArrayAdapter.getItem(i));
        });
    }
    /**
     * @desc kiểm tra lấy dữ liệu từ danh sách khách hàng
     * Lưu trữ dữ liệu vào 1 viewmodel mới
     */
    private void observeDataCustomer(){
        customerViewModel.getCustomerList().observe(getViewLifecycleOwner(),customers -> {
            if(customers!=null){
                contractAddViewModel.setListCustomer(customers);
            }
        });

        contractAddViewModel.getListCustomer().observe(getViewLifecycleOwner(),customers -> {
            if(customers!=null){
                customerArrayAdapter.clear();
                customerArrayAdapter.addAll(customers);
            }
        });

        contractAddViewModel.getSelectCustomer().observe(getViewLifecycleOwner(),customer -> {
            if(customer!=null){

            }
        });
    }
    /**
    * @desc mapping layout
    */
    private void mapping(View view){
        layout_input_customer = view.findViewById(R.id.layout_input_customer);
        dropdown_customer = view.findViewById(R.id.dropdown_customer);


        customerArrayAdapter = new ArrayAdapter<>(requireActivity(), android.R.layout.simple_dropdown_item_1line,new ArrayList<>());
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