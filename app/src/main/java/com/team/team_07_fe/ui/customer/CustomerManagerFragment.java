package com.team.team_07_fe.ui.customer;

import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.navigation.fragment.NavHostFragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.team.team_07_fe.R;
import com.team.team_07_fe.adapter.CustomerAdapter;
import com.team.team_07_fe.adapter.EmployeeAdapter;
import com.team.team_07_fe.models.Customer;
import com.team.team_07_fe.models.Employee;
import com.team.team_07_fe.ui.employee.EmployeeManagerFragment;
import com.team.team_07_fe.ui.employee.EmployeeViewModel;

import java.io.Serializable;
import java.util.List;

public class CustomerManagerFragment extends Fragment {

    private CustomerViewModel mViewModel;
    private CustomerAdapter customerAdapter;
    private RecyclerView recyclerView;
    private EditText Search_customer;
    private FloatingActionButton fab;
    private CustomerViewModel customerViewModel;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }
    public static CustomerManagerFragment newInstance() {
        return new CustomerManagerFragment();
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_customer_manager, container, false);
        recyclerView=view.findViewById(R.id.recyclerView);
        mapping(view);
        //

        customerViewModel = new ViewModelProvider(requireActivity()).get(CustomerViewModel.class);

        return view;
    }
    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        initialAdapter();

    }
    private void mapping(View view){
        Search_customer = view.findViewById(R.id.Search_customer);
        recyclerView = view.findViewById(R.id.recyclerView);
        fab= view.findViewById(R.id.fab);
    }

    private void initialAdapter(){
        customerAdapter = new CustomerAdapter(requireContext(),customerViewModel.getCustomerList().getValue());
        recyclerView.setLayoutManager(new LinearLayoutManager(requireContext()));
        recyclerView.setAdapter(customerAdapter);


    }
    private void handleShowUpdatePasswordForm(int position) {

    }

    private void handleShowConfirmDisable(int position) {
    }
//    private void observeViewModel(){
//        customerViewModel.getCustomerList().observe(getViewLifecycleOwner(), new Observer<List<Customer>>() {
//            @Override
//            public void onChanged(List<Customer> customers) {
//                customerAdapter.setList(customers);
//                Toast.makeText(requireContext(), "Lấy dữ liệu thành công!", Toast.LENGTH_SHORT).show();
//            }
//        });
//    }










}