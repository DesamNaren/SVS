package com.cgg.streetvendor.ui.view;

import android.animation.ObjectAnimator;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.Resources;
import android.graphics.Paint;
import android.os.Build;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;
import androidx.databinding.DataBindingUtil;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;

import com.cgg.streetvendor.R;
import com.cgg.streetvendor.util.AppConstants;
import com.cgg.streetvendor.application.SVSApplication;
import com.cgg.streetvendor.databinding.ActivityLoginBinding;
import com.cgg.streetvendor.interfaces.DeleteVendingInterface;
import com.cgg.streetvendor.interfaces.ErrorHandlerInterface;
import com.cgg.streetvendor.room.repository.SVSSyncPlacesRepository;
import com.cgg.streetvendor.source.reposnse.login.LoginResponse;
import com.cgg.streetvendor.util.ErrorHandler;
import com.cgg.streetvendor.util.LocaleHelper;
import com.cgg.streetvendor.util.Utils;
import com.cgg.streetvendor.viewmodel.LoginCustomViewModel;
import com.cgg.streetvendor.viewmodel.LoginViewModel;

public class LoginActivity extends AppCompatActivity implements ErrorHandlerInterface, DeleteVendingInterface {

    ActivityLoginBinding binding;
    private SharedPreferences sharedPreferences;
    private SharedPreferences.Editor editor;
    private LoginResponse loginResponseMain;
    private SVSSyncPlacesRepository svsSyncPlacesRepository;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(LoginActivity.this, R.layout.activity_login);
        svsSyncPlacesRepository = new SVSSyncPlacesRepository(getApplication());

        binding.userManualTv.setPaintFlags(binding.userManualTv.getPaintFlags() |   Paint.UNDERLINE_TEXT_FLAG);
        binding.userManualTv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(LoginActivity.this, UserManualActivity.class)
                .setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP|Intent.FLAG_ACTIVITY_NEW_TASK));
            }
        });

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            int startColor = getWindow().getStatusBarColor();
            int endColor = ContextCompat.getColor(this, R.color.colorPrimaryDark);
            ObjectAnimator.ofArgb(getWindow(), "statusBarColor", startColor, endColor).start();
        }

        sharedPreferences = SVSApplication.get(this).getPreferences();
        editor = sharedPreferences.edit();
        LoginViewModel loginViewModel =
                ViewModelProviders.of(LoginActivity.this,
                        new LoginCustomViewModel(binding, LoginActivity.this)).get(LoginViewModel.class);

        binding.setViewModel(loginViewModel);


        binding.language.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {


                    if (LocaleHelper.getLanguage(LoginActivity.this).equalsIgnoreCase("te")) {
                        LocaleHelper.setLocale(LoginActivity.this, "en");
                    } else {
                        LocaleHelper.setLocale(LoginActivity.this, "te");
                    }
                    LoginActivity.this.finish();
                    LoginActivity.this.startActivity(LoginActivity.this.getIntent());

                } catch (Resources.NotFoundException e) {
                    e.printStackTrace();
                }

            }
        });
        loginViewModel.geListLiveData().observe(LoginActivity.this, new Observer<LoginResponse>() {
            @Override
            public void onChanged(LoginResponse loginResponse) {

                if (loginResponse != null && loginResponse.getStatus_code() != null) {
                    if (loginResponse.getStatus_code().equalsIgnoreCase(AppConstants.SUCCESS_STRING_CODE)) {

                        navDashBoard(loginResponse);

//                        loginResponseMain = loginResponse;
//
//                        String lastULBCode = sharedPreferences.getString(AppConstants.ULB_ID, "");
//                        String currentUlbId = loginResponse.getUserData().get(0).getUlb_id();
//                        if (TextUtils.isEmpty(lastULBCode)) {
//                            navDashBoard(loginResponse);
//                        } else if (!lastULBCode.equalsIgnoreCase(currentUlbId)) {
//                            deleteVendingData();
//                        } else {
//                            navDashBoard(loginResponse);
//                        }
                    } else if (loginResponse.getStatus_code().equalsIgnoreCase(AppConstants.FAILURE_STRING_CODE)) {
                        Utils.customErrorAlert(LoginActivity.this, getString(R.string.app_name), loginResponse.getStatus_message());
                    } else {
                        Utils.customErrorAlert(LoginActivity.this, getString(R.string.app_name), getString(R.string.something));
                    }
                } else {
                    Utils.customErrorAlert(LoginActivity.this, getString(R.string.app_name), getString(R.string.something));

                }
            }
        });

        binding.etUserName.addTextChangedListener(new GenericTextWatcher(binding.etUserName));
        binding.etPwd.addTextChangedListener(new GenericTextWatcher(binding.etPwd));
    }

    private void deleteVendingData() {
        svsSyncPlacesRepository.deleteAllVendings(this);
    }

    private void navDashBoard(LoginResponse loginResponse) {
        editor.putString(AppConstants.DISTRICT_ID, loginResponse.getUserData().get(0).getDistrict_id());
        editor.putString(AppConstants.STATE_ID, loginResponse.getUserData().get(0).getState_id());
        editor.putString(AppConstants.ULB_ID, loginResponse.getUserData().get(0).getUlb_id());
        editor.putString(AppConstants.USER_DESC, loginResponse.getUserData().get(0).getUser_description());
        editor.putString(AppConstants.USER_NAME, binding.etUserName.getText().toString().trim());
        editor.putString(AppConstants.ROLE_ID, loginResponse.getUserData().get(0).getRoleId());
        editor.commit();
        startActivity(new Intent(LoginActivity.this, DashboardActivity.class));
        finish();
    }


    @Override
    public void handleError(Throwable e, Context context) {
        String errMsg = ErrorHandler.handleError(e, context);
        Utils.customErrorAlert(LoginActivity.this, getString(R.string.app_name), errMsg);
    }

    @Override
    public void onDataNotAvailable() {

    }

    @Override
    public void venCount(int cnt) {
        if (cnt <= 0) {
            navDashBoard(loginResponseMain);
        }
    }

    private class GenericTextWatcher implements TextWatcher {

        private View view;

        private GenericTextWatcher(View view) {
            this.view = view;
        }

        public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {
        }

        public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
        }

        public void afterTextChanged(Editable editable) {
            String text = editable.toString();
            switch (view.getId()) {
                case R.id.et_userName:
                    if (text.length() != 0)
                        binding.etUserName.setError(null);
                    break;
                case R.id.et_pwd:
                    if (text.length() != 0)
                        binding.etPwd.setError(null);
                    break;

            }
        }
    }

}
