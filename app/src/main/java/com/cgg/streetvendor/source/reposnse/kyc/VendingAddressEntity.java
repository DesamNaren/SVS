package com.cgg.streetvendor.source.reposnse.kyc;

import androidx.room.Entity;
import androidx.room.PrimaryKey;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

@Entity(tableName = "vending_address_info")
public class VendingAddressEntity {

    @PrimaryKey(autoGenerate = true)
    private int id;

    @SerializedName("vending_area_id")
    @Expose
    private String vendingAreaId;
    @SerializedName("vending_area")
    @Expose
    private String vendingArea;
    @SerializedName("ulb_id")
    @Expose
    private String ulbId;
    @SerializedName("vending_area_tel")
    @Expose
    private String vendingAreaTel;
    @SerializedName("district_id")
    @Expose
    private String districtId;
    @SerializedName("vending_area_name")
    @Expose
    private String vendingAreaName;
    @SerializedName("vending_area_name_tel")
    @Expose
    private String vendingAreaNameTel;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getVendingAreaId() {
        return vendingAreaId;
    }

    public void setVendingAreaId(String vendingAreaId) {
        this.vendingAreaId = vendingAreaId;
    }

    public String getVendingArea() {
        return vendingArea;
    }

    public void setVendingArea(String vendingArea) {
        this.vendingArea = vendingArea;
    }

    public String getUlbId() {
        return ulbId;
    }

    public void setUlbId(String ulbId) {
        this.ulbId = ulbId;
    }

    public String getVendingAreaTel() {
        return vendingAreaTel;
    }

    public void setVendingAreaTel(String vendingAreaTel) {
        this.vendingAreaTel = vendingAreaTel;
    }

    public String getDistrictId() {
        return districtId;
    }

    public void setDistrictId(String districtId) {
        this.districtId = districtId;
    }

    public String getVendingAreaName() {
        return vendingAreaName;
    }

    public void setVendingAreaName(String vendingAreaName) {
        this.vendingAreaName = vendingAreaName;
    }

    public String getVendingAreaNameTel() {
        return vendingAreaNameTel;
    }

    public void setVendingAreaNameTel(String vendingAreaNameTel) {
        this.vendingAreaNameTel = vendingAreaNameTel;
    }

}
