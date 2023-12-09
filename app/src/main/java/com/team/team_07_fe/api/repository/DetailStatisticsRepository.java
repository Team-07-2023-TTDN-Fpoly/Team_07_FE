package com.team.team_07_fe.api.repository;

import android.util.Log;

import androidx.lifecycle.MutableLiveData;

import com.google.gson.Gson;
import com.team.team_07_fe.api.ApiClient;
import com.team.team_07_fe.api.ApiResponse;
import com.team.team_07_fe.api.service.CustomerService;
import com.team.team_07_fe.api.service.DetailStatisticsService;
import com.team.team_07_fe.models.Customer;
import com.team.team_07_fe.models.DetailStatistics;
import com.team.team_07_fe.models.Statistic;
import com.team.team_07_fe.request.CustomerRequest;
import com.team.team_07_fe.request.DetailStatisticsRequest;

import java.util.Date;
import java.util.List;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class DetailStatisticsRepository {
    private DetailStatisticsService detailStatisticsService;
    private MutableLiveData<List<Statistic>> listDetail = new MutableLiveData<>();
    private MutableLiveData<String> errorMessage = new MutableLiveData<>();

    private MutableLiveData<String> dataInput = new MutableLiveData<>();
    private MutableLiveData<DetailStatistics> dataDetail = new MutableLiveData<>();

    public DetailStatisticsRepository() {
        detailStatisticsService = ApiClient.getClient().create(DetailStatisticsService.class);
    }

    //
    public MutableLiveData<List<Statistic>> getListDetail() {
        return listDetail;
    }

    public MutableLiveData<String> getErrorMessage() {
        return errorMessage;
    }

    public MutableLiveData<String> getDataInput() {
        return dataInput;
    }

    public MutableLiveData<DetailStatistics> getDataDetail() {
        return dataDetail;
    }

    //thông báo lỗi
    private void handeErrorMessage(ResponseBody errorBody) {
        if (errorBody != null) {
            try {
                Gson gson = new Gson();
                ApiResponse error = gson.fromJson(errorBody.string(), ApiResponse.class);
                errorMessage.postValue(error.getMessage());
            } catch (Exception e) {
                Log.i("ERROR", e.getMessage());
                errorMessage.postValue("Lỗi không xác định!");
            }
        }
    }

    public void getAllDetail() {
        detailStatisticsService.getAllDetailStatistics().enqueue(new Callback<ApiResponse<List<Statistic>>>() {
            @Override
            public void onResponse(Call<ApiResponse<List<Statistic>>> call, Response<ApiResponse<List<Statistic>>> response) {
                if (response.isSuccessful()) {
                    ApiResponse<List<Statistic>> apiResponse = response.body();
                    listDetail.postValue(apiResponse.getData());
                } else {
                    handeErrorMessage(response.errorBody());
                }
            }

            @Override
            public void onFailure(Call<ApiResponse<List<Statistic>>> call, Throwable t) {
                Log.i("ERROR LIST DETAIL", t.getMessage());
                errorMessage.postValue("Lỗi kết nối");
            }
        });

    }

    //Thêm mới
    public void CreateDetail(DetailStatisticsRequest detailStatisticsRequest) {
        detailStatisticsService.createDetail(detailStatisticsRequest).enqueue(new Callback<ApiResponse<String>>() {
            @Override
            public void onResponse(Call<ApiResponse<String>> call, Response<ApiResponse<String>> response) {
                if (response.isSuccessful()) {
                    ApiResponse<String> apiResponse = response.body();
                    String deletedData = apiResponse.getData();
                    // Thực hiện các hành động với deletedData (ví dụ: cập nhật giao diện người dùng, thông báo thành công, vv.)
                    dataInput.postValue(deletedData);
                } else {
                    handeErrorMessage(response.errorBody());
                }
            }

            @Override
            public void onFailure(Call<ApiResponse<String>> call, Throwable t) {

            }
        });
    }

    //Cập nhật
    public void UpdateDetail(String id, DetailStatisticsRequest detail){
        detailStatisticsService.updateDetail(id,detail).enqueue(new Callback<ApiResponse<String>>() {
            @Override
            public void onResponse(Call<ApiResponse<String>> call, Response<ApiResponse<String>> response) {
                if(response.isSuccessful()){
                    ApiResponse<String> apiResponse = response.body();
                    dataInput.postValue(apiResponse.getData());
                }else{
                    handeErrorMessage(response.errorBody());
                }
            }

            @Override
            public void onFailure(Call<ApiResponse<String>> call, Throwable t) {

            }
        });
    }


}

