package com.team.team_07_fe.request;

import com.google.gson.annotations.SerializedName;

import java.util.Date;
import java.util.Objects;

public class CustomerRequest {
    @SerializedName("cus_name")
    private String cus_name;

    @SerializedName("cus_phone")
    private String cus_phone;
    @SerializedName("cus_phoneSecond")
    private String cus_phoneSecond;
    @SerializedName("cus_email")
    private String cus_email;
    @SerializedName("cus_wedding_date")
    private Date cus_wedding_date;

    @SerializedName("cus_address")
    private String cus_address;


    //Khi tạo khách hàng
    public CustomerRequest(String cus_name, String cus_phone, String cus_phoneSecond, String cus_email, Date cus_wedding_date, String cus_address) {
        this.cus_name = cus_name;
        this.cus_phone = cus_phone;
        this.cus_phoneSecond = cus_phoneSecond;
        this.cus_email = cus_email;
        this.cus_wedding_date = cus_wedding_date;
        this.cus_address = cus_address;
    }

//    public Customer(String cus_name, String cus_phone, String cus_phoneSecond, String email, Date cus_birthday, String cus_address) {
//        this.cus_name = cus_name;
//        this.cus_phone = cus_phone;
//        this.cus_phoneSecond = cus_phoneSecond;
//        this.email = email;
//        this.cus_birthday = cus_birthday;
//        this.cus_address = cus_address;
//    }

    public String getCus_name() {
        return cus_name;
    }

    public void setCus_name(String cus_name) {
        this.cus_name = cus_name;
    }

    public String getCus_email() {
        return cus_email;
    }

    public void setCus_email(String cus_email) {
        this.cus_email = cus_email;
    }

    public String getCus_phone() {
        return cus_phone;
    }

    public void setCus_phone(String cus_phone) {
        this.cus_phone = cus_phone;
    }

    public String getCus_phoneSecond() {
        return cus_phoneSecond;
    }

    public void setCus_phoneSecond(String cus_phoneSecond) {
        this.cus_phoneSecond = cus_phoneSecond;
    }

    public Date getCus_wedding_date() {
        return cus_wedding_date;
    }

    public void setCus_wedding_date(Date cus_wedding_date) {
        this.cus_wedding_date = cus_wedding_date;
    }


    public String getCus_address() {
        return cus_address;
    }

    public void setCus_address(String cus_address) {
        this.cus_address = cus_address;
    }

}



