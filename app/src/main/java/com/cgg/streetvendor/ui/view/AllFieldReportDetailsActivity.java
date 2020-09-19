package com.cgg.streetvendor.ui.view;

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
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.cgg.streetvendor.R;
import com.cgg.streetvendor.adapter.AllFieldDetailsReportAdapter;
import com.cgg.streetvendor.adapter.DailyDistrictDetailsReportAdapter;
import com.cgg.streetvendor.util.AppConstants;
import com.cgg.streetvendor.application.SVSApplication;
import com.cgg.streetvendor.databinding.ActivityAllFieldReportDetailsBinding;
import com.cgg.streetvendor.source.reposnse.reports.AllFieldReportData;
import com.cgg.streetvendor.source.reposnse.reports.AllFieldReportResponse;
import com.google.gson.Gson;

import java.util.ArrayList;

public class AllFieldReportDetailsActivity extends AppCompatActivity {

    private ActivityAllFieldReportDetailsBinding binding;
    private SharedPreferences sharedPreferences;
    private AllFieldReportResponse allFieldReportResponse;
    private TextView tv;
    private AllFieldDetailsReportAdapter adapter;
    private String ulb, ulb_id, roleId;
    private ArrayList<AllFieldReportData> dailyReportDataList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_all_field_report_details);


        try {
            if (getSupportActionBar() != null) {
                tv = new TextView(getApplicationContext());
                RelativeLayout.LayoutParams lp = new RelativeLayout.LayoutParams(
                        RelativeLayout.LayoutParams.MATCH_PARENT, // Width of TextView
                        RelativeLayout.LayoutParams.WRAP_CONTENT); // Height of TextView
                tv.setLayoutParams(lp);
                tv.setText(getResources().getString(R.string.all_fields_report));
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

        try {
            sharedPreferences = SVSApplication.get(this).getPreferences();
            String dist = getIntent().getStringExtra("DAILY_REPORT_DISTRICT");
            String dist_id = getIntent().getStringExtra("DAILY_REPORT_DISTRICT_ID");
            ulb = getIntent().getStringExtra("DAILY_REPORT_ULB");
            ulb_id = getIntent().getStringExtra("DAILY_REPORT_ULB_ID");
            roleId = sharedPreferences.getString(AppConstants.ROLE_ID, "");
            if (!TextUtils.isEmpty(dist_id)) {
                binding.distNameTv.setText(dist);

                Gson gson = new Gson();
                String str = sharedPreferences.getString("ALL_REPORT_DATA", "");
                allFieldReportResponse = gson.fromJson(str, AllFieldReportResponse.class);
                if (allFieldReportResponse != null && allFieldReportResponse.getAllFieldReportData() != null
                        && allFieldReportResponse.getAllFieldReportData().size() > 0) {
                    dailyReportDataList = new ArrayList<>();
                    if (TextUtils.isEmpty(ulb) && TextUtils.isEmpty(ulb_id)) {
                        for (AllFieldReportData dailyReportData : allFieldReportResponse.getAllFieldReportData()) {
                            if (dist_id.equalsIgnoreCase(dailyReportData.getDistrictId())) {
                                dailyReportDataList.add(dailyReportData);
                            }
                        }
                    } else {
                        for (AllFieldReportData dailyReportData : allFieldReportResponse.getAllFieldReportData()) {
                            if (dist_id.equalsIgnoreCase(dailyReportData.getDistrictId())
                                    && ulb_id.equalsIgnoreCase(dailyReportData.getCityId())) {
                                dailyReportDataList.add(dailyReportData);
                            }
                        }
                    }
                    if (dailyReportDataList.size() > 0) {
                        binding.distLl.setVisibility(View.VISIBLE);
                        binding.rvUlbs.setVisibility(View.VISIBLE);
                        binding.emptyTV.setVisibility(View.GONE);
                        adapter =
                                new AllFieldDetailsReportAdapter
                                        (dailyReportDataList, this, this);
                        binding.rvUlbs.setLayoutManager(new LinearLayoutManager(this));
                        binding.rvUlbs.setAdapter(adapter);
                    }else {
                        binding.distLl.setVisibility(View.GONE);
                        binding.rvUlbs.setVisibility(View.GONE);
                        binding.emptyTV.setVisibility(View.VISIBLE);
                    }
                } else {
                    Toast.makeText(this, getString(R.string.something), Toast.LENGTH_SHORT).show();
                }
            } else {
                Toast.makeText(this, getString(R.string.something), Toast.LENGTH_SHORT).show();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    private Menu mMenu = null;
    private SearchView mSearchView;

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.search_menu, menu);
        mMenu = menu;
        MenuItem mSearch = mMenu.findItem(R.id.action_search);
        if (!TextUtils.isEmpty(ulb)) {
            mSearch.setVisible(false);
        } else {
            mSearch.setVisible(true);
        }
        if(dailyReportDataList!=null && dailyReportDataList.size() > 0){
            mSearch.setVisible(true);
        }else {
            mSearch.setVisible(false);
        }
        mSearchView = (SearchView) mSearch.getActionView();
        mSearchView.setQueryHint(Html.fromHtml("<font color = #ffffff>" +
                getResources().getString(R.string.search_by_ulb) + "</font>"));
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
                if (adapter != null) {
                    adapter.getFilter().filter(newText);
                }
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

    @Override
    public void onBackPressed() {
        if (AppConstants.MC_ROLL_ID.equalsIgnoreCase(roleId) || AppConstants.ULB_ROLL_ID.equalsIgnoreCase(roleId)) { // compare with role id replace with ==
            Intent intent = new Intent(this, DashboardActivity.class);
            intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);
            startActivity(intent);
        } else {
            super.onBackPressed();
        }
    }
}
