package com.cgg.streetvendor.ui;

import android.animation.ObjectAnimator;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;
import androidx.databinding.DataBindingUtil;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import com.cgg.streetvendor.R;
import com.cgg.streetvendor.databinding.ActivityCggUsvsPolicyBinding;
import com.cgg.streetvendor.util.LocaleHelper;

public class Cgg_USVS_Policy extends AppCompatActivity {

    private ActivityCggUsvsPolicyBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_cgg_usvs_policy);

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            int startColor = getWindow().getStatusBarColor();
            int endColor = ContextCompat.getColor(this, R.color.colorPrimaryDark);
            ObjectAnimator.ofArgb(getWindow(), "statusBarColor", startColor, endColor).start();
        }

        if(LocaleHelper.getLanguage(this).equalsIgnoreCase("te")){
            LocaleHelper.setLocale(this,"te");
        }else{
            LocaleHelper.setLocale(this,"en");
        }


        binding.header.headerTitle.setText(getString(R.string.app_name));
        binding.header.backBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        SectionsPagerAdapter sectionsPagerAdapter = new SectionsPagerAdapter(this, getSupportFragmentManager());
        binding.viewPager.setAdapter(sectionsPagerAdapter);
        binding.tabs.setupWithViewPager(binding.viewPager);

        binding.tabs.setSelectedTabIndicatorColor(Color.parseColor("#FF0000"));
        binding.tabs.setTabTextColors(Color.parseColor("#FFFFFF"), Color.parseColor("#FFFFFF"));


    }

    @Override
    protected void onStop() {
        super.onStop();
        if(LocaleHelper.getLanguage(this).equalsIgnoreCase("te")){
            LocaleHelper.setLocale(this,"te");
        }else{
            LocaleHelper.setLocale(this,"en");
        }

    }
}
