package com.team.team_07_fe.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.team.team_07_fe.R;
import com.team.team_07_fe.models.ContractDetail;
import com.team.team_07_fe.utils.FormatHelper;
import com.team.team_07_fe.utils.OnClickListener;

import java.util.List;

public class ContractDetailAdapter extends RecyclerView.Adapter<ContractDetailAdapter.ViewHolder> {
    private Context context;
    private List<ContractDetail> contractDetailList;

    public ContractDetailAdapter(Context context,List<ContractDetail> list){
        this.context = context;
        this.contractDetailList = list;
    }
    public void setList(List<ContractDetail> list){
        this.contractDetailList = list;
        notifyDataSetChanged();
    }
    public static OnClickListener onClickDeleteListener;

    public void setOnClickDeleteListener(OnClickListener onClickDeleteListener) {
        ContractDetailAdapter.onClickDeleteListener = onClickDeleteListener;
    }

    public ContractDetail getItem(int position){
        if(position<0 || position>contractDetailList.size()){
            return null;
        }
        return contractDetailList.get(position);
    }

    @NonNull
    @Override
    public ContractDetailAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.layout_item_contract_choose_dress,parent,false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ContractDetailAdapter.ViewHolder holder, int position) {
        ContractDetail item = contractDetailList.get(position);
        if(item==null){
            return;
        }
        holder.tv_name.setText(item.getDress().getDress_name());
        holder.tv_date_rental.setText(FormatHelper.convertDatetoString(item.getRental_date()));
        holder.tv_date_return.setText(FormatHelper.convertDatetoString(item.getReturn_date()));

        holder.tv_delete.setOnClickListener(view -> {
            if(onClickDeleteListener!=null){
                onClickDeleteListener.onClick(position);
            }
        });
    }

    @Override
    public int getItemCount() {
        return contractDetailList!=null?contractDetailList.size():0;
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        private TextView tv_name,tv_date_rental,tv_date_return,tv_delete;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            tv_name = itemView.findViewById(R.id.tv_name);
            tv_date_rental = itemView.findViewById(R.id.tv_date_rental);
            tv_date_return = itemView.findViewById(R.id.tv_date_return);
            tv_delete = itemView.findViewById(R.id.tv_delete);
        }
    }
}
