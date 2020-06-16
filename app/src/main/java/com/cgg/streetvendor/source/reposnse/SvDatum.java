package com.cgg.streetvendor.source.reposnse;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class SvDatum {

    @SerializedName("sv_name")
    @Expose
    private String svName;
    @SerializedName("sv_photo")
    @Expose
    private String svPhoto;

    public String getSvName() {
        return svName;
    }

    public void setSvName(String svName) {
        this.svName = svName;
    }

    public String getSvPhoto() {
        return svPhoto;
    }

    public void setSvPhoto(String svPhoto) {
        this.svPhoto = svPhoto;
    }

}
