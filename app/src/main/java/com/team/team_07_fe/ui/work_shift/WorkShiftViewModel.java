package com.team.team_07_fe.ui.work_shift;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.team.team_07_fe.api.repository.WorkShiftRepository;
import com.team.team_07_fe.models.WorkShift;
import com.team.team_07_fe.request.WorkShiftRepuest;

import java.util.ArrayList;
import java.util.List;

public class WorkShiftViewModel extends ViewModel {

    private MutableLiveData<List<WorkShift>> listWork;
    private MutableLiveData<String> dataMessage;
    private MutableLiveData<String> errorMessage;
    private WorkShiftRepository repository;
    public WorkShiftViewModel(){
        repository = new WorkShiftRepository();
        listWork = repository.getListWorkShift();
        dataMessage = repository.getDataInput();
        errorMessage = repository.getErrorMessage();
//        initializeExampleList();
    }
    public LiveData<List<WorkShift>> getWorkShiftList() {
        return listWork;
    }

    public LiveData<String> getDataMessage() {
        return dataMessage;
    }
    public LiveData<String> getErrorMessage() {
        return errorMessage;
    }
    public void setErrorMessage(String errorMessage) {
        this.errorMessage.postValue(errorMessage);
    }
    public void createWorkShift(WorkShiftRepuest workShift){
        repository.createWorkShift(workShift);
    }
    public void deleteWorkShift(String id) {
        repository.deleteWorkShift(id);
    }
    public void getAllWorkShift(String search){
        repository.getAllWorkShift(search);
    }
    private void initializeExampleList() {
        List<WorkShift> workShifts = new ArrayList<>();


//        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
//            workShifts.add(new WorkShift(1, "Ca sáng", FormatHelper.convertStringToTime("12:00"), FormatHelper.convertStringToTime("12:00"), "Làm việc buổi sáng"));
//            workShifts.add(new WorkShift(1, "Ca sáng", FormatHelper.convertStringToTime("12:00"), FormatHelper.convertStringToTime("12:00"), "Làm việc buổi sáng"));
//            workShifts.add(new WorkShift(1, "Ca sáng", FormatHelper.convertStringToTime("12:00"), FormatHelper.convertStringToTime("12:00"), "Làm việc buổi sáng"));
//        }

        listWork.setValue(workShifts);
    }

}

