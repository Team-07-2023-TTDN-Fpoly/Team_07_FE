package com.team.team_07_fe.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.team.team_07_fe.R;
import com.team.team_07_fe.models.Dress;
import com.team.team_07_fe.utils.FormatHelper;
import com.team.team_07_fe.utils.OnClickListener;

import java.util.List;

public class DressAdapter extends RecyclerView.Adapter<DressAdapter.ViewHolder> {
    private List<Dress> listDress;
    private Context context;

    private static OnClickListener onClickUpdateClickListener;
    private static OnClickListener onClickDeleteClickListener;

    public DressAdapter(Context context,List<Dress> listDress){
        this.listDress=listDress;
        this.context=context;
    }
    public void setListDress(List<Dress>list){
        this.listDress=list;
        notifyDataSetChanged();
    }


    public void setOnClickDeleteClickListener(OnClickListener onClickDeleteClickListener) {
        DressAdapter.onClickDeleteClickListener = onClickDeleteClickListener;
    }
    public void setOnClickUpdateClickListener(OnClickListener onClickUpdateClickListener) {
        DressAdapter.onClickUpdateClickListener = onClickUpdateClickListener;
    }

    @NonNull
    @Override
    public DressAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_list_dress,parent,false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull DressAdapter.ViewHolder holder, int position) {
    Dress itemDress = listDress.get(position);
    if(itemDress==null) {
    return;
    }
        Glide.with(context).load(itemDress.getImage()).fitCenter().into(holder.imageView);
        holder.tv_nameDress.setText(itemDress.getDress_name());
        holder.tv_typeDressId.setText("Kiểu áo: "+itemDress.getDressTypeId().getType_name());
        holder.tv_priceDress.setText("Giá áo: "+ FormatHelper.convertPriceToString(itemDress.getDress_price()));
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

    @Override
    public int getItemCount() {
        return listDress!=null ? listDress.size(): 0;
    }

    public Dress getItem(int position){
        if(position> listDress.size() || position< 0){
            return null;
        }
        return listDress.get(position);
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
    private TextView  tv_nameDress, tv_typeDressId, tv_priceDress, tv_sizeDress, tv_colorDress;
    private ImageView btn_update;
    private TextView btn_Delete;
    private ImageView imageView;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            tv_nameDress = itemView.findViewById(R.id.tv_tenaocuoi);
            tv_priceDress = itemView.findViewById(R.id.tv_gia);
            tv_sizeDress = itemView.findViewById(R.id.tv_size);
            tv_colorDress = itemView.findViewById(R.id.tv_mau);
            tv_typeDressId = itemView.findViewById((R.id.tv_loai));
            btn_update = itemView.findViewById(R.id.btn_update_item);
            btn_Delete = itemView.findViewById(R.id.btn_delete_item);
            imageView = itemView.findViewById(R.id.imageView4);

        }
    }
}
