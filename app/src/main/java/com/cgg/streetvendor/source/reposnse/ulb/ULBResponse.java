package com.cgg.streetvendor.source.reposnse.ulb;

import java.util.List;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class ULBResponse {

    @SerializedName("status_message")
    @Expose
    private String statusMessage;
    @SerializedName("ulb_data")
    @Expose
    private List<UlbEntity> ulbData = null;
    @SerializedName("status_code")
    @Expose
    private String statusCode;

    public String getStatusMessage() {
        return statusMessage;
    }

    public void setStatusMessage(String statusMessage) {
        this.statusMessage = statusMessage;
    }

    public List<UlbEntity> getUlbData() {
        return ulbData;
    }

    public void setUlbData(List<UlbEntity> ulbData) {
        this.ulbData = ulbData;
    }

    public String getStatusCode() {
        return statusCode;
    }

    public void setStatusCode(String statusCode) {
        this.statusCode = statusCode;
    }

}
