package com.team.team_07_fe.api.repository;

import android.net.Uri;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.lifecycle.MutableLiveData;

import com.bumptech.glide.Glide;
import com.google.gson.Gson;
import com.team.team_07_fe.api.ApiClient;
import com.team.team_07_fe.api.ApiResponse;
import com.team.team_07_fe.api.service.DressService;
import com.team.team_07_fe.models.Customer;
import com.team.team_07_fe.models.Dress;
import com.team.team_07_fe.request.DressRequest;
import com.team.team_07_fe.ui.dress.DressAddFragment;
import com.team.team_07_fe.ui.dress.DressManagerFragment;

import java.io.File;
import java.util.List;

import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
public class DressRepository {
    private DressService dressService;

    private MutableLiveData<List<Dress>> listDress = new MutableLiveData<>();
    private MutableLiveData<String> errorMessage = new MutableLiveData<>();
    private MutableLiveData<String> dataInput = new MutableLiveData<>();
    private MutableLiveData<Dress> dataDress = new MutableLiveData<>();

    public DressRepository(){
        dressService = ApiClient.getClient().create(DressService.class);
    }
    //Getter
    public MutableLiveData<List<Dress>> getListDress() {
        return listDress;
    }

    public MutableLiveData<String> getErrorMessage() {
        return errorMessage;
    }

    public MutableLiveData<String> getDataInput() {
        return dataInput;
    }

    public MutableLiveData<Dress> getDataDress() {
       return dataDress;
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

    //Lệnh để lấy tất cả áo cưới
    public void getAllDress(String search){
        dressService.getAllDress(search).enqueue(new Callback<ApiResponse<List<Dress>>>() {
            @Override
            public void onResponse(Call<ApiResponse<List<Dress>>> call, Response<ApiResponse<List<Dress>>> response) {
                if(response.isSuccessful()){
                    ApiResponse<List<Dress>> apiResponse = response.body();
                    listDress.postValue(apiResponse.getData());
                }else{
                    handeErrorMessage(response.errorBody());
                }
            }

            @Override
            public void onFailure(Call<ApiResponse<List<Dress>>> call, Throwable t) {
                Log.i("ERROR LIST DRESS", t.getMessage());
                errorMessage.postValue("Lỗi kết nối");
            }
        });
    }

    //Tạo mới áo cưới //String dress_image, String dress_name, String dressTypeId, String color, String size, long dress_price, String dress_description
    public void addDress(RequestBody imageUrl, File image,String dress_name, String dressTypeId, String color, String size, long dress_price, String dress_description ){
        MultipartBody.Part imagePart = MultipartBody.Part.createFormData("dress_image", image.getName(), imageUrl);

        // Chuyển các thông tin sản phẩm thành RequestBody
        RequestBody nameBody = RequestBody.create(MediaType.parse("text/plain"), dress_name);
        RequestBody colorBody = RequestBody.create(MediaType.parse("text/plain"), color);
        RequestBody priceBody = RequestBody.create(MediaType.parse("text/plain"), String.valueOf(dress_price));
        RequestBody sizeBody = RequestBody.create(MediaType.parse("text/plain"), size);
        RequestBody descriptionBody = RequestBody.create(MediaType.parse("text/plain"), dress_description);
        RequestBody typeIdBody = RequestBody.create(MediaType.parse("text/plain"), dressTypeId);
        //Khi tạo mới status luôn mặc định là sẵn sàng
        RequestBody statusBody = RequestBody.create(MediaType.parse("text/plain"), "Sẵn sàng");
        Log.i("DATA REQUEST",  String.valueOf(imagePart));
        dressService.addDress(imagePart,nameBody,typeIdBody,colorBody,sizeBody,priceBody,descriptionBody,statusBody).enqueue(new Callback<ApiResponse<String>>() {
            @Override
                public void onResponse(Call<ApiResponse<String>> call, @NonNull Response<ApiResponse<String>> response) {
                      if(response.isSuccessful() && response.body()!=null){
                          ApiResponse<String> apiResponse = response.body();
//                          Glide.with().load // tải ảnh ảnh
//                          String deletedData = apiResponse.getData();
                          dataInput.postValue(apiResponse.getData());
                      }else{
                          if (response.errorBody() != null) {
                              try {
                                  Log.i("ERROR",response.errorBody().string());
                                  Gson gson = new Gson();
                                  ApiResponse error = gson.fromJson(response.errorBody().string(), ApiResponse.class);
                                  errorMessage.postValue(error.getMessage());
                              } catch (Exception e) {
                                  Log.i("ERROR",e.getMessage());
                                  errorMessage.postValue("Lỗi không xác định!");
                              }
                          }
                      }
                     }

            @Override
                public void onFailure(Call<ApiResponse<String>> call, Throwable t) {
                Log.i("ERROR ADD DRESS", t.getMessage());
                errorMessage.postValue("Lỗi kết nối");
            }
        });
    }
    //Update thông tin áo cưới
    public void updateDress(String id,DressRequest dressRequest){
        dressService.updateDress(id,dressRequest).enqueue(new Callback<ApiResponse<String>>() {
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
                Log.i("ERROR UPDATE DRESS",t.getMessage());
                errorMessage.postValue("Lỗi kết nối");
            }
        });
    }

    public void deleteDress(String id, DressRequest dressRequest) {
        dressService.deleteDress(id).enqueue(new Callback<ApiResponse<Dress>>() {
            @Override
            public void onResponse(Call<ApiResponse<Dress>> call, Response<ApiResponse<Dress>> response) {
                if(response.isSuccessful()){
                    ApiResponse<Dress> apiResponse = response.body();
                    Dress deleteDress = apiResponse.getData();
                    dataDress.postValue(apiResponse.getData());
                }
            }

            @Override
            public void onFailure(Call<ApiResponse<Dress>> call, Throwable t) {
                Log.i("ERROR DELETE DRESS", t.getMessage());
                errorMessage.postValue("Lỗi kết nối");
            }
        });
    }
}


