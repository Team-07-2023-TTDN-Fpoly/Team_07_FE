package com.team.team_07_fe.api.service;

import com.team.team_07_fe.api.ApiResponse;
import com.team.team_07_fe.models.Contract;
import com.team.team_07_fe.request.ContractRequest;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Query;

public interface ContractService {

    @GET("/api/contract/")
    Call<ApiResponse<List<Contract>>> getAllContracts(@Query("search")String search);

    @POST("/api/contract/")
    Call<ApiResponse<String>> createContract(@Body ContractRequest contractRequest);
}
