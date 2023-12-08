package com.team.team_07_fe.fragment;

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
import com.team.team_07_fe.MainActivity;
import com.team.team_07_fe.R;
import com.team.team_07_fe.adapter.CustomerAdapter;
import com.team.team_07_fe.adapter.DetailStatisticsAdapter;
import com.team.team_07_fe.api.service.DetailStatisticsService;
import com.team.team_07_fe.models.DetailStatistics;
import com.team.team_07_fe.models.Statistic;
import com.team.team_07_fe.viewmodels.DetailStatisticsViewModel;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class StatisticScreenFragment extends Fragment {


    private DetailStatisticsViewModel detailStatisticsViewModel;

    private DetailStatisticsAdapter detailStatisticsAdapter;
    private FloatingActionButton btn_create_detail;

    private RecyclerView recyclerView;


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_statistic_screen, container, false);
        recyclerView = view.findViewById(R.id.recyclerView);
        mapping(view);
        detailStatisticsViewModel = new ViewModelProvider(requireActivity()).get(DetailStatisticsViewModel.class);
        return view;


    }
    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        detailStatisticsViewModel.getAllDetail();

        initialAdapterDetail();
        observeViewModel();

        btn_create_detail.setOnClickListener(this::handlecreateForm);
    }

    private void initialAdapterDetail() {
        detailStatisticsAdapter = new DetailStatisticsAdapter(requireContext(), new ArrayList<>());
        recyclerView.setLayoutManager(new LinearLayoutManager(requireContext()));
        recyclerView.setAdapter(detailStatisticsAdapter);

        detailStatisticsAdapter.setOnClickUpdateDetailClickListener(this::handleNavigateUpdateDetailForm);

    }
    public void mapping(View view){
        btn_create_detail = view.findViewById(R.id.btn_create_detail);
        recyclerView = view.findViewById(R.id.recyclerView);
//
    }
    private void handleNavigateUpdateDetailForm(int position) {
        Statistic detailStatistics = detailStatisticsAdapter.getItem(position);
        Bundle bundle = new Bundle();
        bundle.putSerializable("data_detail", detailStatistics);
        NavHostFragment.findNavController(StatisticScreenFragment.this)
                .navigate(R.id.action_StatisticScreen_to_UpdateDetail, bundle);
    }
    private void handlecreateForm(View view){
        NavHostFragment.findNavController(StatisticScreenFragment.this)
                .navigate(R.id.action_StatisticScreen_to_createDetailFragment);
    }
    private void observeViewModel() {
        detailStatisticsViewModel.getListDt().observe(getViewLifecycleOwner(), new Observer<List<Statistic>>() {
            @Override
            public void onChanged(List<Statistic> detailStatistics) {
                detailStatisticsAdapter.setListDetail(detailStatistics);
            }
        });
        detailStatisticsViewModel.getErrorMessage().observe(getViewLifecycleOwner(),s -> {
            if(s!=null){
                Toast.makeText(requireContext(), s, Toast.LENGTH_SHORT).show();
                detailStatisticsViewModel.setErrorMessage(null);
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