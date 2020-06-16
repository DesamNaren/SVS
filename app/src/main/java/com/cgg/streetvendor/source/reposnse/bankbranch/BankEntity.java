package com.cgg.streetvendor.source.reposnse.bankbranch;

import androidx.room.Entity;
import androidx.room.PrimaryKey;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

@Entity(tableName = "bank_info")
public class BankEntity {

    @PrimaryKey(autoGenerate = true)
    private int id;

    @SerializedName("bank_name_tel")
    @Expose
    private String bankNameTel;
    @SerializedName("bank_id")
    @Expose
    private String bankId;
    @SerializedName("bank_name")
    @Expose
    private String bankName;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getBankNameTel() {
        return bankNameTel;
    }

    public void setBankNameTel(String bankNameTel) {
        this.bankNameTel = bankNameTel;
    }

    public String getBankId() {
        return bankId;
    }

    public void setBankId(String bankId) {
        this.bankId = bankId;
    }

    public String getBankName() {
        return bankName;
    }

    public void setBankName(String bankName) {
        this.bankName = bankName;
    }

}
