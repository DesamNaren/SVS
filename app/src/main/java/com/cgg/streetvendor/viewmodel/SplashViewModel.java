package com.cgg.streetvendor.viewmodel;

import android.app.Application;
import android.content.Context;

import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;


import com.cgg.streetvendor.interfaces.ErrorHandlerInterface;
import com.cgg.streetvendor.network.SVSService;
import com.cgg.streetvendor.source.reposnse.version.VersionCheckResponse;

import org.jetbrains.annotations.NotNull;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class SplashViewModel extends AndroidViewModel {
    private MutableLiveData<VersionCheckResponse> versionResponseMutableLiveData;
    private Context context;
    private ErrorHandlerInterface errorHandlerInterface;

    public SplashViewModel(Context context, Application application) {
        super(application);
        this.context = context;
        versionResponseMutableLiveData = new MutableLiveData<>();
        errorHandlerInterface = (ErrorHandlerInterface) context;

    }

    public LiveData<VersionCheckResponse> getCurrentVersion() {
        if (versionResponseMutableLiveData != null) {
            callVersionCheck();
        }
        return versionResponseMutableLiveData;
    }

    private void callVersionCheck() {
        SVSService svsService = SVSService.Factory.create();
        svsService.getVersionCheckResponse().enqueue(new Callback<VersionCheckResponse>() {
            @Override
            public void onResponse(@NotNull Call<VersionCheckResponse> call, @NotNull Response<VersionCheckResponse> response) {
                if (response.isSuccessful() && response.body() != null) {
                    versionResponseMutableLiveData.setValue(response.body());
                }
            }

            @Override
            public void onFailure(@NotNull Call<VersionCheckResponse> call, @NotNull Throwable t) {
                errorHandlerInterface.handleError(t, context);
            }
        });
    }

}

