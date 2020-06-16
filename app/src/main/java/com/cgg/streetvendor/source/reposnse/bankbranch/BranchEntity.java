package com.cgg.streetvendor.source.reposnse.bankbranch;

import androidx.room.Entity;
import androidx.room.PrimaryKey;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
@Entity (tableName = "branch_info")
public class BranchEntity {

    @PrimaryKey (autoGenerate = true)
    private int id;

    @SerializedName("ifsc_code")
    @Expose
    private String ifscCode;
    @SerializedName("branch_id")
    @Expose
    private String branchId;
    @SerializedName("bank_id")
    @Expose
    private String bankId;
    @SerializedName("branch_name")
    @Expose
    private String branchName;
    @SerializedName("branch_name_tel")
    @Expose
    private String branchNameTel;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getIfscCode() {
        return ifscCode;
    }

    public void setIfscCode(String ifscCode) {
        this.ifscCode = ifscCode;
    }

    public String getBranchId() {
        return branchId;
    }

    public void setBranchId(String branchId) {
        this.branchId = branchId;
    }

    public String getBankId() {
        return bankId;
    }

    public void setBankId(String bankId) {
        this.bankId = bankId;
    }

    public String getBranchName() {
        return branchName;
    }

    public void setBranchName(String branchName) {
        this.branchName = branchName;
    }

    public String getBranchNameTel() {
        return branchNameTel;
    }

    public void setBranchNameTel(String branchNameTel) {
        this.branchNameTel = branchNameTel;
    }

}
