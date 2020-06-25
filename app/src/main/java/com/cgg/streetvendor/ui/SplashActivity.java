package com.cgg.streetvendor.ui;

import android.Manifest;
import android.animation.ObjectAnimator;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.databinding.DataBindingUtil;
import androidx.lifecycle.Observer;

import com.cgg.streetvendor.R;
import com.cgg.streetvendor.application.AppConstants;
import com.cgg.streetvendor.application.SVSApplication;
import com.cgg.streetvendor.databinding.ActivitySplashBinding;
import com.cgg.streetvendor.databinding.CustomLayoutForPermissionsBinding;
import com.cgg.streetvendor.interfaces.ErrorHandlerInterface;
import com.cgg.streetvendor.source.reposnse.version.VersionCheckResponse;
import com.cgg.streetvendor.util.ErrorHandler;
import com.cgg.streetvendor.util.LocaleHelper;
import com.cgg.streetvendor.util.Utils;
import com.cgg.streetvendor.viewmodel.SplashViewModel;
import com.google.android.material.snackbar.Snackbar;

import org.jetbrains.annotations.NotNull;

public class SplashActivity extends AppCompatActivity implements ErrorHandlerInterface {

    private static final int REQUEST_PERMISSION_CODE = 2000;
    private CustomLayoutForPermissionsBinding customBinding;
    private Context context;
    private SplashViewModel splashViewModel;
    private String appVersion;
    private ActivitySplashBinding binding;
    private SharedPreferences sharedPreferences;
    private String locale;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_splash);

        sharedPreferences = SVSApplication.get(SplashActivity.this).getPreferences();

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            int startColor = getWindow().getStatusBarColor();
            int endColor = ContextCompat.getColor(this, R.color.colorPrimaryDark);
            ObjectAnimator.ofArgb(getWindow(), "statusBarColor", startColor, endColor).start();
        }
        context = SplashActivity.this;
        appVersion = Utils.getVersionName(this);
        splashViewModel = new SplashViewModel(this, getApplication());

        locale = sharedPreferences.getString(AppConstants.LOCALE_LANG, "");
        if (!TextUtils.isEmpty(locale)) {
            LocaleHelper.setLocale(SplashActivity.this, locale);
        } else {
            LocaleHelper.setLocale(SplashActivity.this, "en");
        }

        if (Utils.checkInternetConnection(this)) {
            splashViewModel.getCurrentVersion().observe(this, new Observer<VersionCheckResponse>() {
                @Override
                public void onChanged(VersionCheckResponse versionResponse) {
                    if (versionResponse != null) {
                        if (versionResponse.getStatusCode() != null && versionResponse.getStatusCode().equalsIgnoreCase(AppConstants.SUCCESS_STRING_CODE)) {
                            if (appVersion != null) {
                                if (versionResponse.getVersionData() != null && versionResponse.getVersionData().size() > 0
                                        && versionResponse.getVersionData().get(0).getVersionNo().
                                        equalsIgnoreCase(Utils.getVersionName(SplashActivity.this))) {
                                    new Handler().postDelayed(new Runnable() {
                                        @Override
                                        public void run() {
                                            try {
//                                                int permissionCheck1 = ContextCompat.checkSelfPermission(
//                                                        SplashActivity.this, Manifest.permission.ACCESS_FINE_LOCATION);
                                                int permissionCheck2 = ContextCompat.checkSelfPermission(
                                                        SplashActivity.this, Manifest.permission.CAMERA);
//                                                int permissionCheck3 = ContextCompat.checkSelfPermission(
//                                                        SplashActivity.this, Manifest.permission.WRITE_EXTERNAL_STORAGE);
                                                int permissionCheck4 = ContextCompat.checkSelfPermission(
                                                        SplashActivity.this, Manifest.permission.READ_PHONE_STATE);


                                                if ((permissionCheck2 != PackageManager.PERMISSION_GRANTED)
                                                        && (permissionCheck4 != PackageManager.PERMISSION_GRANTED)
                                                       /* && (permissionCheck3 != PackageManager.PERMISSION_GRANTED)
                                                        && (permissionCheck4 != PackageManager.PERMISSION_GRANTED)*/) {

                                                    customBinding = DataBindingUtil.setContentView(SplashActivity.this,
                                                            R.layout.custom_layout_for_permissions);
                                                    customBinding.accept.setOnClickListener(onBtnClick);
                                                } else {
                                                    startActivity(new Intent(SplashActivity.this, LoginActivity.class));
                                                    finish();

                                                }
                                            } catch (Exception e) {
                                                e.printStackTrace();
                                            }
                                        }
                                    }, 1000);

                                } else if (versionResponse.getStatusMessage() != null) {
                                    Utils.ShowPlayAlert(SplashActivity.this, getResources().getString(R.string.app_name), versionResponse.getStatusMessage());
                                } else {
                                    Utils.ShowPlayAlert(SplashActivity.this, getResources().getString(R.string.app_name), getString(R.string.update_msg));
                                }
                            } else {
                                Toast.makeText(context, getString(R.string.app_ver), Toast.LENGTH_SHORT).show();
                            }
                        } else if (versionResponse.getStatusCode() != null && versionResponse.getStatusCode().equalsIgnoreCase(AppConstants.FAILURE_STRING_CODE)) {
                            if (!TextUtils.isEmpty(versionResponse.getStatusMessage())) {
                                Utils.customErrorAlert(SplashActivity.this, versionResponse.getStatusMessage(), getString(R.string.plz_check_int));
                            } else {
                                Snackbar.make(binding.cl, getString(R.string.something), Snackbar.LENGTH_SHORT).show();
                            }
                        } else {
                            Snackbar.make(binding.cl, getString(R.string.server_not), Snackbar.LENGTH_SHORT).show();
                        }
                    } else {
                        Snackbar.make(binding.cl, getString(R.string.server_not), Snackbar.LENGTH_SHORT).show();
                    }
                }
            });
        } else {
            Utils.customErrorAlert(SplashActivity.this, getResources().getString(R.string.app_name), getString(R.string.plz_check_int));
        }

    }

    View.OnClickListener onBtnClick = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            try {
                switch (view.getId()) {
                    case R.id.accept:
                        ActivityCompat.requestPermissions(SplashActivity.this,
                                new String[]{/*Manifest.permission.ACCESS_FINE_LOCATION*/
                                        Manifest.permission.CAMERA,
                                        /*Manifest.permission.WRITE_EXTERNAL_STORAGE,*/
                                        Manifest.permission.READ_PHONE_STATE},
                                REQUEST_PERMISSION_CODE);
                        break;
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    };

    @Override
    public void onRequestPermissionsResult(int requestCode, @NotNull String permissions[], @NotNull int[] grantResults) {
        try {
            switch (requestCode) {
                case REQUEST_PERMISSION_CODE:

                    if ((grantResults[0] == PackageManager.PERMISSION_GRANTED)
                            && (grantResults[1] == PackageManager.PERMISSION_GRANTED)
                           /* && (grantResults[2] == PackageManager.PERMISSION_GRANTED)
                            && (grantResults[3] == PackageManager.PERMISSION_GRANTED)*/) {
                        //TODO
                        new Handler().postDelayed(new Runnable() {
                            @Override
                            public void run() {

                                startActivity(new Intent(SplashActivity.this, LoginActivity.class));
                                finish();

                            }
                        }, 1000);
                    } else {
                        customAlert();
                    }
                    break;

                default:
                    break;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    private void customAlert() {
        try {
            final Dialog dialog = new Dialog(context);
            dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
            if (dialog.getWindow() != null && dialog.getWindow().getAttributes() != null) {
                dialog.getWindow().getAttributes().windowAnimations = R.style.exitdialog_animation1;
                dialog.setContentView(R.layout.custom_alert_information);
                dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
                dialog.setCancelable(false);
                TextView dialogMessage = dialog.findViewById(R.id.dialog_message);
                dialogMessage.setText(getString(R.string.plz_grant));
                Button yes = dialog.findViewById(R.id.btDialogYes);
                Button no = dialog.findViewById(R.id.btDialogNo);
                yes.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        dialog.dismiss();
                        ActivityCompat.requestPermissions(SplashActivity.this,
                                new String[]{
                                        /*Manifest.permission.ACCESS_FINE_LOCATION*/
                                        Manifest.permission.CAMERA
                                        /*, Manifest.permission.WRITE_EXTERNAL_STORAGE*/
                                        , Manifest.permission.READ_PHONE_STATE},
                                REQUEST_PERMISSION_CODE);
                    }
                });
                no.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        dialog.dismiss();
                        finish();
                    }
                });
                if (!dialog.isShowing())
                    dialog.show();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    @Override
    public void handleError(Throwable e, Context context) {
        String errMsg = ErrorHandler.handleError(e, context);
        Log.i("MSG", "handleError: " + errMsg);
        Snackbar.make(binding.cl, errMsg, Snackbar.LENGTH_SHORT).show();
    }

}


