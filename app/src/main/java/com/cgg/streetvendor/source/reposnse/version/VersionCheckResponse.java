package com.cgg.streetvendor.source.reposnse.version;


import java.util.List;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class VersionCheckResponse {

    @SerializedName("status_message")
    @Expose
    private String statusMessage;
    @SerializedName("status_code")
    @Expose
    private String statusCode;
    @SerializedName("version_data")
    @Expose
    private List<VersionData> versionData = null;

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

    public List<VersionData> getVersionData() {
        return versionData;
    }

    public void setVersionData(List<VersionData> versionData) {
        this.versionData = versionData;
    }

}

