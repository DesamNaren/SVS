package com.cgg.streetvendor.viewmodel;

import android.app.Application;
import android.content.Context;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;

import com.cgg.streetvendor.interfaces.ErrorHandlerInterface;
import com.cgg.streetvendor.interfaces.SubmitInterface;
import com.cgg.streetvendor.network.SVSService;
import com.cgg.streetvendor.source.reposnse.submit.SubmitResponse;
import com.cgg.streetvendor.source.reposnse.submit.ValidateAadharResponse;
import com.cgg.streetvendor.source.request.SubmitRequest;
import com.google.gson.Gson;


import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class SubmitViewModel extends AndroidViewModel {
    private Context context;
    private ErrorHandlerInterface errorHandlerInterface;
    private SubmitInterface submitInterface;

    public SubmitViewModel(Context context, Application application) {
        super(application);
        this.context = context;
        errorHandlerInterface = (ErrorHandlerInterface) context;
        submitInterface = (SubmitInterface) context;
    }


    public void submitCall(SubmitRequest submitRequest) {
        SVSService twdService = SVSService.Factory.create();
        Gson gson = new Gson();
        String str = gson.toJson(submitRequest);

        twdService.submitServiceResponse(submitRequest).enqueue(new Callback<SubmitResponse>() {
            @Override
            public void onResponse(@NonNull Call<SubmitResponse> call,
                                   @NonNull Response<SubmitResponse> response) {
                if (response.isSuccessful() && response.body() != null) {
                    submitInterface.getData(response.body());
                }
            }

            @Override
            public void onFailure(@NonNull Call<SubmitResponse> call, @NonNull Throwable t) {
                errorHandlerInterface.handleError(t, context);
            }
        });
    }


    public void validateAadhar(long aadharNo) {
        SVSService svsService = SVSService.Factory.create();
        svsService.validateAadhar(aadharNo)
                .enqueue(new Callback<ValidateAadharResponse>() {
                    @Override
                    public void onResponse(@NonNull Call<ValidateAadharResponse> call, @NonNull Response<ValidateAadharResponse> response) {
                        submitInterface.getAadharData(response.body());
                    }

                    @Override
                    public void onFailure(@NonNull Call<ValidateAadharResponse> call, @NonNull Throwable t) {
                        errorHandlerInterface.handleError(t, context);
                    }
                });
    }
}



