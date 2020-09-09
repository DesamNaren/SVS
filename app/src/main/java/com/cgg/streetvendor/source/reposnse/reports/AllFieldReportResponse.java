package com.cgg.streetvendor.source.reposnse.reports;

import java.util.List;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class AllFieldReportResponse {

    @SerializedName("status_message")
    @Expose
    private String statusMessage;
    @SerializedName("status_code")
    @Expose
    private String statusCode;
    @SerializedName("sv_data")
    @Expose
    private List<AllFieldReportData> allFieldReportData = null;

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


    public List<AllFieldReportData> getAllFieldReportData() {
        return allFieldReportData;
    }

    public void setAllFieldReportData(List<AllFieldReportData> allFieldReportData) {
        this.allFieldReportData = allFieldReportData;
    }
}

