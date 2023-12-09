package com.team.team_07_fe.ui.work_shift;

import android.app.AlertDialog;
import android.app.TimePickerDialog;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.AppCompatButton;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.fragment.NavHostFragment;

import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TimePicker;
import android.widget.Toast;

import com.google.android.material.textfield.TextInputLayout;
import com.team.team_07_fe.MainActivity;
import com.team.team_07_fe.R;
import com.team.team_07_fe.models.Customer;
import com.team.team_07_fe.models.WorkShift;
import com.team.team_07_fe.request.CustomerRequest;
import com.team.team_07_fe.request.EmployeeRequest;
import com.team.team_07_fe.request.WorkShiftRepuest;
import com.team.team_07_fe.ui.customer.CustomerUpdateFragment;
import com.team.team_07_fe.utils.FormatHelper;
import com.team.team_07_fe.utils.LoadingDialog;
import com.team.team_07_fe.viewmodels.CustomerViewModel;

import java.util.Calendar;
import java.util.Date;

public class WorkShiftUpdateFragment extends Fragment {

    private AppCompatButton btn_update_item_work,btn_reload_item;
    private WorkShiftViewModel wViewModel;

    private WorkShift originalData = null;
    private LoadingDialog loadingDialog;
    AlertDialog.Builder builder;
    TextInputLayout txtTimeEnd,txtTimeStart,layout_input_name_work_shirt,layout_input_id;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_work_shift_update, container, false);

        wViewModel = new ViewModelProvider(requireActivity()).get(WorkShiftViewModel.class);
        loadingDialog = new LoadingDialog(requireContext());
        //mapping
        mapping(view);
        return view;

    }
    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        observeData();
        if(getArguments()!=null){
            originalData = (WorkShift) getArguments().getSerializable("data_workShift");
            setData(originalData);
        }
//        layout_input_name_work_shirt.setOnClickListener(this::handleAddCalam);
        txtTimeStart.getEditText().setOnClickListener(this::chooseTimeForJoin);
        txtTimeEnd.getEditText().setOnClickListener(this::chooseTimeForEnd);
        btn_update_item_work.setOnClickListener(this::handleUpdateData);
        btn_reload_item.setOnClickListener(this::handleReloadData);
    }

    private void observeData() {
        wViewModel.getErrorMessage().observe(getViewLifecycleOwner(),s -> {
            if(s!=null){
//                loadingDialog.dismiss();
                Toast.makeText(requireContext(), "Cập nhật thông tin thành công!", Toast.LENGTH_SHORT).show();
                refreshFragment();
                wViewModel.setDataMessage(null);
            }
        });
        wViewModel.getErrorMessage().observe(getViewLifecycleOwner(),s->{
            if(s!=null){
                loadingDialog.dismiss();
                Toast.makeText(requireContext(), s, Toast.LENGTH_SHORT).show();
                wViewModel.setDataMessage(null);
            }
        });
    }

    private void showDialogConfirmUpdate(String id, WorkShiftRepuest workShiftRepuest){
        AlertDialog.Builder builder = new AlertDialog.Builder(requireContext())
                .setTitle("Thông báo!")
                .setMessage("Bạn có chắc muốn cập nhật ca làm này không? " +
                        "Mọi thông tin trước đó sẽ không được lưu.")
                .setPositiveButton(R.string.yes,(dialog, which) -> {
                    loadingDialog.show();
                    wViewModel.updateWorkShift(id,workShiftRepuest);
                    dialog.dismiss();
                    refreshFragment();
                })
                .setNegativeButton(R.string.no,((dialog, which) -> {
                    dialog.dismiss();
                }));
        builder.create().show();
    }
    private void setData(WorkShift workShift) {
        if (layout_input_id.getEditText() != null) {
            layout_input_id.getEditText().setText(String.valueOf(workShift.getShift_id()));
        }

        layout_input_name_work_shirt.getEditText().setText(workShift.getName());
        String formattedEndTime = FormatHelper.convertTimeToString(workShift.getTimeEnd());
        txtTimeEnd.getEditText().setText(formattedEndTime);
        String formattedStartEnd = FormatHelper.convertTimeToString(workShift.getTimeStart());

        txtTimeStart.getEditText().setText(formattedStartEnd);
    }

//    private void observeData() {
//        wViewModel.getDataInput().observe(getViewLifecycleOwner(),s -> {
//            if(s!=null){
//                loadingDialog.dismiss();
//                Toast.makeText(requireContext(), "Cập nhật thông tin thành công!", Toast.LENGTH_SHORT).show();
//                refreshFragment();
//                wViewModel.setDataInput(null);
//            }
//        });
//        wViewModel.getErrorMessage().observe(getViewLifecycleOwner(),s->{
//            if(s!=null){
//                loadingDialog.dismiss();
//                Toast.makeText(requireContext(), s, Toast.LENGTH_SHORT).show();
//                wViewModel.setDataInput(null);
//            }
//        });
//    }

    private void refreshFragment() {
        loadingDialog.dismiss();
        NavHostFragment.findNavController(WorkShiftUpdateFragment.this)
                .popBackStack();
    }

//    private void confirmCreateWorkShift(WorkShiftRepuest workShiftRepuest) {
//
//        wViewModel.createWorkShift(workShiftRepuest);
//
//    }
    private void handleReloadData(View view) {
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
//        layout_input_name_work_shirt.getEditText().setText("");
//        txtTimeStart.getEditText().setText("");
//        txtTimeEnd.getEditText().setText("");
    }
    //xử lý sự kiến thêm mới
    private void handleUpdateData(View view){
        String id = layout_input_id.getEditText().getText().toString().trim();
        String name = layout_input_name_work_shirt.getEditText().getText().toString().trim();
        String timeStart = txtTimeStart.getEditText().getText().toString().trim();
        String timeEnd = txtTimeEnd.getEditText().getText().toString().trim();




        if(validateInput(name,timeStart,timeEnd)){
            Date formatTimeStart = null;
            Date formatTimeEnd = null;
            if (!TextUtils.isEmpty(timeStart)) {
                formatTimeStart = (FormatHelper.convertStringToTime(timeStart));
            }
            if(!TextUtils.isEmpty(timeEnd)){
                formatTimeEnd = (FormatHelper.convertStringToTime(timeEnd));
            }
            WorkShiftRepuest workShiftRepuest = new WorkShiftRepuest(name,formatTimeStart,formatTimeEnd);
            showDialogConfirmUpdate(id,workShiftRepuest);
        }

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
        layout_input_id = view.findViewById(R.id.layout_input_id);
        btn_update_item_work = view.findViewById(R.id.btn_update_item_work);
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