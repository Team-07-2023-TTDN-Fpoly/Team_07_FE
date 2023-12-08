package com.team.team_07_fe.request;

import com.google.gson.annotations.SerializedName;

import java.util.Date;
import java.util.Objects;

public class ContractDetailRequest {
    @SerializedName("_id")
    private String id;
    @SerializedName("dress_id")
    private String dress_id;
    @SerializedName("rental_date")
    private Date rental_date;
    @SerializedName("return_date")
    private Date return_date;

    public ContractDetailRequest(String dress_id, Date rental_date, Date return_date) {
        this.dress_id = dress_id;
        this.rental_date = rental_date;
        this.return_date = return_date;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getDress_id() {
        return dress_id;
    }

    public void setDress_id(String dress_id) {
        this.dress_id = dress_id;
    }

    public Date getRental_date() {
        return rental_date;
    }

    public void setRental_date(Date rental_date) {
        this.rental_date = rental_date;
    }

    public Date getReturn_date() {
        return return_date;
    }

    public void setReturn_date(Date return_date) {
        this.return_date = return_date;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ContractDetailRequest _dress = (ContractDetailRequest) o;
        return Objects.equals(_dress.getDress_id(), dress_id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(dress_id);
    }
}
