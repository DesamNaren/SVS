package com.cgg.streetvendor.viewmodel;

import android.app.Application;
import android.content.Context;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.cgg.streetvendor.interfaces.ErrorHandlerInterface;
import com.cgg.streetvendor.network.SVSService;
import com.cgg.streetvendor.source.reposnse.reports.AllFieldReportResponse;
import com.cgg.streetvendor.source.reposnse.reports.DailyReportResponse;
import com.cgg.streetvendor.source.reposnse.version.VersionCheckResponse;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class DailyReportViewModel extends AndroidViewModel {
    private MutableLiveData<DailyReportResponse> dailyReportResponseMutableLiveData;
    private MutableLiveData<AllFieldReportResponse> allFieldReportResponseMutableLiveData;
    private Context context;
    private ErrorHandlerInterface errorHandlerInterface;

    public DailyReportViewModel(Context context, Application application) {
        super(application);
        this.context = context;
        dailyReportResponseMutableLiveData = new MutableLiveData<>();
        allFieldReportResponseMutableLiveData = new MutableLiveData<>();
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
            public void onResponse(@NonNull Call<DailyReportResponse> call, @NonNull Response<DailyReportResponse> response) {
                if (response.isSuccessful() && response.body() != null) {
                    dailyReportResponseMutableLiveData.setValue(response.body());
                }
            }

            @Override
            public void onFailure(@NonNull Call<DailyReportResponse> call, @NonNull Throwable t) {
                errorHandlerInterface.handleError(t, context);
            }
        });
    }

    public LiveData<AllFieldReportResponse> getAllFieldReportResponse() {
        if (allFieldReportResponseMutableLiveData != null) {
            callAllFieldReportResponse();
        }
        return allFieldReportResponseMutableLiveData;
    }


    private void callAllFieldReportResponse() {
        SVSService svsService = SVSService.Factory.create();
        svsService.getAllFieldReportDataCall().enqueue(new Callback<AllFieldReportResponse>() {
            @Override
            public void onResponse(@NonNull Call<AllFieldReportResponse> call, @NonNull Response<AllFieldReportResponse> response) {
                if (response.isSuccessful() && response.body() != null) {
                    allFieldReportResponseMutableLiveData.setValue(response.body());
                }
            }

            @Override
            public void onFailure(@NonNull Call<AllFieldReportResponse> call, @NonNull Throwable t) {
                errorHandlerInterface.handleError(t, context);
            }
        });
    }


}

