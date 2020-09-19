package com.cgg.streetvendor.fragment;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.SearchView;

import androidx.core.view.MenuItemCompat;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.cgg.streetvendor.R;
import com.cgg.streetvendor.adapter.AllFieldDistrictReportAdapter;
import com.cgg.streetvendor.util.AppConstants;
import com.cgg.streetvendor.application.SVSApplication;
import com.cgg.streetvendor.databinding.AllFieldDistWiseFragmentBinding;
import com.cgg.streetvendor.source.reposnse.reports.AllFieldReportData;
import com.cgg.streetvendor.source.reposnse.reports.AllFieldReportResponse;
import com.cgg.streetvendor.ui.view.AllFieldReportDetailsActivity;
import com.cgg.streetvendor.util.Utils;
import com.google.gson.Gson;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Objects;
import java.util.Set;


/**
 * Created by lenovo on 03-06-2019.
 */

public class AllFieldDistWiseFragment extends Fragment {

    private SharedPreferences sharedPreferences;
    private AllFieldReportResponse allFieldReportResponse;
    private AllFieldDistWiseFragmentBinding binding;
    private String roleId;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = DataBindingUtil.inflate(inflater,
                R.layout.all_field_dist_wise_fragment, container, false);
        View view = binding.getRoot();
        setHasOptionsMenu(true);

