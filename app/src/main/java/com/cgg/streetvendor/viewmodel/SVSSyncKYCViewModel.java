package com.cgg.streetvendor.viewmodel;

import android.app.Application;
import android.content.Context;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.cgg.streetvendor.interfaces.ErrorHandlerInterface;
import com.cgg.streetvendor.network.SVSService;
import com.cgg.streetvendor.room.repository.SVSSyncKYCRepository;
import com.cgg.streetvendor.source.reposnse.kyc.CasteEntity;
import com.cgg.streetvendor.source.reposnse.kyc.CasteResponse;
import com.cgg.streetvendor.source.reposnse.kyc.GenderEntity;
import com.cgg.streetvendor.source.reposnse.kyc.GenderResponse;
import com.cgg.streetvendor.source.reposnse.kyc.PWDEntity;
import com.cgg.streetvendor.source.reposnse.kyc.PWDResponse;
import com.cgg.streetvendor.source.reposnse.kyc.QualificationEntity;
import com.cgg.streetvendor.source.reposnse.kyc.QualificationResponse;
import com.cgg.streetvendor.source.reposnse.kyc.RelationEntity;
import com.cgg.streetvendor.source.reposnse.kyc.RelationResponse;
import com.cgg.streetvendor.source.reposnse.kyc.ReligionEntity;
import com.cgg.streetvendor.source.reposnse.kyc.ReligionResponse;


