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
import com.team.team_07_fe.models.Contract;
import com.team.team_07_fe.utils.FormatHelper;
import com.team.team_07_fe.utils.OnClickListener;

import java.util.List;

public class ContractAdapter extends RecyclerView.Adapter<ContractAdapter.ContractViewHolder>{
    private Context context;
    private List<Contract> mListContract;

    public ContractAdapter(Context context, List<Contract> mListContract) {
        this.context = context;
        this.mListContract = mListContract;
    }

    public void setData(List<Contract> list){
        this.mListContract = list;
        notifyDataSetChanged();
    }
    private static OnClickListener onClickUpdateListener;

    public void setOnClickUpdateListener(OnClickListener onClickUpdateListener) {
        ContractAdapter.onClickUpdateListener = onClickUpdateListener;
    }

    @NonNull
    @Override
    public ContractViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.layout_item_contract, parent, false);
        return new ContractViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ContractViewHolder holder, int position) {
        Contract contract = mListContract.get(position);
        if (contract == null){
            return;
        }
        holder.tvName.setText(contract.getCustomer().getCus_name());
        holder.tvDate.setText("Ngày ký: " + FormatHelper.convertDatetoString(contract.getCreateAt()));
        holder.tvPhone.setText("SĐT: " + FormatHelper.formatPhoneNumber(contract.getCustomer().getCus_phone()));
        holder.tvDeposit.setText("Tiền cọc: " + FormatHelper.convertPriceToString(contract.getPrepay()));
        holder.tvSumMoney.setText("Tổng chi phí: " + FormatHelper.convertPriceToString(contract.getTotal_amount() - contract.getDiscount()));
        holder.tvStatus.setText("Trạng thái: " + contract.getStatus());

        holder.btnUpdate.setOnClickListener(v->{
            if(onClickUpdateListener!=null){
                onClickUpdateListener.onClick(position);
            }
        });
    }

    @Override
    public int getItemCount() {
        if (mListContract != null){
            return mListContract.size();
        }
        return 0;
    }

    public Contract getItem(int position){
        if(position<0 || position>mListContract.size()){
            return null;
        }
        return mListContract.get(position);
    }


    public class ContractViewHolder extends RecyclerView.ViewHolder {

        private TextView tvName, tvDate, tvPhone, tvDeposit, tvSumMoney, tvStatus;
        private ImageView btnUpdate;
        public ContractViewHolder(@NonNull View itemView) {
            super(itemView);

            tvName = itemView.findViewById(R.id.tvName);
            tvDate = itemView.findViewById(R.id.tvDate);
            tvPhone = itemView.findViewById(R.id.tvPhone);
            tvDeposit = itemView.findViewById(R.id.tvDeposit);
            tvSumMoney = itemView.findViewById(R.id.tvSumMoney);
            tvStatus = itemView.findViewById(R.id.tvStatus);
            btnUpdate = itemView.findViewById(R.id.btnUpdate);
        }
    }
}

