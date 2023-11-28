package com.team.team_07_fe.viewmodels;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.team.team_07_fe.models.DetailStatistics;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class DetailStatisticsViewModel extends ViewModel {
    private MutableLiveData<List<DetailStatistics>> listDt;
    public DetailStatisticsViewModel(){
        listDt = new MutableLiveData<>();
        initializeExampleList();
    }
    public LiveData<List<DetailStatistics>> getListDetailStatistics()
    {return listDt;}
    public void addDetailStatistics(DetailStatistics detailStatistics) {
        List<DetailStatistics> currentList = listDt.getValue();
        if (currentList != null) {
            currentList.add(detailStatistics);
            listDt.setValue(currentList);
        }
    }
    public void updateDetailStatistics(int index, DetailStatistics detailStatistics) {
        List<DetailStatistics> currentList = listDt.getValue();
        if (currentList != null) {
            currentList.set(index, detailStatistics);
            listDt.setValue(currentList);
        }
    }


    private void initializeExampleList(){


        List<DetailStatistics> detailStatistics= new ArrayList<>();
        detailStatistics.add(new DetailStatistics(new Date(), "Nguyễn Văn A", 100000, "abc"));
        detailStatistics.add(new DetailStatistics(new Date(), "Nguyễn Văn B", 1000000, "abc"));
        detailStatistics.addAll(detailStatistics);

    }
}
