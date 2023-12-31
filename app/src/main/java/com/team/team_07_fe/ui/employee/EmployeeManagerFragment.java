package com.team.team_07_fe.ui.employee;

import android.app.AlertDialog;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.SearchView;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.fragment.NavHostFragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.team.team_07_fe.MainActivity;
import com.team.team_07_fe.R;
import com.team.team_07_fe.adapter.EmployeeAdapter;
import com.team.team_07_fe.dialog.AdminChangePasswordDialog;
import com.team.team_07_fe.models.Employee;
import com.team.team_07_fe.viewmodels.AuthViewModel;
import com.team.team_07_fe.viewmodels.EmployeeViewModel;

import java.util.ArrayList;
import java.util.List;

//Người tạo: NghiaTC
public class EmployeeManagerFragment extends Fragment {
    private EmployeeViewModel employeeViewModel;
    private AuthViewModel authViewModel;
    private EmployeeAdapter employeeAdapter;
    private SearchView searchView;
    private RecyclerView recyclerView;
    private FloatingActionButton fab;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_employee_manager, container, false);
        //
        mapping(view);
        //
        employeeViewModel = new ViewModelProvider(requireActivity()).get(EmployeeViewModel.class);
        authViewModel = new ViewModelProvider(requireActivity()).get(AuthViewModel.class);
        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        initialAdapter();
        employeeViewModel.getAllEmployee(null);
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {

                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                searchEmployees(newText);
                return false;
            }
        });
        observeViewModel();
        fab.setOnClickListener(this::handleNavigateCreateForm);
    }

    private void searchEmployees(String query) {
        if(query!=null && !query.isEmpty()){
            employeeViewModel.getAllEmployee(query);
        }else{
            employeeViewModel.getAllEmployee(null);
        }
    }

    private void mapping(View view){
        searchView = view.findViewById(R.id.search_view);
        recyclerView = view.findViewById(R.id.recyclerView);
        fab = view.findViewById(R.id.fab);
    }

    private void initialAdapter(){
        employeeAdapter = new EmployeeAdapter(requireContext(),new ArrayList<>());
        recyclerView.setLayoutManager(new LinearLayoutManager(requireContext()));
        recyclerView.setAdapter(employeeAdapter);

        employeeAdapter.setOnClickUpdateClickListener(this::handleNavigateUpdateForm);
        employeeAdapter.setOnClickChangePasswordClickListener(this::handleShowUpdatePasswordForm);
        employeeAdapter.setOnClickDisableClickListener(this::handleShowConfirmDisable);
    }
    private void handleNavigateCreateForm(View view){
        NavHostFragment.findNavController(EmployeeManagerFragment.this)
                .navigate(R.id.action_navigation_employee_manager_to_navigation_employee_create);
    }
    private void handleNavigateUpdateForm(int position) {
        Employee employee = employeeAdapter.getItem(position);
        Bundle bundle = new Bundle();
        bundle.putSerializable("data_employee",employee);
        NavHostFragment.findNavController(EmployeeManagerFragment.this)
                .navigate(R.id.action_navigation_employee_manager_to_navigation_employee_update,bundle);
    }

    private void handleShowUpdatePasswordForm(int position) {
        AdminChangePasswordDialog dialog =
                new AdminChangePasswordDialog(requireContext(),EmployeeManagerFragment.this,authViewModel,employeeAdapter.getItem(position).getAuth_id());
        dialog.show();
    }

    private void handleShowConfirmDisable(int position) {
        AlertDialog.Builder builder = new AlertDialog.Builder(requireContext())
                .setCancelable(false);
        Employee employee = employeeAdapter.getItem(position);
        String title = employee.isIs_disable() ? "Mở khóa tài khoản" : "Vô hiệu hóa tài khoản";
        builder.setTitle(title).setMessage("Bạn có chắc chắn với lựa chọn của mình?")
                .setPositiveButton(R.string.yes,(dialog,which)->{
                    authViewModel.disableAccount(employee.getAuth_id(),!employee.isIs_disable());
                })
                .setNegativeButton(R.string.no,(dialog,which)->{
                    dialog.dismiss();
                });
        builder.create().show();
    }


    private void observeViewModel(){
        employeeViewModel.getEmployeeList().observe(getViewLifecycleOwner(), new Observer<List<Employee>>() {
            @Override
            public void onChanged(List<Employee> employees) {
                employeeAdapter.setList(employees);
            }
        });
        employeeViewModel.getErrorMessage().observe(getViewLifecycleOwner(),s -> {
            if(s!=null){
                Toast.makeText(requireContext(), s, Toast.LENGTH_SHORT).show();
                employeeViewModel.setErrorMessage(null);
            }
        });
        authViewModel.getDataMessage().observe(getViewLifecycleOwner(),s->{
            if(s!=null){
                Toast.makeText(requireContext(), s, Toast.LENGTH_SHORT).show();
                employeeViewModel.getAllEmployee(null);
                authViewModel.setDataMessage(null);
            }
        });
        authViewModel.getErrorMessage().observe(getViewLifecycleOwner(),s->{
            if(s!=null){
                Toast.makeText(requireContext(), s, Toast.LENGTH_SHORT).show();
                authViewModel.setErrorMessage(null);
            }
        });
    }

    @Override
    public void onResume() {
        super.onResume();
        ((MainActivity) requireActivity()).hiddenBottomBar();
    }

    @Override
    public void onStop() {
        super.onStop();
        ((MainActivity) requireActivity()).showBottomBar();

    }
}