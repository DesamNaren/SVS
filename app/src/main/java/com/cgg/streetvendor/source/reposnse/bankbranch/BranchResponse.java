package com.cgg.streetvendor.source.reposnse.bankbranch;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;


public class BranchResponse {

    @SerializedName("status_message")
    @Expose
    private String statusMessage;
    @SerializedName("status_code")
    @Expose
    private String statusCode;
    @SerializedName("branch_data")
    @Expose
    private List<BranchEntity> branchData = null;

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

    public List<BranchEntity> getBranchData() {
        return branchData;
    }

    public void setBranchData(List<BranchEntity> branchData) {
        this.branchData = branchData;
    }

}
