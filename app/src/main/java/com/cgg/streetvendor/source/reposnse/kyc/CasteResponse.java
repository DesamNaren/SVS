package com.cgg.streetvendor.source.reposnse.kyc;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class CasteResponse {

    @SerializedName("status_message")
    @Expose
    private String statusMessage;
    @SerializedName("caste_data")
    @Expose
    private List<CasteEntity> casteData = null;
    @SerializedName("status_code")
    @Expose
    private String statusCode;

    public String getStatusMessage() {
        return statusMessage;
    }

    public void setStatusMessage(String statusMessage) {
        this.statusMessage = statusMessage;
    }

    public List<CasteEntity> getCasteData() {
        return casteData;
    }

    public void setCasteData(List<CasteEntity> casteData) {
        this.casteData = casteData;
    }

    public String getStatusCode() {
        return statusCode;
    }

    public void setStatusCode(String statusCode) {
        this.statusCode = statusCode;
    }

}
