package com.team.team_07_fe.api.service;

import com.team.team_07_fe.api.ApiResponse;
import com.team.team_07_fe.models.Contract;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface ContractService {

    @GET("/api/contract/")
    Call<ApiResponse<List<Contract>>> getAllContracts(@Query("search")String search);
}
