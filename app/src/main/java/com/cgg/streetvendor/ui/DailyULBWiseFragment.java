package com.cgg.streetvendor.ui;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.SearchView;

import androidx.core.view.MenuItemCompat;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.cgg.streetvendor.R;
import com.cgg.streetvendor.application.SVSApplication;
import com.cgg.streetvendor.databinding.DailyDistrictWiseFragmentBinding;
import com.cgg.streetvendor.source.reposnse.reports.DailyReportData;
import com.cgg.streetvendor.source.reposnse.reports.DailyReportResponse;
import com.cgg.streetvendor.util.Utils;
import com.google.gson.Gson;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Objects;


/**
 * Created by lenovo on 03-06-2019.
 */

public class DailyULBWiseFragment extends Fragment {

    private SharedPreferences sharedPreferences;
    private DailyReportResponse dailyReportResponse;
    private DailyDistrictWiseFragmentBinding binding;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = DataBindingUtil.inflate(inflater,
                R.layout.daily_district_wise_fragment, container, false);
        View view = binding.getRoot();
        setHasOptionsMenu(true);


        try {
            Gson gson = new Gson();
            sharedPreferences = SVSApplication.get(Objects.requireNonNull(getActivity())).getPreferences();
            String string = sharedPreferences.getString("REPORT_DATA", "");
            dailyReportResponse = gson.fromJson(string, DailyReportResponse.class);
            setProjectData(dailyReportResponse);
        } catch (Exception e) {
            e.printStackTrace();
        }


//        shareIV.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                LinearLayout abstractView = getActivity().getWindow().getDecorView().findViewById(R.id.data_ll);
//                Utilities.takeSCImage(getActivity(), abstractView ,
//                        employeeDetailss.getEmployeeDetail().get(defSelection).getEmpName()
//                                + "( " + employeeDetailss.getEmployeeDetail().get(defSelection).getDesignation() + " )" + "_Project Data");
//            }
//        });

        return view;
    }

    private void setProjectData(DailyReportResponse reportResponse) {
        try {
            long total_pop = 0, popPer = 0, svs_prev_day_total = 0, today_svs_total = 0, cum_total = 0, balance_svs = 0;

            for (int x = 0; x < reportResponse.getDailyReportData().size(); x++) {
                total_pop = total_pop + Long.valueOf(reportResponse.getDailyReportData().get(x).getSvMobileRevisedTarget());
                popPer = popPer + Long.valueOf(reportResponse.getDailyReportData().get(x).getSvMobileRevisedTargetPercent());
                svs_prev_day_total = svs_prev_day_total + Long.valueOf(reportResponse.getDailyReportData().get(x).getPrevdayTotal());
                today_svs_total = today_svs_total + Long.valueOf(reportResponse.getDailyReportData().get(x).getTotal());
                cum_total = cum_total + Long.valueOf(reportResponse.getDailyReportData().get(x).getCummTotal());
                balance_svs = balance_svs + Long.valueOf(reportResponse.getDailyReportData().get(x).getTotalBalance());
            }


            binding.includedLayout.totalPopTv.setText(String.valueOf(total_pop));
            binding.includedLayout.popPerTv.setText(String.valueOf(popPer));
            binding.includedLayout.pervDaySvsTv.setText(String.valueOf(svs_prev_day_total));
            binding.includedLayout.todaySvs.setText(String.valueOf(today_svs_total));
            binding.includedLayout.cuumSvsTv.setText(String.valueOf(cum_total));
            binding.includedLayout.balanceSvsTv.setText(String.valueOf(balance_svs));
            binding.includedLayout.balanceSvsTv.setText(String.valueOf(balance_svs));

            binding.includedLayout.preDayTv.setText(getString(R.string.no_of_svs_prev_day) + Utils.getPreviousDate());
            binding.includedLayout.todayTv.setText(getString(R.string.no_of_svs_prev_day) + Utils.getCurrentDate());
            binding.includedLayout.title.setText(getString(R.string.abstract_total));


            prepareAdapter(reportResponse);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    private DailyULBReportAdapter OTProjectReportAdapter;

    private void prepareAdapter(DailyReportResponse reportResponse) {

        try {
            if (reportResponse.getDailyReportData().size() > 0) {
                ArrayList<DailyReportData> projectReportData = new ArrayList<>();
                DailyReportData reportData = null;


                for (int z = 0; z < reportResponse.getDailyReportData().size(); z++) {
                    long total_pop = 0, popPer = 0, svs_prev_day_total = 0, today_svs_total = 0, cum_total = 0, balance_svs = 0;

                    reportData = new DailyReportData();
                    total_pop = total_pop + Long.valueOf(reportResponse.getDailyReportData().get(z).getSvMobileRevisedTarget());
                    popPer = popPer + Long.valueOf(reportResponse.getDailyReportData().get(z).getSvMobileRevisedTargetPercent());
                    svs_prev_day_total = svs_prev_day_total + Long.valueOf(reportResponse.getDailyReportData().get(z).getPrevdayTotal());
                    today_svs_total = today_svs_total + Long.valueOf(reportResponse.getDailyReportData().get(z).getTotal());
                    cum_total = cum_total + Long.valueOf(reportResponse.getDailyReportData().get(z).getCummTotal());
                    balance_svs = balance_svs + Long.valueOf(reportResponse.getDailyReportData().get(z).getTotalBalance());
                    reportData.setCityName(reportResponse.getDailyReportData().get(z).getCityName());


                    reportData.setSvMobileRevisedTarget(String.valueOf(total_pop));
                    reportData.setSvMobileRevisedTargetPercent(String.valueOf(popPer));
                    reportData.setPrevdayTotal(String.valueOf(svs_prev_day_total));
                    reportData.setTotal(String.valueOf(today_svs_total));
                    reportData.setCummTotal(String.valueOf(cum_total));
                    reportData.setTotalBalance(String.valueOf(balance_svs));

                    projectReportData.add(reportData);
                }


                if (projectReportData.size() > 0) {

                    sortData(projectReportData);

                    OTProjectReportAdapter = new DailyULBReportAdapter(projectReportData, getActivity(), getActivity());
                    RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getActivity(), LinearLayoutManager.VERTICAL, false);
                    binding.projectRV.setLayoutManager(mLayoutManager);
                    binding.projectRV.setAdapter(OTProjectReportAdapter);
                }

            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void sortData(ArrayList<DailyReportData> projectReportData) {
        Collections.sort(projectReportData, new Comparator<DailyReportData>() {
            public int compare(DailyReportData lhs, DailyReportData rhs) {
                return (lhs.getCityName().compareTo(rhs.getCityName()));
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

        searchView.setQueryHint("Search by ULB");
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
