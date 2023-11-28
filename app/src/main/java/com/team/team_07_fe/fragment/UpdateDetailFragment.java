package com.team.team_07_fe.fragment;

import android.app.AlertDialog;
import android.os.Bundle;

import androidx.appcompat.widget.AppCompatButton;
import androidx.fragment.app.Fragment;

import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.google.android.material.textfield.TextInputLayout;
import com.team.team_07_fe.R;
import com.team.team_07_fe.models.Customer;
import com.team.team_07_fe.utils.LoadingDialog;


public class UpdateDetailFragment extends Fragment {


    private TextInputLayout update_detail_input_date, update_detail_input_name,
            update_detail_input_money, update_detail_input_text;
    private AppCompatButton btn_reload_item, btn_update_item;
    private LoadingDialog loadingDialog;
    private Customer originalData = null;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_update_detail, container, false);

    }

    private void updateDetail(View view){
        String name = update_detail_input_name.getEditText().getText().toString().trim();
        String money = update_detail_input_money.getEditText().getText().toString().trim();
        String text = update_detail_input_text.getEditText().getText().toString().trim();
    }
    private void handleReloadData(View view){
        if(originalData!=null){
            AlertDialog.Builder builder = new AlertDialog.Builder(requireContext())
                    .setTitle("Thông báo!")
                    .setMessage("Bạn có chắc chắn muốn hoàn tác tất cả thay đổi này không? " +
                            "Mọi thay đổi sẽ không được lưu.")
                    .setPositiveButton(R.string.yes,(dialog, which) -> {
                        setData(originalData);
                        dialog.dismiss();
                    })
                    .setNegativeButton(R.string.no,((dialog, which) -> {
                        dialog.dismiss();
                    }));
            builder.create().show();
        }
    }
    private void setData(Customer customer){
        // Set lại tên khoản chi
        if (update_detail_input_name.getEditText() != null) {
            update_detail_input_name.getEditText().setText(String.valueOf(customer.getCus_id()));
        }
        // Set lại số tiền
        update_detail_input_money.getEditText().setText(customer.getCus_name());

        // Set lại mô tả
        update_detail_input_text.getEditText().setText(customer.getCus_address());


    }
    public boolean valueInput(String name,
                              String money, String text){
        boolean isvalid = true;
        if (TextUtils.isEmpty(name)) {
            update_detail_input_name.setError("Không để trống dữ liệu!");
            isvalid = false;
        } else {
            update_detail_input_name.setError(null);
        }
        if (TextUtils.isEmpty(money)) {
            update_detail_input_money.setError("Không để trống dữ liệu!");
            isvalid = false;
        } else {
            update_detail_input_money.setError(null);
        }
        if (TextUtils.isEmpty(text)) {
            update_detail_input_text.setError(null);
            isvalid = false;
        } else {
            update_detail_input_text.setError(null);
        }

        return isvalid;
    }
    public void mapping(View view){
        update_detail_input_date = view.findViewById(R.id.update_detail_input_date);
        update_detail_input_name = view.findViewById(R.id.update_detail_input_name);
        update_detail_input_money = view.findViewById(R.id.update_detail_input_money);
        update_detail_input_text = view.findViewById(R.id.update_detail_input_text);
        btn_reload_item = view.findViewById(R.id.btn_reload_item);
        btn_update_item = view.findViewById(R.id.btn_update_item);
    }
}