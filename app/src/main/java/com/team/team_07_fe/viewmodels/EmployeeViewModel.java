package com.team.team_07_fe.viewmodels;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.team.team_07_fe.api.repository.EmployeeRepository;
import com.team.team_07_fe.models.Employee;
import com.team.team_07_fe.models.WorkShift;
import com.team.team_07_fe.request.EmployeeRequest;
import com.team.team_07_fe.utils.FormatHelper;

import java.time.LocalTime;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class EmployeeViewModel extends ViewModel {
    private EmployeeRepository employeeRepository;
    private MutableLiveData<List<Employee>> listEmp;
    private MutableLiveData<String> dataInput;
    private MutableLiveData<String> errorMessage;
    public EmployeeViewModel(){
        employeeRepository = new EmployeeRepository();
        listEmp = employeeRepository.getListEmployee();
        dataInput = employeeRepository.getDataInput();
        errorMessage = employeeRepository.getErrorMessage();
    }

    public LiveData<List<Employee>> getEmployeeList() {
        return listEmp;
    }
    public LiveData<String> getDataInput() {
        return dataInput;
    }
    public LiveData<String> getErrorMessage() {
        return errorMessage;
    }

    //
    public void createEmployee(EmployeeRequest employeeRequest){
        employeeRepository.createEmployee(employeeRequest);
    }
    public void updateEmployee(String id, EmployeeRequest employeeRequest){
        employeeRepository.updateEmployee(id,employeeRequest);
    }
    public void getAllEmployee(){
        employeeRepository.getAllEmployee();
    }
}