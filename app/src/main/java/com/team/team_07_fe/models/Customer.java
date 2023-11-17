package com.team.team_07_fe.models;

import java.io.Serializable;
import java.util.Date;
import java.util.Objects;

public class Customer implements Serializable {
    private int cus_id;
    private String cus_name;
    private String cus_phone;
    private String cus_phoneSecond;
    private String email;
    private Date cus_birthday;
    private String cus_address;

    // ten,phone1,phone2,NS,DC
    public Customer(String cus_name, String cus_phone, String cus_phoneSecond , Date cus_birthday, String cus_address) {
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

    public String getCus_phoneimary() {
        return cus_phone;
    }

    public void setCus_phoneimary(String cus_phoneimary) {
        this.cus_phone = cus_phoneimary;
    }

    public String getCus_phonesob() {
        return cus_phoneSecond;
    }

    public void setCus_phonesob(String cus_phonesob) {
        this.cus_phoneSecond = cus_phonesob;
    }

    public Date getCus_birthday() {
        return cus_birthday;
    }

    public void setCus_birthday(Date cus_birthday) {
        this.cus_birthday = cus_birthday;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
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
