package com.team.team_07_fe.request;

import com.google.gson.annotations.SerializedName;

import java.util.Date;

public class WorkShiftRepuest {
    @SerializedName("name")
    private String name;

    @SerializedName("timeStart")
    private Date timeStart;

    @SerializedName("timeEnd")
    private Date timeEnd;

}
