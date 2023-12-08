package com.team.team_07_fe.models;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;
import java.util.Date;
import java.util.Objects;

public class ContractDetail implements Serializable {
    @SerializedName("_id")
    private String id;
    @SerializedName("dress_id")
    private Dress dress;
    @SerializedName("rental_date")
    private Date rental_date;
    @SerializedName("return_date")
    private Date return_date;

    public ContractDetail(String id, Dress dress, Date rental_date, Date return_date) {
        this.id = id;
        this.dress = dress;
        this.rental_date = rental_date;
        this.return_date = return_date;
    }

    public ContractDetail(Dress dress, Date rental_date, Date return_date) {
        this.dress = dress;
        this.rental_date = rental_date;
        this.return_date = return_date;
    }

    public Dress getDress() {
        return dress;
    }

    public Date getRental_date() {
        return rental_date;
    }

    public Date getReturn_date() {
        return return_date;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ContractDetail item = (ContractDetail) o;
        return Objects.equals(dress.getId(), item.getDress().getId());
    }

    @Override
    public int hashCode() {
        return Objects.hash(dress.getId());
    }
}
