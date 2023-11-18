package com.team.team_07_fe.ui.employee;

import androidx.annotation.RequiresApi;
import androidx.appcompat.widget.AppCompatButton;
import androidx.lifecycle.ViewModelProvider;

import android.app.DatePickerDialog;
import android.os.Build;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.text.TextUtils;
import android.util.Patterns;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.DatePicker;
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

import java.text.Format;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

public class EmployeeCreateFragment extends Fragment {
    private TextInputLayout layout_input_name,layout_input_email,layout_input_password,
    layout_input_phone,layout_input_salary,layout_input_birthday,layout_input_join_date,layout_input_address;
    private AutoCompleteTextView dropdown_role,dropdown_work_shift;
    private EmployeeViewModel mViewModel;
    private AppCompatButton btn_add_item;
    private String[] listRole = {Role.CHOOSE,Role.ADMIN,Role.EMPLOYEE};
    private List<WorkShift> listWorkShift;
    private ArrayAdapter<String> roleAdapter;
    private ArrayAdapter<WorkShift> workShiftAdapter;
    private LoadingDialog loadingDialog;

    private String selectRole = Role.CHOOSE;
    private WorkShift selectWorkShift = null;
    @RequiresApi(api = Build.VERSION_CODES.O)
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_employee_create, container, false);
        mViewModel = new ViewModelProvider(requireActivity()).get(EmployeeViewModel.class);
        loadingDialog = new LoadingDialog(requireContext());
        //mapping
        listWorkShift = new ArrayList<>();
        listWorkShift.add(new WorkShift(1, "Ca sáng", FormatHelper.convertStringToTime("12:00"), FormatHelper.convertStringToTime("12:00"), "Làm việc buổi sáng"));
        listWorkShift.add(new WorkShift(2, "Ca sáng", FormatHelper.convertStringToTime("8:00"), FormatHelper.convertStringToTime("14:00"), "Làm việc buổi sáng"));
        listWorkShift.add(new WorkShift(3, "Ca sáng", FormatHelper.convertStringToTime("7:00"), FormatHelper.convertStringToTime("9:00"), "Làm việc buổi sáng"));
        mapping(view);

        //
        roleAdapter = new ArrayAdapter<>(requireActivity(),android.R.layout.simple_dropdown_item_1line,listRole);
        dropdown_role.setAdapter(roleAdapter);
        dropdown_role.setText(Role.CHOOSE,false);
        //
        workShiftAdapter = new ArrayAdapter<>(requireActivity(),android.R.layout.simple_dropdown_item_1line,listWorkShift);
        dropdown_work_shift.setAdapter(workShiftAdapter);
        dropdown_work_shift.setText("Vui lòng chọn ca làm việc",false);
        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        btn_add_item.setOnClickListener(this::handleAddEmployee);
        layout_input_birthday.getEditText().setOnClickListener(this::chooseDateForBirthday);
        layout_input_join_date.getEditText().setOnClickListener(this::chooseDateForJoin);

        dropdown_role.setOnItemClickListener((adapterView, view1, i, l) ->{
            selectRole = (String) adapterView.getItemAtPosition(i);
        });
        dropdown_work_shift.setOnItemClickListener((adapterView, view1, i, l) -> {
            selectWorkShift = (WorkShift) adapterView.getItemAtPosition(i);
        });
    }

    //xử lý sự kiến thêm mới
    private void handleAddEmployee(View view){
        String name = layout_input_name.getEditText().getText().toString().trim();
        String email = layout_input_email.getEditText().getText().toString().trim();
        String password = layout_input_password.getEditText().getText().toString().trim();
        String phone = layout_input_phone.getEditText().getText().toString().trim();
        String birthday = layout_input_birthday.getEditText().getText().toString().trim();
        String join_date = layout_input_birthday.getEditText().getText().toString().trim();
        String address = layout_input_address.getEditText().getText().toString().trim();
        String salary = layout_input_salary.getEditText().getText().toString().trim();

        if(validateInput(name,email,password,phone,salary)){
            Date formatBirthday = null;
            Date formatJoinDate = new Date();
            if (!TextUtils.isEmpty(birthday)) {
                formatBirthday = FormatHelper.convertStringtoDate(birthday);
            }
            if(!TextUtils.isEmpty(join_date)){
                formatJoinDate = FormatHelper.convertStringtoDate(join_date);
            }
            mViewModel.addEmployee(new Employee("3",name,phone,formatBirthday,Long.parseLong(salary),address,selectRole,selectWorkShift,formatJoinDate,email,"3",false));
            Toast.makeText(requireContext(), "Thêm mới nhân viên thành công!", Toast.LENGTH_SHORT).show();
        }
    }

    //Chọn ngày sinh nhật
    private void chooseDateForBirthday(View view){
        Calendar calendar = Calendar.getInstance();
        int year = calendar.get(Calendar.YEAR);
        int month = calendar.get(Calendar.MONTH);
        int day = calendar.get(Calendar.DAY_OF_MONTH);

        DatePickerDialog datePickerDialog = new DatePickerDialog(
                requireContext(),
                new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                        // Xử lý ngày được chọn ở đây
                        String selectedDate = dayOfMonth + "/" + (monthOfYear + 1) + "/" + year;
                        // Ví dụ: set text cho một TextView
                        layout_input_birthday.getEditText().setText(selectedDate);
                    }
                },
                year, month, day);
        datePickerDialog.show();
    }
    //Chọn ngày vào làm
    private void chooseDateForJoin(View view){
        Calendar calendar = Calendar.getInstance();
        int year = calendar.get(Calendar.YEAR);
        int month = calendar.get(Calendar.MONTH);
        int day = calendar.get(Calendar.DAY_OF_MONTH);

        DatePickerDialog datePickerDialog = new DatePickerDialog(
                requireContext(),
                new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                        // Xử lý ngày được chọn ở đây
                        String selectedDate = dayOfMonth + "/" + (monthOfYear + 1) + "/" + year;
                        // Ví dụ: set text cho một TextView
                        layout_input_birthday.getEditText().setText(selectedDate);
                    }
                },
                year, month, day);
        datePickerDialog.show();
    }
    private boolean validateInput(String name,String email,String password,
                                  String phone, String salary){
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
        if(TextUtils.isEmpty(email)){
            layout_input_email.setError("Vui lòng nhập email!");
            isValid = false;
        }else if(!Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
            layout_input_email.setError("Vui lòng nhập đúng định dạng email!");
            isValid = false;
        }else{
            layout_input_email.setError(null);
        }
        if(TextUtils.isEmpty(password) || password.length()<6){
            layout_input_password.setError("Mật khẩu phải từ 6 kí tự trở lên!");
            isValid = false;
        }else{
            layout_input_password.setError(null);
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
        layout_input_name = view.findViewById(R.id.layout_input_name);
        layout_input_email = view.findViewById(R.id.layout_input_email);
        layout_input_password = view.findViewById(R.id.layout_input_password);
        layout_input_phone = view.findViewById(R.id.layout_input_phone);
        layout_input_salary = view.findViewById(R.id.layout_input_salary);
        layout_input_birthday = view.findViewById(R.id.layout_input_birthday);
        layout_input_join_date = view.findViewById(R.id.layout_input_join_date);
        layout_input_address = view.findViewById(R.id.layout_input_address);
        dropdown_role = view.findViewById(R.id.dropdown_role);
        dropdown_work_shift = view.findViewById(R.id.dropdown_work_shift);
        btn_add_item = view.findViewById(R.id.btn_add_item);
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