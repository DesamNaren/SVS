package com.cgg.streetvendor.network;




import com.cgg.streetvendor.source.reposnse.SubmitResponse;
import com.cgg.streetvendor.source.reposnse.ValidateAadharResponse;
import com.cgg.streetvendor.source.reposnse.bankbranch.BankResponse;
import com.cgg.streetvendor.source.reposnse.bankbranch.BranchResponse;
import com.cgg.streetvendor.source.reposnse.kyc.BusinessResponse;
import com.cgg.streetvendor.source.reposnse.kyc.CasteResponse;
import com.cgg.streetvendor.source.reposnse.kyc.GenderResponse;
import com.cgg.streetvendor.source.reposnse.kyc.PWDResponse;
import com.cgg.streetvendor.source.reposnse.kyc.QualificationResponse;
import com.cgg.streetvendor.source.reposnse.kyc.RelationResponse;
import com.cgg.streetvendor.source.reposnse.kyc.ReligionResponse;
import com.cgg.streetvendor.source.reposnse.kyc.VendingAddressResponse;
import com.cgg.streetvendor.source.reposnse.kyc.VendingTypeResponse;
import com.cgg.streetvendor.source.reposnse.login.LoginResponse;
import com.cgg.streetvendor.source.reposnse.places.DistrictResponse;
import com.cgg.streetvendor.source.reposnse.places.MandalResponse;
import com.cgg.streetvendor.source.reposnse.places.StateResponse;
import com.cgg.streetvendor.source.reposnse.places.VillageResponse;
import com.cgg.streetvendor.source.reposnse.ulb.ULBResponse;
import com.cgg.streetvendor.source.reposnse.ulb.WardResponse;
import com.cgg.streetvendor.source.reposnse.version.VersionCheckResponse;
import com.cgg.streetvendor.source.request.LoginRequest;
import com.cgg.streetvendor.source.request.SubmitRequest;
import com.cgg.streetvendor.ui.FamilyInfo;

import java.util.concurrent.TimeUnit;

import okhttp3.OkHttpClient;
import retrofit2.Call;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Path;

public interface SVSService {
    class Factory {
        public static SVSService create() {
            OkHttpClient.Builder httpClient = new OkHttpClient.Builder();
            httpClient.readTimeout(60, TimeUnit.SECONDS);
            httpClient.connectTimeout(60, TimeUnit.SECONDS);
            httpClient.writeTimeout(60, TimeUnit.SECONDS);

            Retrofit retrofit = new Retrofit.Builder()
                    .baseUrl(SVSURL.SVS_BASE_URL)
                    .addConverterFactory(GsonConverterFactory.create())
                    .client(httpClient.build())
                    .build();
            return retrofit.create(SVSService.class);
        }

    }

    @GET("masters/getStateData")
    Call<StateResponse> getStateResponse();

    @GET("masters/getDistrictData/stateID=36")
    Call<DistrictResponse> getDistrictResponse();

    @GET("masters/getMandalData/stateID=36&districtID=0")
    Call<MandalResponse> getMandalResponse();

    @GET("masters/getVillageData/stateID=36&districtID=0&mandalID=0")
    Call<VillageResponse> getVillageResponse();

    @GET("masters/getBankData")
    Call<BankResponse> getBankResponse();

    @GET("masters/getBranchData/bankID=0")
    Call<BranchResponse> getBranchResponse();

    @GET("masters/getULBData")
    Call<ULBResponse> getULBResponse();

    @GET("masters/getWardData/ulbID=0")
    Call<WardResponse> getWardResponse();


    @GET("masters/getGenderData")
    Call<GenderResponse> getGenderResponse();

    @POST("services/submitSurveyDetails")
    Call<SubmitResponse> submitServiceResponse(@Body SubmitRequest submitRequest);

    @GET("services/validateAadhar/aadharNo={aadharNo}")
    Call<ValidateAadharResponse> validateAadhar(@Path("aadharNo") long  aadharNo);

    @GET("masters/getCasteData")
    Call<CasteResponse> getCasteResponse();

    @GET("masters/getPWDData")
    Call<PWDResponse> getPWDResponse();

    @GET("masters/getHighQualificationData")
    Call<QualificationResponse> getQualificationResponse();

    @GET("masters/getRelationData")
    Call<RelationResponse> getRelationResponse();
    @GET("masters/getReligionData")
    Call<ReligionResponse> getReligionResponse();

    @GET("masters/getBusinessData")
    Call<BusinessResponse> getBusinessResponse();

    @GET("masters/getVendingData")
    Call<VendingTypeResponse> getVenTypeResponse();

    @GET("masters/getVendindAreaData/districtID=0&ulbID=0")
    Call<VendingAddressResponse> getVenAddressResponse();

    @POST("services/validateLogin")
    Call<LoginResponse> getLoginResponse(@Body LoginRequest loginRequest);

