package com.team.team_07_fe.ui.contract;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.fragment.NavHostFragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.team.team_07_fe.R;
import com.team.team_07_fe.adapter.ContractAdapter;
import com.team.team_07_fe.ui.customer.CustomerManagerFragment;

public class ContractManagerFragment extends Fragment {
    private RecyclerView recyclerView;

    private FloatingActionButton fab;
    private ContractViewModel contractViewModel;

    @SuppressLint("LocalSuppress")
    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_contract_manager, container, false);
        mapping(view);
        // Khởi tạo RecyclerView
        RecyclerView recyclerView = view.findViewById(R.id.recyclerView1);
        recyclerView.setLayoutManager(new LinearLayoutManager(requireContext()));

        // Khởi tạo Adapter và gán nó cho RecyclerView
        ContractAdapter contractAdapter = new ContractAdapter(requireContext());
        contractAdapter.setItemLayout(R.layout.layout_item_contract);
        recyclerView.setAdapter(contractAdapter);

        // Khởi tạo ViewModel
        contractViewModel = new ViewModelProvider(this).get(ContractViewModel.class);

        // Lấy danh sách hợp đồng từ ViewModel và đặt vào Adapter
        contractViewModel.getContracts().observe(getViewLifecycleOwner(), contracts -> {
            contractAdapter.setContracts(contracts);
        });

        return view;
    }

    private void mapping(View view) {
        recyclerView = view.findViewById(R.id.recyclerView);
         fab = view.findViewById(R.id.fab);
    }
    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        initialAdapter();

        fab.setOnClickListener(this::handleNavigateCreateForm);
        observeViewModel();
//        observeViewModel();
    }

    private void initialAdapter() {
    }

    private void observeViewModel() {
    }



    private void handleNavigateCreateForm(View view) {
        NavHostFragment.findNavController(ContractManagerFragment.this)
                .navigate(R.id.action_navigation_contract_to_navigation_customer_add);
    }
    @Override
    public void onDestroyView() {
        super.onDestroyView();
    }
}
