package com.team.team_07_fe.models;
import com.google.gson.annotations.SerializedName;
import java.io.Serializable;
public class Dress implements Serializable {
    @SerializedName("id")
    private String id;

    @SerializedName("dress_name")
    private String dress_name;
    @SerializedName("image")
    private String image;
    @SerializedName("dressTypeId")
    private String dressTypeId;

    @SerializedName("dress_price")
    private Long dress_price;

    @SerializedName("size")
    private String size;

    @SerializedName("dress_description")
    private String dress_description;

    @SerializedName("color")
    private String color;

    @SerializedName("dress_status")
    private String dress_status;

    public Dress( String id, String dress_name, String dressTypeId, Long dress_price, String size, String dress_description, String color)  {
        this.id=id;
        this.dress_name=dress_name;
        this.dressTypeId=dressTypeId;
        this.dress_price=dress_price;
        this.size=size;
        this.dress_description=dress_description;
        this.color=color;

    }


    public Dress(String dress_name, String dressTypeId, Long dress_price, String color, String size, String dress_description) {
        this.dress_name=dress_name;
        this.dressTypeId=dressTypeId;
        this.dress_price=dress_price;
        this.size=size;
        this.dress_description=dress_description;
        this.color=color;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getDress_name() {
        return dress_name;
    }
    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
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

    public Long getDress_price() {
        return dress_price;
    }

    public void setDress_price(Long dress_price) {
        this.dress_price = dress_price;
    }

    public String getSize() {
        return size;
    }

    public void setSize(String size) {
        this.size = size;
    }

    public String getDress_description() {
        return dress_description;
    }

    public void setDress_description(String dress_description) {
        this.dress_description = dress_description;
    }

    public String getColor() {
        return color;
    }

    public void setColor(String color) {
        this.color = color;
    }

    public String getDress_status() {
        return dress_status;
    }

    public void setDress_status(String dress_status) {
        this.dress_status = dress_status;
    }
}

