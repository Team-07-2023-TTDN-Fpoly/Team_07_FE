package com.team.team_07_fe.api.repository;

import android.util.Log;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.google.gson.Gson;
import com.team.team_07_fe.api.ApiClient;
import com.team.team_07_fe.api.ApiResponse;
import com.team.team_07_fe.api.service.EmployeeService;
import com.team.team_07_fe.models.Employee;
import com.team.team_07_fe.request.EmployeeRequest;

import java.util.List;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class EmployeeRepository {
    private EmployeeService employeeService;

    private MutableLiveData<List<Employee>> listEmployee = new MutableLiveData<>();
    private MutableLiveData<String> errorMessage = new MutableLiveData<>();
    private MutableLiveData<String> dataInput = new MutableLiveData<>();
    private MutableLiveData<Employee> dataEmployee = new MutableLiveData<>();

    public EmployeeRepository(){
        employeeService = ApiClient.getClient().create(EmployeeService.class);
    }
    //Getter
    public MutableLiveData<List<Employee>> getListEmployee() {
        return listEmployee;
    }

    public MutableLiveData<String> getErrorMessage() {
        return errorMessage;
    }

    public MutableLiveData<String> getDataInput() {
        return dataInput;
    }

    public MutableLiveData<Employee> getDataEmployee() {
        return dataEmployee;
    }

    //xử lý phần thông báo lỗi
    private void handeErrorMessage(ResponseBody errorBody){
        if (errorBody != null) {
            try {
                Gson gson = new Gson();
                ApiResponse error = gson.fromJson(errorBody.string(), ApiResponse.class);
                errorMessage.postValue(error.getMessage());
            } catch (Exception e) {
                Log.i("ERROR",e.getMessage());
                errorMessage.postValue("Lỗi không xác định!");
            }
        }
    }
    //Lệnh để lấy tất cả nhân viên
    public void getAllEmployee(String search){
        employeeService.getAllEmployee(search).enqueue(new Callback<ApiResponse<List<Employee>>>() {
            @Override
            public void onResponse(Call<ApiResponse<List<Employee>>> call, Response<ApiResponse<List<Employee>>> response) {
                if(response.isSuccessful()){
                    ApiResponse<List<Employee>> apiResponse = response.body();
                    listEmployee.postValue(apiResponse.getData());
                }else{
                    handeErrorMessage(response.errorBody());
                }
            }
            @Override
            public void onFailure(Call<ApiResponse<List<Employee>>> call, Throwable t) {
                Log.i("ERROR LIST EMPLOYEE",t.getMessage());
                errorMessage.postValue("Lỗi kết nối");
            }
        });
    }

    //Lệnh để lấy nhân viên
    public void getEmployee(String id){
        employeeService.getEmployeeById(id).enqueue(new Callback<ApiResponse<Employee>>() {
            @Override
            public void onResponse(Call<ApiResponse<Employee>> call, Response<ApiResponse<Employee>> response) {
                if(response.isSuccessful()){
                    ApiResponse<Employee> apiResponse = response.body();
                    dataEmployee.postValue(apiResponse.getData());
                }else{
                    handeErrorMessage(response.errorBody());
                }
            }
            @Override
            public void onFailure(Call<ApiResponse<Employee>> call, Throwable t) {
                Log.i("ERROR LIST EMPLOYEE",t.getMessage());
                errorMessage.postValue("Lỗi kết nối");
            }
        });
    }

    //Tạo mới nhân viên
    public void createEmployee(EmployeeRequest employeeRequest){
        employeeService.createEmployeeAndAuth(employeeRequest).enqueue(new Callback<ApiResponse<String>>() {
            @Override
            public void onResponse(Call<ApiResponse<String>> call, Response<ApiResponse<String>> response) {
                if(response.isSuccessful()){
                ApiResponse<String> apiResponse = response.body();
                dataInput.postValue(apiResponse.getData());
                }else{
                    handeErrorMessage(response.errorBody());
                }
            }

            @Override
            public void onFailure(Call<ApiResponse<String>> call, Throwable t) {
                Log.i("ERROR LIST EMPLOYEE",t.getMessage());
                errorMessage.postValue("Lỗi kết nối");
            }
        });
    }
    //Update thông tin nhân vin
    public void updateEmployee(String id,EmployeeRequest employeeRequest){
        employeeService.updateEmployee(id,employeeRequest).enqueue(new Callback<ApiResponse<String>>() {
            @Override
            public void onResponse(Call<ApiResponse<String>> call, Response<ApiResponse<String>> response) {
                if(response.isSuccessful()){
                    ApiResponse<String> apiResponse = response.body();
                    dataInput.postValue(apiResponse.getData());
                }else{
                    handeErrorMessage(response.errorBody());
                }
            }
            @Override
            public void onFailure(Call<ApiResponse<String>> call, Throwable t) {
                Log.i("ERROR LIST EMPLOYEE",t.getMessage());
                errorMessage.postValue("Lỗi kết nối");
            }
        });
    }
}
