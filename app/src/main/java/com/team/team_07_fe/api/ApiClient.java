package com.team.team_07_fe.api;

import android.content.Context;
import java.util.concurrent.TimeUnit;
import okhttp3.OkHttpClient;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class ApiClient {
    public static String LOCAL_HOST = "192.168.1.13";

    private static final String ROOT_URL = "http://"+LOCAL_HOST+":8081";
    private static Retrofit retrofit =null;

    private ApiClient(){
    }
    public static synchronized Retrofit getClient() {
        if(retrofit==null){
            OkHttpClient.Builder httpClient = new OkHttpClient.Builder()
                    .connectTimeout(30, TimeUnit.SECONDS) // Thời gian kết nối tối đa (30 giây)
                    .readTimeout(30, TimeUnit.SECONDS) // Thời gian đọc dữ liệu tối đa (30 giây)
                    .writeTimeout(30, TimeUnit.SECONDS); // Thời gian ghi dữ liệu tối đa (30 giây);
                //  Có thể thiết lập thêm về cookie hoặc session tại đây

            retrofit = new Retrofit.Builder().baseUrl(ROOT_URL).addConverterFactory(GsonConverterFactory.create()).client(httpClient.build()).build();
        }


        return retrofit;
    }

}
