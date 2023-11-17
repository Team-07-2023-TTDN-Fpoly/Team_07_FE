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
        customers.add(new Customer(1,"nguye van a","0355555","0265555","a@gmail.com", FormatHelper.convertStringtoDate("1/2/2012"),"ha noi"));
        customers.add(new Customer(1,"nguye van b","03555955","026995555","ab@gmail.com",FormatHelper.convertStringtoDate("1/2/2015"),"ha noi"));
        livecus.setValue(customers);
    }
    // TODO: Implement the ViewModel
    private MutableLiveData<List<Customer>> listCus;

//    public LiveData<List<Customer>> getCustomerList() {
//        return listCus;
//    }

    public void addCustomer(Customer customer) {
        List<Customer> currentList = listCus.getValue();
        if (currentList != null) {
            currentList.add(customer);
            listCus.setValue(currentList);
        }
    }

    public void removeEmployee(Customer customer) {
        List<Customer> currentList = listCus.getValue();
        if (currentList != null) {
            currentList.remove(customer);
            listCus.setValue(currentList);
        }
    }

    public void updateEmployee(int index, Customer customer) {
        List<Customer> currentList = listCus.getValue();
        if (currentList != null) {
            currentList.set(index, customer);
            listCus.setValue(currentList);
        }
    }

//    private void initializeExampleList() {
//        List<Employee> employees = new ArrayList<>();
//        // Tạo và thêm một số đối tượng Employee mẫu vào danh sách
//        WorkShift morningShift = new WorkShift(1, "Ca sáng", LocalTime.of(8, 0), LocalTime.of(16, 0), "Làm việc buổi sáng");
//        WorkShift eveningShift = new WorkShift(2, "Ca chiều", LocalTime.of(16, 0), LocalTime.of(0, 0), "Làm việc buổi tối");
//
//        employees.add(new Employee(0, "Nguyen Van A", "0123456789", new Date(), 5000000, "Hà Nội", "Nhân viên", morningShift, new Date(), "a@example.com"));
//        employees.add(new Employee(1, "Tran Thi B", "0987654321", new Date(), 6000000, "TP HCM", "Quản lý", eveningShift, new Date(), "b@example.com"));
//        // Thêm thêm nhân viên mẫu tại đây nếu cần
//
//        listCus.setValue(employees); // Cập nhật MutableLiveData
//    }
}