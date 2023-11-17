package com.team.team_07_fe.models;

public class ContractRevenue {
    private  String revenue_id;
    private int contract_id;
    private long amount;
    private String description;

    public String getRevenue_id() {
        return revenue_id;
    }

    public void setRevenue_id(String revenue_id) {
        this.revenue_id = revenue_id;
    }

    public int getContract_id() {
        return contract_id;
    }

    public void setContract_id(int contract_id) {
        this.contract_id = contract_id;
    }

    public long getAmount() {
        return amount;
    }

    public void setAmount(long amount) {
        this.amount = amount;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public ContractRevenue(String revenue_id, int contract_id, long amount, String description) {
        this.revenue_id = revenue_id;
        this.contract_id = contract_id;
        this.amount = amount;
        this.description = description;


    }
}
