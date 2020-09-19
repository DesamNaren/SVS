package com.cgg.streetvendor.ui.view;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Bundle;
import android.text.Html;
import android.text.InputType;
import android.text.TextUtils;
import android.util.TypedValue;
import android.view.Gravity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.RelativeLayout;
import android.widget.SearchView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.Observer;

import com.cgg.streetvendor.R;
import com.cgg.streetvendor.util.AppConstants;
import com.cgg.streetvendor.application.SVSApplication;
import com.cgg.streetvendor.databinding.DailyReportBaseFragmentBinding;
import com.cgg.streetvendor.fragment.DailyDistrictWiseFragment;
import com.cgg.streetvendor.fragment.DailyULBWiseFragment;
import com.cgg.streetvendor.interfaces.ErrorHandlerInterface;
import com.cgg.streetvendor.source.reposnse.reports.DailyReportResponse;
import com.cgg.streetvendor.util.ErrorHandler;
import com.cgg.streetvendor.util.Utils;
import com.cgg.streetvendor.viewmodel.DailyReportViewModel;
import com.google.gson.Gson;


public class DailyReportActivity extends AppCompatActivity implements ErrorHandlerInterface {

    DailyReportBaseFragmentBinding binding;
    private TextView tv;
    private String loginDistID, loginULBID;
    private String distName, ulbName;
    private String roleId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.daily_report_base_fragment);

        SharedPreferences sharedPreferences = SVSApplication.get(DailyReportActivity.this).getPreferences();

        loginDistID = sharedPreferences.getString("DISTRICT_ID", "");
        loginULBID = sharedPreferences.getString("ULB_ID", "");
        roleId = sharedPreferences.getString(AppConstants.ROLE_ID, "");

        try {
            if (getSupportActionBar() != null) {
                tv = new TextView(getApplicationContext());
                RelativeLayout.LayoutParams lp = new RelativeLayout.LayoutParams(
                        RelativeLayout.LayoutParams.MATCH_PARENT, // Width of TextView
                        RelativeLayout.LayoutParams.WRAP_CONTENT); // Height of TextView
                tv.setLayoutParams(lp);
                tv.setText(getResources().getString(R.string.daily_progress_report));
                tv.setGravity(Gravity.CENTER);
                tv.setTextColor(Color.WHITE);
                tv.setTextSize(TypedValue.COMPLEX_UNIT_SP, 18);
                getSupportActionBar().setDisplayOptions(ActionBar.DISPLAY_SHOW_CUSTOM);
                getSupportActionBar().setCustomView(tv);
                getSupportActionBar().setDisplayHomeAsUpEnabled(true);
                getSupportActionBar().setHomeAsUpIndicator(R.drawable.ic_back);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        DailyReportViewModel dailyReportViewModel = new DailyReportViewModel(this,
                getApplication());

        binding.tabLayout.setupWithViewPager(binding.viewPager);
        binding.tabLayout.setSelectedTabIndicatorColor(Color.parseColor("#FFFFFF"));
        binding.tabLayout.setTabTextColors(Color.parseColor("#FFFFFF"), Color.parseColor("#FFFFFF"));

        if (Utils.checkInternetConnection(this)) {
            binding.progress.setVisibility(View.VISIBLE);
            LiveData<DailyReportResponse> dailyReportResponseLiveData = dailyReportViewModel.getDailyReportResponse();
            dailyReportResponseLiveData.observe(this,
                    new Observer<DailyReportResponse>() {
                        @Override
                        public void onChanged(DailyReportResponse dailyReportResponse) {
                            dailyReportResponseLiveData.removeObservers(DailyReportActivity.this);
                            binding.progress.setVisibility(View.GONE);
                            if (dailyReportResponse != null) {
                                if (dailyReportResponse.getStatusCode().equals("200") && dailyReportResponse.getDailyReportData()
                                        != null && dailyReportResponse.getDailyReportData().size() > 0) {
                                    SharedPreferences.Editor editor = sharedPreferences.edit();
                                    Gson gson = new Gson();
                                    editor.putString("REPORT_DATA", gson.toJson(dailyReportResponse));
                                    editor.commit();

                                    if (dailyReportResponse.getDailyReportData() != null && dailyReportResponse.getDailyReportData().size() > 0) {
                                        for (int i = 0; i < dailyReportResponse.getDailyReportData().size(); i++) {
                                            if (dailyReportResponse.getDailyReportData().get(i).getDistrictId().equalsIgnoreCase(loginDistID)) {
                                                distName = dailyReportResponse.getDailyReportData().get(i).getDistrictName();
                                            }
                                            if (dailyReportResponse.getDailyReportData().get(i).getDistrictId().equalsIgnoreCase(loginDistID)
                                                    && dailyReportResponse.getDailyReportData().get(i).getCityId().equalsIgnoreCase(loginULBID)) {
                                                ulbName = dailyReportResponse.getDailyReportData().get(i).getCityName();
                                            }
                                            if (!TextUtils.isEmpty(distName) && !TextUtils.isEmpty(ulbName)) {
                                                break;
                                            }
                                        }
                                    }

                                    if (AppConstants.MC_ROLL_ID.equalsIgnoreCase(roleId) ) { // compare with role id replace with ==
                                        Intent intent = new Intent(DailyReportActivity.this, DailyReportDetailsActivity.class);
                                        intent.putExtra("DAILY_REPORT_DISTRICT",
                                                distName);// pass dist name
                                        intent.putExtra("DAILY_REPORT_DISTRICT_ID",
                                                loginDistID); // pass dist id
                                        intent.putExtra("DAILY_REPORT_ULB",
                                                ""); // [ass city name
                                        intent.putExtra("DAILY_REPORT_ULB_ID",
                                                ""); // pass city id
                                        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);
                                        startActivity(intent);
                                        finish();
                                    } else if (AppConstants.ULB_ROLL_ID.equalsIgnoreCase(roleId)) { // compare with role id replace with ==
                                        Intent intent = new Intent(DailyReportActivity.this, DailyReportDetailsActivity.class);
                                        intent.putExtra("DAILY_REPORT_DISTRICT",
                                                distName);// pass dist name
                                        intent.putExtra("DAILY_REPORT_DISTRICT_ID",
                                                loginDistID); // pass dist id
                                        intent.putExtra("DAILY_REPORT_ULB",
                                                ulbName); // [ass city name
                                        intent.putExtra("DAILY_REPORT_ULB_ID",
                                                loginULBID); // pass city id
                                        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);
                                        startActivity(intent);
                                        finish();
                                    } else {
                                        binding.viewPager.setAdapter(new ViewPagerAdapter(getSupportFragmentManager()));
                                    }
                                } else {
                                    binding.emptyTV.setVisibility(View.VISIBLE);
                                }
                            } else {
                                Utils.customErrorAlert(DailyReportActivity.this, getResources().getString(R.string.app_name), getString(R.string.server_not));
                            }
                        }
                    });
        } else {
            Utils.customErrorAlert(DailyReportActivity.this, getResources().getString(R.string.app_name), getString(R.string.plz_check_int));
        }

    }

    @Override
    public void handleError(Throwable e, Context context) {
        String errMsg = ErrorHandler.handleError(e, context);
        Utils.customErrorAlert(DailyReportActivity.this, getString(R.string.app_name), errMsg);
    }

    @Override
    public void onBackPressed() {
        Intent newIntent = new Intent(DailyReportActivity.this, DashboardActivity.class);
        newIntent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP |
                Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(newIntent);
    }

    public class ViewPagerAdapter extends FragmentPagerAdapter {

        ViewPagerAdapter(FragmentManager fm) {
            super(fm);
        }

        @NonNull
        @Override
        public Fragment getItem(int position) {
            switch (position) {
                case 0:
                    return new DailyDistrictWiseFragment();
                case 1:
                    return new DailyULBWiseFragment();
            }
            return null;
        }

        @Override
        public int getCount() {
            return 2;
        }

        @Override
        public CharSequence getPageTitle(int position) {
            switch (position) {
                case 0:
                    return "District";
                case 1:
                    return "ULB";
            }
            return null;
        }
    }

    private Menu mMenu = null;
    private SearchView mSearchView;

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.search_menu, menu);
        mMenu = menu;
        MenuItem mSearch = mMenu.findItem(R.id.action_search);
        mSearchView = (SearchView) mSearch.getActionView();
        mSearchView.setQueryHint(Html.fromHtml("<font color = #ffffff>" + getResources().getString(R.string.search_by_dist) + "</font>"));
        mSearchView.setInputType(InputType.TYPE_CLASS_TEXT);
        int id = mSearchView.getContext().getResources().getIdentifier("android:id/search_src_text", null, null);
        TextView textView = mSearchView.findViewById(id);
        textView.setTextColor(Color.WHITE);
        mSearchView.setGravity(Gravity.CENTER);

        mSearchView.setOnSearchClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                tv.setVisibility(View.GONE);
            }
        });

        mSearchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                return true;
            }
        });

        mSearchView.setOnCloseListener(new SearchView.OnCloseListener() {
            @Override
            public boolean onClose() {
                tv.setVisibility(View.VISIBLE);
                return false;
            }
        });
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            onBackPressed();
        }
        return true;
    }

}
