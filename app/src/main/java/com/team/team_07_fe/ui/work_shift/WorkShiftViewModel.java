package com.team.team_07_fe.ui.work_shift;

import android.os.Build;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.team.team_07_fe.models.WorkShift;
import com.team.team_07_fe.utils.FormatHelper;

import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;

public class WorkShiftViewModel extends ViewModel {
    private MutableLiveData<List<WorkShift>> listWork;
    public WorkShiftViewModel(){
        listWork = new MutableLiveData<>();
        initializeExampleList();
    }
    public LiveData<List<WorkShift>> getWorkShiftList() {
        return listWork;
    }

    private void initializeExampleList() {
        List<WorkShift> workShifts = new ArrayList<>();


        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            workShifts.add(new WorkShift(1, "Ca sáng", FormatHelper.convertStringToTime("12:00"), FormatHelper.convertStringToTime("12:00"), "Làm việc buổi sáng"));
            workShifts.add(new WorkShift(1, "Ca sáng", FormatHelper.convertStringToTime("12:00"), FormatHelper.convertStringToTime("12:00"), "Làm việc buổi sáng"));
            workShifts.add(new WorkShift(1, "Ca sáng", FormatHelper.convertStringToTime("12:00"), FormatHelper.convertStringToTime("12:00"), "Làm việc buổi sáng"));
        }

        listWork.setValue(workShifts);
    }
}

