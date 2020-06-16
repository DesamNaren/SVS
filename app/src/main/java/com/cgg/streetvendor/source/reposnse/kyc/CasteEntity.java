package com.cgg.streetvendor.source.reposnse.kyc;

import androidx.room.Entity;
import androidx.room.PrimaryKey;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

@Entity(tableName = "caste_info")
public class CasteEntity {

    @PrimaryKey(autoGenerate = true)
    private int id;
    @SerializedName("caste_tel")
    @Expose
    private String casteTel;
    @SerializedName("caste_id")
    @Expose
    private String casteId;
    @SerializedName("caste")
    @Expose
    private String caste;


    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getCasteTel() {
        return casteTel;
    }

    public void setCasteTel(String casteTel) {
        this.casteTel = casteTel;
    }

    public String getCasteId() {
        return casteId;
    }

    public void setCasteId(String casteId) {
        this.casteId = casteId;
    }

    public String getCaste() {
        return caste;
    }

    public void setCaste(String caste) {
        this.caste = caste;
    }

}
