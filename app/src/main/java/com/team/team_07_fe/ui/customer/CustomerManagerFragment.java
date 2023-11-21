package com.team.team_07_fe.ui.customer;

import androidx.appcompat.widget.SearchView;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import android.app.AlertDialog;
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
import android.widget.Toast;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.team.team_07_fe.R;
import com.team.team_07_fe.adapter.CustomerAdapter;
import com.team.team_07_fe.models.Customer;
import com.team.team_07_fe.viewmodels.CustomerViewModel;

import java.util.List;


public class CustomerManagerFragment extends Fragment {
    private CustomerAdapter customerAdapter;
    private RecyclerView recyclerView;
    private SearchView searchView;
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
        recyclerView = view.findViewById(R.id.recyclerView);
        mapping(view);

        customerViewModel = new ViewModelProvider(requireActivity()).get(CustomerViewModel.class);

        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        initialAdapter();

        fab.setOnClickListener(this::handleNavigateCreateForm);
        observeViewModel();
//        observeViewModel();
    }

    private void mapping(View view) {
        searchView = view.findViewById(R.id.search_view);
        recyclerView = view.findViewById(R.id.recyclerView);
        fab = view.findViewById(R.id.fab);
    }


private void initialAdapter() {
    customerViewModel.getCustomerList().observe(getViewLifecycleOwner(), new Observer<List<Customer>>() {
        @Override
        public void onChanged(List<Customer> customer) {
            customerAdapter.setList(customer);
            Toast.makeText(requireContext(), "Lấy dữ liệu thành công!", Toast.LENGTH_SHORT).show();
        }
    });

    customerAdapter = new CustomerAdapter(requireContext(), customerViewModel.getCustomerList().getValue());
    recyclerView.setLayoutManager(new LinearLayoutManager(requireContext()));
    recyclerView.setAdapter(customerAdapter);

    customerAdapter.setOnClickUpdateCustomerClickListener(this::handleNavigateUpdateCustomerForm);
    customerAdapter.setOnClickDeleteCustomerClickListener(this::handleNavigateDeleteForm);
}

    private void handleNavigateCreateForm(View view) {
        NavHostFragment.findNavController(CustomerManagerFragment.this)
                .navigate(R.id.action_navigation_customer_to_navigation_customer_create);
    }

    private void handleNavigateUpdateCustomerForm(int position) {
        Customer customer = customerAdapter.getItem(position);
        Bundle bundle = new Bundle();
        bundle.putSerializable("data_customer", customer);
        NavHostFragment.findNavController(CustomerManagerFragment.this)
                .navigate(R.id.action_navigation_customer_to_navigation_customer_update, bundle);
    }

    private void handleNavigateDeleteForm(int position) {
            AlertDialog.Builder builder = new AlertDialog.Builder(requireContext())
                    .setTitle("Cảnh báo!")
                    .setMessage("Bạn có chắc muốn xóa khách hàng này không?")
                    .setPositiveButton(R.string.yes, (dialog, which) -> {
                        customerViewModel.DeleteCustomer(customerAdapter.getItem(position));
                        dialog.dismiss();
                    })
                    .setNegativeButton(R.string.no, ((dialog, which) -> {
                        dialog.dismiss();
                    }));
            builder.create().show();
    }

    private void observeViewModel() {
        customerViewModel.getCustomerList().observe(getViewLifecycleOwner(), new Observer<List<Customer>>() {
            @Override
            public void onChanged(List<Customer> customer) {
                customerAdapter.setList(customer);
                Toast.makeText(requireContext(), "Lấy dữ liệu thành công!", Toast.LENGTH_SHORT).show();
            }
        });
    }
}

