package com.team.team_07_fe.models;

import java.io.Serializable;
import java.util.Date;

public class Customer implements Serializable {
    private int cus_id;
    private String cus_name;
    private String cus_phone;
    private String cus_phoneSecond;
    private String email;
    private Date cus_birthday;
    private String cus_address;

    // ten,phone1,phone2,NS,DC
    public Customer(int cus_name, String cus_phone, String cus_phoneSecond , String cus_birthday, String cus_address) {
        this.cus_name = cus_name;
        this.cus_phone = cus_phone;
        this.cus_phoneSecond = cus_phoneSecond;
        this.cus_birthday = cus_birthday;
        this.cus_address = cus_address;
    }

    public Customer(int cus_id, String cus_name, String cus_phone, String cus_phoneSecond, String email, Date cus_birthday, String cus_address) {
        this.cus_id = cus_id;
        this.cus_name = cus_name;
        this.cus_phone = cus_phone;
        this.cus_phoneSecond = cus_phoneSecond;
        this.email = email;
        this.cus_birthday = cus_birthday;
        this.cus_address = cus_address;
    }

    public int getCus_id() {
        return cus_id;
    }

    public void setCus_id(int cus_id) {
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

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public Date getCus_birthday() {
        return cus_birthday;
    }

    public void setCus_birthday(Date cus_birthday) {
        this.cus_birthday = cus_birthday;
    }

    public String getCus_address() {
        return cus_address;
    }

    public void setCus_address(String cus_address) {
        this.cus_address = cus_address;
    }
}
