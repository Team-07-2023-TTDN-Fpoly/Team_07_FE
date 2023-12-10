package com.team.team_07_fe.api.repository;

import android.util.Log;

import androidx.lifecycle.MutableLiveData;

import com.google.gson.Gson;
import com.team.team_07_fe.api.ApiClient;
import com.team.team_07_fe.api.ApiResponse;
import com.team.team_07_fe.api.service.DressTypeService;
import com.team.team_07_fe.models.DressType;
import com.team.team_07_fe.request.DressTypeRequest;

import java.util.List;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class DressTypeRepository {
    private DressTypeService dressTypeService;

    private MutableLiveData<List<DressType>> listDressType = new MutableLiveData<>();
    private MutableLiveData<String> errorMessage = new MutableLiveData<>();
    private MutableLiveData<String> dataInput = new MutableLiveData<>();
    private MutableLiveData<DressType> dataDressType = new MutableLiveData<>();

    public DressTypeRepository(){
        dressTypeService = ApiClient.getClient().create(DressTypeService.class);
    }
    //Getter
    public MutableLiveData<List<DressType>> getListDressType() {
        return listDressType;
    }

    public MutableLiveData<String> getErrorMessage() {
        return errorMessage;
    }

    public MutableLiveData<String> getDataInput() {
        return dataInput;
    }

    public MutableLiveData<DressType> getDataDressType() {
        return dataDressType;
    }

    //xử lý phần thông báo lỗi
    private void handeErrorMessage(ResponseBody errorBody){
        if (errorBody != null) {
            try {
                Gson gson = new Gson();
                ApiResponse error = gson.fromJson(errorBody.string(), ApiResponse.class);
                errorMessage.postValue(error.getMessage());
            } catch (Exception e) {
                Log.i("ERROR",e.getMessage());
                errorMessage.postValue("Lỗi không xác định!");
            }
        }
    }
    //Lệnh để lấy tất cả nhân viên
    public void getAllDressType(String search){
        dressTypeService.getAllDressType(search).enqueue(new Callback<ApiResponse<List<DressType>>>() {
            @Override
            public void onResponse(Call<ApiResponse<List<DressType>>> call, Response<ApiResponse<List<DressType>>> response) {
                if(response.isSuccessful()){
                    ApiResponse<List<DressType>> apiResponse = response.body();
                    listDressType.postValue(apiResponse.getData());
                }else{
                    handeErrorMessage(response.errorBody());
                }
            }
            @Override
            public void onFailure(Call<ApiResponse<List<DressType>>> call, Throwable t) {
                Log.i("ERROR LIST EMPLOYEE",t.getMessage());
                errorMessage.postValue("Lỗi kết nối");
            }
        });
    }

    //Lệnh để lấy nhân viên
    public void getDressType(String id){
        dressTypeService.getDressTypeById(id).enqueue(new Callback<ApiResponse<DressType>>() {
            @Override
            public void onResponse(Call<ApiResponse<DressType>> call, Response<ApiResponse<DressType>> response) {
                if(response.isSuccessful()){
                    ApiResponse<DressType> apiResponse = response.body();
                    dataDressType.postValue(apiResponse.getData());
                }else{
                    handeErrorMessage(response.errorBody());
                }
            }
            @Override
            public void onFailure(Call<ApiResponse<DressType>> call, Throwable t) {
                Log.i("ERROR LIST EMPLOYEE",t.getMessage());
                errorMessage.postValue("Lỗi kết nối");
            }
        });
    }

    //Tạo mới
    public void createDressType(DressTypeRequest dressTypeRequest){
        dressTypeService.createDressType(dressTypeRequest).enqueue(new Callback<ApiResponse<String>>() {
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
                Log.i("ERROR LIST EMPLOYEE",t.getMessage());
                errorMessage.postValue("Lỗi kết nối");
            }
        });
    }
    //Update thông tin loại áo
    public void updateDressType(String id, DressTypeRequest dressTypeRequest){
        dressTypeService.updateDressType(id, dressTypeRequest).enqueue(new Callback<ApiResponse<String>>() {
            @Override
            public void onResponse(Call<ApiResponse<String>> call, Response<ApiResponse<String>> response) {
                if(response.isSuccessful()){
                    ApiResponse<String> apiResponse = response.body();
                    dataInput.postValue(apiResponse.getMessage());
                } else {
                    handeErrorMessage(response.errorBody());
                }
            }
            @Override
            public void onFailure(Call<ApiResponse<String>> call, Throwable t) {
                Log.i("ERROR LIST EMPLOYEE",t.getMessage());
                errorMessage.postValue("Lỗi kết nối");
            }
        });
    }
    public void deleteDressType(String id) {
        dressTypeService.deleteDressType(id).enqueue(new Callback<ApiResponse<String>>() {
            @Override
            public void onResponse(Call<ApiResponse<String>> call, Response<ApiResponse<String>> response) {
                if (response.isSuccessful()) {
                    ApiResponse<String> apiResponse = response.body();
                    dataInput.postValue(apiResponse.getMessage());
                } else {
                    handeErrorMessage(response.errorBody());
                }
            }

            @Override
            public void onFailure(Call<ApiResponse<String>> call, Throwable t) {
                Log.i("ERROR DELETE DRESS TYPE", t.getMessage());
                errorMessage.postValue("Lỗi kết nối");
            }
        });
    }
}
