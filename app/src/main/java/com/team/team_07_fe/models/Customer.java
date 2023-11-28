package com.team.team_07_fe.models;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;
import java.util.Date;
import java.util.Objects;

public class Customer implements Serializable {
    @SerializedName("cus_id")
    private String cus_id;

    @SerializedName("cus_name")
    private String cus_name;

    @SerializedName("cus_phone")
    private String cus_phone;
    @SerializedName("cus_phoneSecond")
    private String cus_phoneSecond;

    @SerializedName("cus_wedding_date")
    private Date cus_wedding_date;

    @SerializedName("cus_address")
    private String cus_address;

    @SerializedName("cus_email")
    private String cus_email;

    public Customer(String cus_name, String cus_phone, String cus_phoneSecond, String cus_email, Date cus_wedding_date, String cus_address) {
        this.cus_name = cus_name;
        this.cus_phone = cus_phone;
        this.cus_phoneSecond = cus_phoneSecond;
        this.cus_email = cus_email;
        this.cus_wedding_date = cus_wedding_date;
        this.cus_address = cus_address;
    }

    public Customer(String cus_id, String cus_name, String cus_phone, String cus_phoneSecond, String cus_email, Date cus_wedding_date, String cus_address) {
        this.cus_id = cus_id;
        this.cus_name = cus_name;
        this.cus_phone = cus_phone;
        this.cus_phoneSecond = cus_phoneSecond;
        this.cus_email = cus_email;
        this.cus_wedding_date = cus_wedding_date;
        this.cus_address = cus_address;
    }

    public String getCus_id() {
        return cus_id;
    }

    public void setCus_id(String cus_id) {
        this.cus_id = cus_id;
    }

    public String getCus_name() {
        return cus_name;
    }

    public void setCus_name(String cus_name) {
        this.cus_name = cus_name;
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

    public String getCus_email() {
        return cus_email;
    }

    public void setCus_email(String cus_email) {
        this.cus_email = cus_email;
    }

    public String getCus_address() {
        return cus_address;
    }

    public void setCus_address(String cus_address) {
        this.cus_address = cus_address;
    }


    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Customer item = (Customer) o;
        return Objects.equals(cus_id, item.cus_id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(cus_id);
    }
}
