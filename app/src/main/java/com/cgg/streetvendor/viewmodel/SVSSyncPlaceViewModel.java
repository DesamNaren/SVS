package com.cgg.streetvendor.viewmodel;

import android.app.Application;
import android.content.Context;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.cgg.streetvendor.interfaces.ErrorHandlerInterface;
import com.cgg.streetvendor.network.SVSService;
import com.cgg.streetvendor.room.repository.SVSSyncPlacesRepository;
import com.cgg.streetvendor.source.reposnse.places.DistrictEntity;
import com.cgg.streetvendor.source.reposnse.places.DistrictResponse;
import com.cgg.streetvendor.source.reposnse.places.MandalEntity;
import com.cgg.streetvendor.source.reposnse.places.MandalResponse;
import com.cgg.streetvendor.source.reposnse.places.StateEntity;
import com.cgg.streetvendor.source.reposnse.places.StateResponse;
import com.cgg.streetvendor.source.reposnse.places.VillageEntity;
import com.cgg.streetvendor.source.reposnse.places.VillageResponse;


import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class SVSSyncPlaceViewModel extends AndroidViewModel {
    private SVSSyncPlacesRepository svsSyncPlacesRepository;
    private MutableLiveData<StateResponse> stateResponseMutableLiveData;
    private MutableLiveData<DistrictResponse> districtResponseMutableLiveData;
    private MutableLiveData<MandalResponse> mandalResponseMutableLiveData;
    private MutableLiveData<VillageResponse> villageResponseMutableLiveData;
    //    private MutableLiveData<PUnitMasterResponse> pUnitMasterResponseMutableLiveData;
//    private MutableLiveData<PetrolPumpMasterResponse> petrolPumpMasterResponseMutableLiveData;
//    private MutableLiveData<LPGMasterResponse> lpgMasterResponseMutableLiveData;
    private Context context;
    private ErrorHandlerInterface errorHandlerInterface;
    private LiveData<List<StateEntity>> allStates;
    private LiveData<List<DistrictEntity>> allDistricts;
    private LiveData<List<MandalEntity>> allMandals;
    private LiveData<List<MandalEntity>> distMandals;
    private LiveData<List<VillageEntity>> manVillages;
    private LiveData<List<VillageEntity>> allVillages;

    public SVSSyncPlaceViewModel(Context context, Application application) {
        super(application);
        this.context = context;
        svsSyncPlacesRepository = new SVSSyncPlacesRepository(application);
        stateResponseMutableLiveData = new MutableLiveData<>();
        districtResponseMutableLiveData = new MutableLiveData<>();
        mandalResponseMutableLiveData = new MutableLiveData<>();
        villageResponseMutableLiveData = new MutableLiveData<>();
        allStates = new MutableLiveData<>();
        allDistricts = new MutableLiveData<>();
        allMandals = new MutableLiveData<>();
        allVillages = new MutableLiveData<>();
        distMandals = new MutableLiveData<>();
        manVillages = new MutableLiveData<>();
        errorHandlerInterface = (ErrorHandlerInterface) context;

    }

    public LiveData<List<MandalEntity>> getDistrictMandals(String distId) {
        if (distMandals != null) {
            distMandals = svsSyncPlacesRepository.getDistrictMandals(distId);
        }
        return distMandals;
    }

    public LiveData<List<VillageEntity>> getMandalVillages(String distId, String manId) {
        if (manVillages != null) {
            manVillages = svsSyncPlacesRepository.getMandalVillages(distId, manId);
        }
        return manVillages;
    }


    public LiveData<List<StateEntity>> getAllStates() {
        if (allStates != null) {
            allStates = svsSyncPlacesRepository.getAllstates();
        }
        return allStates;
    }

    public LiveData<List<DistrictEntity>> getAllDistricts() {
        if (allDistricts != null) {
            allDistricts = svsSyncPlacesRepository.getAllDistricts();
        }
        return allDistricts;
    }


    public LiveData<List<MandalEntity>> getAllMandals() {
        if (allMandals != null) {
            allMandals = svsSyncPlacesRepository.getAllMandals();
        }
        return allMandals;
    }

    public LiveData<List<VillageEntity>> getAllVillages() {
        if (allVillages != null) {
            allVillages = svsSyncPlacesRepository.getAllVillages();
        }
        return allVillages;
    }


    public LiveData<StateResponse> getStateResponse() {
        if (stateResponseMutableLiveData != null) {
            getStateResponseCall();
        }
        return stateResponseMutableLiveData;
    }

    private void getStateResponseCall() {
        SVSService twdService = SVSService.Factory.create();
        twdService.getStateResponse().enqueue(new Callback<StateResponse>() {
            @Override
            public void onResponse(@NonNull Call<StateResponse> call, @NonNull Response<StateResponse> response) {
                if (response.isSuccessful() && response.body() != null) {
                    stateResponseMutableLiveData.setValue(response.body());
                }
            }

            @Override
            public void onFailure(@NonNull Call<StateResponse> call, @NonNull Throwable t) {
                errorHandlerInterface.handleError(t, context);
            }
        });
    }

    public LiveData<DistrictResponse> getDistrictResponse(String stateID) {
        if (districtResponseMutableLiveData != null) {
            getDistrictMasterCall(stateID);
        }
        return districtResponseMutableLiveData;
    }

    private void getDistrictMasterCall(String stateID) {
        SVSService twdService = SVSService.Factory.create();
        twdService.getDistrictResponse(stateID).enqueue(new Callback<DistrictResponse>() {
            @Override
            public void onResponse(@NonNull Call<DistrictResponse> call, @NonNull Response<DistrictResponse> response) {
                if (response.isSuccessful() && response.body() != null) {
                    districtResponseMutableLiveData.setValue(response.body());
                }
            }

            @Override
            public void onFailure(@NonNull Call<DistrictResponse> call, @NonNull Throwable t) {
                errorHandlerInterface.handleError(t, context);
            }
        });
    }

    public LiveData<MandalResponse> getMandalResponse() {
        if (mandalResponseMutableLiveData != null) {
            getMandalMasterCall();
        }
        return mandalResponseMutableLiveData;
    }

    private void getMandalMasterCall() {
        SVSService twdService = SVSService.Factory.create();
        twdService.getMandalResponse().enqueue(new Callback<MandalResponse>() {
            @Override
            public void onResponse(@NonNull Call<MandalResponse> call, @NonNull Response<MandalResponse> response) {
                if (response.isSuccessful() && response.body() != null) {
                    mandalResponseMutableLiveData.setValue(response.body());
                }
            }

            @Override
            public void onFailure(@NonNull Call<MandalResponse> call, @NonNull Throwable t) {
                errorHandlerInterface.handleError(t, context);
            }
        });
    }

    public LiveData<VillageResponse> getVillageResponse() {
        if (villageResponseMutableLiveData != null) {
            getVillageMasterCall();
        }
        return villageResponseMutableLiveData;
    }

    private void getVillageMasterCall() {
        SVSService twdService = SVSService.Factory.create();
        twdService.getVillageResponse().enqueue(new Callback<VillageResponse>() {
            @Override
            public void onResponse(@NonNull Call<VillageResponse> call, @NonNull Response<VillageResponse> response) {
                if (response.isSuccessful() && response.body() != null) {
                    villageResponseMutableLiveData.setValue(response.body());
                }
            }

            @Override
            public void onFailure(@NonNull Call<VillageResponse> call, @NonNull Throwable t) {
                errorHandlerInterface.handleError(t, context);
            }
        });
    }
}



