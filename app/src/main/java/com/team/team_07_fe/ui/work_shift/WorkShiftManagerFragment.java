package com.team.team_07_fe.ui.work_shift;

import android.app.AlertDialog;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
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
import com.team.team_07_fe.R;
import com.team.team_07_fe.adapter.CustomerAdapter;
import com.team.team_07_fe.adapter.WorkShiftAdapter;
import com.team.team_07_fe.models.Customer;
import com.team.team_07_fe.models.DressType;
import com.team.team_07_fe.models.Employee;
import com.team.team_07_fe.models.WorkShift;
import com.team.team_07_fe.ui.customer.CustomerManagerFragment;
import com.team.team_07_fe.viewmodels.AuthViewModel;

import java.util.ArrayList;
import java.util.List;


public class WorkShiftManagerFragment extends Fragment {
    private WorkShiftViewModel workShiftViewModel;
    private AuthViewModel authViewModel;
    private WorkShiftAdapter workShiftAdapter;
    private RecyclerView recyclerViewW;
    private FloatingActionButton fab;

    private List<WorkShift> listW;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_work_shift_manager, container, false);
        mapping(view);
        workShiftViewModel = new ViewModelProvider(requireActivity()).get(WorkShiftViewModel.class);


        return view;
    }
    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        initialAdapter();
        workShiftViewModel.getAllWorkShift(null);
        observeViewModel();
        fab.setOnClickListener(this::handleNavigateCreateForm);
    }
    private void mapping(View view){

        recyclerViewW = view.findViewById(R.id.recyclerView_work_shift);
        fab = view.findViewById(R.id.fab);
    }
    private void initialAdapter() {
//        if (workShiftViewModel != null) {
//            // Initialize the adapter with an empty list or initial data
//            workShiftAdapter = new WorkShiftAdapter(requireContext(), new ArrayList<>());
//
//            // Set the adapter to the RecyclerView
//            recyclerViewW.setLayoutManager(new LinearLayoutManager(requireContext()));
//            recyclerViewW.setAdapter(workShiftAdapter);
//        }

        workShiftAdapter = new WorkShiftAdapter(requireContext(), new ArrayList<>());
        recyclerViewW.setLayoutManager(new LinearLayoutManager(requireContext()));
        recyclerViewW.setAdapter(workShiftAdapter);
        workShiftAdapter.setOnClickDeleteClickListener(this::handleShowConfirmDelete);
        workShiftAdapter.onClickUpdateWorkShiftClickListener(this::WorkShifthandleNavigateUpdateForm);
    }

    private void WorkShifthandleNavigateUpdateForm(int position) {
        WorkShift workShift = workShiftAdapter.getItem(position);
        Bundle bundle = new Bundle();
        bundle.putSerializable("data_workShift", workShift);
        NavHostFragment.findNavController(WorkShiftManagerFragment.this)
                .navigate(R.id.action_workShiftManagerFragment_to_workShiftUpdateFragment, bundle);
    }
    private void handleShowConfirmDelete(int position) {
        WorkShift workShift = workShiftAdapter.getItem(position);

        AlertDialog.Builder builder = new AlertDialog.Builder(requireContext());
        builder.setTitle("Xác nhận xóa");
        builder.setMessage("Bạn có chắc muốn xóa ca làm này?");
        //Tại sao chỗ này lại là xóa loại áo cưới
        builder.setPositiveButton("Xóa", (dialog, which) -> {
            workShiftViewModel.deleteWorkShift(workShift.getShift_id());

            dialog.dismiss();
        });

        builder.setNegativeButton("Hủy bỏ", (dialog, which) -> {
            dialog.dismiss();
        });

        AlertDialog alertDialog = builder.create();
        alertDialog.setCanceledOnTouchOutside(false);

        alertDialog.show();
    }
    private void handleNavigateCreateForm(View view){
        NavHostFragment.findNavController(WorkShiftManagerFragment.this)
                .navigate(R.id.action_workShiftManagerFragment_to_workShiftCreateFragment);
    }
    private void observeViewModel() {
        workShiftViewModel.getWorkShiftList().observe(getViewLifecycleOwner(), new Observer<List<WorkShift>>() {
            @Override
            public void onChanged(List<WorkShift> workShifts) {
                workShiftAdapter.setList(workShifts);
                Toast.makeText(requireContext(), "Lấy dữ liệu thành công!", Toast.LENGTH_SHORT).show();
            }
        });
    }


}