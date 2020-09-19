package com.cgg.streetvendor.ui.home;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProviders;

import com.cgg.streetvendor.R;
import com.cgg.streetvendor.util.AppConstants;
import com.cgg.streetvendor.application.SVSApplication;
import com.cgg.streetvendor.databinding.FragmentHomeBinding;
import com.cgg.streetvendor.ui.view.DownloadActivity;
import com.cgg.streetvendor.ui.view.MainActivity;

import java.util.Objects;

public class HomeFragment extends Fragment {

    private HomeViewModel homeViewModel;

    private SharedPreferences sharedPreferences;
    private SharedPreferences.Editor editor;


    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        homeViewModel =
                ViewModelProviders.of(this).get(HomeViewModel.class);
        FragmentHomeBinding binding = DataBindingUtil.inflate(inflater, R.layout.fragment_home, container, false);

        try {
            sharedPreferences = SVSApplication.get(Objects.requireNonNull(getActivity())).getPreferences();
            editor = sharedPreferences.edit();
            String userName = sharedPreferences.getString(AppConstants.USER_NAME, "");
            binding.tvUserName.setText(userName);
        } catch (Exception e) {
            e.printStackTrace();
        }

        binding.llStartSurvey.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getActivity(), MainActivity.class));
            }
        });

        binding.llMasterDownload.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getActivity(), DownloadActivity.class));
            }
        });
        return binding.getRoot();
    }

}