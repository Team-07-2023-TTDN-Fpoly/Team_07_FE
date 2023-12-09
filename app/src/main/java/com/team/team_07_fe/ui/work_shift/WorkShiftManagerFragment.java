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

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.team.team_07_fe.MainActivity;
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
    private WorkShiftAdapter workShiftAdapter;
    private RecyclerView recyclerViewW;
    private FloatingActionButton fab;
    private List<WorkShift> workShiftList;

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
        workShiftList = new ArrayList<>();

        workShiftAdapter = new WorkShiftAdapter(requireContext(),workShiftList);
        recyclerViewW.setLayoutManager(new LinearLayoutManager(requireContext()));
        recyclerViewW.setAdapter(workShiftAdapter);
        return view;
    }
    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        workShiftViewModel.getAllWorkShift(null);

        observeViewModel();
        initialAdapter();

        fab.setOnClickListener(this::handleNavigateCreateForm);
    }
    private void mapping(View view){

        recyclerViewW = view.findViewById(R.id.recyclerView_work_shift);
        fab = view.findViewById(R.id.fab);
    }
    private void initialAdapter() {

        workShiftAdapter.setOnClickDeleteClickListener(this::handleNavigateDeleteForm);
        workShiftAdapter.onClickUpdateWorkShiftClickListener(this::WorkShifthandleNavigateUpdateForm);
    }

    private void WorkShifthandleNavigateUpdateForm(int position) {
        WorkShift workShift = workShiftAdapter.getItem(position);
        Bundle bundle = new Bundle();
        bundle.putSerializable("data_workShift", workShift);
        NavHostFragment.findNavController(WorkShiftManagerFragment.this)
                .navigate(R.id.action_workShiftManagerFragment_to_workShiftUpdateFragment, bundle);
    }
    private void handleNavigateDeleteForm(int position) {
        WorkShift workShift = workShiftAdapter.getItem(position);
        Bundle bundle = new Bundle();
        bundle.putSerializable("data_workShift", workShift);
        AlertDialog.Builder builder = new AlertDialog.Builder(requireContext())
                .setTitle("Cảnh báo!")
                .setMessage("Bạn có chắc muốn xóa ca làm này không?")
                .setPositiveButton(R.string.yes, (dialog, which) -> {
                    // Gọi phương thức xóa khách hàng với ID tương ứng
                    Toast.makeText(requireContext(), "Xóa thành công!", Toast.LENGTH_SHORT).show();
                    String  workShiftId= workShift.getShift_id(); // Lấy ID
                    workShiftViewModel.deleteWorkShift(workShiftId);// Gọi phương thức xóa  ID
                    workShiftViewModel.getAllWorkShift(null);
                    dialog.dismiss();
                })
                .setNegativeButton(R.string.no, ((dialog, which) -> {
                    dialog.dismiss();
                }));
        builder.create().show();
    }
    private void handleNavigateCreateForm(View view){
        NavHostFragment.findNavController(WorkShiftManagerFragment.this)
                .navigate(R.id.action_workShiftManagerFragment_to_workShiftCreateFragment);
    }
    private void observeViewModel() {
        workShiftViewModel.getWorkShiftList().observe(getViewLifecycleOwner(), new Observer<List<WorkShift>>() {
            @Override
            public void onChanged(List<WorkShift> workShifts) {
                if(workShifts!=null){
                    workShiftList.clear();
                    workShiftList.addAll(workShifts);
                    workShiftAdapter.setList(workShiftList);
                    workShiftAdapter.notifyDataSetChanged();
                    Toast.makeText(requireContext(), "Lấy dữ liệu thành công!", Toast.LENGTH_SHORT).show();
                }

            }
        });
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