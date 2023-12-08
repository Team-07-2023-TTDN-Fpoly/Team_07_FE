package com.team.team_07_fe.ui.contract;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.team.team_07_fe.models.ContractDetail;
import com.team.team_07_fe.request.ContractDetailRequest;

import java.util.ArrayList;
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

    /**
     * @param contractDetail - thông tin thuê áo cưới
     * @desc Thêm mới dress vào contract
     */
    public void addDressToListContractDetail(ContractDetail contractDetail){
        ContractDetailRequest contractDetailRequest = new ContractDetailRequest(contractDetail.getDress().getId(),contractDetail.getRental_date(),contractDetail.getReturn_date());

        List<ContractDetailRequest> listRequest = contractDetailRequestList.getValue();
        if (listRequest == null) {
            listRequest = new ArrayList<>();
        }

        if(!listRequest.contains(contractDetailRequest)){
        listRequest.add(contractDetailRequest);
        contractDetailRequestList.setValue(listRequest);
        }

    }

    /**
     * @param dress - thông tin thuê áo cưới
     * @desc loại bỏ dress ra khỏi contract
     */
    public void removeDress(ContractDetail dress) {
        List<ContractDetailRequest> currentDresses = contractDetailRequestList.getValue();
        if (dress != null) {
            for (ContractDetailRequest request: currentDresses) {
                if(request.getDress_id().equals(dress.getDress().getId())){
                    currentDresses.remove(request);
                }
            }
            contractDetailRequestList.setValue(currentDresses);
        }
    }

    public void resetAllData(){
        contractDetailRequestList.setValue(null);
        selectContractDetailRequest.setValue(null);
    }
}
