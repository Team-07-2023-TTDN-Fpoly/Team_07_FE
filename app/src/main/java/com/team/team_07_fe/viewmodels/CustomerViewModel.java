package com.team.team_07_fe.viewmodels;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.team.team_07_fe.models.Customer;
import com.team.team_07_fe.models.Employee;
import com.team.team_07_fe.models.WorkShift;
import com.team.team_07_fe.utils.FormatHelper;

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
//    public void AddCustomer(Customer customer) {
//        List<Customer> customers = livecus.getValue();
//        if (customers != null) {
//            customers.add(customer);
//            livecus.setValue(customers);
//        }
//    }
public void AddCustomer(Customer customer) {
    List<Customer> customers = livecus.getValue();
    if (customers != null) {
        // Tìm giá trị ID lớn nhất trong danh sách khách hàng hiện tại
        int maxId = 0;
        for (Customer c : customers) {
            if (c.getCus_id() > maxId) {
                maxId = c.getCus_id();
            }
        }

        // Tăng giá trị ID lên 1 và gán cho khách hàng mới
        int newId = maxId + 1;
        customer.setCus_id(newId);

        // Thêm khách hàng mới vào danh sách khách hàng và cập nhật LiveData
        customers.add(customer);
        livecus.setValue(customers);
    }
}
    public void updateCustomer(int index, Customer customer) {
        List<Customer> currentList = livecus.getValue();
        if (currentList != null && index >= 0 && index < currentList.size()) {
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
    customers.add(new Customer(0, "Nguyễn Văn An", "0123456789", "0987654321", "an1999@gmail.com", FormatHelper.convertStringtoDate("1/2/2023"), "Đà Nẵng"));
    customers.add(new Customer(1, "Nguyễn Thanh Bình", "0123456789", "0987654321", "binh@gmail.com", FormatHelper.convertStringtoDate("1/12/2023"), "Đà Nẵng"));
    customers.add(new Customer(2, "Nguyễn Thanh Liên", "0123456789", "0987654321", "binh@gmail.com", FormatHelper.convertStringtoDate("1/12/2023"), "Đà Nẵng"));
    customers.add(new Customer(3, "Nguyễn Hữu Thọ", "0123456789", "0987654321", "binh@gmail.com", FormatHelper.convertStringtoDate("1/12/2023"), "Đà Nẵng"));
    livecus.setValue(customers);
}

}