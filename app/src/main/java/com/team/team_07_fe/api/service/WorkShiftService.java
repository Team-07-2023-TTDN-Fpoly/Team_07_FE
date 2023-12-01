package com.team.team_07_fe.api.service;

import com.team.team_07_fe.api.ApiResponse;
import com.team.team_07_fe.models.Customer;
import com.team.team_07_fe.models.WorkShift;
import com.team.team_07_fe.request.CustomerRequest;
import com.team.team_07_fe.request.WorkShiftRepuest;

import java.util.Date;
import java.util.List;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.DELETE;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.PUT;
import retrofit2.http.Path;
import retrofit2.http.Query;

public interface WorkShiftService {

    //Lấy tất cả 
    @GET("/api/workshift")
    Call<ApiResponse<List<WorkShift>>> getAllWorkShift(@Query("search")String search);
    //Tạo tài khoản cho 
    @POST("/api/workshift")
    @FormUrlEncoded
    Call<ApiResponse<String>> createWorkShift(@Field("name") String name,
                                              @Field("timeStart") Date timeStart,
                                              @Field("timeEnd") Date timeEnd,
                                              @Field("shift_description") String description);
    @DELETE("/api/workshift/{id}")
    Call<ApiResponse<String>> deleteWorkShift(@Path("id") String shift_id);
    @PUT("/api/workshift/information/{id}")
    Call<ApiResponse<String>> updateWorkShift(@Path("id")String shift_id, @Body WorkShiftRepuest workShiftRepuest);

}
