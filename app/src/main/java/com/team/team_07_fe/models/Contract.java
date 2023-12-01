package com.team.team_07_fe.models;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

public class Contract implements Serializable {
    @SerializedName("_id")
    private String id;
    @SerializedName("cus_id")
    private Customer customer;
    @SerializedName("emp_id")
    private Employee employee;
    @SerializedName("total_amount")
    private long total_amount;
    @SerializedName("prepay")
    private long prepay;
    @SerializedName("discount")
    private long discount;
    @SerializedName("contract_details")
    private List<ContractDetail> listContractDetail;
    @SerializedName("createAt")
    private Date createAt;
    @SerializedName("endAt")
    private Date endAt;
    @SerializedName("contract_description")
    private String description;
    @SerializedName("contract_status")
    private String status;

    public Customer getCustomer() {
        return customer;
    }

    public Employee getEmployee() {
        return employee;
    }

    public long getTotal_amount() {
        return total_amount;
    }

    public long getPrepay() {
        return prepay;
    }

    public long getDiscount() {
        return discount;
    }

    public List<ContractDetail> getListContractDetail() {
        return listContractDetail;
    }

    public Date getCreateAt() {
        return createAt;
    }

    public Date getEndAt() {
        return endAt;
    }

    public String getDescription() {
        return description;
    }

    public String getStatus() {
        return status;
    }

    public String getId() {
        return id;
    }
}
