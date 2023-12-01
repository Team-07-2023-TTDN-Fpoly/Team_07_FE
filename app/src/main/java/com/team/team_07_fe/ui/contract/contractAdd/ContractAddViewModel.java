package com.team.team_07_fe.ui.contract.contractAdd;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.team.team_07_fe.models.Customer;

import java.util.ArrayList;
import java.util.List;

public class ContractAddViewModel extends ViewModel {

    private MutableLiveData<List<Customer>> listCustomer = new MutableLiveData<>();
    private MutableLiveData<Customer> selectCustomer = new MutableLiveData<>();
    public ContractAddViewModel(){
        listCustomer.postValue(new ArrayList<>());
        selectCustomer.postValue(new Customer());
    }

    public MutableLiveData<List<Customer>> getListCustomer() {
        return listCustomer;
    }

    public void setListCustomer(List<Customer> listCustomer) {
        this.listCustomer.postValue(listCustomer);
    }

    public MutableLiveData<Customer> getSelectCustomer() {
        return selectCustomer;
    }

    public void setSelectCustomer(Customer selectCustomer) {
        this.selectCustomer.postValue(selectCustomer);
    }
}
