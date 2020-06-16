package com.cgg.streetvendor.viewmodel;

import android.app.Application;
import android.content.Context;

import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.cgg.streetvendor.interfaces.ErrorHandlerInterface;
import com.cgg.streetvendor.network.SVSService;
import com.cgg.streetvendor.source.reposnse.SubmitResponse;
import com.cgg.streetvendor.source.reposnse.ValidateAadharResponse;
import com.cgg.streetvendor.source.request.SubmitRequest;
import com.google.gson.Gson;

import org.jetbrains.annotations.NotNull;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class SubmitViewModel extends AndroidViewModel {
    private MutableLiveData<SubmitResponse> submitResponseMutableLiveData;
    private Context context;
    private ErrorHandlerInterface errorHandlerInterface;

    public MutableLiveData<ValidateAadharResponse> aadharResponseLiveData;

    public SubmitViewModel(Context context, Application application) {
        super(application);
        this.context = context;
        submitResponseMutableLiveData = new MutableLiveData<>();
        aadharResponseLiveData = new MutableLiveData<>();
        errorHandlerInterface = (ErrorHandlerInterface) context;

    }


    public LiveData<SubmitResponse> submitCallResponse(SubmitRequest submitRequest) {
        if (submitResponseMutableLiveData != null) {
            submitCall(submitRequest);
        }
        return submitResponseMutableLiveData;
    }

    private void submitCall(SubmitRequest submitRequest) {
        SVSService twdService = SVSService.Factory.create();
        Gson gson = new Gson();
        String str = gson.toJson(submitRequest);

        twdService.submitServiceResponse(submitRequest).enqueue(new Callback<SubmitResponse>() {
            @Override
            public void onResponse(@NotNull Call<SubmitResponse> call,
                                   @NotNull Response<SubmitResponse> response) {
                if (response.isSuccessful() && response.body() != null) {
                    submitResponseMutableLiveData.setValue(response.body());
                }
            }

            @Override
            public void onFailure(@NotNull Call<SubmitResponse> call, @NotNull Throwable t) {
                errorHandlerInterface.handleError(t, context);
            }
        });
    }


    public LiveData<ValidateAadharResponse> validateAadharResponseLiveData(long  aadharNo) {
        if (aadharResponseLiveData != null) {
            validateAadhar(aadharNo);
        }
        return aadharResponseLiveData;
    }

    private void validateAadhar(long aadharNo) {
        SVSService svsService = SVSService.Factory.create();
        svsService.validateAadhar(aadharNo)
                .enqueue(new Callback<ValidateAadharResponse>() {
                    @Override
                    public void onResponse(@NotNull Call<ValidateAadharResponse> call, @NotNull Response<ValidateAadharResponse> response) {
                        aadharResponseLiveData.setValue(response.body());
                    }

                    @Override
                    public void onFailure(@NotNull Call<ValidateAadharResponse> call, @NotNull Throwable t) {
                        errorHandlerInterface.handleError(t, context);
                    }
                });
    }
}



