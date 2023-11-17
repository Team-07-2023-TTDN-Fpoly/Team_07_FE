package com.team.team_07_fe.api.service;

import com.team.team_07_fe.api.ApiResponse;

import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.PUT;
import retrofit2.http.Path;

public interface AuthService {

    //Quản lý thay đổi mật khẩu nhân viên
    @PUT("api/auth/change/password/{id}")
    @FormUrlEncoded
    Call<ApiResponse<Void>> adminChangePassword(@Path("id") String auth_id, @Field("newPassword") String newPassword);
    //Vô hiệu hóa hoặc mở khóa tài khoản
    @PUT("api/auth/change/disable/{id}")
    @FormUrlEncoded
    Call<ApiResponse<Void>> disableAccount(@Path("id") String auth_id,@Field("disable") boolean disable);
}
