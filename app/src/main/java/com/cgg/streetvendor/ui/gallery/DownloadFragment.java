package com.cgg.streetvendor.ui.gallery;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.annotation.NonNull;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;

import com.cgg.streetvendor.R;
import com.cgg.streetvendor.databinding.ActivityDownloadBinding;
import com.cgg.streetvendor.databinding.FragmentHomeBinding;

public class DownloadFragment extends Fragment {

    private GalleryViewModel galleryViewModel;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        galleryViewModel =
                ViewModelProviders.of(this).get(GalleryViewModel.class);
        ActivityDownloadBinding binding = DataBindingUtil.inflate(inflater, R.layout.activity_download, container, false);
        binding.header.headerTitle.setText(getString(R.string.download_masters));

        binding.btnPlacesMaster.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });
        return binding.getRoot();
    }
}