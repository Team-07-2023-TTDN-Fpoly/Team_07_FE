package com.team.team_07_fe.request;

import com.google.gson.annotations.SerializedName;

import java.util.Date;

public class ContractDetailRequest {
    @SerializedName("dress_id")
    private String dress_id;
    @SerializedName("rental_date")
    private Date rental_date;
    @SerializedName("return_date")
    private Date return_date;
}
