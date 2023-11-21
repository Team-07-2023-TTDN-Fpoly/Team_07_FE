package com.team.team_07_fe.ui.employee;

import android.app.AlertDialog;
import android.os.Build;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
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
import com.team.team_07_fe.models.Employee;
import com.team.team_07_fe.models.WorkShift;
import com.team.team_07_fe.utils.FormatHelper;
import com.team.team_07_fe.utils.LoadingDialog;
import com.team.team_07_fe.viewmodels.EmployeeViewModel;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

public class EmployeeUpdateFragment extends Fragment {
    private TextInputLayout layout_input_id,layout_input_name,layout_input_email,
            layout_input_phone,layout_input_salary,layout_input_birthday,layout_input_join_date,layout_input_address;
    private AutoCompleteTextView dropdown_role,dropdown_work_shift;
    private EmployeeViewModel mViewModel;
    private AppCompatButton btn_reload_item,btn_update_item;
    private String[] listRole = {Role.CHOOSE,Role.ADMIN,Role.EMPLOYEE};
    private List<WorkShift> listWorkShift;
    private ArrayAdapter<String> roleAdapter;
    private ArrayAdapter<WorkShift> workShiftAdapter;
    private String selectRole = Role.CHOOSE;
    private WorkShift selectWorkShift = null;
    private Employee originalData =null;
    private LoadingDialog loadingDialog;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_employee_update, container, false);
        mViewModel = new ViewModelProvider(requireActivity()).get(EmployeeViewModel.class);
        loadingDialog = new LoadingDialog(requireContext());
        //mapping
        mapping(view);
        //
        listWorkShift = new ArrayList<>();
        listWorkShift.add(new WorkShift(1, "Ca sáng", FormatHelper.convertStringToTime("12:00"), FormatHelper.convertStringToTime("12:00"), "Làm việc buổi sáng"));
        listWorkShift.add(new WorkShift(2, "Ca sáng", FormatHelper.convertStringToTime("12:00"), FormatHelper.convertStringToTime("11:00"), "Làm việc buổi sáng"));
        listWorkShift.add(new WorkShift(3, "Ca sáng", FormatHelper.convertStringToTime("12:00"), FormatHelper.convertStringToTime("12:00"), "Làm việc buổi sáng"));
        //
        roleAdapter = new ArrayAdapter<>(requireActivity(),android.R.layout.simple_dropdown_item_1line,listRole);
        dropdown_role.setAdapter(roleAdapter);
        //
        workShiftAdapter = new ArrayAdapter<>(requireActivity(),android.R.layout.simple_dropdown_item_1line,listWorkShift);
        dropdown_work_shift.setAdapter(workShiftAdapter);
        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        if(getArguments()!=null){
            originalData = (Employee) getArguments().getSerializable("data_employee");
            setData(originalData);
        }
        //Click button
        btn_reload_item.setOnClickListener(this::handleReloadData);
        btn_update_item.setOnClickListener(this::handleUpdateData);
        //
        dropdown_role.setOnItemClickListener((adapterView, view1, i, l) ->{
            selectRole = (String) adapterView.getItemAtPosition(i);
        });
        dropdown_work_shift.setOnItemClickListener((adapterView, view1, i, l) -> {
            selectWorkShift = (WorkShift) adapterView.getItemAtPosition(i);
        });
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
        String name = layout_input_name.getEditText().getText().toString().trim();
        String id = layout_input_id.getEditText().getText().toString().trim();
        String phone = layout_input_phone.getEditText().getText().toString().trim();
        String birthday = layout_input_birthday.getEditText().getText().toString().trim();
        String join_date = layout_input_birthday.getEditText().getText().toString().trim();
        String address = layout_input_address.getEditText().getText().toString().trim();
        String salary = layout_input_salary.getEditText().getText().toString().trim();




        if(validateInput(name,phone,salary)){
            Date formatBirthday = null;
            Date formatJoinDate = new Date();
            if (!TextUtils.isEmpty(birthday)) {
                formatBirthday = FormatHelper.convertStringtoDate(birthday);
            }
            if(!TextUtils.isEmpty(join_date)){
                formatJoinDate = FormatHelper.convertStringtoDate(join_date);
            }
            Employee employeeRequest = new Employee(name,phone,formatBirthday,Long.parseLong(salary),address,selectRole,selectWorkShift,formatJoinDate);
            showDialogConfirmUpdate(id,employeeRequest);
        }

    }
    private void showDialogConfirmUpdate(String id, Employee employeeRequest){
        AlertDialog.Builder builder = new AlertDialog.Builder(requireContext())
                .setTitle("Thông báo!")
                .setMessage("Bạn có chắc muốn cập nhật nhân viên này không? " +
                        "Mọi thông tin trước đó sẽ không được lưu.")
                .setPositiveButton(R.string.yes,(dialog, which) -> {
                    mViewModel.updateEmployee(Integer.parseInt(id),employeeRequest);
                    refreshFragment();
                    dialog.dismiss();
                })
                .setNegativeButton(R.string.no,((dialog, which) -> {
                    dialog.dismiss();
                }));
        builder.create().show();
    }
    //Set dữ liệu
    private void setData(Employee employee) {
        // Set lại thông tin id, nếu có trường hiển thị id
        if (layout_input_id.getEditText() != null) {
            layout_input_id.getEditText().setText(employee.getEmp_id());
        }
        // Set lại tên nhân viên
        layout_input_name.getEditText().setText(employee.getEmp_name());
        // Set lại email
        layout_input_email.getEditText().setText(employee.getEmail());
        layout_input_email.getEditText().setEnabled(false);
        // Set lại số điện thoại
        layout_input_phone.getEditText().setText(employee.getEmp_phone());
        // Set lại cccd (Chứng minh nhân dân hoặc thẻ căn cước)
        // Set lại ngày sinh - bạn cần định dạng lại Date thành String
        if (employee.getEmp_birthday() != null) {
            String birthdayStr = FormatHelper.convertDatetoString(employee.getEmp_birthday()); // Giả sử bạn có phương thức này
            layout_input_birthday.getEditText().setText(birthdayStr);
        }
        // Set lại lương
        layout_input_salary.getEditText().setText(String.valueOf(employee.getBasic_salary()));
        // Set lại địa chỉ
        layout_input_address.getEditText().setText(employee.getEmp_address());

        // Set lại vai trò
        int roleIndex = Arrays.asList(listRole).indexOf(employee.getRole());
        if (roleIndex >= 0) { // Kiểm tra xem có tìm thấy vai trò trong listRole hay không
            dropdown_role.setText(listRole[roleIndex], false);
            selectRole = listRole[roleIndex]; // Cập nhật biến selectRole
        } else {
            dropdown_role.setText(Role.CHOOSE, false);
            selectRole = Role.CHOOSE; // Cập nhật biến selectRole
        }

        // Set lại ca làm việc
        int workShiftIndex = -1;
        for (int i = 0; i < listWorkShift.size(); i++) {
            if (listWorkShift.get(i).getShift_id() == employee.getWorkShift().getShift_id()) {
                workShiftIndex = i;
                break;
            }
        }
        if (workShiftIndex >= 0) { // Kiểm tra xem có tìm thấy ca làm việc trong listWorkShift hay không
            dropdown_work_shift.setText(listWorkShift.get(workShiftIndex).toString(), false);
            selectWorkShift = listWorkShift.get(workShiftIndex); // Cập nhật biến selectWorkShift
        } else {
            dropdown_work_shift.setText("Vui lòng chọn ca làm việc", false);
            selectWorkShift = null; // Cập nhật biến selectWorkShift
        }

    }
    private boolean validateInput(String name,String phone, String salary){
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

        if(TextUtils.isEmpty(salary)){
            layout_input_salary.setError("Vui lòng nhập lương cơ bản nhân viên!");
            isValid = false;
        }else{
            layout_input_salary.setError(null);
        }

        if(selectRole.equals(Role.CHOOSE)){
            isValid = false;
            Toast.makeText(requireContext(), "Vui lòng chọn chức vụ!", Toast.LENGTH_SHORT).show();
        }

        if(selectWorkShift==null){
            isValid = false;
            Toast.makeText(requireContext(), "Vui lòng chọn ca làm việc!", Toast.LENGTH_SHORT).show();
        }
        return isValid;
    }
    private void mapping(View view){
        layout_input_id = view.findViewById(R.id.layout_input_id);
        layout_input_name = view.findViewById(R.id.layout_input_name);
        layout_input_email = view.findViewById(R.id.layout_input_email);
        layout_input_phone = view.findViewById(R.id.layout_input_phone);
        layout_input_salary = view.findViewById(R.id.layout_input_salary);
        layout_input_birthday = view.findViewById(R.id.layout_input_birthday);
        layout_input_join_date = view.findViewById(R.id.layout_input_join_date);
        layout_input_address = view.findViewById(R.id.layout_input_address);
        dropdown_role = view.findViewById(R.id.dropdown_role);
        dropdown_work_shift = view.findViewById(R.id.dropdown_work_shift);
        btn_update_item = view.findViewById(R.id.btn_update_item);
        btn_reload_item = view.findViewById(R.id.btn_reload_item);
    }

    private void refreshFragment(){
        loadingDialog.dismiss();
        NavHostFragment.findNavController(EmployeeUpdateFragment.this)
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