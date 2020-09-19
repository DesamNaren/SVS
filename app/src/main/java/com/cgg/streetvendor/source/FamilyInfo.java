package com.cgg.streetvendor.source;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class FamilyInfo {

    @SerializedName("fmember_name")
    @Expose
    private String fmemberName;
    @SerializedName("fgender")
    @Expose
    private String fgender;
    @SerializedName("fage")
    @Expose
    private String fage;
    @SerializedName("frelation")
    @Expose
    private String frelation;
    @SerializedName("fqualification")
    @Expose
    private String fqualification;

    private String gender_sel;
    private String rel_sel;
    private String qal_sel;

    public FamilyInfo(String fmemberName, String fgender, String fage, String frelation, String fqualification, String gender_sel, String rel_sel, String qal_sel) {
        this.fmemberName = fmemberName;
        this.fgender = fgender;
        this.fage = fage;
        this.frelation = frelation;
        this.fqualification = fqualification;
        this.gender_sel = gender_sel;
        this.rel_sel = rel_sel;
        this.qal_sel = qal_sel;
    }

    public String getGender_sel() {
        return gender_sel;
    }

    public void setGender_sel(String gender_sel) {
        this.gender_sel = gender_sel;
    }

    public String getRel_sel() {
        return rel_sel;
    }

    public void setRel_sel(String rel_sel) {
        this.rel_sel = rel_sel;
    }

    public String getQal_sel() {
        return qal_sel;
    }

    public void setQal_sel(String qal_sel) {
        this.qal_sel = qal_sel;
    }

    public String getFmemberName() {
        return fmemberName;
    }

    public void setFmemberName(String fmemberName) {
        this.fmemberName = fmemberName;
    }

    public String getFgender() {
        return fgender;
    }

    public void setFgender(String fgender) {
        this.fgender = fgender;
    }

    public String getFage() {
        return fage;
    }

    public void setFage(String fage) {
        this.fage = fage;
    }

    public String getFrelation() {
        return frelation;
    }

    public void setFrelation(String frelation) {
        this.frelation = frelation;
    }

    public String getFqualification() {
        return fqualification;
    }

    public void setFqualification(String fqualification) {
        this.fqualification = fqualification;
    }
}
