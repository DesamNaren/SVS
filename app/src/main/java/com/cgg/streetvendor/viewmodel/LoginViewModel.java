package com.cgg.streetvendor.viewmodel;

import android.content.Context;
import android.text.TextUtils;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.cgg.streetvendor.R;
import com.cgg.streetvendor.databinding.ActivityLoginBinding;
import com.cgg.streetvendor.interfaces.ErrorHandlerInterface;
import com.cgg.streetvendor.network.SVSService;
import com.cgg.streetvendor.source.reposnse.login.LoginResponse;
import com.cgg.streetvendor.source.request.LoginRequest;
import com.cgg.streetvendor.util.CustomProgressDialog;
import com.cgg.streetvendor.util.Utils;


import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class LoginViewModel extends ViewModel {

    private MutableLiveData<LoginResponse> responseMutableLiveData;
    public MutableLiveData<String> username = new MutableLiveData<>();
    public MutableLiveData<String> password = new MutableLiveData<>();
    private Context context;
    private ActivityLoginBinding binding;
    private ErrorHandlerInterface errorHandlerInterface;
    private CustomProgressDialog customProgressDialog;

    LoginViewModel(ActivityLoginBinding binding, Context context) {
        this.binding = binding;
        this.context = context;
        customProgressDialog = new CustomProgressDialog(context);
        errorHandlerInterface = (ErrorHandlerInterface) context;
    }

    public LiveData<LoginResponse> geListLiveData() {
        responseMutableLiveData = new MutableLiveData<>();
        return responseMutableLiveData;
    }

    public void onBtnClick() {

        LoginRequest loginRequest = new LoginRequest(username.getValue(), password.getValue());
        if (TextUtils.isEmpty(loginRequest.getUsername())) {
            binding.etUserName.setError(context.getString(R.string.please_enter_username));
            binding.etUserName.requestFocus();
            return;

        } else {
            binding.etUserName.setError(null);
        }
        if (TextUtils.isEmpty(loginRequest.getPassword())) {
            binding.etPwd.setError(context.getString(R.string.please_enter_password));
            binding.etPwd.requestFocus();
            return;
        } else {
            binding.etPwd.setError(null);
        }

        if (Utils.checkInternetConnection(context)) {
            callLoginAPI(loginRequest);
        } else {
            Utils.customErrorAlert(context, context.getResources().getString(R.string.app_name), context.getString(R.string.plz_check_int));
        }
    }

    private void callLoginAPI(LoginRequest loginRequest) {
        Utils.hideKeyboard(context, binding.btnSubmit);
        binding.btnSubmit.setVisibility(View.GONE);
        customProgressDialog.show();
        SVSService svsService = SVSService.Factory.create();
        svsService.getLoginResponse(loginRequest)
                .enqueue(new Callback<LoginResponse>() {
                    @Override
                    public void onResponse(@NonNull Call<LoginResponse> call, @NonNull Response<LoginResponse> response) {
                        customProgressDialog.dismiss();
                        binding.btnSubmit.setVisibility(View.VISIBLE);
                        responseMutableLiveData.setValue(response.body());
                    }

                    @Override
                    public void onFailure(@NonNull Call<LoginResponse> call, @NonNull Throwable t) {
                        customProgressDialog.dismiss();
                        binding.btnSubmit.setVisibility(View.VISIBLE);
                        errorHandlerInterface.handleError(t, context);
                    }
                });
    }
}
