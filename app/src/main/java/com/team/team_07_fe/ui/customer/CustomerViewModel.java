package com.team.team_07_fe.ui.customer;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.team.team_07_fe.models.Customer;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class CustomerViewModel extends ViewModel {
    private MutableLiveData<List<Customer>> livecus;

    public CustomerViewModel() {
        livecus= new MutableLiveData<>();
        Listviewcustomer();
    }
    public LiveData<List<Customer>> getCustomerList(){
        return livecus;
    }

    public void DeleteCustomer(Customer customer){
        List<Customer> customers= livecus.getValue();
        if (customers != null){
            customers.remove(customers);
            livecus.setValue(customers);
        }
    }
    public void AddCustomer(Customer customer){
        List<Customer> customers=livecus.getValue();
        if (customers != null){
            customers.add((Customer) customers);
        }
    }
    private void Listviewcustomer(){
        List<Customer> customers = new ArrayList<>();
        customers.add(new Customer(1,"nguye van a","0355555","0265555","1/2/2012","a@gmail.com","ha noi"));
        customers.add(new Customer(1,"nguye van b","03555955","026995555","1/2/2015","ab@gmail.com","ha noi"));
        livecus.setValue(customers);
    }
}