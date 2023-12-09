package com.team.team_07_fe.viewmodels;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.team.team_07_fe.api.repository.AuthRepository;

public class AuthViewModel extends ViewModel {
    private AuthRepository authRepository;
    private MutableLiveData<String> dataMessage;
    private MutableLiveData<String> errorMessage;

    public AuthViewModel(){
        authRepository = new AuthRepository();
        dataMessage = authRepository.getMessageData();
        errorMessage = authRepository.getErrorMessage();
    }

    //setter

    public void setDataMessage(String dataMessage) {
        this.dataMessage.postValue(dataMessage);
    }

    public void setErrorMessage(String errorMessage) {
        this.errorMessage.postValue(errorMessage);
    }

    //
    public LiveData<String> getDataMessage() {
        return dataMessage;
    }

    public LiveData<String> getErrorMessage() {
        return errorMessage;
    }

    //hàm
    public void adminChangePassword(String id,String newPassword){
        authRepository.adminChangePassword(id,newPassword);
    }
    public void disableAccount(String id,boolean disable){
        authRepository.disableAccount(id,disable);
    }
    public void changePassword(String id, String oldPassword, String newPassword, String checkPassword){
        authRepository.changePassword(id, oldPassword, newPassword, checkPassword);
    }
}
