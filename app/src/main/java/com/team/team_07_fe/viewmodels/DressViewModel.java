package com.team.team_07_fe.viewmodels;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.team.team_07_fe.models.Dress;


import java.util.List;

public class DressViewModel extends ViewModel {
    private MutableLiveData<List<Dress>> listDress;

    public DressViewModel (){
        listDress = new MutableLiveData<>();
        ListViewDress();
    }
    public LiveData<List<Dress>> getDressList() {
        return listDress;
    }
    public void addDress(Dress dress) {
        List<Dress> currentList = listDress.getValue();
        if (currentList != null) {
            currentList.add(dress);
            listDress.setValue(currentList);
        }
    }
    public void removeDress(Dress dress) {
        List<Dress> currentList = listDress.getValue();
        if (currentList != null) {
            currentList.remove(dress);
            listDress.setValue(currentList);
        }
    }
    public void updateDress(int index, Dress dress) {
        List<Dress> currentList = listDress.getValue();
        if (currentList != null) {
            currentList.set(index, dress);
            listDress.setValue(currentList);
        }
    }
    private void ListViewDress(){

    }
}
