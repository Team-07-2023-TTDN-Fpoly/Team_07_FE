package com.team.team_07_fe.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.team.team_07_fe.R;
import com.team.team_07_fe.models.Dress;
import com.team.team_07_fe.utils.LoadingDialog;
import com.team.team_07_fe.utils.OnClickListener;

import java.util.List;

public class DressAdapter extends RecyclerView.Adapter<DressAdapter.ViewHolder> {
    private List<Dress> listDress;
    private Context context;

    private static OnClickListener onClickUpdateClickListener;
    private static OnClickListener onClickAddClickListener;
    private static OnClickListener onClickDeleteClickListener;
    public DressAdapter(Context context,List<Dress> listDress){
        this.listDress=listDress;
        this.context=context;
    }
    public void setListDress(List<Dress>list){
        this.listDress=listDress;
        notifyDataSetChanged();
    }

    public static void setOnClickAddClickListener(OnClickListener onClickAddClickListener) {
        DressAdapter.onClickAddClickListener = onClickAddClickListener;
    }

    public static void setOnClickDeleteClickListener(OnClickListener onClickDeleteClickListener) {
        DressAdapter.onClickDeleteClickListener = onClickDeleteClickListener;
    }
    public static void setOnClickUpdateClickListener(OnClickListener onClickUpdateClickListener) {
        DressAdapter.onClickUpdateClickListener = onClickUpdateClickListener;
    }

    @NonNull
    @Override
    public DressAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.fragment_dress_list,parent,false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull DressAdapter.ViewHolder holder, int position) {
    Dress itemDress = listDress.get(position);
    if(itemDress!=null) {
    holder.tv_nameDress.setText(itemDress.getDress_name());
    holder.tv_typeDressId.setText("Kiểu áo: "+itemDress.getDressTypeId());
    holder.tv_priceDress.setText("Giá áo: "+itemDress.getDress_price());
    holder.tv_sizeDress.setText("Size: "+itemDress.getSize());
    holder.tv_colorDress.setText("Màu sắc: "+itemDress.getColor());

        holder.btn_update.setOnClickListener(v->{
            if(onClickUpdateClickListener!=null){
                onClickUpdateClickListener.onClick(position);
            }
        });

        holder.btn_Delete.setOnClickListener(v->{
            if(onClickDeleteClickListener!=null){
                onClickDeleteClickListener.onClick(position);
            }
        });
    }

    }

    @Override
    public int getItemCount() {
        return listDress.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
    private TextView  tv_nameDress, tv_typeDressId, tv_priceDress, tv_sizeDress, tv_colorDress, btn_update, btn_Delete;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            tv_nameDress = itemView.findViewById(R.id.tv_tenaocuoi);
            tv_priceDress = itemView.findViewById(R.id.tv_gia);
            tv_sizeDress = itemView.findViewById(R.id.tv_size);
            tv_colorDress = itemView.findViewById(R.id.tv_mau);
            tv_typeDressId = itemView.findViewById((R.id.tv_loai));
            btn_update = itemView.findViewById(R.id.btn_update);
            btn_Delete = itemView.findViewById(R.id.btn_Delete);

        }
    }
}
