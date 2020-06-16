package com.cgg.streetvendor.source.reposnse.kyc;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class GenderResponse {

    @SerializedName("status_message")
    @Expose
    private String statusMessage;
    @SerializedName("status_code")
    @Expose
    private String statusCode;
    @SerializedName("gender_data")
    @Expose
    private List<GenderEntity> genderData = null;

    public String getStatusMessage() {
        return statusMessage;
    }

    public void setStatusMessage(String statusMessage) {
        this.statusMessage = statusMessage;
    }

    public String getStatusCode() {
        return statusCode;
    }

    public void setStatusCode(String statusCode) {
        this.statusCode = statusCode;
    }

    public List<GenderEntity> getGenderData() {
        return genderData;
    }

    public void setGenderData(List<GenderEntity> genderData) {
        this.genderData = genderData;
    }

}