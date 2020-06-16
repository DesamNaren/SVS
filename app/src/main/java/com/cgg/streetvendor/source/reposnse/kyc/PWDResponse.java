package com.cgg.streetvendor.source.reposnse.kyc;

import java.util.List;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class PWDResponse {

    @SerializedName("status_message")
    @Expose
    private String statusMessage;
    @SerializedName("status_code")
    @Expose
    private String statusCode;
    @SerializedName("pwd_data")
    @Expose
    private List<PWDEntity> pwdData = null;

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

    public List<PWDEntity> getPwdData() {
        return pwdData;
    }

    public void setPwdData(List<PWDEntity> pwdData) {
        this.pwdData = pwdData;
    }

}

