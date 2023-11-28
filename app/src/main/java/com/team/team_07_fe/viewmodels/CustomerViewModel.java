package com.team.team_07_fe.viewmodels;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.team.team_07_fe.api.repository.CustomerRepository;
import com.team.team_07_fe.models.Customer;
import com.team.team_07_fe.request.CustomerRequest;
import java.util.List;

public class CustomerViewModel extends ViewModel {
private CustomerRepository customerRepository;
    private MutableLiveData<List<Customer>> listCus;
    private MutableLiveData<String> dataInput;
    private MutableLiveData<String> errorMessage;
    public CustomerViewModel(){
        customerRepository = new CustomerRepository();
        listCus = customerRepository.getListCustomer();
        dataInput = customerRepository.getDataInput();
        errorMessage = customerRepository.getErrorMessage();
    }

    //setters

    public void setListCus(List<Customer> listCus) {
        this.listCus.postValue(listCus);
    }

    public void setDataInput(String dataInput) {
        this.dataInput.postValue(dataInput);
    }

    public void setErrorMessage(String errorMessage) {
        this.errorMessage.postValue(errorMessage);
    }

    //getters
    public LiveData<List<Customer>> getCustomerList() {
        return listCus;
    }
    public LiveData<String> getDataInput() {
        return dataInput;
    }
    public LiveData<String> getErrorMessage() {
        return errorMessage;
    }

    //
    public void createCustomer(CustomerRequest customerRequest){
        customerRepository.createCustomer(customerRequest);
    }
    public void updateCustomer(String id, CustomerRequest customerRequest){
        customerRepository.updateCustomer(id,customerRequest);
    }
    public void DeleteCustomer(String id){
        customerRepository.deleteCustomer(id);
    }
    public void getAllCustomer(String search){
        customerRepository.getAllCustomer(search);
    }


}