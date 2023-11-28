package com.team.team_07_fe.api;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.google.gson.annotations.SerializedName;

public class ApiResponse<T> {
    @SerializedName("message")
    private String message;
    @SerializedName("data")
    private T data;

    public ApiResponse(T data,String message) {
        this.message = message;
        this.data = data;
    }


    public enum Status{
        SUCCESS,ERROR
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public T getData() {
        return data;
    }

    public void setData(T data) {
        this.data = data;
    }
}
