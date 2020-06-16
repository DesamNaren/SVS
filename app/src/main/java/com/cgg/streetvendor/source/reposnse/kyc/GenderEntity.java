package com.cgg.streetvendor.source.reposnse.kyc;

import androidx.room.Entity;
import androidx.room.PrimaryKey;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

@Entity(tableName = "gender_info")
public class GenderEntity {

    @PrimaryKey(autoGenerate = true)
    private int id;
    @SerializedName("gender_name_tel")
    @Expose
    private String genderNameTel;
    @SerializedName("gender_code")
    @Expose
    private String genderCode;
    @SerializedName("gender_id")
    @Expose
    private String genderId;
    @SerializedName("gender_name")
    @Expose
    private String genderName;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getGenderNameTel() {
        return genderNameTel;
    }

    public void setGenderNameTel(String genderNameTel) {
        this.genderNameTel = genderNameTel;
    }

    public String getGenderCode() {
        return genderCode;
    }

    public void setGenderCode(String genderCode) {
        this.genderCode = genderCode;
    }

    public String getGenderId() {
        return genderId;
    }

    public void setGenderId(String genderId) {
        this.genderId = genderId;
    }

    public String getGenderName() {
        return genderName;
    }

    public void setGenderName(String genderName) {
        this.genderName = genderName;
    }

}
