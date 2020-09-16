package com.cgg.streetvendor.source.reposnse.reports;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class DailyReportData {

    @SerializedName("prevday_transgender")
    @Expose
    private String prevdayTransgender;
    @SerializedName("sv_mobile_target")
    @Expose
    private String svMobileTarget;
    @SerializedName("prevday_female")
    @Expose
    private String prevdayFemale;
    @SerializedName("prevday_male")
    @Expose
    private String prevdayMale;
    @SerializedName("cumm_male")
    @Expose
    private String cummMale;
    @SerializedName("cumm_transgender")
    @Expose
    private String cummTransgender;
    @SerializedName("total_balance")
    @Expose
    private String totalBalance;
    @SerializedName("district_name")
    @Expose
    private String districtName;
    @SerializedName("sv_mobile_revised_target")
    @Expose
    private String svMobileRevisedTarget;
    @SerializedName("city_name")
    @Expose
    private String cityName;
    @SerializedName("total")
    @Expose
    private String total;
    @SerializedName("sv_mobile_revised_target_percent")
    @Expose
    private String svMobileRevisedTargetPercent;
    @SerializedName("prevday_total")
    @Expose
    private String prevdayTotal;
    @SerializedName("female")
    @Expose
    private String female;
    @SerializedName("cumm_female")
    @Expose
    private String cummFemale;
    @SerializedName("male")
    @Expose
    private String male;
    @SerializedName("transgender")
    @Expose
    private String transgender;
    @SerializedName("cumm_total")
    @Expose
    private String cummTotal;
    @SerializedName("district_id")
    @Expose
    private String districtId;
    @SerializedName("city_id")
    @Expose
    private String cityId;

    public String getDistrictId() {
        return districtId;
    }

    public void setDistrictId(String districtId) {
        this.districtId = districtId;
    }

    public String getCityId() {
        return cityId;
    }

    public void setCityId(String cityId) {
        this.cityId = cityId;
    }

    public String getPrevdayTransgender() {
        return prevdayTransgender;
    }

    public void setPrevdayTransgender(String prevdayTransgender) {
        this.prevdayTransgender = prevdayTransgender;
    }

    public String getSvMobileTarget() {
        return svMobileTarget;
    }

    public void setSvMobileTarget(String svMobileTarget) {
        this.svMobileTarget = svMobileTarget;
    }

    public String getPrevdayFemale() {
        return prevdayFemale;
    }

    public void setPrevdayFemale(String prevdayFemale) {
        this.prevdayFemale = prevdayFemale;
    }

    public String getPrevdayMale() {
        return prevdayMale;
    }

    public void setPrevdayMale(String prevdayMale) {
        this.prevdayMale = prevdayMale;
    }

    public String getCummMale() {
        return cummMale;
    }

    public void setCummMale(String cummMale) {
        this.cummMale = cummMale;
    }

    public String getCummTransgender() {
        return cummTransgender;
    }

    public void setCummTransgender(String cummTransgender) {
        this.cummTransgender = cummTransgender;
    }

    public String getTotalBalance() {
        return totalBalance;
    }

    public void setTotalBalance(String totalBalance) {
        this.totalBalance = totalBalance;
    }

    public String getDistrictName() {
        return districtName;
    }

    public void setDistrictName(String districtName) {
        this.districtName = districtName;
    }

    public String getSvMobileRevisedTarget() {
        return svMobileRevisedTarget;
    }

    public void setSvMobileRevisedTarget(String svMobileRevisedTarget) {
        this.svMobileRevisedTarget = svMobileRevisedTarget;
    }

    public String getCityName() {
        return cityName;
    }

    public void setCityName(String cityName) {
        this.cityName = cityName;
    }

    public String getTotal() {
        return total;
    }

    public void setTotal(String total) {
        this.total = total;
    }

    public String getSvMobileRevisedTargetPercent() {
        return svMobileRevisedTargetPercent;
    }

    public void setSvMobileRevisedTargetPercent(String svMobileRevisedTargetPercent) {
        this.svMobileRevisedTargetPercent = svMobileRevisedTargetPercent;
    }

    public String getPrevdayTotal() {
        return prevdayTotal;
    }

    public void setPrevdayTotal(String prevdayTotal) {
        this.prevdayTotal = prevdayTotal;
    }

    public String getFemale() {
        return female;
    }

    public void setFemale(String female) {
        this.female = female;
    }

    public String getCummFemale() {
        return cummFemale;
    }

    public void setCummFemale(String cummFemale) {
        this.cummFemale = cummFemale;
    }

    public String getMale() {
        return male;
    }

    public void setMale(String male) {
        this.male = male;
    }

    public String getTransgender() {
        return transgender;
    }

    public void setTransgender(String transgender) {
        this.transgender = transgender;
    }

    public String getCummTotal() {
        return cummTotal;
    }

    public void setCummTotal(String cummTotal) {
        this.cummTotal = cummTotal;
    }

}
