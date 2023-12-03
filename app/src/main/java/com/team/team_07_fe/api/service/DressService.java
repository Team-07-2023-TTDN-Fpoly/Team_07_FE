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
import retrofit2.http.Query;

public interface DressService {
    @GET("/api/dresses/")
    Call<ApiResponse<List<Dress>>> getAllDress(@Query("search")String search);
    @POST ("/api/dresses/")
    Call<ApiResponse<String>> addDress(@Body DressRequest dressRequest);
    @PUT("/api/dresses/{id}")
    Call<ApiResponse<String>> updateDress(@Path("id") String id, @Body DressRequest dressRequest );
    @DELETE("/api/dresses/{id}")
    Call<ApiResponse<Dress>> deleteDress(@Path("id") String id);
}
