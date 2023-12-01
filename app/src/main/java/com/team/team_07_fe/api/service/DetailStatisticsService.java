package com.team.team_07_fe.api.service;

import com.team.team_07_fe.api.ApiResponse;
import com.team.team_07_fe.models.Customer;
import com.team.team_07_fe.models.DetailStatistics;
import com.team.team_07_fe.models.Statistic;
import com.team.team_07_fe.request.CustomerRequest;
import com.team.team_07_fe.request.DetailStatisticsRequest;

import java.util.Date;
import java.util.List;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.PUT;
import retrofit2.http.Path;
import retrofit2.http.Query;

public interface DetailStatisticsService {
    //lấy danh sách khoản chi
    @GET("/api/detailstatistics/")
    Call<ApiResponse<List<Statistic>>> getAllDetailStatistics();
    //Thêm mới khoản chi
    @POST ("/api/detailstatistics/")
    Call<ApiResponse<String>> createDetail(@Body DetailStatisticsRequest detailStatisticsRequest);
    //Cập nhật khoản chi
    @PUT("api/detailstatistics/information/{id}")
    Call<ApiResponse<String>> updateDetail(@Path("id")String id, @Body DetailStatisticsRequest detailStatisticsRequest);
}
