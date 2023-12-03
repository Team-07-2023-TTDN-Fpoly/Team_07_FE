package com.team.team_07_fe.api.service;

import com.team.team_07_fe.api.ApiResponse;
import com.team.team_07_fe.models.Dress;
import com.team.team_07_fe.request.DressRequest;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.DELETE;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.PUT;
import retrofit2.http.Path;

public interface DressService {
    @POST ("/api/dress")
    Call<ApiResponse<String>> addDress(@Body DressRequest dressRequest);
    @PUT("/api/dress/{id}")
    Call<ApiResponse<String>> updateDress(@Path("id") String id, @Body DressRequest dressRequest );
    @DELETE("/api/dress/{id}")
    Call<ApiResponse<Dress>> deleteDress(@Path("id") String id);
}
