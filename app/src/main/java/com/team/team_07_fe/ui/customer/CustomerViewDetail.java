package com.team.team_07_fe.ui.customer;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.team.team_07_fe.models.Customer;

import java.util.List;

public class CustomerViewDetail extends ViewModel {
    private MutableLiveData<List<Customer>> listCus;
    public CustomerViewDetail(){
        listCus = new MutableLiveData<>();

    }
    public void addDetail(Customer customer) {
        List<Customer> currentList = listCus.getValue();
        if (currentList != null) {
            currentList.add(customer);
            listCus.setValue(currentList);
        }

    }

    public void removeDetail(Customer customer) {
        List<Customer> currentList = listCus.getValue();
        if (currentList != null) {
            currentList.remove(customer);
            listCus.setValue(currentList);
        }
    }
    public void updateDetail(int index, Customer customer) {
        List<Customer> currentList = listCus.getValue();
        if (currentList != null) {
            currentList.set(index, customer);
            listCus.setValue(currentList);
        }
    }
}
