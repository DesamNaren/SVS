package com.cgg.streetvendor.source.reposnse.places;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class VillageResponse {

    @SerializedName("status_message")
    @Expose
    private String statusMessage;
    @SerializedName("status_code")
    @Expose
    private String statusCode;
    @SerializedName("village_data")
    @Expose
    private List<VillageEntity> villageData = null;

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

    public List<VillageEntity> getVillageData() {
        return villageData;
    }

    public void setVillageData(List<VillageEntity> villageData) {
        this.villageData = villageData;
    }

}
