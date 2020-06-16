package com.cgg.streetvendor.source.reposnse.places;

import androidx.room.Entity;
import androidx.room.PrimaryKey;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

@Entity(tableName = "district_info")
public class DistrictEntity {

    @PrimaryKey(autoGenerate = true)
    private int id;
    @SerializedName("district_name")
    @Expose
    private String districtName;
    @SerializedName("district_name_tel")
    @Expose
    private String districtNameTel;
    @SerializedName("district_id")
    @Expose
    private String districtId;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getDistrictName() {
        return districtName;
    }

    public void setDistrictName(String districtName) {
        this.districtName = districtName;
    }

    public String getDistrictNameTel() {
        return districtNameTel;
    }

    public void setDistrictNameTel(String districtNameTel) {
        this.districtNameTel = districtNameTel;
    }

    public String getDistrictId() {
        return districtId;
    }

    public void setDistrictId(String districtId) {
        this.districtId = districtId;
    }

}
