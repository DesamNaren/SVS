package com.cgg.streetvendor.source.reposnse.kyc;

import androidx.room.Entity;
import androidx.room.PrimaryKey;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

@Entity(tableName = "vending_type_info")
public class VendingTypeEntity {

    @PrimaryKey(autoGenerate = true)
    private  int id;

    @SerializedName("vending_id")
    @Expose
    private String vendingId;
    @SerializedName("vending_type")
    @Expose
    private String vendingType;
    @SerializedName("vending_type_tel")
    @Expose
    private String vendingTypeTel;


    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getVendingId() {
        return vendingId;
    }

    public void setVendingId(String vendingId) {
        this.vendingId = vendingId;
    }

    public String getVendingType() {
        return vendingType;
    }

    public void setVendingType(String vendingType) {
        this.vendingType = vendingType;
    }

    public String getVendingTypeTel() {
        return vendingTypeTel;
    }

    public void setVendingTypeTel(String vendingTypeTel) {
        this.vendingTypeTel = vendingTypeTel;
    }

}
