package com.team.team_07_fe.api.repository;

import android.util.Log;

import androidx.lifecycle.MutableLiveData;

import com.google.gson.Gson;
import com.team.team_07_fe.api.ApiClient;
import com.team.team_07_fe.api.ApiResponse;
import com.team.team_07_fe.api.service.CustomerService;
import com.team.team_07_fe.models.Customer;
import com.team.team_07_fe.request.CustomerRequest;

import java.util.List;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class CustomerRepository {
    private CustomerService customerService;

    private MutableLiveData<List<Customer>> listCustomer = new MutableLiveData<>();
    private MutableLiveData<String> errorMessage = new MutableLiveData<>();
    private MutableLiveData<String> dataInput = new MutableLiveData<>();
    private MutableLiveData<Customer> dataCustomer = new MutableLiveData<>();

    public CustomerRepository(){
        customerService = ApiClient.getClient().create(CustomerService.class);
    }
    //Getter
    public MutableLiveData<List<Customer>> getListCustomer() {
        return listCustomer;
    }

    public MutableLiveData<String> getErrorMessage() {
        return errorMessage;
    }

    public MutableLiveData<String> getDataInput() {
        return dataInput;
    }

    public MutableLiveData<Customer> getDataCustomer() {
        return dataCustomer;
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
    public void getAllCustomer(String search){
        customerService.getAllCustomers(search).enqueue(new Callback<ApiResponse<List<Customer>>>() {
            @Override
            public void onResponse(Call<ApiResponse<List<Customer>>> call, Response<ApiResponse<List<Customer>>> response) {
                if(response.isSuccessful()){
                    ApiResponse<List<Customer>> apiResponse = response.body();
                    listCustomer.postValue(apiResponse.getData());
                }else{
                    handeErrorMessage(response.errorBody());
                }
            }
            @Override
            public void onFailure(Call<ApiResponse<List<Customer>>> call, Throwable t) {
                Log.i("ERROR LIST Customer",t.getMessage());
                errorMessage.postValue("Lỗi kết nối");
            }
        });
    }

    //Lệnh để lấy nhân viên
    public void getCustomer(String id){
        customerService.getCustomerById(id).enqueue(new Callback<ApiResponse<Customer>>() {
            @Override
            public void onResponse(Call<ApiResponse<Customer>> call, Response<ApiResponse<Customer>> response) {
                if(response.isSuccessful()){
                    ApiResponse<Customer> apiResponse = response.body();
                    dataCustomer.postValue(apiResponse.getData());
                }else{
                    handeErrorMessage(response.errorBody());
                }
            }
            @Override
            public void onFailure(Call<ApiResponse<Customer>> call, Throwable t) {
                Log.i("ERROR LIST CUSTOMER",t.getMessage());
                errorMessage.postValue("Lỗi kết nối");
            }
        });
    }

    //Tạo mới nhân viên
    public void createCustomer(CustomerRequest customerRequest){
        customerService.createCustomer(customerRequest).enqueue(new Callback<ApiResponse<String>>() {
            @Override
            public void onResponse(Call<ApiResponse<String>> call, Response<ApiResponse<String>> response) {
                if(response.isSuccessful()){
                    ApiResponse<String> apiResponse = response.body();
                    String deletedData = apiResponse.getData();
                    // Thực hiện các hành động với deletedData (ví dụ: cập nhật giao diện người dùng, thông báo thành công, vv.)
                    dataInput.postValue(deletedData);
                }else{
                    handeErrorMessage(response.errorBody());
                }
            }

            @Override
            public void onFailure(Call<ApiResponse<String>> call, Throwable t) {
                Log.i("ERROR LIST CUSTOMER",t.getMessage());
                errorMessage.postValue("Lỗi kết nối");
            }
        });
    }
    //Update thông tin nhân vin
    public void updateCustomer(String id, CustomerRequest customerRequest){
        customerService.updateCustomer(id,customerRequest).enqueue(new Callback<ApiResponse<String>>() {
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
    public void deleteCustomer(String id) {
        customerService.deleteCustomer(id).enqueue(new Callback<ApiResponse<Customer>>() {
            @Override
            public void onResponse(Call<ApiResponse<Customer>> call, Response<ApiResponse<Customer>> response) {
                if (response.isSuccessful()) {
                    ApiResponse<Customer> apiResponse = response.body();
                    Customer deletedCustomer = apiResponse.getData();
                    dataCustomer.postValue(deletedCustomer);
                    // ...
                } else {
                    handeErrorMessage(response.errorBody());
                }
            }
            @Override
            public void onFailure(Call<ApiResponse<Customer>> call, Throwable t) {
                Log.i("ERROR DELETE CUSTOMER", t.getMessage());
                errorMessage.postValue("Lỗi kết nối");
            }
        });
    }

}
