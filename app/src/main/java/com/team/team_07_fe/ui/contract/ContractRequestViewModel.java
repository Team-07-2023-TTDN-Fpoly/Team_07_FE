package com.team.team_07_fe.ui.contract;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.team.team_07_fe.models.ContractDetail;
import com.team.team_07_fe.request.ContractDetailRequest;

import java.util.List;

public class ContractRequestViewModel extends ViewModel {

    private MutableLiveData<List<ContractDetailRequest>> contractDetailRequestList =new MutableLiveData<>();
    private MutableLiveData<ContractDetailRequest> selectContractDetailRequest = new MutableLiveData<>();

    public ContractRequestViewModel(){
        contractDetailRequestList.setValue(null);
        selectContractDetailRequest.setValue(null);
    }

    public MutableLiveData<List<ContractDetailRequest>> getContractDetailRequestList() {
        return contractDetailRequestList;
    }

    public void setContractDetailRequestList(List<ContractDetailRequest> contractDetailRequestList) {
        this.contractDetailRequestList.postValue(contractDetailRequestList);
    }
}
