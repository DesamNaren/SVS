package com.cgg.streetvendor.source.reposnse.ulb;

import androidx.room.Entity;
import androidx.room.PrimaryKey;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

@Entity (tableName = "ulb_info")
public class UlbEntity {

    @PrimaryKey(autoGenerate = true)
    private int id;
    @SerializedName("ulb_name_tel")
    @Expose
    private String ulbNameTel;
    @SerializedName("ulb_id")
    @Expose
    private String ulbId;
    @SerializedName("ulb_name")
    @Expose
    private String ulbName;
    @SerializedName("ulb_code")
    @Expose
    private String ulbCode;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getUlbNameTel() {
        return ulbNameTel;
    }

    public void setUlbNameTel(String ulbNameTel) {
        this.ulbNameTel = ulbNameTel;
    }

    public String getUlbId() {
        return ulbId;
    }

    public void setUlbId(String ulbId) {
        this.ulbId = ulbId;
    }

    public String getUlbName() {
        return ulbName;
    }

    public void setUlbName(String ulbName) {
        this.ulbName = ulbName;
    }

    public String getUlbCode() {
        return ulbCode;
    }

    public void setUlbCode(String ulbCode) {
        this.ulbCode = ulbCode;
    }

}
