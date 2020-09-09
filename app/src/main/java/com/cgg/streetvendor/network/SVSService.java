package com.cgg.streetvendor.network;


import com.cgg.streetvendor.source.reposnse.submit.SubmitResponse;
import com.cgg.streetvendor.source.reposnse.submit.ValidateAadharResponse;
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
import com.cgg.streetvendor.source.reposnse.reports.AllFieldReportData;
import com.cgg.streetvendor.source.reposnse.reports.DailyReportResponse;
import com.cgg.streetvendor.source.reposnse.ulb.ULBResponse;
import com.cgg.streetvendor.source.reposnse.ulb.WardResponse;
import com.cgg.streetvendor.source.reposnse.version.VersionCheckResponse;
import com.cgg.streetvendor.source.request.LoginRequest;
import com.cgg.streetvendor.source.request.SubmitRequest;

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

    @GET("masters/getDistrictData/stateID={stateID}")
    Call<DistrictResponse> getDistrictResponse(@Path("stateID") String stateID);

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

    @GET("masters/getVendindAreaData/{districtID}")
    Call<VendingAddressResponse> getVenAddressResponse(@Path(value = "districtID") String districtID);

    @POST("services/validateLogin")
    Call<LoginResponse> getLoginResponse(@Body LoginRequest loginRequest);

    @GET("services/getVersionDetails")
    Call<VersionCheckResponse> getVersionCheckResponse();

    @GET("services/getSVDailyProgressDetails")
    Call<DailyReportResponse> getDailyReportResponse();

    @GET("services/getSVAllFieldsReport")
    Call<AllFieldReportData> getAllFieldReportDataCall();
}