        try {
            Gson gson = new Gson();
            sharedPreferences = SVSApplication.get(Objects.requireNonNull(getActivity())).getPreferences();
            String string = sharedPreferences.getString("ALL_REPORT_DATA", "");
            roleId = sharedPreferences.getString(AppConstants.ROLE_ID, "");
            allFieldReportResponse = gson.fromJson(string, AllFieldReportResponse.class);
            if (AppConstants.SMC_ROLL_ID.equalsIgnoreCase(roleId) || AppConstants.CDMA_ROLL_ID.equalsIgnoreCase(roleId)) {
                setProjectData(allFieldReportResponse);
            } else if (AppConstants.MC_ROLL_ID.equalsIgnoreCase(roleId)){
                Intent intent = new Intent(getActivity(), AllFieldReportDetailsActivity.class);
                intent.putExtra("DAILY_REPORT_DISTRICT",
                        "");// pass dist name
                intent.putExtra("DAILY_REPORT_DISTRICT_ID",
                        ""); // pass dist id
                intent.putExtra("DAILY_REPORT_ULB",
                        ""); // [ass city name
                intent.putExtra("DAILY_REPORT_ULB_ID",
                        ""); // pass city id
                intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);
                startActivity(intent);
            }else if (AppConstants.ULB_ROLL_ID.equalsIgnoreCase(roleId)){
                Intent intent = new Intent(getActivity(), AllFieldReportDetailsActivity.class);
                intent.putExtra("DAILY_REPORT_DISTRICT",
                        "");// pass dist name
                intent.putExtra("DAILY_REPORT_DISTRICT_ID",
                        ""); // pass dist id
                intent.putExtra("DAILY_REPORT_ULB",
                        ""); // [ass city name
                intent.putExtra("DAILY_REPORT_ULB_ID",
                        ""); // pass city id
                intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);
                startActivity(intent);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }


        binding.includedLayout.shareIV.setVisibility(View.GONE);
        binding.includedLayout.shareIV.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                LinearLayout abstractView = getActivity().getWindow().getDecorView().findViewById(R.id.data_ll);
                Utils.takeSCImage(getActivity(), abstractView,
                        "All Field District Report Abstract Data");
            }
        });

        return view;
    }

    private void setProjectData(AllFieldReportResponse reportResponse) {
        try {
            long total_pop = 0, popPer = 0, sur_cnt = 0, ids_cnt = 0, cer_cnt = 0, aad_cnt = 0, ban_cnt = 0;

            for (int x = 0; x < reportResponse.getAllFieldReportData().size(); x++) {
                total_pop = total_pop + Long.valueOf(reportResponse.getAllFieldReportData().get(x).getSvMobileRevisedTarget());
                popPer = popPer + Long.valueOf(reportResponse.getAllFieldReportData().get(x).getSvMobileRevisedTargetPercent());
                sur_cnt = sur_cnt + Long.valueOf(reportResponse.getAllFieldReportData().get(x).getTotal());
                ids_cnt = ids_cnt + Long.valueOf(reportResponse.getAllFieldReportData().get(x).getTotalIssuedIdcards());
                cer_cnt = cer_cnt + Long.valueOf(reportResponse.getAllFieldReportData().get(x).getTotalVendingCertIssued());
                aad_cnt = aad_cnt + Long.valueOf(reportResponse.getAllFieldReportData().get(x).getTotalAdharHaving());
                ban_cnt = ban_cnt + Long.valueOf(reportResponse.getAllFieldReportData().get(x).getTotalNoAccounts());
            }


            binding.includedLayout.totalPopTv.setText(String.valueOf(total_pop));
            binding.includedLayout.popPerTv.setText(String.valueOf(popPer));
            binding.includedLayout.totSurTv.setText(String.valueOf(sur_cnt));
            binding.includedLayout.totIdTv.setText(String.valueOf(ids_cnt));
            binding.includedLayout.totCerTv.setText(String.valueOf(cer_cnt));
            binding.includedLayout.totAadTv.setText(String.valueOf(aad_cnt));
            binding.includedLayout.totBankTv.setText(String.valueOf(ban_cnt));

            binding.includedLayout.title.setText(getString(R.string.abstract_total));


            prepareAdapter(reportResponse);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    private AllFieldDistrictReportAdapter OTProjectReportAdapter;

    private void prepareAdapter(AllFieldReportResponse reportResponse) {

        try {
            Set<String> hashSet = new HashSet<>();
            for (int x = 0; x < reportResponse.getAllFieldReportData().size(); x++) {
                hashSet.add(reportResponse.getAllFieldReportData().get(x).getDistrictName());
            }

            if (reportResponse.getAllFieldReportData().size() > 0) {
                ArrayList<AllFieldReportData> projectReportData = new ArrayList<>();
                AllFieldReportData reportData = null;
                Iterator<String> iterator = hashSet.iterator();

                while (iterator.hasNext()) {
                    long total_pop = 0, popPer = 0, sur_cnt = 0, ids_cnt = 0, cer_cnt = 0, aad_cnt = 0, ban_cnt = 0;
                    String projectName = iterator.next();
                    reportData = new AllFieldReportData();

                    for (int x = 0; x < reportResponse.getAllFieldReportData().size(); x++) {

                        if (projectName.equalsIgnoreCase(reportResponse.getAllFieldReportData().get(x).getDistrictName())) {

                            total_pop = total_pop + Long.valueOf(reportResponse.getAllFieldReportData().get(x).getSvMobileRevisedTarget());
                            popPer = popPer + Long.valueOf(reportResponse.getAllFieldReportData().get(x).getSvMobileRevisedTargetPercent());
                            sur_cnt = sur_cnt + Long.valueOf(reportResponse.getAllFieldReportData().get(x).getTotal());
                            ids_cnt = ids_cnt + Long.valueOf(reportResponse.getAllFieldReportData().get(x).getTotalIssuedIdcards());
                            cer_cnt = cer_cnt + Long.valueOf(reportResponse.getAllFieldReportData().get(x).getTotalVendingCertIssued());
                            aad_cnt = aad_cnt + Long.valueOf(reportResponse.getAllFieldReportData().get(x).getTotalAdharHaving());
                            ban_cnt = ban_cnt + Long.valueOf(reportResponse.getAllFieldReportData().get(x).getTotalNoAccounts());
                            reportData.setDistrictName(reportResponse.getAllFieldReportData().get(x).getDistrictName());
                            reportData.setDistrictId(reportResponse.getAllFieldReportData().get(x).getDistrictId());

                        }


                        reportData.setSvMobileRevisedTarget(String.valueOf(total_pop));
                        reportData.setSvMobileRevisedTargetPercent(String.valueOf(popPer));
                        reportData.setTotal(String.valueOf(sur_cnt));
                        reportData.setTotalIssuedIdcards(String.valueOf(ids_cnt));
                        reportData.setTotalVendingCertIssued(String.valueOf(cer_cnt));
                        reportData.setTotalAdharHaving(String.valueOf(aad_cnt));
                        reportData.setTotalNoAccounts(String.valueOf(ban_cnt));

                    }
                    projectReportData.add(reportData);
                }

                if (projectReportData.size() > 0) {

                    sortData(projectReportData);

                    OTProjectReportAdapter = new AllFieldDistrictReportAdapter(projectReportData, getActivity());
                    RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getActivity(), LinearLayoutManager.VERTICAL, false);
                    binding.projectRV.setLayoutManager(mLayoutManager);
                    binding.projectRV.setAdapter(OTProjectReportAdapter);
                }

            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void sortData(ArrayList<AllFieldReportData> projectReportData) {
        Collections.sort(projectReportData, new Comparator<AllFieldReportData>() {
            public int compare(AllFieldReportData lhs, AllFieldReportData rhs) {
                return (lhs.getDistrictName().compareTo(rhs.getDistrictName()));
            }
        });
    }

    private Menu mMenu;
    private SearchView searchView = null;

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        super.onPrepareOptionsMenu(menu);
        inflater.inflate(R.menu.search_menu, menu);
        mMenu = menu;

        mMenu.findItem(R.id.action_search).setVisible(true);

        MenuItem menuItem = mMenu.findItem(R.id.action_search);
        searchView = (SearchView) MenuItemCompat.getActionView(menuItem);

        searchView.setQueryHint("Search by District");
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String s) {
                searchView.clearFocus();
                return false;
            }

            @Override
            public boolean onQueryTextChange(String s) {
                search(searchView);
                return true;
            }
        });

    }

    private void search(SearchView searchView) {
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                try {
                    if (OTProjectReportAdapter != null) {
                        OTProjectReportAdapter.getFilter().filter(newText);
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
                return true;
            }
        });
    }

//    @Override
//    public void setUserVisibleHint(boolean isVisibleToUser) {
//        super.setUserVisibleHint(isVisibleToUser);
//        if (isVisibleToUser) {
//            try {
//                getFragmentManager().beginTransaction().detach(this).attach(this).commit();
//            } catch (Exception e) {
//                e.printStackTrace();
//            }
//        }
//    }

}
