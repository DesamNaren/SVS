package com.cgg.streetvendor.source.reposnse.kyc;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class VendingTypeResponse {

    @SerializedName("status_message")
    @Expose
    private String statusMessage;
    @SerializedName("vending_data")
    @Expose
    private List<VendingTypeEntity> vendingData = null;
    @SerializedName("status_code")
    @Expose
    private String statusCode;

    public String getStatusMessage() {
        return statusMessage;
    }

    public void setStatusMessage(String statusMessage) {
        this.statusMessage = statusMessage;
    }

    public List<VendingTypeEntity> getVendingData() {
        return vendingData;
    }

    public void setVendingData(List<VendingTypeEntity> vendingData) {
        this.vendingData = vendingData;
    }

    public String getStatusCode() {
        return statusCode;
    }

    public void setStatusCode(String statusCode) {
        this.statusCode = statusCode;
    }

}
