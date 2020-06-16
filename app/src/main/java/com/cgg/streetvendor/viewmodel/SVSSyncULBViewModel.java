package com.cgg.streetvendor.viewmodel;

import android.app.Application;
import android.content.Context;

import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.cgg.streetvendor.interfaces.ErrorHandlerInterface;
import com.cgg.streetvendor.network.SVSService;
import com.cgg.streetvendor.room.repository.SVSSyncULBRepository;
import com.cgg.streetvendor.source.reposnse.ulb.ULBResponse;
import com.cgg.streetvendor.source.reposnse.ulb.UlbEntity;
import com.cgg.streetvendor.source.reposnse.ulb.WardEntity;
import com.cgg.streetvendor.source.reposnse.ulb.WardResponse;

import org.jetbrains.annotations.NotNull;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class SVSSyncULBViewModel extends AndroidViewModel {
    private MutableLiveData<ULBResponse> ulbResponseMutableLiveData;
    private MutableLiveData<WardResponse> wardResponseMutableLiveData;
    private Context context;
    private ErrorHandlerInterface errorHandlerInterface;
    private LiveData<List<UlbEntity>> allULBs;
    private LiveData<List<UlbEntity>> loggedULBs;
    private LiveData<List<WardEntity>> allWards;
    private LiveData<List<WardEntity>> ulbWards;
    private SVSSyncULBRepository svsSyncULBRepository;

    public SVSSyncULBViewModel(Context context, Application application) {
        super(application);
        this.context = context;
        ulbResponseMutableLiveData = new MutableLiveData<>();
        wardResponseMutableLiveData = new MutableLiveData<>();
        allULBs = new MutableLiveData<>();
        loggedULBs = new MutableLiveData<>();
        allWards = new MutableLiveData<>();
        ulbWards = new MutableLiveData<>();
        errorHandlerInterface = (ErrorHandlerInterface) context;
        svsSyncULBRepository = new SVSSyncULBRepository(application);
    }

    public LiveData<List<UlbEntity>> getAllULBs() {
        if (allULBs != null) {
            allULBs = svsSyncULBRepository.getAllULBs();
        }
        return allULBs;
    }

    public LiveData<List<UlbEntity>> getLoggedULBs(String ulbId) {
        if (loggedULBs != null) {
            loggedULBs = svsSyncULBRepository.getLoggedULBs(ulbId);
        }
        return loggedULBs;
    }

    public LiveData<List<WardEntity>> getAllWards() {
        if (allWards != null) {
            allWards = svsSyncULBRepository.getAllWards();
        }
        return allWards;
    }

    public LiveData<String> getULBID(String ulbName) {
        return svsSyncULBRepository.getULBID(ulbName);
    }

    public LiveData<String> getTelULBId(String ulbName) {
        return svsSyncULBRepository.getTelULBId(ulbName);
    }

    public LiveData<String> getWardID(String wardName, String ulbId) {
        return svsSyncULBRepository.getWardID(wardName, ulbId);
    }


    public LiveData<List<WardEntity>> getULBWards(String UlbId) {
        if (ulbWards != null) {
            ulbWards = svsSyncULBRepository.getULBWards(UlbId);
        }
        return ulbWards;
    }

    public LiveData<ULBResponse> getULBResponse() {
        if (ulbResponseMutableLiveData != null) {
            getULBResponseCall();
        }
        return ulbResponseMutableLiveData;
    }

    private void getULBResponseCall() {
        SVSService twdService = SVSService.Factory.create();
        twdService.getULBResponse().enqueue(new Callback<ULBResponse>() {
            @Override
            public void onResponse(@NotNull Call<ULBResponse> call, @NotNull Response<ULBResponse> response) {
                if (response.isSuccessful() && response.body() != null) {
                    ulbResponseMutableLiveData.setValue(response.body());
                }
            }

            @Override
            public void onFailure(@NotNull Call<ULBResponse> call, @NotNull Throwable t) {
                errorHandlerInterface.handleError(t, context);
            }
        });
    }

    public LiveData<WardResponse> getWardResponse() {
        if (wardResponseMutableLiveData != null) {
            getWardMasterCall();
        }
        return wardResponseMutableLiveData;
    }

    private void getWardMasterCall() {
        SVSService twdService = SVSService.Factory.create();
        twdService.getWardResponse().enqueue(new Callback<WardResponse>() {
            @Override
            public void onResponse(@NotNull Call<WardResponse> call, @NotNull Response<WardResponse> response) {
                if (response.isSuccessful() && response.body() != null) {
                    wardResponseMutableLiveData.setValue(response.body());
                }
            }

            @Override
            public void onFailure(@NotNull Call<WardResponse> call, @NotNull Throwable t) {
                errorHandlerInterface.handleError(t, context);
            }
        });
    }

}



