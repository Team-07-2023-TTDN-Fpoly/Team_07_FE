package com.team.team_07_fe.request;

import com.google.gson.annotations.SerializedName;

public class DressRequest {
    @SerializedName("id")
    private String dress_id;
    @SerializedName("dress_name")
    private String dress_name;
    @SerializedName("dressTypeId")
    private String dressTypeId;
    @SerializedName("dress_price")
    private long dress_price;
    @SerializedName("size")
    private String size;
    @SerializedName("color")
    private String color;
    @SerializedName("dress_image")
    private String dress_image;
    @SerializedName("dress_description")
    private String dress_description;
    @SerializedName("dress_status")
    private String dress_status;

    ///khi thêm áo cưới
    public DressRequest(String dress_image, String dress_name, String dressTypeId, String color, String size, long dress_price, String dress_description){
        this.dress_image = dress_image;
        this.dress_name = dress_name;
        this.dressTypeId = dressTypeId;
        this.color = color;
        this.size = size;
        this.dress_price = dress_price;
        this.dress_description = dress_description;
    }
    /// khi cập nhật áo cưới
    public DressRequest(String id,String dress_image, String dress_name, String dressTypeId, String color, String size, long dress_price, String dress_description){
        this.dress_id = id;
        this.dress_image = dress_image;
        this.dress_name = dress_name;
        this.dressTypeId = dressTypeId;
        this.color = color;
        this.size = size;
        this.dress_price = dress_price;
        this.dress_description = dress_description;
    }

    public String getDress_name() {
        return dress_name;
    }

    public void setDress_name(String dress_name) {
        this.dress_name = dress_name;
    }

    public String getDressTypeId() {
        return dressTypeId;
    }

    public void setDressTypeId(String dressTypeId) {
        this.dressTypeId = dressTypeId;
    }

    public long getDress_price() {
        return dress_price;
    }

    public void setDress_price(long dress_price) {
        this.dress_price = dress_price;
    }

    public String getSize() {
        return size;
    }

    public void setSize(String size) {
        this.size = size;
    }

    public String getColor() {
        return color;
    }

    public void setColor(String color) {
        this.color = color;
    }

    public String getDress_image() {
        return dress_image;
    }

    public void setDress_image(String dress_image) {
        this.dress_image = dress_image;
    }

    public String getDress_description() {
        return dress_description;
    }

    public void setDress_description(String dress_description) {
        this.dress_description = dress_description;
    }

    public String getDress_status() {
        return dress_status;
    }

    public void setDress_status(String dress_status) {
        this.dress_status = dress_status;
    }
}
