package com.cgg.streetvendor.source.reposnse;

import com.cgg.streetvendor.source.reposnse.kyc.GenderEntity;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class SubmitResponse {

    @SerializedName("status_message")
    @Expose
    private String statusMessage;
    @SerializedName("status_code")
    @Expose
    private String statusCode;

    @SerializedName("ref_no")
    @Expose
    private String refNO;

    public String getRefNO() {
        return refNO;
    }

    public void setRefNO(String refNO) {
        this.refNO = refNO;
    }

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


}