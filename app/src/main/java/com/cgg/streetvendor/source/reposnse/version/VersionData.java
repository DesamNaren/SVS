package com.cgg.streetvendor.source.reposnse.version;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class VersionData {

    @SerializedName("version_no")
    @Expose
    private String versionNo;
    @SerializedName("inserted_time")
    @Expose
    private String insertedTime;
    @SerializedName("is_updated")
    @Expose
    private String isUpdated;

    public String getVersionNo() {
        return versionNo;
    }

    public void setVersionNo(String versionNo) {
        this.versionNo = versionNo;
    }

    public String getInsertedTime() {
        return insertedTime;
    }

    public void setInsertedTime(String insertedTime) {
        this.insertedTime = insertedTime;
    }

    public String getIsUpdated() {
        return isUpdated;
    }

    public void setIsUpdated(String isUpdated) {
        this.isUpdated = isUpdated;
    }

}
