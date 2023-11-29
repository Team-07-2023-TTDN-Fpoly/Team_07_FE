package com.team.team_07_fe.ui.employee;

import android.app.AlertDialog;
import android.app.DatePickerDialog;
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
import android.util.Log;
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
import com.team.team_07_fe.request.EmployeeRequest;
import com.team.team_07_fe.ui.work_shift.WorkShiftViewModel;
import com.team.team_07_fe.utils.FormatHelper;
import com.team.team_07_fe.utils.LoadingDialog;
import com.team.team_07_fe.viewmodels.EmployeeViewModel;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

//Người tạo: NghiaTC
public class EmployeeUpdateFragment extends Fragment {
    private TextInputLayout layout_input_id,layout_input_name,layout_input_email,
            layout_input_phone,layout_input_salary,layout_input_birthday,layout_input_join_date,layout_input_address;
    private AutoCompleteTextView dropdown_role,dropdown_work_shift;
    private EmployeeViewModel mViewModel;
    private AppCompatButton btn_reload_item,btn_update_item;
    private String[] listRole = {Role.ADMIN,Role.EMPLOYEE};
    private List<WorkShift> listWorkShift;
    private ArrayAdapter<String> roleAdapter;
    private ArrayAdapter<WorkShift> workShiftAdapter;
    private String selectRole ;
    private WorkShift selectWorkShift = null;
    private Employee originalData =null;
    private LoadingDialog loadingDialog;
    private WorkShiftViewModel workShiftViewModel;
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
        workShiftViewModel = new ViewModelProvider(requireActivity()).get(WorkShiftViewModel.class);
        loadingDialog = new LoadingDialog(requireContext());
        //mapping
        mapping(view);
        //
        listWorkShift = new ArrayList<>();
        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        workShiftViewModel.getAllWorkShift(null);
        //
        observeData();

        roleAdapter = new ArrayAdapter<>(requireActivity(),android.R.layout.simple_dropdown_item_1line,listRole);
        dropdown_role.setAdapter(roleAdapter);
        //
        workShiftAdapter = new ArrayAdapter<>(requireActivity(),android.R.layout.simple_dropdown_item_1line,listWorkShift);
        dropdown_work_shift.setAdapter(workShiftAdapter);


        //Click button
        btn_reload_item.setOnClickListener(this::handleReloadData);
        btn_update_item.setOnClickListener(this::handleUpdateData);
        //
        layout_input_birthday.getEditText().setOnClickListener(this::chooseDateForBirthday);
        layout_input_join_date.getEditText().setOnClickListener(this::chooseDateForJoin);

        if(getArguments()!=null){
            Employee employee = (Employee) getArguments().getSerializable("data_employee");
            originalData = employee;
            setData(employee);
        }

        dropdown_role.setOnItemClickListener((adapterView, view1, i, l) ->{
            selectRole = (String) adapterView.getItemAtPosition(i);
        });
        dropdown_work_shift.setOnItemClickListener((adapterView, view1, i, l) -> {
            selectWorkShift = (WorkShift) adapterView.getItemAtPosition(i);
        });

    }
    //Xử lý sự kiện với viewModel
    private void observeData(){
        mViewModel.getDataInput().observe(getViewLifecycleOwner(),s -> {
            if(s!=null){
                loadingDialog.dismiss();
                Toast.makeText(requireContext(), "Cập nhật thông tin thành công!", Toast.LENGTH_SHORT).show();
                refreshFragment();
                mViewModel.setDataInput(null);
            }
        });
        mViewModel.getErrorMessage().observe(getViewLifecycleOwner(),s->{
            if(s!=null){
                loadingDialog.dismiss();
                Toast.makeText(requireContext(), s, Toast.LENGTH_SHORT).show();
                mViewModel.setDataInput(null);
            }
        });
        workShiftViewModel.getWorkShiftList().observe(getViewLifecycleOwner(),workShifts -> {
            if(workShifts!=null){
                listWorkShift.clear();
                listWorkShift.addAll(workShifts);
                workShiftAdapter.notifyDataSetChanged();

            }
        });
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
                        layout_input_join_date.getEditText().setText(selectedDate);
                    }
                },
                year, month, day);
        datePickerDialog.show();
    }
    //Reload lai data
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
    //Xử lý sự kiện cập nhật thông tin nhân viên
    private void handleUpdateData(View view){
        String name = layout_input_name.getEditText().getText().toString().trim();
        String id = originalData.getEmp_id();
        String phone = layout_input_phone.getEditText().getText().toString().trim();
        String birthday = layout_input_birthday.getEditText().getText().toString().trim();
        String join_date = layout_input_join_date.getEditText().getText().toString().trim();
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
            EmployeeRequest employeeRequest = new EmployeeRequest(name,phone,formatBirthday,Long.parseLong(salary),address,selectRole,selectWorkShift.getShift_id(),formatJoinDate);
            showDialogConfirmUpdate(id,employeeRequest);
        }

    }
    private void showDialogConfirmUpdate(String id, EmployeeRequest employeeRequest){
        AlertDialog.Builder builder = new AlertDialog.Builder(requireContext())
                .setTitle("Thông báo!")
                .setMessage("Bạn có chắc muốn cập nhật nhân viên này không? " +
                        "Mọi thông tin trước đó sẽ không được lưu.")
                .setPositiveButton(R.string.yes,(dialog, which) -> {
                    loadingDialog.show();
                    mViewModel.updateEmployee(id,employeeRequest);
                    dialog.dismiss();
                })
                .setNegativeButton(R.string.no,((dialog, which) -> {
                    dialog.dismiss();
                }));
        builder.create().show();
    }
    //Set dữ liệu
    private void setData(Employee employee) {
        Log.i("WORKSHIFT",employee.getWorkShift().toString());
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
        // Set lại ngày sinh - bạn cần định dạng lại Date thành String
        if (employee.getEmp_birthday() != null) {
            String birthdayStr = FormatHelper.convertDatetoString(employee.getEmp_birthday());
            layout_input_birthday.getEditText().setText(birthdayStr);
        }
        if (employee.getJoin_date() != null) {
            String join_date = FormatHelper.convertDatetoString(employee.getJoin_date());
            layout_input_join_date.getEditText().setText(join_date);
        }
        // Set lại lương
        layout_input_salary.getEditText().setText(String.valueOf(employee.getBasic_salary()));
        // Set lại địa chỉ
        layout_input_address.getEditText().setText(employee.getEmp_address());

        // Set lại vai trò
        if (!employee.getRole().isEmpty()) { // Kiểm tra xem có tìm thấy vai trò trong listRole hay không
            dropdown_role.setText(employee.getRole(), false);
            selectRole = employee.getRole(); // Cập nhật biến selectRole
        }

        if(employee.getWorkShift()!=null){
            selectWorkShift = employee.getWorkShift();
            dropdown_work_shift.setText(employee.getWorkShift().toString(),false);
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

        if(selectRole.isEmpty()){
            isValid = false;
            Toast.makeText(requireContext(), "Vui lòng chọn chức vụ!", Toast.LENGTH_SHORT).show();
        }

        if(selectWorkShift == null){
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