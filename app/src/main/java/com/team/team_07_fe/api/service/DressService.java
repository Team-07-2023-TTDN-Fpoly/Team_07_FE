package com.team.team_07_fe.api.service;

import com.team.team_07_fe.api.ApiResponse;
import com.team.team_07_fe.models.Dress;
import com.team.team_07_fe.request.DressRequest;

import java.util.List;

import javax.xml.namespace.QName;

import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.DELETE;
import retrofit2.http.GET;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.PUT;
import retrofit2.http.Part;
import retrofit2.http.Path;
import retrofit2.http.Query;

public interface DressService {
    @GET("/api/dresses/")
    Call<ApiResponse<List<Dress>>> getAllDress(@Query("search")String search);
    @Multipart
    @POST ("/api/dresses/")//String dress_image, String dress_name, String dressTypeId, String color, String size, long dress_price, String dress_description
    Call<ApiResponse<String>> addDress(@Part MultipartBody.Part image,
                                       @Part("dress_name") RequestBody dress_name,
                                       @Part("dressTypeId") RequestBody dressTypeId,
                                       @Part("color") RequestBody color,
                                       @Part("size") RequestBody size,
                                       @Part("dress_price") RequestBody dress_price,
                                       @Part("dress_description") RequestBody dress_description,
                                       @Part("dress_status") RequestBody dress_status
    );
    @Multipart
    @PUT("/api/dresses/{id}")
    Call<ApiResponse<String>> updateDress(@Path("id") String id,
                                          @Part MultipartBody.Part image,
                                          @Part("dress_name") RequestBody dress_name,
                                          @Part("dressTypeId") RequestBody dressTypeId,
                                          @Part("color") RequestBody color,
                                          @Part("size") RequestBody size,
                                          @Part("dress_price") RequestBody dress_price,
                                          @Part("dress_description") RequestBody dress_description,
                                          @Part("dress_status") RequestBody dress_status);
    @DELETE("/api/dresses/{id}")
    Call<ApiResponse<Dress>> deleteDress(@Path("id") String id);
}
