package com.cgg.streetvendor.source.reposnse.kyc;

import androidx.room.Entity;
import androidx.room.PrimaryKey;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

@Entity(tableName = "religion_info")
public class ReligionEntity {

    @PrimaryKey(autoGenerate = true)
    private int id;
    @SerializedName("religion_id")
    @Expose
    private String religionId;
    @SerializedName("religion_name_tel")
    @Expose
    private String religionNameTel;
    @SerializedName("religion_name")
    @Expose
    private String religionName;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getReligionId() {
        return religionId;
    }

    public void setReligionId(String religionId) {
        this.religionId = religionId;
    }

    public String getReligionNameTel() {
        return religionNameTel;
    }

    public void setReligionNameTel(String religionNameTel) {
        this.religionNameTel = religionNameTel;
    }

    public String getReligionName() {
        return religionName;
    }

    public void setReligionName(String religionName) {
        this.religionName = religionName;
    }

}
