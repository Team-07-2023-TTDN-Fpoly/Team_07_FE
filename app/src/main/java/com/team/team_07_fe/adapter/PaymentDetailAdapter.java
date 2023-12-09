package com.team.team_07_fe.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.team.team_07_fe.R;
import com.team.team_07_fe.models.Statistic;
import com.team.team_07_fe.utils.FormatHelper;
import com.team.team_07_fe.utils.OnClickListener;

import java.text.DecimalFormat;
import java.util.List;

public class PaymentDetailAdapter extends RecyclerView.Adapter<PaymentDetailAdapter.ViewHolder>{
    private List<Statistic> listpayment;
    private Context context;
    private static OnClickListener onClickUpdateClickListener;
    @NonNull
    @Override
    public PaymentDetailAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.fragment_payment_detail,parent,false);
        return new ViewHolder(view);
    }
    @Override
    public void onBindViewHolder(@NonNull PaymentDetailAdapter.ViewHolder holder, int position) {
        Statistic list = listpayment.get(position);
        if(list!=null){
            holder.tv_date.setText(FormatHelper.convertMonthtoString(list.getDt_date()));
            holder.tv_revenue.setText("Tiền thu từ hợp đồng: " +list.getTotal_revenue());
            holder.tv_expenditure.setText("Tổng tiền chi: " +list.getTotal_amount());

            holder.itemView.setOnClickListener(v->{
                if(onClickUpdateClickListener!=null){
                    onClickUpdateClickListener.onClick(position);
                }
            });
        }

    }



    @Override
    public int getItemCount() {
        return listpayment!=null?listpayment.size():0;
    }

    public PaymentDetailAdapter(Context context, List<Statistic> listpayment){
        this.listpayment = listpayment;
        this.context = context;
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        private TextView tv_date, tv_revenue, tv_expenditure,text1;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            tv_date = itemView.findViewById(R.id.tv_date_statistics);
            tv_revenue = itemView.findViewById(R.id.tv_total_revenue);
            tv_expenditure = itemView.findViewById(R.id.tv_total_expenditure);
            text1 = itemView.findViewById(R.id.text1);
        }
    }

}
