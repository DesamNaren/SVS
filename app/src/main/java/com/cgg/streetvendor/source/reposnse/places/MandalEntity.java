package com.cgg.streetvendor.source.reposnse.places;

import androidx.room.Entity;
import androidx.room.PrimaryKey;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
@Entity (tableName = "mandal_info")
public class MandalEntity {

    @PrimaryKey(autoGenerate = true)
    private int id;

    @SerializedName("mandal_name")
    @Expose
    private String mandalName;
    @SerializedName("mandal_id")
    @Expose
    private String mandalId;
    @SerializedName("mandal_name_tel")
    @Expose
    private String mandalNameTel;
    @SerializedName("district_id")
    @Expose
    private String districtId;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getMandalName() {
        return mandalName;
    }

    public void setMandalName(String mandalName) {
        this.mandalName = mandalName;
    }

    public String getMandalId() {
        return mandalId;
    }

    public void setMandalId(String mandalId) {
        this.mandalId = mandalId;
    }

    public String getMandalNameTel() {
        return mandalNameTel;
    }

    public void setMandalNameTel(String mandalNameTel) {
        this.mandalNameTel = mandalNameTel;
    }

    public String getDistrictId() {
        return districtId;
    }

    public void setDistrictId(String districtId) {
        this.districtId = districtId;
    }

}
