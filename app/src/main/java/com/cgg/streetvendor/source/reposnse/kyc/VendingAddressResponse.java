package com.cgg.streetvendor.source.reposnse.kyc;


import java.util.List;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class VendingAddressResponse {

    @SerializedName("status_message")
    @Expose
    private String statusMessage;
    @SerializedName("status_code")
    @Expose
    private String statusCode;
    @SerializedName("village_data")
    @Expose
    private List<VendingAddressEntity> villageData = null;

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

    public List<VendingAddressEntity> getVillageData() {
        return villageData;
    }

    public void setVillageData(List<VendingAddressEntity> villageData) {
        this.villageData = villageData;
    }

}

