package com.cgg.streetvendor.source.reposnse.places;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class StateResponse {

    @SerializedName("status_message")
    @Expose
    private String statusMessage;
    @SerializedName("state_data")
    @Expose
    private List<StateEntity> stateData = null;
    @SerializedName("status_code")
    @Expose
    private String statusCode;

    public String getStatusMessage() {
        return statusMessage;
    }

    public void setStatusMessage(String statusMessage) {
        this.statusMessage = statusMessage;
    }

    public List<StateEntity> getStateData() {
        return stateData;
    }

    public void setStateData(List<StateEntity> stateData) {
        this.stateData = stateData;
    }

    public String getStatusCode() {
        return statusCode;
    }

    public void setStatusCode(String statusCode) {
        this.statusCode = statusCode;
    }

}