package com.team.team_07_fe.ui.contract;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import org.jetbrains.annotations.Contract;

import java.util.List;

public class ContractViewModel extends ViewModel {

    private MutableLiveData<List<Contract>> contracts = new MutableLiveData<>();

    public void setContracts(List<Contract> contractList) {
        contracts.setValue(contractList);
    }

    public LiveData<List<Contract>> getContracts() {
        return contracts;
    }
}
