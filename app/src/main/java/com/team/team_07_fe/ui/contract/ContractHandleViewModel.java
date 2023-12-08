package com.team.team_07_fe.ui.contract;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.team.team_07_fe.models.ContractDetail;
import com.team.team_07_fe.models.Customer;
import com.team.team_07_fe.models.Dress;

import java.util.ArrayList;
import java.util.List;

public class ContractHandleViewModel extends ViewModel {

    private MutableLiveData<List<Customer>> listCustomer = new MutableLiveData<>();
    private MutableLiveData<Customer> selectCustomer = new MutableLiveData<>();

    private MutableLiveData<Dress> selectDress = new MutableLiveData<>();
    private MutableLiveData<Long> discount = new MutableLiveData<>();
    private MutableLiveData<Long> prepay = new MutableLiveData<>();
    private MutableLiveData<Long> total = new MutableLiveData<>();
    private MutableLiveData<String> dateCreateAt = new MutableLiveData<>();
    private MutableLiveData<String> dateEndAt = new MutableLiveData<>();

    private MutableLiveData<List<ContractDetail>> listContractDetail =new MutableLiveData<>();
    private ContractDetail selectContractDetail;

    private MutableLiveData<Boolean> addStatus = new MutableLiveData<>();
    public ContractHandleViewModel(){
        listContractDetail.postValue(null);
        listCustomer.postValue(null);
        selectCustomer.postValue(null);
        selectDress.setValue(null);
        discount.setValue(0L);
        prepay.setValue(0L);
        total.setValue(0L);
    }

    public MutableLiveData<Long> getDiscount() {
        return discount;
    }
    public void setDiscount(Long discount) {
        this.discount.postValue(discount);
    }

    public MutableLiveData<Long> getPrepay() {
        return prepay;
    }
    public void setPrepay(Long prepay) {
        this.prepay.postValue(prepay);
    }

    public MutableLiveData<Long> getTotal() {
        return total;
    }
    public void setTotal(long total) {
        this.total.postValue(total);
    }

    public MutableLiveData<List<Customer>> getListCustomer() {
        return listCustomer;
    }
    public void setListCustomer(List<Customer> listCustomer) {
        this.listCustomer.postValue(listCustomer);
    }

    public MutableLiveData<Customer> getSelectCustomer() {
        return selectCustomer;
    }
    public void setSelectCustomer(Customer selectCustomer) {
        this.selectCustomer.postValue(selectCustomer);
    }

    public MutableLiveData<Dress> getSelectDress() {
        return selectDress;
    }
    public void setSelectDress(Dress selectDress) {
        this.selectDress.postValue(selectDress);
    }

    public LiveData<List<ContractDetail>> getListContractDetail() {
        return listContractDetail;
    }
    public void setListContractDetail(List<ContractDetail> listContractDetail) {
        this.listContractDetail.postValue(listContractDetail);
    }

    public LiveData<String> getDateCreateAt() {
        return dateCreateAt;
    }
    public void setDateCreateAt(String dateCreateAt) {
        this.dateCreateAt.postValue(dateCreateAt);
    }

    public LiveData<String> getDateEndAt() {
        return dateEndAt;
    }
    public void setDateEndAt(String dateEndAt) {
        this.dateEndAt.postValue(dateEndAt);
    }

    public ContractDetail getSelectContractDetail() {
        return selectContractDetail;
    }
    public void setSelectContractDetail(ContractDetail selectContractDetail) {
        this.selectContractDetail = selectContractDetail;
    }

    public void updateTotalAmount(){
        long sum = 0;
        List<ContractDetail> detailList = listContractDetail.getValue();
        if(detailList!=null){
            for (ContractDetail detail : detailList) {
                sum += detail.getDress().getDress_price();
            }
        }
        total.setValue(sum);
    }

    public LiveData<Boolean> getAddStatus() {
        return addStatus;
    }

    public void setAddStatus(Boolean addStatus) {
        this.addStatus.postValue(addStatus);
    }

    /**
     * @param contractDetail - thông tin thuê áo cưới
     * @desc Thêm mới dress vào contract
     */
    public void addDressToListContractDetail(ContractDetail contractDetail){
        List<ContractDetail> list = listContractDetail.getValue();
        if(list==null){
            list =new ArrayList<>();
        }
        if(!list.contains(contractDetail)){
            list.add(contractDetail);
            listContractDetail.setValue(list);
            addStatus.postValue(true);
        }else{
            addStatus.postValue(false);
        }
    }

    /**
     * @param dress - thông tin thuê áo cưới
     * @desc loại bỏ dress ra khỏi contract
     */
    public void removeDress(ContractDetail dress) {
        List<ContractDetail> currentDresses = listContractDetail.getValue();
        if(currentDresses == null){
            currentDresses = new ArrayList<>();
        }
        currentDresses.remove(dress);
        listContractDetail.setValue(currentDresses); // Cập nhật LiveData
    }

    public void resetChooseDress(){
        addStatus.setValue(null);
        selectDress.setValue(null);
    }

    public void resetAllData(){
        total.setValue(0L);
        prepay.setValue(0L);
        discount.setValue(0L);
        selectCustomer.setValue(null);
        listContractDetail.setValue(null);
        dateCreateAt.setValue(null);
        dateEndAt.setValue(null);
    }
}
