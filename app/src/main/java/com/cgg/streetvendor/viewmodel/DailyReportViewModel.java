package com.cgg.streetvendor.viewmodel;

import android.app.Application;
import android.content.Context;

import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.cgg.streetvendor.interfaces.ErrorHandlerInterface;
import com.cgg.streetvendor.network.SVSService;
import com.cgg.streetvendor.source.reposnse.reports.DailyReportResponse;
import com.cgg.streetvendor.source.reposnse.version.VersionCheckResponse;

import org.jetbrains.annotations.NotNull;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class DailyReportViewModel extends AndroidViewModel {
    private MutableLiveData<DailyReportResponse> dailyReportResponseMutableLiveData;
    private Context context;
    private ErrorHandlerInterface errorHandlerInterface;

    public DailyReportViewModel(Context context, Application application) {
        super(application);
        this.context = context;
        dailyReportResponseMutableLiveData = new MutableLiveData<>();
        errorHandlerInterface = (ErrorHandlerInterface) context;

    }

    public LiveData<DailyReportResponse> getDailyReportResponse() {
        if (dailyReportResponseMutableLiveData != null) {
            callDailyReportResponse();
        }
        return dailyReportResponseMutableLiveData;
    }

    private void callDailyReportResponse() {
        SVSService svsService = SVSService.Factory.create();
        svsService.getDailyReportResponse().enqueue(new Callback<DailyReportResponse>() {
            @Override
            public void onResponse(@NotNull Call<DailyReportResponse> call, @NotNull Response<DailyReportResponse> response) {
                if (response.isSuccessful() && response.body() != null) {
                    dailyReportResponseMutableLiveData.setValue(response.body());
                }
            }

            @Override
            public void onFailure(@NotNull Call<DailyReportResponse> call, @NotNull Throwable t) {
                errorHandlerInterface.handleError(t, context);
            }
        });
    }

}

