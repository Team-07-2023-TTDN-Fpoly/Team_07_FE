package com.team.team_07_fe.models;

import com.google.gson.annotations.SerializedName;
import com.team.team_07_fe.utils.FormatHelper;

import java.io.Serializable;
import java.time.LocalTime;
import java.util.Date;
import java.util.Objects;

public class WorkShift implements Serializable {
    @SerializedName("shift_id")
    private String shift_id;
    @SerializedName("name")
    private String name;
    @SerializedName("timeStart")
    private Date timeStart;
    @SerializedName("timeEnd")
    private Date timeEnd;
    @SerializedName("shift_description")
    private String shift_description;

    public WorkShift(String name, Date timeStart, Date timeEnd, String shift_description) {
        this.name = name;
        this.timeStart = timeStart;
        this.timeEnd = timeEnd;
        this.shift_description = shift_description;
    }

    public WorkShift(String shift_id, String name, Date timeStart, Date timeEnd, String shift_description) {
        this.shift_id = shift_id;
        this.name = name;
        this.timeStart = timeStart;
        this.timeEnd = timeEnd;
        this.shift_description = shift_description;
    }

    public String getShift_id() {
        return shift_id;
    }

    public void setShift_id(String shift_id) {
        this.shift_id = shift_id;
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

    public String getShift_description() {
        return shift_description;
    }

    public void setShift_description(String shift_description) {
        this.shift_description = shift_description;
    }

    //

    @Override
    public String toString() {
        return name + " : " + FormatHelper.convertTimeToString(timeStart) + "h - " + FormatHelper.convertTimeToString(timeEnd) +"h";
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        WorkShift item = (WorkShift) o;
        return Objects.equals(shift_id, item.shift_id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(shift_id);
    }
}
