package com.team.team_07_fe.ui.contract;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.team.team_07_fe.models.Customer;
import com.team.team_07_fe.models.Dress;

import java.util.ArrayList;
import java.util.List;

public class ContractHandleViewModel extends ViewModel {

    private MutableLiveData<List<Customer>> listCustomer = new MutableLiveData<>();
    private MutableLiveData<Customer> selectCustomer = new MutableLiveData<>();

    private MutableLiveData<Dress> selectDress = new MutableLiveData<>();
    public ContractHandleViewModel(){
        listCustomer.postValue(null);
        selectCustomer.postValue(null);
        selectDress.setValue(null);
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

    public MutableLiveData<Dress> getSelectDress() {
        return selectDress;
    }
    public void setSelectDress(Dress selectDress) {
        this.selectDress.postValue(selectDress);
    }
}
