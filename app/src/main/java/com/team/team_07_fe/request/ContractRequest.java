package com.team.team_07_fe.request;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;
import java.util.List;

public class ContractRequest implements Serializable {
    @SerializedName("cus_id")
    private String cus_id;
    @SerializedName("emp_id")
    private String emp_id;
    @SerializedName("total_amount")
    private long total_amount;
    @SerializedName("prepay")
    private long prepay;
    @SerializedName("discount")
    private long discount;
    @SerializedName("contract_details")
    private List<ContractDetailRequest> contractDetailRequestList;

    public ContractRequest(String cus_id, String emp_id, long total_amount, long prepay, long discount, List<ContractDetailRequest> contractDetailRequestList) {
        this.cus_id = cus_id;
        this.emp_id = emp_id;
        this.total_amount = total_amount;
        this.prepay = prepay;
        this.discount = discount;
        this.contractDetailRequestList = contractDetailRequestList;
    }

    public String getCus_id() {
        return cus_id;
    }

    public void setCus_id(String cus_id) {
        this.cus_id = cus_id;
    }

    public String getEmp_id() {
        return emp_id;
    }

    public void setEmp_id(String emp_id) {
        this.emp_id = emp_id;
    }

    public long getTotal_amount() {
        return total_amount;
    }

    public void setTotal_amount(long total_amount) {
        this.total_amount = total_amount;
    }

    public long getPrepay() {
        return prepay;
    }

    public void setPrepay(long prepay) {
        this.prepay = prepay;
    }

    public long getDiscount() {
        return discount;
    }

    public void setDiscount(long discount) {
        this.discount = discount;
    }

    public List<ContractDetailRequest> getContractDetailRequestList() {
        return contractDetailRequestList;
    }

    public void setContractDetailRequestList(List<ContractDetailRequest> contractDetailRequestList) {
        this.contractDetailRequestList = contractDetailRequestList;
    }
}
