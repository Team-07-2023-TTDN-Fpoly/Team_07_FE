package com.team.team_07_fe.viewmodels;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.team.team_07_fe.api.repository.DetailStatisticsRepository;
import com.team.team_07_fe.models.DetailStatistics;
import com.team.team_07_fe.models.Statistic;
import com.team.team_07_fe.request.DetailStatisticsRequest;

import java.util.Date;
import java.util.List;

public class DetailStatisticsViewModel extends ViewModel {
    private DetailStatisticsRepository detailStatisticsRepository;
    private MutableLiveData<List<Statistic>> listDt;
    private MutableLiveData<String> dataInput;
    private MutableLiveData<String> errorMessage;
    public DetailStatisticsViewModel(){
        detailStatisticsRepository = new DetailStatisticsRepository();
        listDt = detailStatisticsRepository.getListDetail();
        dataInput = detailStatisticsRepository.getDataInput();
        errorMessage = detailStatisticsRepository.getErrorMessage();
    }
    public LiveData<List<Statistic>> getListDetailStatistics()
    {return listDt;}

    //setter
    public void setListDt(MutableLiveData<List<Statistic>> listDt) {
        this.listDt = listDt;
    }

    public void setDataInput(MutableLiveData<String> dataInput) {
        this.dataInput = dataInput;
    }

    public void setErrorMessage(String errorMessage) {
        this.errorMessage.postValue(errorMessage);
    }
    //getter
    public LiveData<List<Statistic>> getListDt() {
        return listDt;
    }

    public LiveData<String> getDataInput() {
        return dataInput;
    }

    public LiveData<String> getErrorMessage() {
        return errorMessage;
    }

    public void addDetailStatistics(DetailStatisticsRequest detailStatisticsRequest) {
        detailStatisticsRepository.CreateDetail(detailStatisticsRequest);
    }
    public void updateDetailStatistics(String id , DetailStatisticsRequest detailStatisticsRequest) {
        detailStatisticsRepository.UpdateDetail(id,detailStatisticsRequest);
    }



    public void getAllDetail(){
        detailStatisticsRepository.getAllDetail();
    }
}
