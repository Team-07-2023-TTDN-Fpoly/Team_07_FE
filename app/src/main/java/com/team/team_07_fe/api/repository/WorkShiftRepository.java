package com.team.team_07_fe.api.repository;

import android.util.Log;

import androidx.lifecycle.MutableLiveData;

import com.google.gson.Gson;
import com.team.team_07_fe.api.ApiClient;
import com.team.team_07_fe.api.ApiResponse;
import com.team.team_07_fe.api.service.WorkShiftService;
import com.team.team_07_fe.models.WorkShift;

import java.util.List;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class WorkShiftRepository {
    private WorkShiftService workShiftService;

    private MutableLiveData<List<WorkShift>> listWorkShift = new MutableLiveData<>();
    private MutableLiveData<String> errorMessage = new MutableLiveData<>();
    private MutableLiveData<String> dataInput = new MutableLiveData<>();
    private MutableLiveData<WorkShift> dataWorkShift = new MutableLiveData<>();

    public WorkShiftRepository(){
        workShiftService = ApiClient.getClient().create(WorkShiftService.class);
    }
    //Getter
    public MutableLiveData<List<WorkShift>> getListWorkShift() {
        return listWorkShift;
    }

    public MutableLiveData<String> getErrorMessage() {
        return errorMessage;
    }

    public MutableLiveData<String> getDataInput() {
        return dataInput;
    }

    public MutableLiveData<WorkShift> getDataWorkShift() {
        return dataWorkShift;
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
    //Lệnh để lấy tất cả 
    public void getAllWorkShift(String search){
        workShiftService.getAllWorkShift(search).enqueue(new Callback<ApiResponse<List<WorkShift>>>() {
            @Override
            public void onResponse(Call<ApiResponse<List<WorkShift>>> call, Response<ApiResponse<List<WorkShift>>> response) {
                if(response.isSuccessful()){
                    ApiResponse<List<WorkShift>> apiResponse = response.body();
                    listWorkShift.postValue(apiResponse.getData());
                }else{
                    handeErrorMessage(response.errorBody());
                }
            }

            @Override
            public void onFailure(Call<ApiResponse<List<WorkShift>>> call, Throwable t) {
                Log.i("ERROR LIST",t.getMessage());
                errorMessage.postValue("Lỗi kết nối");
            }
        });
    }

    //Lệnh để lấy 
    public void getWorkShift(String id){
    }

    //Tạo mới 
    public void createWorkShift(WorkShift workShiftRequest){
        workShiftService.createWorkShift(workShiftRequest.getName(),workShiftRequest.getTimeStart(),workShiftRequest.getTimeEnd(),workShiftRequest.getShift_description()).enqueue(new Callback<ApiResponse<String>>() {
            @Override
            public void onResponse(Call<ApiResponse<String>> call, Response<ApiResponse<String>> response) {
                if(response.isSuccessful()  && response.body() != null){
                    ApiResponse<String> apiResponse = response.body();
                    dataInput.postValue(apiResponse.getData());
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
            public void onFailure(Call<ApiResponse<String>> call, Throwable t) {
                Log.i("ERROR CREATE ",t.getMessage());
                errorMessage.postValue("Lỗi kết nối");
            }
        });
    }
}
