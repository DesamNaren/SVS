package com.cgg.streetvendor.source.request;

import com.cgg.streetvendor.source.FamilyInfo;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;



public class SubmitRequest {

    @SerializedName("ulbid")
    @Expose
    private String ulbid;
    @SerializedName("district_id")
    @Expose
    private String districtId;
    @SerializedName("business_ward")
    @Expose
    private String businessWard;
    @SerializedName("sv_name")
    @Expose
    private String svName;
    @SerializedName("sv_father_name")
    @Expose
    private String svFatherName;
    @SerializedName("dob")
    @Expose
    private String dob;
    @SerializedName("age")
    @Expose
    private String age;
    @SerializedName("religion")
    @Expose
    private String religion;
    @SerializedName("gender")
    @Expose
    private String gender;
    @SerializedName("caste")
    @Expose
    private String caste;
    @SerializedName("pwd_type")
    @Expose
    private String pwdType;
    @SerializedName("qualification")
    @Expose
    private String qualification;
    @SerializedName("sv_adhar_card_num")
    @Expose
    private String svAdharCardNum;
    @SerializedName("bank_id")
    @Expose
    private String bankId;
    @SerializedName("branchid")
    @Expose
    private String branchid;
    @SerializedName("bank_ac_no")
    @Expose
    private String bankAcNo;
    @SerializedName("ifsc_code")
    @Expose
    private String ifscCode;
    @SerializedName("mobile_no")
    @Expose
    private String mobileNo;
    @SerializedName("emergency_contact_no")
    @Expose
    private String emergencyContactNo;
    @SerializedName("business_type")
    @Expose
    private String businessType;
    @SerializedName("vending_type")
    @Expose
    private String vendingType;
    @SerializedName("vending_area")
    @Expose
    private String vendingArea;
    @SerializedName("address")
    @Expose
    private String address;
    @SerializedName("permanent_hno")
    @Expose
    private String permanentHno;
    @SerializedName("permanent_street")
    @Expose
    private String permanentStreet;
    @SerializedName("permanent_state")
    @Expose
    private String permanentState;
    @SerializedName("permanent_dist")
    @Expose
    private String permanentDist;
    @SerializedName("permanent_mand")
    @Expose
    private String permanentMand;
    @SerializedName("permanent_village")
    @Expose
    private String permanentVillage;
    @SerializedName("permanent_dist_others")
    @Expose
    private String permanentDistOthers;
    @SerializedName("permanent_mand_others")
    @Expose
    private String permanentMandOthers;
    @SerializedName("permanent_village_others")
    @Expose
    private String permanentVillageOthers;
    @SerializedName("police_station")
    @Expose
    private String policeStation;
    @SerializedName("start_vending_act")
    @Expose
    private String startVendingAct;
    @SerializedName("no_family_members")
    @Expose
    private String noFamilyMembers;
    @SerializedName("caste_others")
    @Expose
    private String casteOthers;
    @SerializedName("religion_others")
    @Expose
    private String religionOthers;
    @SerializedName("business_type_others")
    @Expose
    private String businessTypeOthers;
    @SerializedName("ip_address")
    @Expose
    private String ipAddress;
    @SerializedName("sv_photo_path_b64")
    @Expose
    private String svPhotoPathB64;
    @SerializedName("sv_act_photo_path_b64")
    @Expose
    private String svActPhotoPathB64;
    @SerializedName("family_info")
    @Expose
    private List<FamilyInfo> familyInfo = null;

    public String getReligionOthers() {
        return religionOthers;
    }

    public void setReligionOthers(String religionOthers) {
        this.religionOthers = religionOthers;
    }

    public String getDistrictId() {
        return districtId;
    }

    public void setDistrictId(String districtId) {
        this.districtId = districtId;
    }

    public String getUlbid() {
        return ulbid;
    }

    public void setUlbid(String ulbid) {
        this.ulbid = ulbid;
    }

    public String getBusinessWard() {
        return businessWard;
    }

    public void setBusinessWard(String businessWard) {
        this.businessWard = businessWard;
    }

    public String getSvName() {
        return svName;
    }

    public void setSvName(String svName) {
        this.svName = svName;
    }

    public String getSvFatherName() {
        return svFatherName;
    }

    public void setSvFatherName(String svFatherName) {
        this.svFatherName = svFatherName;
    }

    public String getDob() {
        return dob;
    }

    public void setDob(String dob) {
        this.dob = dob;
    }

    public String getAge() {
        return age;
    }

    public void setAge(String age) {
        this.age = age;
    }

    public String getReligion() {
        return religion;
    }

