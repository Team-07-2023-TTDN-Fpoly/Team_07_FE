package com.team.team_07_fe.models;

public class Contract {
    private String Name;
    private String Date;
    private String Phone;
    private String Deposit;
    private String SumMoney;
    private String Status;

    public Contract(String name, String date, String phone, String deposit, String sumMoney, String status) {
        Name = name;
        Date = date;
        Phone = phone;
        Deposit = deposit;
        SumMoney = sumMoney;
        Status = status;
    }

    public String getName() {
        return Name;
    }

    public void setName(String name) {
        Name = name;
    }

    public String getDate() {
        return Date;
    }

    public void setDate(String date) {
        Date = date;
    }

    public String getPhone() {
        return Phone;
    }

    public void setPhone(String phone) {
        Phone = phone;
    }

    public String getDeposit() {
        return Deposit;
    }

    public void setDeposit(String deposit) {
        Deposit = deposit;
    }

    public String getSumMoney() {
        return SumMoney;
    }

    public void setSumMoney(String sumMoney) {
        SumMoney = sumMoney;
    }

    public String getStatus() {
        return Status;
    }

    public void setStatus(String status) {
        Status = status;
    }
}
