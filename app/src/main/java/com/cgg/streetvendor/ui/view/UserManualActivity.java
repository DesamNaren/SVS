package com.cgg.streetvendor.ui.view;

import android.graphics.Bitmap;
import android.os.Bundle;
import android.view.View;
import android.webkit.WebSettings;
import android.webkit.WebView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;

import com.cgg.streetvendor.BuildConfig;
import com.cgg.streetvendor.R;
import com.cgg.streetvendor.databinding.UserManualActivityBinding;
import com.cgg.streetvendor.util.CustomProgressDialog;
import com.cgg.streetvendor.util.LocaleHelper;

/**
 * A placeholder fragment containing a simple view.
 */
public class UserManualActivity extends AppCompatActivity {


    private CustomProgressDialog customProgressDialog;

    @Override
    protected void onStop() {
        super.onStop();
        if(LocaleHelper.getLanguage(this).equalsIgnoreCase("te")){
            LocaleHelper.setLocale(this,"te");
        }else{
            LocaleHelper.setLocale(this,"en");
        }

    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        UserManualActivityBinding binding = DataBindingUtil.setContentView(this, R.layout.user_manual_activity);

        binding.header.headerTitle.setText(getString(R.string.user_manual));
        binding.header.backBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        customProgressDialog = new CustomProgressDialog(this);
        customProgressDialog.show();
        customProgressDialog.setCancelable(true);
        customProgressDialog.setCanceledOnTouchOutside(true);
        binding.webViewManual.setWebViewClient(new WebViewClient());
        binding.webViewManual.loadUrl(BuildConfig.USER_MANUAL_URL);
        WebSettings webSettings = binding.webViewManual.getSettings();
        webSettings.setJavaScriptEnabled(true);
    }

    public class WebViewClient extends android.webkit.WebViewClient {
        @Override
        public void onPageStarted(WebView view, String url, Bitmap favicon) {
            super.onPageStarted(view, url, favicon);
        }

        @Override
        public boolean shouldOverrideUrlLoading(WebView view, String url) {
            view.loadUrl(url);
            return true;
        }

        @Override
        public void onPageFinished(WebView view, String url) {
            customProgressDialog.hide();
            super.onPageFinished(view, url);

        }
    }
}