package com.team.team_07_fe.ui.customer;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.team.team_07_fe.models.Customer;
import com.team.team_07_fe.models.Employee;
import com.team.team_07_fe.models.WorkShift;

import java.time.LocalTime;
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
        List<Customer> currentList = livecus.getValue();
        if (currentList != null) {
            currentList.remove(customer);
            livecus.setValue(currentList);
        }
    }
    public void AddCustomer(Customer customer) {
        List<Customer> customers = livecus.getValue();
        if (customers != null) {
            customers.add(customer);
            livecus.setValue(customers);
        }
    }
    public void updateCustomer(int index, Customer customer) {
        List<Customer> currentList = livecus.getValue();
        if (currentList != null) {
            currentList.set(index, customer);
            livecus.setValue(currentList);
        }
    }
//    public void updateCustomer(int index, Customer customer) {
//        List<Customer> currentList = livecus.getValue();
//        if (currentList != null && index >= 0 && index < currentList.size()) {
//            currentList.set(index, customer);
//            livecus.setValue(currentList);
//        }
//    }
private void Listviewcustomer() {
    List<Customer> customers = new ArrayList<>();
    customers.add(new Customer(1, "Nguyễn Văn A", "0123456789", "0987654321", "a@gmail.com", FormatHelper.convertStringtoDate("1/2/1990"), "Hà Nội"));
    customers.add(new Customer(2, "Nguyễn Văn B", "0123456789", "0987654321", "b@gmail.com", FormatHelper.convertStringtoDate("1/2/1995"), "Hồ Chí Minh"));
    livecus.setValue(customers);
}

}