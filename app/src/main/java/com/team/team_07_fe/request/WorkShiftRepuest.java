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

    @SerializedName("shift_description")
    private Date shift_description;

    public WorkShiftRepuest(String name, Date timeStart, Date timeEnd) {
        this.name = name;
        this.timeStart = timeStart;
        this.timeEnd = timeEnd;
    }



    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Date getTimeStart() {
        return timeStart;
    }

    public void setTimeStart(Date timeStart) {
        this.timeStart = timeStart;
    }

    public Date getTimeEnd() {
        return timeEnd;
    }

    public void setTimeEnd(Date timeEnd) {
        this.timeEnd = timeEnd;
    }

    public Date getShift_description() {
        return shift_description;
    }

    public void setShift_description(Date shift_description) {
        this.shift_description = shift_description;
    }
}
