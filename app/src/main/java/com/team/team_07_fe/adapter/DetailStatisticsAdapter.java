package com.team.team_07_fe.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.team.team_07_fe.R;
import com.team.team_07_fe.models.DetailStatistics;
import com.team.team_07_fe.models.Dress;
import com.team.team_07_fe.utils.OnClickListener;

import java.util.List;

public class DetailStatisticsAdapter extends RecyclerView.Adapter<DetailStatisticsAdapter.ViewHolder>{


private List<DetailStatistics> listDetail;
    private Context context;

    private static OnClickListener onClickUpdateClickListener;
    private static OnClickListener onClickAddClickListener;
    private static OnClickListener onClickDeleteClickListener;
    public DetailStatistics getItem(int position){
        return listDetail.get(position);
    }
    public DetailStatisticsAdapter(Context context, List<DetailStatistics> listDetail){
        this.listDetail = listDetail;
        this.context = context;
    }
    public void setOnClickUpdateDetailClickListener(OnClickListener onClickUpdateDetailClickListener) {
        DetailStatisticsAdapter.
                onClickUpdateClickListener = onClickUpdateDetailClickListener;
    }
    public void setListDetail(List<DetailStatistics>list){
        this.listDetail=listDetail;
        notifyDataSetChanged();
    }

    public static void setOnClickAddClickListener(OnClickListener onClickAddClickListener) {
        DetailStatisticsAdapter.onClickAddClickListener = onClickAddClickListener;
    }

    public static void setOnClickDeleteClickListener(OnClickListener onClickDeleteClickListener) {
       DetailStatisticsAdapter.onClickDeleteClickListener = onClickDeleteClickListener;
    }
    public static void setOnClickUpdateClickListener(OnClickListener onClickUpdateClickListener) {
       DetailStatisticsAdapter.onClickUpdateClickListener = onClickUpdateClickListener;
    }

    @NonNull
    @Override
    public DetailStatisticsAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.fragment_statistic_screen,parent,false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull DetailStatisticsAdapter.ViewHolder holder, int position) {
        DetailStatistics itemDetail = listDetail.get(position);
        if(itemDetail!=null) {

            holder.tv_date.setText((CharSequence) itemDetail.getDt_date());;
            holder.tv_revenue.setText("Tổng thu: ");
            holder.tv_expenditure.setText("Tổng chi"+itemDetail.getDt_money());


            holder.btn_update.setOnClickListener(v->{
                if(onClickUpdateClickListener!=null){
                    onClickUpdateClickListener.onClick(position);
                }
            });

        }

    }

    @Override
    public int getItemCount() {
        return listDetail!=null?listDetail.size():0;
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        private TextView  tv_date, tv_revenue, tv_expenditure, btn_update;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            tv_date = itemView.findViewById(R.id.tv_date_statistics);
            tv_revenue = itemView.findViewById(R.id.tv_total_revenue);
            tv_expenditure = itemView.findViewById(R.id.tv_total_expenditure);
            btn_update = itemView.findViewById(R.id.btnupdateDetailStatistic);


        }
    }

}
