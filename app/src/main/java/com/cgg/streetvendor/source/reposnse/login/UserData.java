package com.cgg.streetvendor.source.reposnse.login;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class UserData {
    @SerializedName("ulb_id")
    @Expose
    private String ulb_id;
    @SerializedName("state_id")
    @Expose
    private String state_id;
    @SerializedName("district_id")
    @Expose
    private String district_id;
    @SerializedName("user_description")
    @Expose
    private String user_description;

    public String getUlb_id() {
        return ulb_id;
    }

    public void setUlb_id(String ulb_id) {
        this.ulb_id = ulb_id;
    }

    public String getState_id() {
        return state_id;
    }

    public void setState_id(String state_id) {
        this.state_id = state_id;
    }

    public String getDistrict_id() {
        return district_id;
    }

    public void setDistrict_id(String district_id) {
        this.district_id = district_id;
    }

    public String getUser_description() {
        return user_description;
    }

    public void setUser_description(String user_description) {
        this.user_description = user_description;
    }
}