    public void setReligion(String religion) {
        this.religion = religion;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public String getCaste() {
        return caste;
    }

    public void setCaste(String caste) {
        this.caste = caste;
    }

    public String getPwdType() {
        return pwdType;
    }

    public void setPwdType(String pwdType) {
        this.pwdType = pwdType;
    }

    public String getQualification() {
        return qualification;
    }

    public void setQualification(String qualification) {
        this.qualification = qualification;
    }

    public String getSvAdharCardNum() {
        return svAdharCardNum;
    }

    public void setSvAdharCardNum(String svAdharCardNum) {
        this.svAdharCardNum = svAdharCardNum;
    }

    public String getBankId() {
        return bankId;
    }

    public void setBankId(String bankId) {
        this.bankId = bankId;
    }

    public String getBranchid() {
        return branchid;
    }

    public void setBranchid(String branchid) {
        this.branchid = branchid;
    }

    public String getBankAcNo() {
        return bankAcNo;
    }

    public void setBankAcNo(String bankAcNo) {
        this.bankAcNo = bankAcNo;
    }

    public String getIfscCode() {
        return ifscCode;
    }

    public void setIfscCode(String ifscCode) {
        this.ifscCode = ifscCode;
    }

    public String getMobileNo() {
        return mobileNo;
    }

    public void setMobileNo(String mobileNo) {
        this.mobileNo = mobileNo;
    }

    public String getEmergencyContactNo() {
        return emergencyContactNo;
    }

    public void setEmergencyContactNo(String emergencyContactNo) {
        this.emergencyContactNo = emergencyContactNo;
    }

    public String getBusinessType() {
        return businessType;
    }

    public void setBusinessType(String businessType) {
        this.businessType = businessType;
    }

    public String getVendingType() {
        return vendingType;
    }

    public void setVendingType(String vendingType) {
        this.vendingType = vendingType;
    }

    public String getVendingArea() {
        return vendingArea;
    }

    public void setVendingArea(String vendingArea) {
        this.vendingArea = vendingArea;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getPermanentHno() {
        return permanentHno;
    }

    public void setPermanentHno(String permanentHno) {
        this.permanentHno = permanentHno;
    }

    public String getPermanentStreet() {
        return permanentStreet;
    }

    public void setPermanentStreet(String permanentStreet) {
        this.permanentStreet = permanentStreet;
    }

    public String getPermanentState() {
        return permanentState;
    }

    public void setPermanentState(String permanentState) {
        this.permanentState = permanentState;
    }

    public String getPermanentDist() {
        return permanentDist;
    }

    public void setPermanentDist(String permanentDist) {
        this.permanentDist = permanentDist;
    }

    public String getPermanentMand() {
        return permanentMand;
    }

    public void setPermanentMand(String permanentMand) {
        this.permanentMand = permanentMand;
    }

    public String getPermanentVillage() {
        return permanentVillage;
    }

    public void setPermanentVillage(String permanentVillage) {
        this.permanentVillage = permanentVillage;
    }

    public String getPermanentDistOthers() {
        return permanentDistOthers;
    }

    public void setPermanentDistOthers(String permanentDistOthers) {
        this.permanentDistOthers = permanentDistOthers;
    }

    public String getPermanentMandOthers() {
        return permanentMandOthers;
    }

    public void setPermanentMandOthers(String permanentMandOthers) {
        this.permanentMandOthers = permanentMandOthers;
    }

    public String getPermanentVillageOthers() {
        return permanentVillageOthers;
    }

    public void setPermanentVillageOthers(String permanentVillageOthers) {
        this.permanentVillageOthers = permanentVillageOthers;
    }

    public String getPoliceStation() {
        return policeStation;
    }

    public void setPoliceStation(String policeStation) {
        this.policeStation = policeStation;
    }

    public String getStartVendingAct() {
        return startVendingAct;
    }

    public void setStartVendingAct(String startVendingAct) {
        this.startVendingAct = startVendingAct;
    }

    public String getNoFamilyMembers() {
        return noFamilyMembers;
    }

    public void setNoFamilyMembers(String noFamilyMembers) {
        this.noFamilyMembers = noFamilyMembers;
    }

    public String getCasteOthers() {
        return casteOthers;
    }

    public void setCasteOthers(String casteOthers) {
        this.casteOthers = casteOthers;
    }

    public String getBusinessTypeOthers() {
        return businessTypeOthers;
    }

    public void setBusinessTypeOthers(String businessTypeOthers) {
        this.businessTypeOthers = businessTypeOthers;
    }

    public String getIpAddress() {
        return ipAddress;
    }

    public void setIpAddress(String ipAddress) {
        this.ipAddress = ipAddress;
    }

    public String getSvPhotoPathB64() {
        return svPhotoPathB64;
    }

    public void setSvPhotoPathB64(String svPhotoPathB64) {
        this.svPhotoPathB64 = svPhotoPathB64;
    }

    public String getSvActPhotoPathB64() {
        return svActPhotoPathB64;
    }

    public void setSvActPhotoPathB64(String svActPhotoPathB64) {
        this.svActPhotoPathB64 = svActPhotoPathB64;
    }

    public List<FamilyInfo> getFamilyInfo() {
        return familyInfo;
    }

    public void setFamilyInfo(List<FamilyInfo> familyInfo) {
        this.familyInfo = familyInfo;
    }

}
