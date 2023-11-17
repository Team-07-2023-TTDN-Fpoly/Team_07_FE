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

import java.util.List;

public class WorkShiftAdapter extends RecyclerView.Adapter<WorkShiftAdapter.WorkShiftViewHolder>{
    private List<WorkShift> mWorkShiftrs;
    private Context context;

    public WorkShiftAdapter(Context context,List<WorkShift> mWorkShiftrs) {
        this.mWorkShiftrs = mWorkShiftrs;
        this.context = context;
    }
    public void setList(List<WorkShift> mWorkShiftrs) {
        this.mWorkShiftrs = mWorkShiftrs;
        notifyDataSetChanged();

    }
    @NonNull
    @Override
    public WorkShiftAdapter.WorkShiftViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(context).inflate(R.layout.layout_item_work_shift,parent,false);
        return new WorkShiftViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull WorkShiftViewHolder holder, int position) {
        WorkShift workShift= mWorkShiftrs.get(position);
        if(workShift!=null){
            holder.tv_name_work.setText(workShift.getName());
            holder.tv_start_time.setText("Giờ bắt đầu: " + workShift.getTimeStart());
            holder.tv_end_time.setText("Giờ kết thúc: "+workShift.getTimeEnd());
            Log.e("===///","sd"+workShift.getName());
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
        private TextView tv_name_work,tv_start_time,tv_end_time;
        public WorkShiftViewHolder(@NonNull View itemView) {
            super(itemView);
            tv_name_work = itemView.findViewById(R.id.tv_name_work_shift);
            tv_start_time = itemView.findViewById(R.id.tv_start_time);
            tv_end_time = itemView.findViewById(R.id.tv_end_time);

        }
    }
}

