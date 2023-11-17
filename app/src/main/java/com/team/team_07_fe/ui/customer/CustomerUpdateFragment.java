package com.team.team_07_fe.ui.customer;

import android.app.AlertDialog;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.AppCompatButton;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.fragment.NavHostFragment;

import android.text.TextUtils;
import android.util.Patterns;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Toast;

import com.google.android.material.textfield.TextInputLayout;
import com.team.team_07_fe.MainActivity;
import com.team.team_07_fe.R;
import com.team.team_07_fe.anotition.Role;
import com.team.team_07_fe.models.Customer;
import com.team.team_07_fe.models.Employee;
import com.team.team_07_fe.models.WorkShift;
import com.team.team_07_fe.ui.employee.EmployeeUpdateFragment;
import com.team.team_07_fe.utils.FormatHelper;
import com.team.team_07_fe.utils.LoadingDialog;

import java.util.Arrays;
import java.util.Date;
import java.util.List;

public class CustomerUpdateFragment extends Fragment {
    private TextInputLayout layout_input_id,layout_input_name,layout_input_email,
            layout_input_phone,layout_input_phoneSecond,layout_input_birthday,layout_input_address;
    private CustomerViewModel mViewModel;
    private AppCompatButton btn_reload_item,btn_update_item,btn_delete_item;
    private Customer originalData = null;
    private LoadingDialog loadingDialog;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_customer_update, container, false);
        mViewModel = new ViewModelProvider(requireActivity()).get(CustomerViewModel.class);
        loadingDialog = new LoadingDialog(requireContext());
        //mapping
        mapping(view);
        return view;
    }
    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        if(getArguments()!=null){
            originalData = (Customer) getArguments().getSerializable("data_customer");
            setData(originalData);
        }
        //Click button
        btn_reload_item.setOnClickListener(this::handleReloadData);
        btn_update_item.setOnClickListener(this::handleUpdateData);
        btn_delete_item.setOnClickListener(this::handleDeleteData);
        //
    }
    private void handleReloadData(View view){
        if(originalData!=null){
            AlertDialog.Builder builder = new AlertDialog.Builder(requireContext())
                    .setTitle("Cảnh báo!")
                    .setMessage("Bạn có chắc với quyết định hoàn tác này không? " +
                            "Mọi thay đổi của bạn sẽ không được lưu.")
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
    private void handleUpdateData(View view){
        String id = layout_input_id.getEditText().getText().toString().trim();
        String name = layout_input_name.getEditText().getText().toString().trim();
        String phone = layout_input_phone.getEditText().getText().toString().trim();
        String phoneSecond = layout_input_phoneSecond.getEditText().getText().toString().trim();
        String email = layout_input_email.getEditText().getText().toString().trim();
        String birthday = layout_input_birthday.getEditText().getText().toString().trim();
        String address = layout_input_address.getEditText().getText().toString().trim();




        if(validateInput(name,phone,phoneSecond)){
            Date formatBirthday = null;
            if (!TextUtils.isEmpty(birthday)) {
                formatBirthday = FormatHelper.convertStringtoDate(birthday);
            }// ten,phone1,phone2,NS,DC
            Customer customerRequest = new Customer(name,phone,phoneSecond,formatBirthday,address);
            showDialogConfirmUpdate(id,customerRequest);
        }

    }
    private void handleDeleteData(View view) {
        if (originalData != null) {
            AlertDialog.Builder builder = new AlertDialog.Builder(requireContext())
                    .setTitle("Cảnh báo!")
                    .setMessage("Bạn có chắc muốn xóa khách hàng này không?")
                    .setPositiveButton(R.string.yes, (dialog, which) -> {
                        loadingDialog.show();
                        mViewModel.removeEmployee(originalData);
                        dialog.dismiss();
                    })
                    .setNegativeButton(R.string.no, ((dialog, which) -> {
                        dialog.dismiss();
                    }));
            builder.create().show();
        }
    }
    private void showDialogConfirmUpdate(String id, Customer customerRequest){
        AlertDialog.Builder builder = new AlertDialog.Builder(requireContext())
                .setTitle("Thông báo!")
                .setMessage("Bạn có chắc muốn cập nhật khách hàng này không? " +
                        "Mọi thông tin trước đó sẽ không được lưu.")
                .setPositiveButton(R.string.yes,(dialog, which) -> {
                    loadingDialog.show();
                    mViewModel.updateEmployee(Integer.parseInt(id),customerRequest);
                    dialog.dismiss();
                })
                .setNegativeButton(R.string.no,((dialog, which) -> {
                    dialog.dismiss();
                }));
        builder.create().show();
    }
    //Set dữ liệu
    private void setData(Customer customer){ //tên, sdt1,sdt2,email,ngày sinh,địa chỉ
        // Set lại thông tin id, nếu có trường hiển thị id
        if (layout_input_id.getEditText() != null) {
            layout_input_id.getEditText().setText(String.valueOf(customer.getCus_id()));
        }
        // Set lại tên khách hàng
        layout_input_name.getEditText().setText(customer.getCus_name());
        // Set lại email
        layout_input_email.getEditText().setText(customer.getEmail());
        layout_input_email.getEditText().setEnabled(false);
        // Set lại số điện thoại
        layout_input_phone.getEditText().setText(FormatHelper.formatPhoneNumber(customer.getCus_phone()));
        layout_input_phoneSecond.getEditText().setText(FormatHelper.formatPhoneNumber(customer.getCus_phoneSecond()));
        // Set lại ngày sinh - bạn cần định dạng lại Date thành String
        if (customer.getCus_birthday() != null) {
            String birthdayStr = FormatHelper.convertDatetoString(customer.getCus_birthday());
            layout_input_birthday.getEditText().setText(birthdayStr);
        }
        // Set lại địa chỉ
        layout_input_address.getEditText().setText(customer.getCus_address());


    }
    private boolean validateInput(String name,
                                  String phone,  String phoneSecond){ //CN name,phone
        boolean isValid = true;

        if(TextUtils.isEmpty(name)){
            layout_input_name.setError("Vui lòng nhập tên nhân viên!");
            isValid = false;
        }else{
            layout_input_name.setError(null);
        }

        if(TextUtils.isEmpty(phone) || phone.length() != 10 ||!Patterns.PHONE.matcher(phone).matches()){
            layout_input_phone.setError("Vui lòng nhập đúng định dạng!");
            isValid = false;
        }else{
            layout_input_phone.setError(null);
        }
        if (TextUtils.isEmpty(phoneSecond) || phoneSecond.length() != 10 || !Patterns.PHONE.matcher(phoneSecond).matches()) {
            layout_input_phoneSecond.setError("Vui lòng nhập đúng định dạng!");
            isValid = false;
        }else{
            layout_input_phoneSecond.setError(null);
        }
        return isValid;
    }
    private void mapping(View view){
        layout_input_id = view.findViewById(R.id.layout_input_id);
        layout_input_name = view.findViewById(R.id.layout_input_name);
        layout_input_email = view.findViewById(R.id.layout_input_email);
        layout_input_phone = view.findViewById(R.id.layout_input_phone);
        layout_input_phoneSecond = view.findViewById(R.id.layout_input_phoneSecond);
        layout_input_birthday = view.findViewById(R.id.layout_input_birthday);
        layout_input_address = view.findViewById(R.id.layout_input_address);
        btn_update_item = view.findViewById(R.id.btn_update_item);
        btn_reload_item = view.findViewById(R.id.btn_reload_item);
    }

    private void refreshFragment(){
        loadingDialog.dismiss();
        NavHostFragment.findNavController(CustomerUpdateFragment.this)
                .popBackStack();
    }
    @Override
    public void onResume() {
        super.onResume();
        ((MainActivity)requireActivity()).hiddenBottomBar();
    }

    @Override
    public void onStop() {
        super.onStop();
        ((MainActivity) requireActivity()).showBottomBar();
    }
}
