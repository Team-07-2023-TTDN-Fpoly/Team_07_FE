package com.team.team_07_fe.models;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

public class Statistic implements Serializable {
    @SerializedName("dt_date")
    private Date dt_date;
    @SerializedName("dt_name")
    private String dt_name;
    @SerializedName("dt_money")
    private Long dt_money;
    @SerializedName("dt_text")
    private String dt_text;
    @SerializedName("total_revenue")
    private Long total_revenue;
    @SerializedName("total_amount")
    private Long total_amount;
    @SerializedName("list_amount")
    private List<DetailStatistics> listDetail; // Danh sách khoản chỉ trong tháng
    public Statistic(Date dt_date,String dt_name,Long dt_money,String dt_text ,Long total_revenue, Long total_amount) {
        this.dt_date = dt_date;
        this.dt_name = dt_name;
        this.dt_money = dt_money;
        this.dt_text = dt_text;
        this.total_revenue = total_revenue;
        this.total_amount = total_amount;
    }

    public Date getDt_date() {
        return dt_date;
    }

    public void setDt_date(Date dt_date) {
        this.dt_date = dt_date;
    }

    public String getDt_name() {
        return dt_name;
    }

    public Long getDt_money() {
        return dt_money;
    }

    public String getDt_text() {
        return dt_text;
    }

    public Long getTotal_revenue() {
        return total_revenue;
    }

    public void setTotal_revenue(Long total_revenue) {
        this.total_revenue = total_revenue;
    }

    public Long getTotal_amount() {
        return total_amount;
    }

    public void setTotal_amount(Long total_amount) {
        this.total_amount = total_amount;
    }

    public void setDt_name(String dt_name) {
        this.dt_name = dt_name;
    }

    public void setDt_money(Long dt_money) {
        this.dt_money = dt_money;
    }

    public void setDt_text(String dt_text) {
        this.dt_text = dt_text;
    }
}
