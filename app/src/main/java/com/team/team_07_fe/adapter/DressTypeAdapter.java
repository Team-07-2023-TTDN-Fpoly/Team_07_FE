package com.team.team_07_fe.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.team.team_07_fe.R;
import com.team.team_07_fe.models.DressType;
import com.team.team_07_fe.utils.OnClickListener;

import java.util.List;

public class DressTypeAdapter extends RecyclerView.Adapter<DressTypeAdapter.ViewHolder> {
    private List<DressType> listtype;
    private Context ct1;
    private OnClickListener onClickUpdateTypeClickListener;
    private OnClickListener onClickDeleteClickListener;
    private OnClickListener onClickEditClickListener;

    public DressTypeAdapter(Context context, List<DressType> list) {
        this.listtype = list;
        this.ct1 = context;
    }

    public void setList(List<DressType> list) {
        this.listtype = list;
        notifyDataSetChanged();
    }

    public void setOnClickDeleteClickListener(OnClickListener onClickDeleteClickListener) {
        this.onClickDeleteClickListener = onClickDeleteClickListener;
    }
    public void setOnClickEditClickListener(OnClickListener onClickEditClickListener) {
        this.onClickEditClickListener = onClickEditClickListener;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(ct1).inflate(R.layout.layout_item_dresstype, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        DressType itemType = listtype.get(position);
        if (itemType != null) {
            holder.tv_tenloai.setText("Tên loại: " + itemType.getType_name());
            holder.tv_maloai.setText("Mã loại: " + itemType.getType_id());

            holder.btn_delete.setOnClickListener(view -> {
                if (onClickDeleteClickListener != null) {
                    onClickDeleteClickListener.onClick(position);
                }
            });

            holder.btn_edit.setOnClickListener(v -> {
                if (onClickUpdateTypeClickListener != null) {
                    onClickUpdateTypeClickListener.onClick(position);
                }
            });
            holder.btn_edit.setOnClickListener(v -> {
                if (onClickEditClickListener != null) {
                    onClickEditClickListener.onClick(position);
                }
            });
        }
    }

    @Override
    public int getItemCount() {
        return listtype.size();
    }

    public DressType getItem(int position) {
        return listtype.get(position);
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        private TextView tv_tenloai, tv_maloai, btn_delete, btn_update, btn_edit;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            tv_tenloai = itemView.findViewById(R.id.tv_tenloai);
            tv_maloai = itemView.findViewById(R.id.tv_maloai);
            btn_delete = itemView.findViewById(R.id.btn_delete);
            btn_edit = itemView.findViewById(R.id.btn_edit);
        }
    }
}

