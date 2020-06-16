package com.cgg.streetvendor.source.reposnse.kyc;

import androidx.room.Entity;
import androidx.room.PrimaryKey;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

@Entity(tableName = "qualification_info")
public class QualificationEntity {

    @PrimaryKey(autoGenerate = true)
    private int id;

    @SerializedName("qualification_id")
    @Expose
    private String qualificationId;
    @SerializedName("qualification_type")
    @Expose
    private String qualificationType;
    @SerializedName("qualification_type_tel")
    @Expose
    private String qualificationTypeTel;


    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getQualificationId() {
        return qualificationId;
    }

    public void setQualificationId(String qualificationId) {
        this.qualificationId = qualificationId;
    }

    public String getQualificationType() {
        return qualificationType;
    }

    public void setQualificationType(String qualificationType) {
        this.qualificationType = qualificationType;
    }

    public String getQualificationTypeTel() {
        return qualificationTypeTel;
    }

    public void setQualificationTypeTel(String qualificationTypeTel) {
        this.qualificationTypeTel = qualificationTypeTel;
    }

}
