package com.team.team_07_fe.api.repository;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.team.team_07_fe.api.ApiClient;
import com.team.team_07_fe.api.ApiResponse;
import com.team.team_07_fe.api.service.EmployeeService;
import com.team.team_07_fe.models.Employee;

import java.util.List;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class EmployeeRepository {
    private EmployeeService employeeService;

    public EmployeeRepository(){
        employeeService = ApiClient.getClient().create(EmployeeService.class);
    }

//    public LiveData<List<Employee>> getAllEmployee(){
//        final MutableLiveData<List<Employee>> liveData = new MutableLiveData<>();
//        employeeService.getAllEmployee().enqueue(new Callback<ApiResponse<List<Employee>>>() {
//            @Override
//            public void onResponse(Call<ApiResponse<List<Employee>>> call, Response<ApiResponse<List<Employee>>> response) {
//                if(response.isSuccessful()){
//                    ApiResponse<List<Employee>> apiResponse = response.body();
//                    liveData.postValue(apiResponse.getData());
//                }else{
//                    ResponseBody errorBody = response.errorBody();
//                    try {
//
//                    }catch (Exception e){
//
//                    }
//                }
//            }
//
//            @Override
//            public void onFailure(Call<ApiResponse<List<Employee>>> call, Throwable t) {
//
//            }
//        });
//        return
//    }
}
