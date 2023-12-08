package com.team.team_07_fe.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.team.team_07_fe.R;
import com.team.team_07_fe.models.DetailStatistics;
import com.team.team_07_fe.models.Statistic;
import com.team.team_07_fe.utils.FormatHelper;
import com.team.team_07_fe.utils.OnClickListener;

import java.util.List;

public class DetailStatisticsAdapter extends RecyclerView.Adapter<DetailStatisticsAdapter.ViewHolder>{

private List<Statistic> listtotal;
    private Context context;

    private static OnClickListener onClickUpdateClickListener;


    public DetailStatisticsAdapter(Context context, List<Statistic> listDetail){
        this.listtotal = listDetail;
        this.context = context;
    }
    public void setListDetail(List<Statistic>listDetail){
        this.listtotal=listDetail;
        notifyDataSetChanged();
    }
    public Statistic getItem(int position){
        return listtotal.get(position);
    }

    public void setOnClickUpdateDetailClickListener(OnClickListener onClickUpdateDetailClickListener) {
        DetailStatisticsAdapter.
                onClickUpdateClickListener = onClickUpdateDetailClickListener;
    }

    @NonNull
    @Override
    public DetailStatisticsAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.layout_item_statistics,parent,false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull DetailStatisticsAdapter.ViewHolder holder, int position) {
        Statistic statistic = listtotal.get(position);
        if(statistic!=null) {

            holder.tv_date.setText(FormatHelper.convertMonthtoString(statistic.getDt_date()));;
            holder.tv_revenue.setText("Tổng thu: "+ FormatHelper.convertPriceToString(0));
            holder.tv_expenditure.setText("Tổng chi: "+ FormatHelper.convertPriceToString(statistic.getTotal_amount()));
            holder.itemView.setOnClickListener(v->{
                if(onClickUpdateClickListener!=null){
                    onClickUpdateClickListener.onClick(position);
                }
            });
        }
    }

    @Override
    public int getItemCount()
    {return listtotal!=null?listtotal.size():0;
}
    public class ViewHolder extends RecyclerView.ViewHolder {
        private TextView  tv_date, tv_revenue, tv_expenditure;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            tv_date = itemView.findViewById(R.id.tv_date_statistics);
            tv_revenue = itemView.findViewById(R.id.tv_total_revenue);
            tv_expenditure = itemView.findViewById(R.id.tv_total_expenditure);

        }
    }
}
