package com.team.team_07_fe.api;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

public class ApiResponse<T> {
    @NonNull
    private Status status;
    @Nullable
    private String message;
    @Nullable
    private T data;

    public ApiResponse(@NonNull Status status, @Nullable T data, @Nullable String message) {
        this.status = status;
        this.message = message;
        this.data = data;
    }

    public static <T> ApiResponse<T> success(@NonNull T data,String msg) {
        return new ApiResponse<>(Status.SUCCESS, data, msg);
    }

    public static <T> ApiResponse<T> error(String msg) {
        return new ApiResponse<>(Status.ERROR, null, msg);
    }

    public enum Status{
        SUCCESS,ERROR
    }

    @NonNull
    public Status getStatus() {
        return status;
    }

    public void setStatus(@NonNull Status status) {
        this.status = status;
    }

    @Nullable
    public String getMessage() {
        return message;
    }

    public void setMessage(@Nullable String message) {
        this.message = message;
    }

    @Nullable
    public T getData() {
        return data;
    }

    public void setData(@Nullable T data) {
        this.data = data;
    }
}
