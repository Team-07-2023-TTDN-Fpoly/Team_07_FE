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
import com.team.team_07_fe.R;
import com.team.team_07_fe.adapter.DetailStatisticsAdapter;
import com.team.team_07_fe.models.DetailStatistics;
import com.team.team_07_fe.viewmodels.DetailStatisticsViewModel;

import java.util.List;

public class StatisticScreenFragment extends Fragment {


    private DetailStatisticsViewModel detailStatisticsViewModel = new ViewModelProvider(requireActivity()).get(DetailStatisticsViewModel.class);

    private DetailStatisticsAdapter detailStatisticsAdapter;
    private FloatingActionButton btn_create_detail;

    private RecyclerView recyclerView;


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
        initialAdapterDetail();
        observeViewModel();
        initialAdapter();
        btn_create_detail.setOnClickListener(this::handlecreateForm);

    }

    private void initialAdapterDetail() {
        detailStatisticsViewModel.getListDetailStatistics().observe(getViewLifecycleOwner(), new Observer<List<DetailStatistics>>() {
            @Override
            public void onChanged(List<DetailStatistics> detailStatistics) {
                detailStatisticsAdapter.setList(detailStatistics);
                Toast.makeText(requireContext(), "Lấy dữ liệu thành công!", Toast.LENGTH_SHORT).show();
            }
        });
        detailStatisticsAdapter = new DetailStatisticsAdapter(requireContext(), detailStatisticsViewModel.getListDetailStatistics().getValue());
        recyclerView.setLayoutManager(new LinearLayoutManager(requireContext()));
        recyclerView.setAdapter(detailStatisticsAdapter);

        detailStatisticsAdapter.setOnClickUpdateDetailClickListener(this::handleNavigateUpdateDetailForm);

    }
    private void handleNavigateCreateForm(View view) {
        NavHostFragment.findNavController(StatisticScreenFragment.this)
                .navigate(R.id.action_StatisticScreen_to_createDetailFragment);
    }

    public void mapping(View view){
        btn_create_detail = view.findViewById(R.id.btn_create_detail);
        recyclerView = view.findViewById(R.id.recyclerView);
//
    }
    private void initialAdapter() {
        detailStatisticsViewModel.getListDetailStatistics().observe(getViewLifecycleOwner(), new Observer<List<DetailStatistics>>() {
            @Override
            public void onChanged(List<DetailStatistics> detailStatistics) {
                detailStatisticsAdapter.setList(detailStatistics);
                Toast.makeText(requireContext(), "Lấy dữ liệu thành công!", Toast.LENGTH_SHORT).show();
            }
        });

        detailStatisticsAdapter = new DetailStatisticsAdapter(requireContext(), detailStatisticsViewModel.getListDetailStatistics().getValue());
        recyclerView.setLayoutManager(new LinearLayoutManager(requireContext()));
        recyclerView.setAdapter(detailStatisticsAdapter);

        detailStatisticsAdapter.setOnClickUpdateDetailClickListener(this::handleNavigateUpdateDetailForm);

    }
    private void handleNavigateUpdateDetailForm(int position) {
        DetailStatistics detailStatistics = detailStatisticsAdapter.getItem(position);
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
        detailStatisticsViewModel.getListDetailStatistics().observe(getViewLifecycleOwner(), new Observer<List<DetailStatistics>>() {
            @Override
            public void onChanged(List<DetailStatistics> detailStatistics) {
                detailStatisticsAdapter.setList(detailStatistics);
                Toast.makeText(requireContext(), "Lấy dữ liệu thành công!", Toast.LENGTH_SHORT).show();
            }
        });
    }
}