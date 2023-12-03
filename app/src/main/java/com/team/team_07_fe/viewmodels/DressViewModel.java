package com.team.team_07_fe.viewmodels;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.team.team_07_fe.api.repository.DressRepository;
import com.team.team_07_fe.models.Dress;
import com.team.team_07_fe.request.DressRequest;


import java.util.List;

public class DressViewModel extends ViewModel {
    private DressRepository dressRepository;
    private MutableLiveData<List<Dress>> listDress;
    private MutableLiveData<String> dataInput;
    private MutableLiveData<String> errorMessage;
    public DressViewModel(){
        dressRepository = new DressRepository();
        listDress = dressRepository.getListDress();
        dataInput = dressRepository.getDataInput();
        errorMessage = dressRepository.getErrorMessage();
    }

    public void setListDress(MutableLiveData<List<Dress>> listDress) {
        this.listDress = listDress;
    }

    public void setDataInput(MutableLiveData<String> dataInput) {
        this.dataInput = dataInput;
    }

    public void setErrorMessage(MutableLiveData<String> errorMessage) {
        this.errorMessage = errorMessage;
    }

    public MutableLiveData<List<Dress>> getListDress() {
        return listDress;
    }

    public MutableLiveData<String> getDataInput() {
        return dataInput;
    }

    public MutableLiveData<String> getErrorMessage() {
        return errorMessage;
    }
    public void addDress(DressRequest dressRequest){
        dressRepository.addDress(dressRequest);
    }
    public void updateDress(String id, DressRequest dressRequest){
        dressRepository.updateDress(id, dressRequest);
    }
    public void deleteDress(String id,DressRequest dressRequest){
        dressRepository.deleteDress(id, dressRequest);
    }
}
