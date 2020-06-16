package com.cgg.streetvendor.source.reposnse.bankbranch;


import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;


public class BankResponse {

    @SerializedName("status_message")
    @Expose
    private String statusMessage;
    @SerializedName("status_code")
    @Expose
    private String statusCode;
    @SerializedName("bank_data")
    @Expose
    private List<BankEntity> bankData = null;

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

    public List<BankEntity> getBankData() {
        return bankData;
    }

    public void setBankData(List<BankEntity> bankData) {
        this.bankData = bankData;
    }

}
