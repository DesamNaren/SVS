package com.cgg.streetvendor.fragment;

import android.graphics.Bitmap;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebSettings;
import android.webkit.WebView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;

import com.cgg.streetvendor.R;
import com.cgg.streetvendor.databinding.FragmentMainBinding;
import com.cgg.streetvendor.viewmodel.PrivacyPolicyViewModel;
import com.cgg.streetvendor.util.CustomProgressDialog;

/**
 * A placeholder fragment containing a simple view.
 */
public class PrivacyPolicyFragment extends Fragment {

    private static final String ARG_SECTION_NUMBER = "section_number";

    private PrivacyPolicyViewModel privacyPolicyViewModel;
    private CustomProgressDialog customProgressDialog;
    private FragmentMainBinding binding;


    public static PrivacyPolicyFragment newInstance(int index) {
        PrivacyPolicyFragment fragment = new PrivacyPolicyFragment();
        Bundle bundle = new Bundle();
        bundle.putInt(ARG_SECTION_NUMBER, index);
        fragment.setArguments(bundle);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        privacyPolicyViewModel = ViewModelProviders.of(this).get(PrivacyPolicyViewModel.class);
        customProgressDialog = new CustomProgressDialog(getActivity());
        int index = 1;
        if (getArguments() != null) {
            index = getArguments().getInt(ARG_SECTION_NUMBER);
        }
        privacyPolicyViewModel.setIndex(index);
    }

    @Override
    public View onCreateView(
            @NonNull LayoutInflater inflater, ViewGroup container,
            Bundle savedInstanceState) {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_main, container, false);
        privacyPolicyViewModel.getText().observe(this, new Observer<String>() {
            @Override
            public void onChanged(@Nullable String url) {
                try {
                    customProgressDialog.show();
                    customProgressDialog.setCancelable(true);
                    customProgressDialog.setCanceledOnTouchOutside(true);
                    binding.webView.setWebViewClient(new WebViewClient());
                    binding.webView.loadUrl(url);
                    WebSettings webSettings = binding.webView.getSettings();
                    webSettings.setJavaScriptEnabled(true);
                } catch (Exception e) {
                    customProgressDialog.hide();
                    e.printStackTrace();
                }

            }
        });

        return binding.getRoot();
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