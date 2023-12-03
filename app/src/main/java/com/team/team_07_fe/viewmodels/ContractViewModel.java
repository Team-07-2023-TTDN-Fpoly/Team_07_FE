package com.team.team_07_fe.viewmodels;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.team.team_07_fe.api.repository.ContractRepository;
import com.team.team_07_fe.models.Contract;
import com.team.team_07_fe.models.Customer;

import java.util.ArrayList;
import java.util.List;

public class ContractViewModel extends ViewModel {
    private ContractRepository contractRepository;

    private MutableLiveData<List<Contract>> listContract;
    private MutableLiveData<String> errorMessage;

    public ContractViewModel(){
        contractRepository = new ContractRepository();
        listContract = contractRepository.getListContract();
        errorMessage = contractRepository.getErrorMessage();
    }


    public LiveData<List<Contract>> getListContract() {
        return listContract;
    }
    public LiveData<String> getErrorMessage() {
        return errorMessage;
    }

    public void getAllContracts(String name){
        contractRepository.getAllContract(name);
    }
}
