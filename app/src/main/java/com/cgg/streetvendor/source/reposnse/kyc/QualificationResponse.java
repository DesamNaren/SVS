package com.cgg.streetvendor.source.reposnse.kyc;


import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class QualificationResponse {

    @SerializedName("status_message")
    @Expose
    private String statusMessage;
    @SerializedName("qualification_data")
    @Expose
    private List<QualificationEntity> qualificationData = null;
    @SerializedName("status_code")
    @Expose
    private String statusCode;

    public String getStatusMessage() {
        return statusMessage;
    }

    public void setStatusMessage(String statusMessage) {
        this.statusMessage = statusMessage;
    }

    public List<QualificationEntity> getQualificationData() {
        return qualificationData;
    }

    public void setQualificationData(List<QualificationEntity> qualificationData) {
        this.qualificationData = qualificationData;
    }

    public String getStatusCode() {
        return statusCode;
    }

    public void setStatusCode(String statusCode) {
        this.statusCode = statusCode;
    }

}