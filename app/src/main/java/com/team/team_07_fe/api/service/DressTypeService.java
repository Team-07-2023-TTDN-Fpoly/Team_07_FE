package com.team.team_07_fe.api.service;

import com.team.team_07_fe.api.ApiResponse;
import com.team.team_07_fe.models.DressType;
import com.team.team_07_fe.request.DressTypeRequest;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.DELETE;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.PUT;
import retrofit2.http.Path;
import retrofit2.http.Query;

public interface DressTypeService {
    //Lấy tất cả
    @GET("/api/dresstype")
    Call<ApiResponse<List<DressType>>> getAllDressType(@Query("search")String search);
    //Lấy thông tin của một loại áo
    @GET("/api/dresstype/{id}")
    Call<ApiResponse<DressType>> getDressTypeById(@Path("id") String type_id);
    //Thêm loại áo
    @POST("/api/dresstype")
    Call<ApiResponse<String>> createDressType(@Body DressTypeRequest dressTypeRequest);
    //Cập nhật thông tin loại áo
    @PUT("/api/dresstype/information/{id}")
    Call<ApiResponse<String>> updateDressType(@Path("id")String type_id, @Body DressTypeRequest dressTypeRequest);
    @DELETE("/api/dresstype/{id}")
    Call<ApiResponse<String>> deleteDressType(@Path("id") String type_id);

}
