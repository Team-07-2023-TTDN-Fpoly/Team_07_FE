package com.team.team_07_fe.viewmodels;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.team.team_07_fe.api.repository.ContractRepository;
import com.team.team_07_fe.models.Contract;
import com.team.team_07_fe.models.Customer;
import com.team.team_07_fe.request.ContractRequest;

import java.util.ArrayList;
import java.util.List;

public class ContractViewModel extends ViewModel {
    private ContractRepository contractRepository;

    private MutableLiveData<List<Contract>> listContract;
    private MutableLiveData<String> errorMessage;
    private MutableLiveData<String> dataMessage;
    private MutableLiveData<String> dataInput;

    public ContractViewModel(){
        contractRepository = new ContractRepository();
        listContract = contractRepository.getListContract();
        errorMessage = contractRepository.getErrorMessage();
        dataInput = contractRepository.getDataInput();
    }


    public LiveData<List<Contract>> getListContract() {
        if(listContract == null){
            listContract = contractRepository.getListContract();
        }

        return listContract;
    }

    public void setErrorMessage(String errorMessage) {
        this.errorMessage.postValue(errorMessage);
    }

    public void setDataMessage(String dataMessage) {
        this.dataMessage.postValue(dataMessage);
    }

    public void setDataInput(String dataInput) {
        this.dataInput.postValue(dataInput);
    }

    public LiveData<String> getErrorMessage() {
        return errorMessage;
    }
    public LiveData<String> getDataMessage() {
        return dataMessage;
    }
    public LiveData<String> getDataInput() {
        return dataInput;
    }


    public void getAllContracts(String name){
        contractRepository.getAllContract(name);
    }
    public void createContract(ContractRequest contractRequest){
        contractRepository.createContract(contractRequest);
    }
}
