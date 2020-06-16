package com.cgg.streetvendor.source.reposnse.places;

import androidx.room.Entity;
import androidx.room.PrimaryKey;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

@Entity(tableName = "village_info")
public class VillageEntity {

    @PrimaryKey(autoGenerate = true)
    private int id;

    @SerializedName("village_id")
    @Expose
    private String villageId;
    @SerializedName("mandal_id")
    @Expose
    private String mandalId;
    @SerializedName("village_name_tel")
    @Expose
    private String villageNameTel;
    @SerializedName("district_id")
    @Expose
    private String districtId;
    @SerializedName("village_name")
    @Expose
    private String villageName;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getVillageId() {
        return villageId;
    }

    public void setVillageId(String villageId) {
        this.villageId = villageId;
    }

    public String getMandalId() {
        return mandalId;
    }

    public void setMandalId(String mandalId) {
        this.mandalId = mandalId;
    }

    public String getVillageNameTel() {
        return villageNameTel;
    }

    public void setVillageNameTel(String villageNameTel) {
        this.villageNameTel = villageNameTel;
    }

    public String getDistrictId() {
        return districtId;
    }

    public void setDistrictId(String districtId) {
        this.districtId = districtId;
    }

    public String getVillageName() {
        return villageName;
    }

    public void setVillageName(String villageName) {
        this.villageName = villageName;
    }

}
