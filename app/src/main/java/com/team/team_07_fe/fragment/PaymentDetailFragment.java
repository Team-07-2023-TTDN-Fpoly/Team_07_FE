package com.team.team_07_fe.fragment;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.navigation.fragment.NavHostFragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.team.team_07_fe.R;
import com.team.team_07_fe.models.Employee;
import com.team.team_07_fe.models.Statistic;
import com.team.team_07_fe.utils.FormatHelper;

public class PaymentDetailFragment extends Fragment {
    private TextView tv_date_statistics,tv_total_revenuet,tv_total_expenditure,create_detail_input_name,
    text4,text5;
    private ImageView deletelist;
    private FloatingActionButton btn_create_detail;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_payment_detail, container, false);
        mapping(view);
        return view;

    }
    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        if(getArguments()!=null){
            Statistic data = (Statistic) getArguments().getSerializable("data_detail");
            setData(data);

        }
    }

    //gán data
    private void setData(Statistic statistic){
        tv_date_statistics.setText(FormatHelper.convertMonthtoString(statistic.getDt_date()));
        tv_total_revenuet.setText("Tiền thu từ hợp đồng: " +(statistic.getTotal_revenue()));
        tv_total_expenditure.setText("Tổng tiền chi: " + FormatHelper.convertPriceToString(statistic.getTotal_amount()));
        text4.setText(statistic.getDt_name());
        text5.setText(FormatHelper.convertPriceToString(statistic.getTotal_amount()));
    }
    private void mapping(View view){
        tv_date_statistics = view.findViewById(R.id.tv_date_statistics);
        tv_total_revenuet = view.findViewById(R.id.tv_total_revenuet);
        tv_total_expenditure = view.findViewById(R.id.tv_total_expenditure);
        btn_create_detail = view.findViewById(R.id.btn_create_detail);
        create_detail_input_name = view.findViewById(R.id.create_detail_input_name);
        text4 = view.findViewById(R.id.text4);
        text5 = view.findViewById(R.id.text5);
        deletelist = view.findViewById(R.id.deletelist);
    }
}