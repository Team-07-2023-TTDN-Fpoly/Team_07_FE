package com.team.team_07_fe.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.team.team_07_fe.models.Employee;
import java.util.List;
import com.team.team_07_fe.R;
import com.team.team_07_fe.utils.OnClickListener;

public class EmployeeAdapter extends RecyclerView.Adapter<EmployeeAdapter.ViewHolder> {
    private List<Employee> list;
    private Context context;

    private static OnClickListener onClickUpdateClickListener;
    private static OnClickListener onClickDisableClickListener;
    private static OnClickListener onClickChangePasswordClickListener;
    public EmployeeAdapter(Context context,List<Employee> list){
        this.list =list;
        this.context = context;
    }

    public void setList(List<Employee> list) {
        this.list = list;
        notifyDataSetChanged();
    }

    public void setOnClickUpdateClickListener(OnClickListener onClickUpdateClickListener) {
        EmployeeAdapter.onClickUpdateClickListener = onClickUpdateClickListener;
    }

    public void setOnClickDisableClickListener(OnClickListener onClickDisableClickListener) {
        EmployeeAdapter.onClickDisableClickListener = onClickDisableClickListener;
    }

    public void setOnClickChangePasswordClickListener(OnClickListener onClickChangePasswordClickListener) {
        EmployeeAdapter.onClickChangePasswordClickListener = onClickChangePasswordClickListener;
    }

    @NonNull
    @Override
    public EmployeeAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.layout_item_employee,parent,false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull EmployeeAdapter.ViewHolder holder, int position) {
        Employee item = list.get(position);
        if(item!=null){
            holder.tv_name.setText(item.getEmp_name());
            holder.tv_phone.setText("SDT: " + item.getEmp_name());
            holder.tv_email.setText("Email: "+item.getEmail());
            holder.tv_role.setText("Chức vụ: "+item.getRole());

            holder.btn_change_pass.setOnClickListener(v->{
                if(onClickChangePasswordClickListener!=null){
                    onClickChangePasswordClickListener.onClick(position);
                }
            });

            holder.btn_disable.setOnClickListener(v->{
                if(onClickDisableClickListener!=null){
                    onClickDisableClickListener.onClick(position);
                }
            });

            holder.itemView.setOnClickListener(v->{
                if(onClickUpdateClickListener!=null){
                    onClickUpdateClickListener.onClick(position);
                }
            });
        }
    }

    @Override
    public int getItemCount() {
        return list.size();
    }
    public Employee getItem(int position){
        return list.get(position);
    }
    public class ViewHolder extends RecyclerView.ViewHolder {
        private TextView tv_name,tv_phone,tv_email,tv_role,btn_change_pass,btn_disable;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            tv_name = itemView.findViewById(R.id.tv_name);
            tv_phone = itemView.findViewById(R.id.tv_phone);
            tv_role = itemView.findViewById(R.id.tv_role);
            tv_email = itemView.findViewById(R.id.tv_email);
            btn_change_pass = itemView.findViewById(R.id.btn_change_pass);
            btn_disable = itemView.findViewById(R.id.btn_disable);
        }
    }
}