    @GET("services/getVersionDetails")
    Call<VersionCheckResponse> getVersionCheckResponse();
//
//    @GET("CTWServiceDetails/getGCCDetails")
//    Call<GCCReportResponse> getGCCReports(@Query("officerId") String username);
//
//    @GET("CTWWorks/viewWorkDetails")
//    Call<WorkReportResponse> getEngReports(@Query("officerId") String officerId);
//
//    @GET("CTWServiceDetails/getSchemeDetails") //
//    Call<SchemeReportResponse> getSchemeReports(@Query("officerId") String username);
//
//   @GET("CTWServiceDetails/getSchoolDetails")
//    Call<InspReportResponse> getInspectionReports(@Query("officerId") String username);
//
//    @GET("getBenificiaryDetails")
//    Call<BeneficiaryReport> getBeneficiaryDetails(@Query("distId") int distId, @Query("mandalId") int mandalId, @Query("villageId") int villageId, @Query("finYearId") String finYearId);
//
//    @GET("getInspectionRemarks")
//    Call<InspectionRemarkResponse> getInspectionRemarks();
//
//    @GET("getDMVMasters")
//    Call<SchemeDMVResponse> getSchemeDMV();
//
//    @GET("getFinancialYears")
//    Call<FinancialYearResponse> getFinancialYears();
//
//    @GET("getSchemes")
//    Call<SchemeResponse> getSchemeResponse();
//
//    @POST("submitSchemeInspectionDetails")
//    Call<SchemeSubmitResponse> getSchemeSubmitResponse(@Body SchemeSubmitRequest schemeSubmitRequest);
//
//    @POST("submitGCCInspectionDetails")
//    Call<GCCSubmitResponse> getGCCSubmitResponse(@Body GCCSubmitRequest gccSubmitRequest);
//
//    @POST("submitSchoolInspectionDetails")
//    Call<InstSubmitResponse> getInstSubmitResponse(@Body InstSubmitRequest instSubmitRequest);
//
//    @Multipart
//    @POST("upload/uploadSchemeInspectionPhotos")
//    Call<SchemePhotoSubmitResponse> uploadSchemeImageCall(@Part List<MultipartBody.Part> partList);
//
//    @Multipart
//    @POST("upload/uploadSchoolInspectionPhotos")
//    Call<SchemePhotoSubmitResponse> uploadSchoolImageCall(@Part List<MultipartBody.Part> partList);
//
//    @Multipart
//    @POST("upload/uploadGCCInspectionPhotos")
//    Call<GCCPhotoSubmitResponse> uploadGCCImageCall(@Part List<MultipartBody.Part> partList);
//
//    @GET("CTWServiceDetails/getDMVMasters")
//    Call<SchoolDMVResponse> getSchoolDMV(@Query("userId") String officerId);
//
//    @GET("CTWServiceDetails/getInstInfo")
//    Call<InstMasterResponse> getInstMasterResponse();
//    //------------------- Login & Logout ----------------------------------------
//
//
//    //------------------- GCC ----------------------------------------
//
//    @POST("getOffices")
//    Call<GetOfficesResponse> getDivisionMasterResponse();
//
//    @POST("getGodowns/DR Depot")
//    Call<DRDepotMasterResponse> getDRDepotMasterResponse();
//
//    @POST("getGodowns/DR Godown")
//    Call<DRGoDownMasterResponse> getDRGoDownMasterResponse();
//
//    @POST("getGodowns/MFP Godown")
//    Call<MFPGoDownMasterResponse> getMFPDownMasterResponse();
//
//    @POST("getGodowns/Processing unit")
//    Call<PUnitMasterResponse> getPUnitMasterResponse();
//
//
//    @POST("getGodowns/Petrolpump")
//    Call<PetrolPumpMasterResponse> getPetrolPumpMasterResponse();
//
//    @POST("getGodowns/LPG")
//    Call<LPGMasterResponse> getLPGMasterResponse();
//
//    @POST("getStockDetails/{id}")
//    Call<StockDetailsResponse> getDRDepotMasterResponse(@Path("id") String id);
//
//    @POST("getStockDetails/{id}")
//    Call<PetrolStockDetailsResponse> getPLPGMasterResponse(@Path("id") String id);
//
//    //------------------- Engineering ----------------------------------------
//
//
//    @GET("CTWWorks/getWorksMaster")
//    Call<WorksMasterResponse> getWorksMaster();
//
//    @GET("CTWWorks/getMajorStage")
//    Call<SubmittedStageResponse> getSubmittedStage(@Query("work_id") int workId);
//
//
//    @GET("CTWWorks/getSectors")
//    Call<SectorsResponse> getSectorsMaster();
//
//
//    @GET("CTWWorks/getGrantSandSchemes")
//    Call<GrantSchemesResponse> getGrantSandSchemes();
//
//    @GET("CTWWorks/getStages")
//    Call<StagesResponse> getStages(@Query("sector_id") int sectorId);
//
//    @Multipart
//    @POST("upload/uploadEngineeringWorksPhotos")
//    Call<GCCPhotoSubmitResponse> uploadEngPhotoCall(@Part List<MultipartBody.Part> partList);
//
//    @POST("submitWorkInspectionDetails")
//    Call<SubmitEngWorksResponse> getEngWorksSubmitResponse(@Body SubmitEngWorksRequest submitEngWorksRequest);

}



