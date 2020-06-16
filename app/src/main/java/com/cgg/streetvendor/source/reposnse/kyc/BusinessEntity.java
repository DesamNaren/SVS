package com.cgg.streetvendor.source.reposnse.kyc;

import androidx.room.Entity;
import androidx.room.PrimaryKey;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

@Entity(tableName = "business_info")
public class BusinessEntity {

    @PrimaryKey(autoGenerate = true)
    private int id;

    @SerializedName("business_name_tel")
    @Expose
    private String businessNameTel;
    @SerializedName("business_name")
    @Expose
    private String businessName;
    @SerializedName("business_id")
    @Expose
    private String businessId;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getBusinessNameTel() {
        return businessNameTel;
    }

    public void setBusinessNameTel(String businessNameTel) {
        this.businessNameTel = businessNameTel;
    }

    public String getBusinessName() {
        return businessName;
    }

    public void setBusinessName(String businessName) {
        this.businessName = businessName;
    }

    public String getBusinessId() {
        return businessId;
    }

    public void setBusinessId(String businessId) {
        this.businessId = businessId;
    }

}
