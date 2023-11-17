package com.team.team_07_fe.ui.home;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.GridView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.team.team_07_fe.R;
import com.team.team_07_fe.adapter.ContractAdapter;
import com.team.team_07_fe.models.Contract;

import java.util.ArrayList;
import java.util.List;

public class HomeFragment extends Fragment {
    private HomeViewModel homeViewModel;
    private RecyclerView rcvContract;
    private ContractAdapter contractAdapter;

    String[] tvItem = {
            "Quản lý loại áo cưới",
            "Quản lý nhân viên",
            "Thông tin nhân viên",
            "Thống kê",
            "Đổi mật khẩu"
    };
    int[] imgItem = {
            R.drawable.photography,
            R.drawable.team,
            R.drawable.bill,
            R.drawable.analytics,
            R.drawable.lock_reset
    };
    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_home, container, false);
        homeViewModel =
                new ViewModelProvider(this).get(HomeViewModel.class);

        GridView gridView = view.findViewById(R.id.gridView);
        rcvContract = view.findViewById(R.id.rcvContract);
        contractAdapter = new ContractAdapter(getContext());

        GridItemAdapter gridItemAdapter = new GridItemAdapter(getContext(), tvItem, imgItem);
        gridView.setAdapter(gridItemAdapter);

        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getContext(), RecyclerView.VERTICAL, false);
        rcvContract.setLayoutManager(linearLayoutManager);
        contractAdapter.setData(getListContract());
        rcvContract.setAdapter(contractAdapter);


        return view;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
    }

    public List<Contract> getListContract(){
        List<Contract> list = new ArrayList<>();
        list.add(new Contract("Lê Nguyên Sáng", "20/10/2023", "0899202665","3.000.000 VND", "14.000.000 VND", "Chưa thanh toán"));
        list.add(new Contract("Nguyễn Văn Hùng", "20/10/2023", "0899202665","5.000.000 VND", "20.000.000 VND", "Đã thanh toán"));
        list.add(new Contract("Trương Công Nghĩa", "20/10/2023", "0899202665","7.000.000 VND", "20.000.000 VND", "Chưa thanh toán"));
        list.add(new Contract("Bùi Ngọc Nguyên Vũ", "20/10/2023", "0899202665","14.000.000 VND", "30.000.000 VND", "Chưa thanh toán"));
        list.add(new Contract("Nguyễn Thanh Hoàng", "20/10/2023", "0899202665","28.000.000 VND", "30.000.000 VND", "Đã thanh toán"));
        return list;
    }
}