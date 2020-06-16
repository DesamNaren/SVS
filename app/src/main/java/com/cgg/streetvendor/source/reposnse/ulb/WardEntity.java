package com.cgg.streetvendor.source.reposnse.ulb;

import androidx.room.Entity;
import androidx.room.PrimaryKey;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

@Entity(tableName = "ward_info")
public class WardEntity {

    @PrimaryKey(autoGenerate = true)
    private int id;
    @SerializedName("ward_name")
    @Expose
    private String wardName;
    @SerializedName("ulb_id")
    @Expose
    private String ulbId;
    @SerializedName("ward_id")
    @Expose
    private String wardId;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getWardName() {
        return wardName;
    }

    public void setWardName(String wardName) {
        this.wardName = wardName;
    }

    public String getUlbId() {
        return ulbId;
    }

    public void setUlbId(String ulbId) {
        this.ulbId = ulbId;
    }

    public String getWardId() {
        return wardId;
    }

    public void setWardId(String wardId) {
        this.wardId = wardId;
    }

}
