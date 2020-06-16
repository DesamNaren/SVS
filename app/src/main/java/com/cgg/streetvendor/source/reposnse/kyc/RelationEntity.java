package com.cgg.streetvendor.source.reposnse.kyc;

import androidx.room.Entity;
import androidx.room.PrimaryKey;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

@Entity(tableName = "relation_info")
public class RelationEntity {

    @PrimaryKey(autoGenerate = true)
    private int id;

    @SerializedName("relation_name_tel")
    @Expose
    private String relationNameTel;
    @SerializedName("relation_name")
    @Expose
    private String relationName;
    @SerializedName("relation_id")
    @Expose
    private String relationId;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getRelationNameTel() {
        return relationNameTel;
    }

    public void setRelationNameTel(String relationNameTel) {
        this.relationNameTel = relationNameTel;
    }

    public String getRelationName() {
        return relationName;
    }

    public void setRelationName(String relationName) {
        this.relationName = relationName;
    }

    public String getRelationId() {
        return relationId;
    }

    public void setRelationId(String relationId) {
        this.relationId = relationId;
    }

}
