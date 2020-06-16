package com.cgg.streetvendor.viewmodel;

import android.app.Application;
import android.content.Context;

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

import org.jetbrains.annotations.NotNull;

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


    public LiveData<String> getReligionId(String religion) {
        return svsSyncKYCRepository.getReligionId(religion);
    }    public LiveData<String> getTelReligionId(String religion) {
        return svsSyncKYCRepository.getTelReligionId(religion);
    }

    public LiveData<String> getCasteId(String caste) {
        return svsSyncKYCRepository.getCasteId(caste);
    }

    public LiveData<String> getTelCasteId(String caste) {
        return svsSyncKYCRepository.getTelCasteId(caste);
    }

    public LiveData<String> getGenderCode(String gender) {
        return svsSyncKYCRepository.getGenderCode(gender);
    }
    public LiveData<String> getTelGenderCode(String gender) {
        return svsSyncKYCRepository.getTelGenderCode(gender);
    }

    public LiveData<String> getGenderID(String gender) {
        return svsSyncKYCRepository.getGenderID(gender);
    }    public LiveData<String> getTelGenderID(String gender) {
        return svsSyncKYCRepository.getTelGenderID(gender);
    }


    public LiveData<String> getPWDId(String pwd) {
        return svsSyncKYCRepository.getPWDId(pwd);
    }

    public LiveData<String> getTelPWDId(String pwd) {
        return svsSyncKYCRepository.getTelPWDId(pwd);
    }


    public LiveData<String> getQualificationId(String qua) {
        return svsSyncKYCRepository.getQualificationId(qua);
    }

    public LiveData<String> getTelQualificationId(String qua) {
        return svsSyncKYCRepository.getTelQualificationId(qua);
    }


    public LiveData<String> getRelationId(String qua) {
        return svsSyncKYCRepository.getRelationId(qua);
    }    public LiveData<String> getTelRelationId(String qua) {
        return svsSyncKYCRepository.getTelRelationId(qua);
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
            public void onResponse(@NotNull Call<GenderResponse> call, @NotNull Response<GenderResponse> response) {
                if (response.isSuccessful() && response.body() != null) {
                    genderResponseMutableLiveData.setValue(response.body());
                }
            }

            @Override
            public void onFailure(@NotNull Call<GenderResponse> call, @NotNull Throwable t) {
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
            public void onResponse(@NotNull Call<CasteResponse> call, @NotNull Response<CasteResponse> response) {
                if (response.isSuccessful() && response.body() != null) {
                    casteResponseMutableLiveData.setValue(response.body());
                }
            }

            @Override
            public void onFailure(@NotNull Call<CasteResponse> call, @NotNull Throwable t) {
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
            public void onResponse(@NotNull Call<PWDResponse> call, @NotNull Response<PWDResponse> response) {
                if (response.isSuccessful() && response.body() != null) {
                    pwdResponseMutableLiveData.setValue(response.body());
                }
            }

            @Override
            public void onFailure(@NotNull Call<PWDResponse> call, @NotNull Throwable t) {
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
            public void onResponse(@NotNull Call<QualificationResponse> call, @NotNull Response<QualificationResponse> response) {
                if (response.isSuccessful() && response.body() != null) {
                    qualificationResponseMutableLiveData.setValue(response.body());
                }
            }

            @Override
            public void onFailure(@NotNull Call<QualificationResponse> call, @NotNull Throwable t) {
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
            public void onResponse(@NotNull Call<RelationResponse> call, @NotNull Response<RelationResponse> response) {
                if (response.isSuccessful() && response.body() != null) {
                    relationResponseMutableLiveData.setValue(response.body());
                }
            }

            @Override
            public void onFailure(@NotNull Call<RelationResponse> call, @NotNull Throwable t) {
                errorHandlerInterface.handleError(t, context);
            }
        });
    }

    private void getReligionMasterCall() {
        SVSService twdService = SVSService.Factory.create();
        twdService.getReligionResponse().enqueue(new Callback<ReligionResponse>() {
            @Override
            public void onResponse(@NotNull Call<ReligionResponse> call, @NotNull Response<ReligionResponse> response) {
                if (response.isSuccessful() && response.body() != null) {
                    religionResponseMutableLiveData.setValue(response.body());
                }
            }

            @Override
            public void onFailure(@NotNull Call<ReligionResponse> call, @NotNull Throwable t) {
                errorHandlerInterface.handleError(t, context);
            }
        });
    }

}



