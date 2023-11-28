package com.team.team_07_fe.api;

import java.io.IOException;
import java.util.concurrent.TimeUnit;

import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class ApiClient {

    public static String LOCAL_HOST = "192.168.38.136";

    private static final String ROOT_URL = "http://"+LOCAL_HOST+":3000";
    private static Retrofit retrofit;

    private ApiClient(){
    }
    public static synchronized Retrofit getClient() {
        if(retrofit==null){
            OkHttpClient.Builder httpClient = new OkHttpClient.Builder()
                    .connectTimeout(30, TimeUnit.SECONDS) // Thời gian kết nối tối đa (30 giây)
                    .readTimeout(30, TimeUnit.SECONDS) // Thời gian đọc dữ liệu tối đa (30 giây)
                    .writeTimeout(30, TimeUnit.SECONDS); // Thời gian ghi dữ liệu tối đa (30 giây);
                //  Có thể thiết lập thêm về cookie hoặc session tại đây
            httpClient.addInterceptor(new Interceptor() {
                @Override
                public Response intercept(Chain chain) throws IOException {
                    Request originalRequest = chain.request();
                    // Thêm cookie vào header của yêu cầu
                    Request newRequest = originalRequest.newBuilder()
                            .method(originalRequest.method(), originalRequest.body())
                            .build();

                    return chain.proceed(newRequest);
                }
            });
            retrofit = new retrofit2.Retrofit.Builder()
                    .baseUrl(ROOT_URL)
                    .addConverterFactory(GsonConverterFactory.create())
                    .client(httpClient.build())
                    .build();
        }


        return retrofit;
    }


}
