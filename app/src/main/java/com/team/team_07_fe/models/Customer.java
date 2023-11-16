package com.team.team_07_fe.models;

import java.util.Date;
import java.util.Objects;

public class Customer {
    private int cus_id;
    private String cus_name;
    private String cus_phoneimary;
    private String cus_phonesob;
    private String cus_birthday;
    private String email;
    private String cus_address;

    public Customer(String cus_name, String cus_phoneimary, String cus_phonesob, String cus_birthday, String email, String cus_address) {
        this.cus_name = cus_name;
        this.cus_phoneimary = cus_phoneimary;
        this.cus_phonesob = cus_phonesob;
        this.cus_birthday = cus_birthday;
        this.email = email;
        this.cus_address = cus_address;
    }

    public Customer(int cus_id, String cus_name, String cus_phoneimary, String cus_phonesob, String cus_birthday, String email, String cus_address) {
        this.cus_id = cus_id;
        this.cus_name = cus_name;
        this.cus_phoneimary = cus_phoneimary;
        this.cus_phonesob = cus_phonesob;
        this.cus_birthday = cus_birthday;
        this.email = email;
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
        return cus_phoneimary;
    }

    public void setCus_phoneimary(String cus_phoneimary) {
        this.cus_phoneimary = cus_phoneimary;
    }

    public String getCus_phonesob() {
        return cus_phonesob;
    }

    public void setCus_phonesob(String cus_phonesob) {
        this.cus_phonesob = cus_phonesob;
    }

    public String getCus_birthday() {
        return cus_birthday;
    }

    public void setCus_birthday(String cus_birthday) {
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
