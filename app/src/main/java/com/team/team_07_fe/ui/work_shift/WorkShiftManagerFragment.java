package com.team.team_07_fe.ui.work_shift;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.fragment.NavHostFragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.team.team_07_fe.R;
import com.team.team_07_fe.adapter.WorkShiftAdapter;
import com.team.team_07_fe.models.WorkShift;

import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link WorkShiftManagerFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class WorkShiftManagerFragment extends Fragment {
    private WorkShiftViewModel workShiftViewModel;
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
        fab.setOnClickListener(this::handleNavigateCreateForm);
    }
    private void mapping(View view){

        recyclerViewW = view.findViewById(R.id.recyclerView_work_shift);
        fab = view.findViewById(R.id.fab);
    }
    private void initialAdapter(){
        if (workShiftViewModel != null) {
            // Your logic here
            workShiftAdapter = new WorkShiftAdapter(requireContext(),workShiftViewModel.getWorkShiftList().getValue());
        }
        recyclerViewW.setLayoutManager(new LinearLayoutManager(requireContext()));
        recyclerViewW.setAdapter(workShiftAdapter);
    }
    private void handleNavigateCreateForm(View view){
        NavHostFragment.findNavController(WorkShiftManagerFragment.this)
                .navigate(R.id.action_workShiftManagerFragment_to_workShiftCreateFragment);
    }


}