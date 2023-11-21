package com.team.team_07_fe.api.service;

import com.team.team_07_fe.api.ApiResponse;
import com.team.team_07_fe.models.Employee;
import com.team.team_07_fe.request.EmployeeRequest;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.PUT;
import retrofit2.http.Path;

public interface EmployeeService {
    //Lấy tất cả nhân viên
    @GET("/api/employee")
    Call<ApiResponse<List<Employee>>> getAllEmployee();
    //Lấy thông tin của một nhân viên
    @GET("/api/employee/{id}")
    Call<ApiResponse<Employee>> getEmployeeById(@Path("id") String emp_id);
    //Tạo tài khoản cho nhân viên
    @POST("/api/employee")
    Call<ApiResponse<String>> createEmployeeAndAuth(@Body EmployeeRequest employeeRequest);
    //Cập nhật thông tin nhân viên
    @PUT("/api/employee/information/{id}")
    Call<ApiResponse<String>> updateEmployee(@Path("id")String emp_id, @Body EmployeeRequest employeeRequest);
}
