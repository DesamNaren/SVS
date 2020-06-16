package com.cgg.streetvendor.viewmodel;

import android.app.Application;
import android.content.Context;

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

import org.jetbrains.annotations.NotNull;

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

    public LiveData<String> getStateId(String stateName) {
        return svsSyncPlacesRepository.getStateId(stateName);
    }


    public LiveData<String> getTelStateId(String stateName) {
        return svsSyncPlacesRepository.getTelStateId(stateName);
    }


    public LiveData<String> getDistId(String distName) {
        return svsSyncPlacesRepository.getDistrictID(distName);
    }

    public LiveData<String> getTelDistId(String distName) {
        return svsSyncPlacesRepository.getTelDistId(distName);
    }

    public LiveData<String> getManId(String manName, String distId) {
        return svsSyncPlacesRepository.getMandalID(manName, distId);
    }

    public LiveData<String> getTelMandalId(String manName, String distId) {
        return svsSyncPlacesRepository.getTelMandalId(manName, distId);
    }


    public LiveData<String> getVilId(String vilName, String distId, String manId) {
        return svsSyncPlacesRepository.getVillageId(vilName, distId, manId);
    }    public LiveData<String> getTelVillageId(String vilName, String distId, String manId) {
        return svsSyncPlacesRepository.getTelVillageId(vilName, distId, manId);
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
            public void onResponse(@NotNull Call<StateResponse> call, @NotNull Response<StateResponse> response) {
                if (response.isSuccessful() && response.body() != null) {
                    stateResponseMutableLiveData.setValue(response.body());
                }
            }

            @Override
            public void onFailure(@NotNull Call<StateResponse> call, @NotNull Throwable t) {
                errorHandlerInterface.handleError(t, context);
            }
        });
    }

    public LiveData<DistrictResponse> getDistrictResponse() {
        if (districtResponseMutableLiveData != null) {
            getDistrictMasterCall();
        }
        return districtResponseMutableLiveData;
    }

    private void getDistrictMasterCall() {
        SVSService twdService = SVSService.Factory.create();
        twdService.getDistrictResponse().enqueue(new Callback<DistrictResponse>() {
            @Override
            public void onResponse(@NotNull Call<DistrictResponse> call, @NotNull Response<DistrictResponse> response) {
                if (response.isSuccessful() && response.body() != null) {
                    districtResponseMutableLiveData.setValue(response.body());
                }
            }

            @Override
            public void onFailure(@NotNull Call<DistrictResponse> call, @NotNull Throwable t) {
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
            public void onResponse(@NotNull Call<MandalResponse> call, @NotNull Response<MandalResponse> response) {
                if (response.isSuccessful() && response.body() != null) {
                    mandalResponseMutableLiveData.setValue(response.body());
                }
            }

            @Override
            public void onFailure(@NotNull Call<MandalResponse> call, @NotNull Throwable t) {
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
            public void onResponse(@NotNull Call<VillageResponse> call, @NotNull Response<VillageResponse> response) {
                if (response.isSuccessful() && response.body() != null) {
                    villageResponseMutableLiveData.setValue(response.body());
                }
            }

            @Override
            public void onFailure(@NotNull Call<VillageResponse> call, @NotNull Throwable t) {
                errorHandlerInterface.handleError(t, context);
            }
        });
    }
}



