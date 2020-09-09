package com.cgg.streetvendor.source.reposnse.submit;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class ValidateAadharResponse {

    @SerializedName("status_message")
    @Expose
    private String statusMessage;
    @SerializedName("status_code")
    @Expose
    private String statusCode;
    @SerializedName("sv_data")
    @Expose
    private List<SvDatum> svData = null;

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

    public List<SvDatum> getSvData() {
        return svData;
    }

    public void setSvData(List<SvDatum> svData) {
        this.svData = svData;
    }

}