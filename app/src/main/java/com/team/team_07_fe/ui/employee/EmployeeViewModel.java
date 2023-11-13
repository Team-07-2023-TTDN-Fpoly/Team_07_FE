package com.team.team_07_fe.ui.employee;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.team.team_07_fe.models.Employee;
import com.team.team_07_fe.models.WorkShift;

import java.time.LocalTime;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class EmployeeViewModel extends ViewModel {

    private MutableLiveData<List<Employee>> listEmp;
    public EmployeeViewModel(){
        listEmp = new MutableLiveData<>();
        initializeExampleList();
    }

    public LiveData<List<Employee>> getEmployeeList() {
        return listEmp;
    }

    public void addEmployee(Employee employee) {
        List<Employee> currentList = listEmp.getValue();
        if (currentList != null) {
            currentList.add(employee);
            listEmp.setValue(currentList);
        }
    }

    public void removeEmployee(Employee employee) {
        List<Employee> currentList = listEmp.getValue();
        if (currentList != null) {
            currentList.remove(employee);
            listEmp.setValue(currentList);
        }
    }

    public void updateEmployee(int index, Employee employee) {
        List<Employee> currentList = listEmp.getValue();
        if (currentList != null) {
            currentList.set(index, employee);
            listEmp.setValue(currentList);
        }
    }

    private void initializeExampleList() {
        List<Employee> employees = new ArrayList<>();
        // Tạo và thêm một số đối tượng Employee mẫu vào danh sách
        WorkShift morningShift = new WorkShift(1, "Ca sáng", LocalTime.of(8, 0), LocalTime.of(16, 0), "Làm việc buổi sáng");
        WorkShift eveningShift = new WorkShift(2, "Ca chiều", LocalTime.of(16, 0), LocalTime.of(0, 0), "Làm việc buổi tối");

        employees.add(new Employee(1, "Nguyen Van A", "0123456789", new Date(), 5000000, "Hà Nội", "Nhân viên", morningShift, new Date(), "a@example.com"));
        employees.add(new Employee(2, "Tran Thi B", "0987654321", new Date(), 6000000, "TP HCM", "Quản lý", eveningShift, new Date(), "b@example.com"));
        // Thêm thêm nhân viên mẫu tại đây nếu cần

        listEmp.setValue(employees); // Cập nhật MutableLiveData
    }
}