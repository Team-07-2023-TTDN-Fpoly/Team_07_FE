package com.team.team_07_fe.adapter;


import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.team.team_07_fe.R;
import com.team.team_07_fe.models.Customer;
import com.team.team_07_fe.models.Employee;
import com.team.team_07_fe.ui.customer.CustomerManagerFragment;
import com.team.team_07_fe.utils.FormatHelper;
import com.team.team_07_fe.utils.OnClickListener;

import java.util.List;

public class CustomerAdapter extends RecyclerView.Adapter<CustomerAdapter.ViewHolder> {
    private List<Customer> list;
    private Context context;
    private static OnClickListener onClickUpdateCustomerClickListener;
    private static OnClickListener onClickDeleteCustomerClickListener;

    public CustomerAdapter(Context context, List<Customer> list){
        this.list= list;
        this.context= context;
    }

    public void setList(List<Customer> list) {
        this.list = list;
        notifyDataSetChanged();
    }
    public void setOnClickUpdateCustomerClickListener(OnClickListener onClickUpdateCustomerClickListener) {
        CustomerAdapter.onClickUpdateCustomerClickListener = onClickUpdateCustomerClickListener;
    }

    public void setOnClickDeleteCustomerClickListener(OnClickListener onClickDeleteCustomerClickListener) {
        CustomerAdapter.onClickDeleteCustomerClickListener = onClickDeleteCustomerClickListener;
    }

    @NonNull
    @Override
    public CustomerAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(context).inflate(R.layout.layout_item_customer,parent,false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull CustomerAdapter.ViewHolder holder, int position) {
        Customer item=list.get(position);
        if (item !=null){

            holder.tv_cusname.setText(item.getCus_name());
            holder.tv_cusphonemary.setText("SĐT: "+ item.getCus_phone());
            holder.tv_cusphonesob.setText("SDT phụ: " +item.getCus_phoneSecond());
            holder.tv_cusbariday.setText("Ngày cưới: " + FormatHelper.convertDatetoString(item.getCus_wedding_date()));
            holder.tv_cusemail.setText("Email: "+ item.getCus_email());
            holder.tv_cusaddres.setText("Địa chỉ: " + item.getCus_address());

            holder.btn_delete_item.setOnClickListener(v->{
                if(onClickDeleteCustomerClickListener!=null){
                    onClickDeleteCustomerClickListener.onClick(position);
                }
            });

            holder.itemView.setOnClickListener(v->{
                if(onClickUpdateCustomerClickListener!=null){
                    onClickUpdateCustomerClickListener.onClick(position);
                }
            });

        }
    }

    @Override
    public int getItemCount() {
        return list.size();
    }
    public Customer getItem(int position){
        return list.get(position);
    }

    public class ViewHolder extends RecyclerView.ViewHolder{
    private TextView tv_cusid, tv_cusname,tv_cusphonemary,tv_cusphonesob,tv_cusbariday,tv_cusemail,tv_cusaddres,btn_delete_item;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            tv_cusname=itemView.findViewById(R.id.tv_cusname);
            tv_cusphonemary= itemView.findViewById(R.id.tv_cusphone);
            tv_cusphonesob=itemView.findViewById(R.id.tv_cusphonesob);
            tv_cusbariday=itemView.findViewById(R.id.tv_cusngaysinh);
            tv_cusemail=itemView.findViewById(R.id.tv_cusemail);
            tv_cusaddres=itemView.findViewById(R.id.tv_cusaddres);
            btn_delete_item=itemView.findViewById(R.id.btn_delete_item);
        }
    }
}
