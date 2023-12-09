package com.team.team_07_fe.ui.contract;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.SearchView;
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
    private SearchView searchView;
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
        searchView = view.findViewById(R.id.search_view);
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
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {

            @Override
            public boolean onQueryTextSubmit(String query) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                searchContract(newText);
                return false;
            }
        });
        observeViewModel();


        fab.setOnClickListener(this::handleNavigateCreateForm);
        handleNavigateUpdateForm();
    }
    private void searchContract(String query) {
        if (query!=null && !query.isEmpty()) {
            contractViewModel.getAllContracts(query);
        } else {
            contractViewModel.getAllContracts(null);
        }
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
            contractViewModel.setErrorMessage(null);
        });
    }

    /**
     *
     */
    private void handleNavigateUpdateForm(){
        contractAdapter.setOnClickUpdateListener(position -> {
            Bundle bundle = new Bundle();
            bundle.putSerializable("data_contract",contractAdapter.getItem(position));

            NavHostFragment.findNavController(ContractManagerFragment.this).navigate(R.id.action_navigation_contract_to_navigation_contract_update,bundle);
        });
    }

    private void handleNavigateCreateForm(View view) {
        NavHostFragment.findNavController(ContractManagerFragment.this)
                .navigate(R.id.action_navigation_contract_to_navigation_contract_add);
    }
    @Override
    public void onDestroyView() {
        super.onDestroyView();
    }
}
