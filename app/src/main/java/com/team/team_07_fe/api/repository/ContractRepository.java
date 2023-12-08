package com.team.team_07_fe.api.repository;

import android.util.Log;

import androidx.lifecycle.MutableLiveData;

import com.google.gson.Gson;
import com.team.team_07_fe.api.ApiClient;
import com.team.team_07_fe.api.ApiResponse;
import com.team.team_07_fe.api.service.ContractService;
import com.team.team_07_fe.models.Contract;
import com.team.team_07_fe.request.ContractRequest;

import java.util.List;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ContractRepository {
    private ContractService contractService;

    private MutableLiveData<List<Contract>> listContract = new MutableLiveData<>();
    private MutableLiveData<String> errorMessage = new MutableLiveData<>();
    private MutableLiveData<String> dataInput = new MutableLiveData<>();
    private MutableLiveData<Contract> dataContract = new MutableLiveData<>();

    public ContractRepository(){
        contractService = ApiClient.getClient().create(ContractService.class);
    }
    //Getter
    public MutableLiveData<List<Contract>> getListContract() {
        return listContract;
    }

    public MutableLiveData<String> getErrorMessage() {
        return errorMessage;
    }

    public MutableLiveData<String> getDataInput() {
        return dataInput;
    }

    public MutableLiveData<Contract> getDataContract() {
        return dataContract;
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
    //Lệnh để lấy tất cả hợp đồng
    public void getAllContract(String search){
        contractService.getAllContracts(search).enqueue(new Callback<ApiResponse<List<Contract>>>() {
            @Override
            public void onResponse(Call<ApiResponse<List<Contract>>> call, Response<ApiResponse<List<Contract>>> response) {
                if(response.isSuccessful()&& response.body()!=null){
                    ApiResponse<List<Contract>> apiResponse = response.body();
                    listContract.postValue(apiResponse.getData());
                }else{
                    handeErrorMessage(response.errorBody());
                }
            }
            @Override
            public void onFailure(Call<ApiResponse<List<Contract>>> call, Throwable t) {
                Log.i("ERROR LIST Contract",t.getMessage());
                errorMessage.postValue("Lỗi kết nối");
            }
        });
    }
    
    public void createContract(ContractRequest contractRequest){
        contractService.createContract(contractRequest).enqueue(new Callback<ApiResponse<String>>() {
            @Override
            public void onResponse(Call<ApiResponse<String>> call, Response<ApiResponse<String>> response) {
                if(response.isSuccessful()&& response.body()!=null){
                    ApiResponse<String> apiResponse = response.body();
                    dataInput.postValue(apiResponse.getData());
                }else{
                    handeErrorMessage(response.errorBody());
                }
            }

            @Override
            public void onFailure(Call<ApiResponse<String>> call, Throwable t) {
                Log.i("ERROR LIST Contract",t.getMessage());
                errorMessage.postValue("Lỗi kết nối");
            }
        });
    }
}