import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class SVSSyncKYCViewModel extends AndroidViewModel {
    private MutableLiveData<GenderResponse> genderResponseMutableLiveData;
    private MutableLiveData<CasteResponse> casteResponseMutableLiveData;
    private MutableLiveData<PWDResponse> pwdResponseMutableLiveData;
    private MutableLiveData<QualificationResponse> qualificationResponseMutableLiveData;
    private MutableLiveData<RelationResponse> relationResponseMutableLiveData;
    private MutableLiveData<ReligionResponse> religionResponseMutableLiveData;
    private Context context;
    private ErrorHandlerInterface errorHandlerInterface;
    private SVSSyncKYCRepository svsSyncKYCRepository;
    private LiveData<List<GenderEntity>> allGender;
    private LiveData<List<CasteEntity>> allCaste;
    private LiveData<List<PWDEntity>> allPWD;
    private LiveData<List<QualificationEntity>> allQual;
    private LiveData<List<RelationEntity>> allRel;
    private LiveData<List<ReligionEntity>> allReligion;


    public SVSSyncKYCViewModel(Context context, Application application) {
        super(application);
        this.context = context;
        svsSyncKYCRepository = new SVSSyncKYCRepository(application);
        genderResponseMutableLiveData = new MutableLiveData<>();
        casteResponseMutableLiveData = new MutableLiveData<>();
        pwdResponseMutableLiveData = new MutableLiveData<>();
        qualificationResponseMutableLiveData = new MutableLiveData<>();
        relationResponseMutableLiveData = new MutableLiveData<>();
        religionResponseMutableLiveData = new MutableLiveData<>();
        allGender = new MutableLiveData<>();
        allCaste = new MutableLiveData<>();
        allPWD = new MutableLiveData<>();
        allQual = new MutableLiveData<>();
        allRel = new MutableLiveData<>();
        allReligion = new MutableLiveData<>();
        errorHandlerInterface = (ErrorHandlerInterface) context;

    }

    public LiveData<List<ReligionEntity>> getAllReligion() {
        if (allReligion != null) {
            allReligion = svsSyncKYCRepository.getAllReligions();
        }
        return allReligion;
    }

    public LiveData<List<GenderEntity>> getAllGender() {
        if (allGender != null) {
            allGender = svsSyncKYCRepository.getAllGenders();
        }
        return allGender;
    }

    public LiveData<List<CasteEntity>> getAllCaste() {
        if (allCaste != null) {
            allCaste = svsSyncKYCRepository.getAllCaste();
        }
        return allCaste;
    }

    public LiveData<List<PWDEntity>> getAllPWDs() {
        if (allPWD != null) {
            allPWD = svsSyncKYCRepository.getAllPWD();
        }
        return allPWD;
    }

    public LiveData<List<QualificationEntity>> getAllQual() {
        if (allQual != null) {
            allQual = svsSyncKYCRepository.getAllQual();
        }
        return allQual;
    }

    public LiveData<List<RelationEntity>> getAllRelations() {
        if (allRel != null) {
            allRel = svsSyncKYCRepository.getAllRel();
        }
        return allRel;
    }


    public LiveData<GenderResponse> getGenderResponse() {
        if (genderResponseMutableLiveData != null) {
            getGenderResponseCall();
        }
        return genderResponseMutableLiveData;
    }

    private void getGenderResponseCall() {
        SVSService twdService = SVSService.Factory.create();
        twdService.getGenderResponse().enqueue(new Callback<GenderResponse>() {
            @Override
            public void onResponse(@NonNull Call<GenderResponse> call, @NonNull Response<GenderResponse> response) {
                if (response.isSuccessful() && response.body() != null) {
                    genderResponseMutableLiveData.setValue(response.body());
                }
            }

            @Override
            public void onFailure(@NonNull Call<GenderResponse> call, @NonNull Throwable t) {
                errorHandlerInterface.handleError(t, context);
            }
        });
    }

    public LiveData<CasteResponse> getCasteResponse() {
        if (casteResponseMutableLiveData != null) {
            getCasteMasterCall();
        }
        return casteResponseMutableLiveData;
    }

    private void getCasteMasterCall() {
        SVSService twdService = SVSService.Factory.create();
        twdService.getCasteResponse().enqueue(new Callback<CasteResponse>() {
            @Override
            public void onResponse(@NonNull Call<CasteResponse> call, @NonNull Response<CasteResponse> response) {
                if (response.isSuccessful() && response.body() != null) {
                    casteResponseMutableLiveData.setValue(response.body());
                }
            }

            @Override
            public void onFailure(@NonNull Call<CasteResponse> call, @NonNull Throwable t) {
                errorHandlerInterface.handleError(t, context);
            }
        });
    }

    public LiveData<PWDResponse> getPWDResponse() {
        if (pwdResponseMutableLiveData != null) {
            getPWDMasterCall();
        }
        return pwdResponseMutableLiveData;
    }

    private void getPWDMasterCall() {
        SVSService twdService = SVSService.Factory.create();
        twdService.getPWDResponse().enqueue(new Callback<PWDResponse>() {
            @Override
            public void onResponse(@NonNull Call<PWDResponse> call, @NonNull Response<PWDResponse> response) {
                if (response.isSuccessful() && response.body() != null) {
                    pwdResponseMutableLiveData.setValue(response.body());
                }
            }

            @Override
            public void onFailure(@NonNull Call<PWDResponse> call, @NonNull Throwable t) {
                errorHandlerInterface.handleError(t, context);
            }
        });
    }

    public LiveData<QualificationResponse> getQuaResponse() {
        if (qualificationResponseMutableLiveData != null) {
            getQuaMasterCall();
        }
        return qualificationResponseMutableLiveData;
    }

    private void getQuaMasterCall() {
        SVSService twdService = SVSService.Factory.create();
        twdService.getQualificationResponse().enqueue(new Callback<QualificationResponse>() {
            @Override
            public void onResponse(@NonNull Call<QualificationResponse> call, @NonNull Response<QualificationResponse> response) {
                if (response.isSuccessful() && response.body() != null) {
                    qualificationResponseMutableLiveData.setValue(response.body());
                }
            }

            @Override
            public void onFailure(@NonNull Call<QualificationResponse> call, @NonNull Throwable t) {
                errorHandlerInterface.handleError(t, context);
            }
        });
    }

    public LiveData<RelationResponse> getRelationResponse() {
        if (relationResponseMutableLiveData != null) {
            getRelationMasterCall();
        }
        return relationResponseMutableLiveData;
    }

    public LiveData<ReligionResponse> getReligionResponse() {
        if (religionResponseMutableLiveData != null) {
            getReligionMasterCall();
        }
        return religionResponseMutableLiveData;
    }

    private void getRelationMasterCall() {
        SVSService twdService = SVSService.Factory.create();
        twdService.getRelationResponse().enqueue(new Callback<RelationResponse>() {
            @Override
            public void onResponse(@NonNull Call<RelationResponse> call, @NonNull Response<RelationResponse> response) {
                if (response.isSuccessful() && response.body() != null) {
                    relationResponseMutableLiveData.setValue(response.body());
                }
            }

            @Override
            public void onFailure(@NonNull Call<RelationResponse> call, @NonNull Throwable t) {
                errorHandlerInterface.handleError(t, context);
            }
        });
    }

    private void getReligionMasterCall() {
        SVSService twdService = SVSService.Factory.create();
        twdService.getReligionResponse().enqueue(new Callback<ReligionResponse>() {
            @Override
            public void onResponse(@NonNull Call<ReligionResponse> call, @NonNull Response<ReligionResponse> response) {
                if (response.isSuccessful() && response.body() != null) {
                    religionResponseMutableLiveData.setValue(response.body());
                }
            }

            @Override
            public void onFailure(@NonNull Call<ReligionResponse> call, @NonNull Throwable t) {
                errorHandlerInterface.handleError(t, context);
            }
        });
    }

}



