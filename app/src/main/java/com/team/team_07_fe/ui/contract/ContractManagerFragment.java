package com.team.team_07_fe.ui.contract;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.fragment.NavHostFragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.team.team_07_fe.R;
import com.team.team_07_fe.adapter.ContractAdapter;
import com.team.team_07_fe.models.Contract;
import com.team.team_07_fe.viewmodels.ContractViewModel;

import java.util.ArrayList;
import java.util.List;

public class ContractManagerFragment extends Fragment {
    private RecyclerView recyclerView;

    private FloatingActionButton fab;
    private ContractViewModel contractViewModel;
    private ContractAdapter contractAdapter;
    private List<Contract> listContract;
    @SuppressLint("LocalSuppress")
    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_contract_manager, container, false);
        mapping(view);


        // Khởi tạo ViewModel
        contractViewModel = new ViewModelProvider(requireActivity()).get(ContractViewModel.class);
        listContract = new ArrayList<>();


        return view;
    }

    private void mapping(View view) {
        recyclerView = view.findViewById(R.id.recyclerView1);
         fab = view.findViewById(R.id.fab);

        // Khởi tạo RecyclerView
        recyclerView.setLayoutManager(new LinearLayoutManager(requireContext()));
        // Khởi tạo Adapter và gán nó cho RecyclerView
        contractAdapter = new ContractAdapter(requireContext(),listContract);
        recyclerView.setAdapter(contractAdapter);
    }
    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        contractViewModel.getAllContracts(null);
        observeViewModel();

        fab.setOnClickListener(this::handleNavigateCreateForm);
    }


    private void observeViewModel() {
        // Lấy danh sách hợp đồng từ ViewModel và đặt vào Adapter
        contractViewModel.getListContract().observe(getViewLifecycleOwner(), contracts -> {
            if(contracts!=null){
                listContract.clear();
                listContract.addAll(contracts);
                contractAdapter.setData(listContract);
            }
        });
        contractViewModel.getErrorMessage().observe(getViewLifecycleOwner(),s -> {
            if(s!=null){
                Toast.makeText(requireContext(), s, Toast.LENGTH_SHORT).show();
            }
        });
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
