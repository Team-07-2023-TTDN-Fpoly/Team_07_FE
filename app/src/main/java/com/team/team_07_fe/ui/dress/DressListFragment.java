package com.team.team_07_fe.ui.dress;

import android.app.AlertDialog;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.SearchView;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.fragment.NavHostFragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.team.team_07_fe.R;
import com.team.team_07_fe.adapter.DressAdapter;
import com.team.team_07_fe.models.Customer;
import com.team.team_07_fe.models.Dress;
import com.team.team_07_fe.viewmodels.DressViewModel;

import java.util.ArrayList;
import java.util.List;

public class DressListFragment extends Fragment {
    private RecyclerView recyclerView;
    private SearchView searchView;
    private FloatingActionButton fab;
    TextView txtDel;
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
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {

            @Override
            public boolean onQueryTextSubmit(String query) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                searchDress(newText);
                return false;
            }
        });


        observeViewModel();
        handleClick();
    }
    private void searchDress(String query) {
        if (query!=null && !query.isEmpty()) {
            dressViewModel.getAllDress(query);
        } else {
            dressViewModel.getAllDress(null);
        }
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
            Bundle bundle = new Bundle();
            bundle.putSerializable("data_dress",dressAdapter.getItem(position));
            NavHostFragment.findNavController(DressListFragment.this).navigate(R.id.action_navigation_dress_to_dressUpdateFragment,bundle);
        });
        dressAdapter.setOnClickDeleteClickListener(this::handleNavigateDeleteForm);
    }

    private void handleNavigateDeleteForm(int position) {
        AlertDialog.Builder builder = new AlertDialog.Builder(requireContext())
                .setTitle("Cảnh báo!")
                .setMessage("Bạn có chắc muốn xóa áo cưới này không?")
                .setPositiveButton(R.string.yes, (dialog, which) -> {
                    Dress dress = dressAdapter.getItem(position);
                    String dressId = dress.getId();
                    dressViewModel.deleteDress(dressId);
                    dialog.dismiss();
                })
                .setNegativeButton(R.string.no, ((dialog, which) -> {
                    dialog.dismiss();
                }));
        builder.create().show();
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
        dressViewModel.getDataMessage().observe(getViewLifecycleOwner(),s -> {
            if(s!=null){
                Toast.makeText(requireContext(), s, Toast.LENGTH_SHORT).show();
                dressViewModel.getAllDress(null);
                dressViewModel.setDataMessage(null);
            }
        });
    }
    /**
     * mapping dữ liệu
     * @param view
     */
    private void mapping(View view){
        searchView = view.findViewById(R.id.search_view);
        recyclerView = view.findViewById(R.id.danh_sach_ao_cuoi);
        fab = view.findViewById(R.id.button_more);
        txtDel = view.findViewById(R.id.btn_delete_item);
        dressList = new ArrayList<>();
        recyclerView.setLayoutManager(new LinearLayoutManager(requireContext()));
        dressAdapter = new DressAdapter(requireContext(),dressList);
        recyclerView.setAdapter(dressAdapter);
    }
}