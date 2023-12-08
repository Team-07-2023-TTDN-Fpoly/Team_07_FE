package com.team.team_07_fe.api.repository;

import android.util.Log;

import androidx.annotation.NonNull;
import androidx.lifecycle.MutableLiveData;

import com.google.gson.Gson;
import com.team.team_07_fe.api.ApiClient;
import com.team.team_07_fe.api.ApiResponse;
import com.team.team_07_fe.api.service.AuthService;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class AuthRepository {
    AuthService authService;

    private MutableLiveData<String> messageData = new MutableLiveData<>();
    private MutableLiveData<String> errorMessage = new MutableLiveData<>();

    public AuthRepository(){
        authService = ApiClient.getClient().create(AuthService.class);
    }

    //Getters
    public MutableLiveData<String> getMessageData() {
        return messageData;
    }

    public MutableLiveData<String> getErrorMessage() {
        return errorMessage;
    }

    //xử lý phần thông báo lỗi
    private void handeErrorMessage(ResponseBody errorBody){
        if (errorBody != null) {
            try {
                Gson gson = new Gson();
                ApiResponse error = gson.fromJson(errorBody.string(), ApiResponse.class);
                errorMessage.postValue(error.getMessage());
            } catch (Exception e) {
                e.printStackTrace();
                errorMessage.postValue("Lỗi không xác định!");
            }
        }
    }

    public void adminChangePassword(String id,String newPassword){
        authService.adminChangePassword(id,newPassword).enqueue(new Callback<ApiResponse<Void>>() {
            @Override
            public void onResponse(Call<ApiResponse<Void>> call, @NonNull Response<ApiResponse<Void>> response) {
                if(response.isSuccessful()&& response.body()!=null){
                    ApiResponse<Void> apiResponse = response.body();
                    messageData.postValue(apiResponse.getMessage());
                }else{
                    try {
                        Gson gson = new Gson();
                        ApiResponse error = gson.fromJson(response.errorBody().string(), ApiResponse.class);
                        errorMessage.postValue(error.getMessage());
                    } catch (Exception e) {
                        e.printStackTrace();
                        errorMessage.postValue("Lỗi không xác định!");
                    }
                }
            }
            @Override
            public void onFailure(Call<ApiResponse<Void>> call, Throwable t) {
                Log.i("ERROR",t.getMessage());
                errorMessage.postValue("Lỗi kết nối!");
            }
        });
    }
    public void disableAccount(String id,boolean disable){
        authService.disableAccount(id,disable).enqueue(new Callback<ApiResponse<Void>>() {
            @Override
            public void onResponse(Call<ApiResponse<Void>> call, @NonNull Response<ApiResponse<Void>> response) {
                if(response.isSuccessful()&& response.body()!=null){
                    ApiResponse<Void> apiResponse = response.body();
                    messageData.postValue(apiResponse.getMessage());
                }else{
                    try {
                        Gson gson = new Gson();
                        ApiResponse error = gson.fromJson(response.errorBody().string(), ApiResponse.class);
                        errorMessage.postValue(error.getMessage());
                    } catch (Exception e) {
                        e.printStackTrace();
                        errorMessage.postValue("Lỗi không xác định!");
                    }
                }
            }
            @Override
            public void onFailure(Call<ApiResponse<Void>> call, Throwable t) {
                Log.i("ERROR",t.getMessage());
                errorMessage.postValue("Lỗi kết nối!");
            }
        });
    }
    public void changePassword(String id, String oldPassword, String newPassword, String checkPassword){
        authService.changePassword(id, oldPassword, newPassword, checkPassword).enqueue(new Callback<ApiResponse<Void>>(){
            @Override
            public void onResponse(Call<ApiResponse<Void>> call, Response<ApiResponse<Void>> response) {
                if(response.isSuccessful()&& response.body()!=null){
                    ApiResponse<Void> apiResponse = response.body();
                    messageData.postValue(apiResponse.getMessage());
                }else{
                    try {
                        Gson gson = new Gson();
                        ApiResponse error = gson.fromJson(response.errorBody().string(), ApiResponse.class);
                        errorMessage.postValue(error.getMessage());
                    } catch (Exception e) {
                        e.printStackTrace();
                        errorMessage.postValue("Lỗi không xác định!");
                    }
                }
            }
            @Override
            public void onFailure(Call<ApiResponse<Void>> call, Throwable t) {
                Log.i("ERROR",t.getMessage());
                errorMessage.postValue("Lỗi kết nối!");
            }
        });
    }
}
