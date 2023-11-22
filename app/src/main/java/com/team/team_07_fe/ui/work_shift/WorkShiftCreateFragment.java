package com.team.team_07_fe.ui.work_shift;

import android.app.AlertDialog;
import android.app.TimePickerDialog;
import android.content.Context;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;

import com.google.android.material.textfield.TextInputLayout;
import com.team.team_07_fe.R;
import com.team.team_07_fe.models.WorkShift;
import com.team.team_07_fe.utils.FormatHelper;

import java.util.Calendar;

public class WorkShiftCreateFragment extends Fragment {
    Button btnAdd;
    AlertDialog.Builder builder;
    private WorkShiftViewModel model;
    private TextInputLayout layout_input_name,layout_input_timeStart,layout_input_timeEnd;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_work_shift_create, container, false);
        model = new ViewModelProvider(this).get(WorkShiftViewModel.class);
        layout_input_name = view.findViewById(R.id.layout_input_id_work_shirt);
        layout_input_timeStart = view.findViewById(R.id.layout_input_time_start);
        layout_input_timeEnd = view.findViewById(R.id.layout_input_time_end);
        btnAdd = view.findViewById(R.id.btn_update_item_work);
        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        btnAdd.setOnClickListener(v->{
            String name = layout_input_name.getEditText().getText().toString().trim();
            String timeStart = layout_input_timeStart.getEditText().getText().toString().trim();
            String timeEnd = layout_input_timeEnd.getEditText().getText().toString().trim();
            WorkShift workShift = new WorkShift(name,FormatHelper.convertStringToTime(timeStart),FormatHelper.convertStringToTime(timeEnd),"");
            model.createWorkShift(workShift);
        });

        layout_input_timeStart.getEditText().setOnClickListener(v->{
            chooseDate(requireContext(),layout_input_timeStart);
        });
        layout_input_timeEnd.getEditText().setOnClickListener(v->{
            chooseDate(requireContext(),layout_input_timeEnd);
        });
        model.getErrorMessage().observe(getViewLifecycleOwner(),s -> {
            if(s!=null){
                Toast.makeText(requireContext(), s, Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void mapping(View view){

        btnAdd = view.findViewById(R.id.btn_update_item_work);
        builder = new AlertDialog.Builder(requireContext());
//        btnAdd.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                builder.setTitle("Thông báo")
//                        .setMessage("")
//            }
//        });
    }

    private void chooseDate(Context context, TextInputLayout inputLayout){
        final Calendar currentDate = Calendar.getInstance();
        final Calendar date = Calendar.getInstance();
        new TimePickerDialog(context,(timePicker, hourOfDay, minute) -> {
            date.set(Calendar.HOUR_OF_DAY, hourOfDay);
            date.set(Calendar.MINUTE, minute);
            // Định dạng ngày giờ và hiển thị

            inputLayout.getEditText().setText(FormatHelper.convertTimeToString(date.getTime()));
        },currentDate.get(Calendar.HOUR_OF_DAY), currentDate.get(Calendar.MINUTE), true).show();
    }
}