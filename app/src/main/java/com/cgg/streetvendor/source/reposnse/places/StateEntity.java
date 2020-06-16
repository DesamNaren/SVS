package com.cgg.streetvendor.source.reposnse.places;

import androidx.room.Entity;
import androidx.room.PrimaryKey;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

@Entity (tableName = "State_Info")
public class StateEntity {

    @PrimaryKey (autoGenerate = true)
    private int id;

    @SerializedName("state_type_tel")
    @Expose
    private String stateTypeTel;
    @SerializedName("state_type")
    @Expose
    private String stateType;
    @SerializedName("state_id")
    @Expose
    private String stateId;


    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getStateTypeTel() {
        return stateTypeTel;
    }

    public void setStateTypeTel(String stateTypeTel) {
        this.stateTypeTel = stateTypeTel;
    }

    public String getStateType() {
        return stateType;
    }

    public void setStateType(String stateType) {
        this.stateType = stateType;
    }

    public String getStateId() {
        return stateId;
    }

    public void setStateId(String stateId) {
        this.stateId = stateId;
    }

}
