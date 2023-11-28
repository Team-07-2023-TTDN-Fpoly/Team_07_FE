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
import com.team.team_07_fe.utils.OnClickListener;

import java.util.List;

public class DetailStatisticsAdapter extends RecyclerView.Adapter<DetailStatisticsAdapter.ViewHolder>{
    private List<DetailStatistics> list;
    private Context context;
    private static OnClickListener onClickUpdateDetailClickListener;
    public DetailStatisticsAdapter(Context context, List<DetailStatistics> list){
        this.list = list;
        this.context = context;
    }
    public void setList(List<DetailStatistics> list) {
        this.list = list;
        notifyDataSetChanged();
    }
    public void setOnClickUpdateDetailClickListener(OnClickListener onClickUpdateDetailClickListener) {
        DetailStatisticsAdapter.
                onClickUpdateDetailClickListener = onClickUpdateDetailClickListener;
    }
    @NonNull
    @Override
    public DetailStatisticsAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(context).inflate(R.layout.layout_item_statistics,parent,false);
        return new ViewHolder(view);
    }

    @Override
    public int getItemCount() {
        return 0;
    }
    @Override
    public void onBindViewHolder(@NonNull DetailStatisticsAdapter.ViewHolder holder, int position) {
        DetailStatistics item=list.get(position);
        if (item !=null){

            holder.tv_date_statistics.setText((CharSequence) item.getDt_date());
            holder.tv_total_revenue.setText("Tổng thu: "+item);
            holder.tv_total_expenditure.setText("Tổng chi: "+item.getDt_money());

            holder.itemView.setOnClickListener(v->{
                if(onClickUpdateDetailClickListener!=null){
                    onClickUpdateDetailClickListener.onClick(position);
                }
            });


        }
    }

    public DetailStatistics getItem(int position){
        return list.get(position);
    }
    public class ViewHolder extends RecyclerView.ViewHolder{

        private TextView tv_date_statistics, tv_total_revenue,tv_total_expenditure;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            tv_date_statistics = itemView.findViewById(R.id.tv_date_statistics);
            tv_total_revenue = itemView.findViewById(R.id.tv_total_revenue);
            tv_total_expenditure = itemView.findViewById(R.id.tv_total_expenditure);

        }
    }


}
