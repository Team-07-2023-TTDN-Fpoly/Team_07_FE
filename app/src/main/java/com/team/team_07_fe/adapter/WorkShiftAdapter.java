package com.team.team_07_fe.adapter;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.team.team_07_fe.R;
import com.team.team_07_fe.models.WorkShift;
import com.team.team_07_fe.utils.FormatHelper;
import com.team.team_07_fe.utils.OnClickListener;

import java.util.List;

public class WorkShiftAdapter extends RecyclerView.Adapter<WorkShiftAdapter.WorkShiftViewHolder>{
    private List<WorkShift> mWorkShiftrs;
    private Context context;
    private static OnClickListener onClickUpdateWorkShiftClickListener;
    private OnClickListener onClickDeleteClickListener;
    public WorkShiftAdapter(Context context,List<WorkShift> mWorkShiftrs) {
        this.mWorkShiftrs = mWorkShiftrs;
        this.context = context;
    }
    public void setList(List<WorkShift> mWorkShiftrs) {
        this.mWorkShiftrs = mWorkShiftrs;
        notifyDataSetChanged();

    }
    public void setOnClickDeleteClickListener(OnClickListener onClickDeleteClickListener) {
        this.onClickDeleteClickListener = onClickDeleteClickListener;
    }
    public void onClickUpdateWorkShiftClickListener(OnClickListener onClickUpdateWorkShiftClickListener) {
        WorkShiftAdapter.onClickUpdateWorkShiftClickListener = onClickUpdateWorkShiftClickListener;
    }
    @NonNull
    @Override
    public WorkShiftAdapter.WorkShiftViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(context).inflate(R.layout.layout_item_work_shift,parent,false);
        return new WorkShiftViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull WorkShiftViewHolder holder, int position) {
        WorkShift workShift = mWorkShiftrs.get(position);
        if (workShift != null) {
            holder.tv_name_work.setText(workShift.getName());

            String formattedStartTime = FormatHelper.convertTimeToString(workShift.getTimeStart());
            holder.tv_start_time.setText("Giờ bắt đầu: " + formattedStartTime);

            String formattedEndTime = FormatHelper.convertTimeToString(workShift.getTimeEnd());
            holder.tv_end_time.setText("Giờ kết thúc: " + formattedEndTime);

            holder.btn_disable.setOnClickListener(view -> {
                if (onClickDeleteClickListener != null) {
                    onClickDeleteClickListener.onClick(position);
                }
            });
            holder.btn_update.setOnClickListener(view -> {
                if (onClickUpdateWorkShiftClickListener != null) {
                    onClickUpdateWorkShiftClickListener.onClick(position);
                }
            });
        }

    }

    @Override
    public int getItemCount() {
//        if (mWorkShiftrs!= null){
//            return mWorkShiftrs.size();
//        }
        return mWorkShiftrs.size();
    }
    public WorkShift getItem(int position){
        return mWorkShiftrs.get(position);
    }

    public class WorkShiftViewHolder extends RecyclerView.ViewHolder {
        private TextView tv_name_work,tv_start_time,tv_end_time,btn_disable,btn_update;
        public WorkShiftViewHolder(@NonNull View itemView) {
            super(itemView);
            tv_name_work = itemView.findViewById(R.id.tv_name_work_shift);
            tv_start_time = itemView.findViewById(R.id.tv_start_time);
            tv_end_time = itemView.findViewById(R.id.tv_end_time);
            btn_disable =itemView.findViewById(R.id.btn_disable);
            btn_update = itemView.findViewById(R.id.btn_update_work_shift);
        }
    }
}

