package com.cgg.streetvendor.source.reposnse.kyc;

import androidx.room.Entity;
import androidx.room.PrimaryKey;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

@Entity(tableName = "pwd_info")
public class PWDEntity {

    @PrimaryKey(autoGenerate =true)
    private int id;
    @SerializedName("pwd_type")
    @Expose
    private String pwdType;
    @SerializedName("pwd_id")
    @Expose
    private String pwdId;
    @SerializedName("pwd_type_tel")
    @Expose
    private String pwdTypeTel;


    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getPwdType() {
        return pwdType;
    }

    public void setPwdType(String pwdType) {
        this.pwdType = pwdType;
    }

    public String getPwdId() {
        return pwdId;
    }

    public void setPwdId(String pwdId) {
        this.pwdId = pwdId;
    }

    public String getPwdTypeTel() {
        return pwdTypeTel;
    }

    public void setPwdTypeTel(String pwdTypeTel) {
        this.pwdTypeTel = pwdTypeTel;
    }

}
