package com.team.team_07_fe.models;

import java.time.LocalTime;
import java.util.Objects;

public class WorkShift {
    private int shift_id;
    private String name;
    private LocalTime timeStart;
    private LocalTime timeEnd;
    private String shift_description;

    public WorkShift(String name, LocalTime timeStart, LocalTime timeEnd, String shift_description) {
        this.name = name;
        this.timeStart = timeStart;
        this.timeEnd = timeEnd;
        this.shift_description = shift_description;
    }

    public WorkShift(int shift_id, String name, LocalTime timeStart, LocalTime timeEnd, String shift_description) {
        this.shift_id = shift_id;
        this.name = name;
        this.timeStart = timeStart;
        this.timeEnd = timeEnd;
        this.shift_description = shift_description;
    }

    public int getShift_id() {
        return shift_id;
    }

    public void setShift_id(int shift_id) {
        this.shift_id = shift_id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public LocalTime getTimeStart() {
        return timeStart;
    }

    public void setTimeStart(LocalTime timeStart) {
        this.timeStart = timeStart;
    }

    public LocalTime getTimeEnd() {
        return timeEnd;
    }

    public void setTimeEnd(LocalTime timeEnd) {
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
        return name + " : " + timeStart + "h - " + timeEnd +"h";
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