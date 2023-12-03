package com.team.team_07_fe.models;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;
import java.util.Date;

public class ContractDetail implements Serializable {
    @SerializedName("_id")
    private String id;
    @SerializedName("dress_id")
    private Dress dress;
    @SerializedName("rental_date")
    private Date rental_date;
    @SerializedName("return_date")
    private Date return_date;

    public Dress getDress() {
        return dress;
    }

    public Date getRental_date() {
        return rental_date;
    }

    public Date getReturn_date() {
        return return_date;
    }
}
