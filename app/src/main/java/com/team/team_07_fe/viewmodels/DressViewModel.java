package com.team.team_07_fe.viewmodels;

import android.net.Uri;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.team.team_07_fe.api.repository.DressRepository;
import com.team.team_07_fe.models.Dress;
import com.team.team_07_fe.request.DressRequest;


import java.io.File;
import java.util.List;

import okhttp3.RequestBody;

public class DressViewModel extends ViewModel {
    private DressRepository dressRepository;
    private MutableLiveData<List<Dress>> listDress;
    private MutableLiveData<String> dataInput;
    private MutableLiveData<String> errorMessage;
    private MutableLiveData<String> dataMessage;
    public DressViewModel(){
        dressRepository = new DressRepository();
        listDress = dressRepository.getListDress();
        dataInput = dressRepository.getDataInput();
        errorMessage = dressRepository.getErrorMessage();
        dataMessage = dressRepository.getDataMessage();
    }

    public void setListDress(List<Dress> listDress) {
        this.listDress.postValue(listDress);
    }

    public void setDataInput(String dataInput) {
        this.dataInput.postValue(dataInput);
    }
    public void setDataMessage(String dataInput) {
        this.dataMessage.postValue(dataInput);
    }

    public void setErrorMessage(String errorMessage) {
        this.errorMessage.postValue(errorMessage);
    }

    public MutableLiveData<List<Dress>> getListDress() {
        if(listDress ==null){
            listDress = dressRepository.getListDress();
        }
        return listDress;
    }

    public MutableLiveData<String> getDataInput() {
        return dataInput;
    }
    public MutableLiveData<String> getDataMessage() {
        return dataMessage;
    }

    public MutableLiveData<String> getErrorMessage() {
        return errorMessage;
    }

    public void getAllDress(String search){
        dressRepository.getAllDress(search);
    }
    public void addDress(RequestBody mUri, File tempFile, String name, String type, String color, String size, Long price, String des){
        dressRepository.addDress(mUri,tempFile,name,type,color,size,price,des);
    }
    public void updateDress(String id,RequestBody mUri, File tempFile, String name, String type, String color, String size, Long price, String des,String status){
        dressRepository.updateDress(id, mUri,tempFile,name,type,color,size,price,des,status);
    }
    public void deleteDress(String id){
        dressRepository.deleteDress(id);
    }
}
