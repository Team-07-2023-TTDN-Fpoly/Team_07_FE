package com.team.team_07_fe.ui.work_shift;

import android.app.AlertDialog;
import android.app.TimePickerDialog;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.AppCompatButton;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.Navigation;
import androidx.navigation.fragment.NavHostFragment;

import android.text.TextUtils;
import android.util.Patterns;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;
import com.team.team_07_fe.R;
import com.team.team_07_fe.adapter.WorkShiftAdapter;
import com.team.team_07_fe.anotition.Role;
import com.team.team_07_fe.models.Employee;
import com.team.team_07_fe.models.WorkShift;
import com.team.team_07_fe.request.CustomerRequest;
import com.team.team_07_fe.request.WorkShiftRepuest;
import com.team.team_07_fe.utils.FormatHelper;
import com.team.team_07_fe.viewmodels.CustomerViewModel;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class WorkShiftCreateFragment extends Fragment {
    private AppCompatButton btnAdd,btn_reload_item;

    TextInputLayout txtTimeEnd,txtTimeStart,layout_input_name_work_shirt;
    AlertDialog.Builder builder;
    WorkShiftAdapter workShiftAdapter;
    private WorkShiftViewModel wViewModel;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_work_shift_create, container, false);
        mapping(view);
        wViewModel = new ViewModelProvider(requireActivity()).get(WorkShiftViewModel.class);

        return view;
    }
    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
//        layout_input_name_work_shirt.setOnClickListener(this::handleAddCalam);
        txtTimeStart.getEditText().setOnClickListener(this::chooseTimeForJoin);
        txtTimeEnd.getEditText().setOnClickListener(this::chooseTimeForEnd);
        btnAdd.setOnClickListener(this::handleAddWork);
        btn_reload_item.setOnClickListener(this::handleReloadData);
    }
    private void handleReloadData(View view) {
        layout_input_name_work_shirt.getEditText().setText("");
        txtTimeStart.getEditText().setText("");
        txtTimeEnd.getEditText().setText("");
    }

    //xử lý sự kiến thêm mới
    private void handleAddWork(View view){

        String name = layout_input_name_work_shirt.getEditText().getText().toString().trim();
        String TimeStart = txtTimeStart.getEditText().getText().toString().trim();
        String TimeEnd = txtTimeEnd.getEditText().getText().toString().trim();

        if(validateInput(name, TimeStart, TimeEnd)){
            Date formatEndTime = new Date();
            Date formatStartTime = new Date();

            if (!TextUtils.isEmpty(TimeStart)) {
                formatStartTime = FormatHelper.convertStringToTime(TimeStart);
            }

            if(!TextUtils.isEmpty(TimeEnd)){
                formatEndTime = FormatHelper.convertStringToTime(TimeEnd);
            }

            // Kiểm tra xem TimeStart và TimeEnd có trùng nhau không
            if (!formatStartTime.equals(formatEndTime)) {
                // Nếu không trùng nhau, tạo đối tượng WorkShiftRepuest và tiếp tục xử lý
                WorkShiftRepuest workShiftRepuest = new WorkShiftRepuest(name, formatStartTime, formatEndTime);
                confirmCreateWorkShift(workShiftRepuest);
            } else {
                // Nếu trùng nhau, hiển thị thông báo hoặc thực hiện hành động phù hợp
                Toast.makeText(requireContext(), "Giờ bắt đầu và giờ kết thúc không được trùng nhau.", Toast.LENGTH_SHORT).show();
            }
        }
    }


    private void confirmCreateWorkShift(WorkShiftRepuest workShiftRepuest) {

        wViewModel.createWorkShift(workShiftRepuest);

        NavHostFragment.findNavController(this).popBackStack();
        wViewModel.setDataMessage(null);

        Toast.makeText(requireContext(), "Thêm mới thành công!", Toast.LENGTH_SHORT).show();

    }

    private boolean validateInput(String name,String TimeStart,String TimeEnd){
        boolean isValid = true;

        if(TextUtils.isEmpty(name)){
            layout_input_name_work_shirt.setError("Vui lòng nhập tên ca làm !");
            isValid = false;
        }else{
            layout_input_name_work_shirt.setError(null);
        }
        if(TextUtils.isEmpty(TimeStart)){
            txtTimeStart.setError("Vui lòng nhập giờ bắt đầu");
            isValid = false;
        }else{
            txtTimeStart.setError(null);
        }

        if(TextUtils.isEmpty(TimeEnd)){
            txtTimeEnd.setError("Vui lòng nhập giờ kết thúc!");
            isValid = false;
        }else{
            txtTimeEnd.setError(null);
        }

        return isValid;
    }
    private void mapping(View view){

        btnAdd = view.findViewById(R.id.btn_create_item_work);
        btn_reload_item = view.findViewById(R.id.btn_reload_item);
        txtTimeEnd = view.findViewById(R.id.layout_input_gio_ket_thuc);
        txtTimeStart = view.findViewById(R.id.layout_input_gio_bat_dau);
        layout_input_name_work_shirt = view.findViewById(R.id.layout_input_name_work_shirt);
        builder = new AlertDialog.Builder(requireContext());
    }
    private void chooseTimeForJoin(View view){
        Calendar calendar = Calendar.getInstance();
        int hour = calendar.get(Calendar.HOUR_OF_DAY);
        int minute = calendar.get(Calendar.MINUTE);

        TimePickerDialog timePickerDialog = new TimePickerDialog(
                requireContext(),
                new TimePickerDialog.OnTimeSetListener() {
                    @Override
                    public void onTimeSet(TimePicker view, int hourOfDay, int minuteOfHour) {
                        // Xử lý thời gian được chọn ở đây
                        String selectedTime = hourOfDay + ":" + minuteOfHour;
                        // Ví dụ: set text cho một TextView
                        txtTimeStart.getEditText().setText(selectedTime);
                    }
                },
                hour, minute, true);
        timePickerDialog.show();
    }
    private void chooseTimeForEnd(View view) {
        Calendar calendar = Calendar.getInstance();
        int hour = calendar.get(Calendar.HOUR_OF_DAY);
        int minute = calendar.get(Calendar.MINUTE);

        TimePickerDialog timePickerDialog = new TimePickerDialog(
                requireContext(),
                new TimePickerDialog.OnTimeSetListener() {
                    @Override
                    public void onTimeSet(TimePicker view, int hourOfDay, int minuteOfHour) {
                        // Xử lý thời gian được chọn ở đây
                        String selectedTime = hourOfDay + ":" + minuteOfHour;
                        // Ví dụ: set text cho một TextView
                        txtTimeEnd.getEditText().setText(selectedTime);
                    }
                },
                hour, minute, true);
        timePickerDialog.show();

    }
}