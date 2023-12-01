package com.team.team_07_fe.ui.dresstype;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.team.team_07_fe.api.repository.DressTypeRepository;
import com.team.team_07_fe.models.DressType;
import com.team.team_07_fe.request.DressTypeRequest;

import java.util.List;

public class DressTypeViewModel extends ViewModel {
    private DressTypeRepository dressTypeRepository;
    private MutableLiveData<List<DressType>> listType;
    private MutableLiveData<String> dataInput;
    private MutableLiveData<String> errorMessage;


    public DressTypeViewModel(){
        dressTypeRepository = new DressTypeRepository();
        listType = dressTypeRepository.getListDressType();
        dataInput = dressTypeRepository.getDataInput();
        errorMessage = dressTypeRepository.getErrorMessage();
    }

    //setters

    public void setListType(List<DressType> listType) {
        this.listType.postValue(listType);
    }

    public void setDataInput(String dataInput) {
        this.dataInput.postValue(dataInput);
    }

    public void setErrorMessage(String errorMessage) {
        this.errorMessage.postValue(errorMessage);
    }

    //getters
    public LiveData<List<DressType>> getDressTypeList() {
        return listType;
    }
    public LiveData<String> getDataInput() {
        return dataInput;
    }
    public LiveData<String> getErrorMessage() {
        return errorMessage;
    }

    //
    public void createDressType(DressTypeRequest dressTypeRequest){
        dressTypeRepository.createDressType(dressTypeRequest);
    }
    public void updateDressType(String id, DressTypeRequest dressTypeRequest) {
        dressTypeRepository.updateDressType(id,dressTypeRequest);
    }
    public void deleteDressType(String id) {
        dressTypeRepository.deleteDressType(id);
    }
    public void getAllDressType(String search){
        dressTypeRepository.getAllDressType(search);
    }


}
