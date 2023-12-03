package com.team.team_07_fe.ui.dress;

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
import com.team.team_07_fe.adapter.DressAdapter;
import com.team.team_07_fe.models.Dress;
import com.team.team_07_fe.viewmodels.DressViewModel;

import java.util.ArrayList;
import java.util.List;

public class DressListFragment extends Fragment {
    private RecyclerView recyclerView;
    private FloatingActionButton fab;
    private DressViewModel dressViewModel;
    private DressAdapter dressAdapter;
    private List<Dress> dressList;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_dress_list, container, false);
        mapping(view);
        dressViewModel = new ViewModelProvider(DressListFragment.this).get(DressViewModel.class);
        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        dressViewModel.getAllDress(null);



        observeViewModel();
        handleClick();
    }

    /**
     * Xử lý sự kiện các nút nhấn
     */
    private void handleClick(){
        //Chuyển sang trang thêm mới áo cưới
        fab.setOnClickListener(v->{
            NavHostFragment.findNavController(DressListFragment.this).navigate(R.id.action_navigation_dress_to_dressAddFragment);
        });
        //Sự kiện nhấn của các item trong adapter
        dressAdapter.setOnClickUpdateClickListener(position -> {

        });
        dressAdapter.setOnClickDeleteClickListener(position -> {

        });
    }
    /**
     * kiểm soát dữ liệu
     */
    private void observeViewModel(){
        dressViewModel.getListDress().observe(getViewLifecycleOwner(),dresses -> {
            if(dresses!=null){
                dressList.clear();
                dressList.addAll(dresses);
                dressAdapter.setListDress(dressList);
            }
        });
    }
    /**
     * mapping dữ liệu
     * @param view
     */
    private void mapping(View view){
        recyclerView = view.findViewById(R.id.danh_sach_ao_cuoi);
        fab = view.findViewById(R.id.button_more);

        dressList = new ArrayList<>();
        recyclerView.setLayoutManager(new LinearLayoutManager(requireContext()));
        dressAdapter = new DressAdapter(requireContext(),dressList);
        recyclerView.setAdapter(dressAdapter);
    }
}