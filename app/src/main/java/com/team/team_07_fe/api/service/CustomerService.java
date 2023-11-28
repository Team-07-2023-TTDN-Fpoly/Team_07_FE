package com.team.team_07_fe.api.service;

import com.team.team_07_fe.api.ApiResponse;
import com.team.team_07_fe.models.Customer;
import com.team.team_07_fe.request.CustomerRequest;


import java.util.List;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.DELETE;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.PUT;
import retrofit2.http.Path;
import retrofit2.http.Query;

public interface CustomerService {
    @GET("/api/Customer")
    Call<ApiResponse<List<Customer>>> getAllCustomers(@Query("search")String search);
    //Lấy thông tin của một khách hàng
    @GET("/api/customer/{id}")
    Call<ApiResponse<Customer>> getCustomerById(@Path("id") String cus_id);
    //Tạo tài khoản cho khách hanàng
    @POST("/api/customer")
    Call<ApiResponse<String>> createCustomer(@Body CustomerRequest customerRequest);
    //Cập nhật thông tin khách hàng
    @PUT("/api/customer/information/{id}")
    Call<ApiResponse<String>> updateCustomer(@Path("id")String cus_id, @Body CustomerRequest customerRequest);
    @DELETE("/api/customer/{id}")
    Call<ApiResponse<Customer>> deleteCustomer(@Path("id") String cus_id);

}
