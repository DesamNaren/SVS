//package com.cgg.streetvendor.ui;
//
//import android.content.SharedPreferences;
//import android.content.res.Resources;
//import android.os.Bundle;
//import android.view.LayoutInflater;
//import android.view.Menu;
//import android.view.MenuInflater;
//import android.view.MenuItem;
//import android.view.View;
//import android.view.ViewGroup;
//import android.widget.LinearLayout;
//import android.widget.SearchView;
//
//import androidx.core.view.MenuItemCompat;
//import androidx.databinding.DataBindingUtil;
//import androidx.fragment.app.Fragment;
//import androidx.recyclerview.widget.LinearLayoutManager;
//import androidx.recyclerview.widget.RecyclerView;
//
//import com.cgg.streetvendor.R;
//import com.cgg.streetvendor.databinding.DailyDistrictWiseFragmentBinding;
//import com.cgg.streetvendor.source.reposnse.places.DistrictResponse;
//import com.cgg.streetvendor.source.reposnse.places.StateResponse;
//import com.cgg.streetvendor.source.reposnse.reports.DailyReportData;
//import com.cgg.streetvendor.source.reposnse.reports.DailyReportResponse;
//import com.cgg.streetvendor.util.Utils;
//import com.google.gson.Gson;
//
//import java.util.ArrayList;
//import java.util.Collections;
//import java.util.Comparator;
//import java.util.HashSet;
//import java.util.Iterator;
//import java.util.Set;
//
//import static android.content.Context.MODE_PRIVATE;
//
//
///**
// * Created by lenovo on 03-06-2019.
// */
//
//public class ReportDWiseFragment extends Fragment {
//
//    SharedPreferences sharedPreferences;
//    DailyReportResponse reportResponse;
//    private DailyDistrictWiseFragmentBinding binding;
//
//    @Override
//    public View onCreateView(LayoutInflater inflater, ViewGroup container,
//                             Bundle savedInstanceState) {
//        binding = DataBindingUtil.inflate(inflater,
//                R.layout.daily_district_wise_fragment, container, false);
//        View view = binding.getRoot();
//        setHasOptionsMenu(true);
//
//        try {
//            Gson gson = new Gson();
//            sharedPreferences = getActivity().getSharedPreferences("APP_PREF", MODE_PRIVATE);
//            String string = sharedPreferences.getString("REPORT_DATA", "");
//            reportResponse = gson.fromJson(string, DailyReportResponse.class);
//
//            setCEData(reportResponse);
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
//
//
//        shareIV.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                LinearLayout abstractView = getActivity().getWindow().getDecorView().findViewById(R.id.data_ll);
//                Utilities.takeSCImage(getActivity(), abstractView,
//                        employeeDetailss.getEmployeeDetail().get(defSelection).getEmpName()
//                                + "( " + employeeDetailss.getEmployeeDetail().get(defSelection).getDesignation() + " )" + "_Unit Data");
//            }
//        });
//
//
//        return view;
//    }
//
//
//    private void setCEData(DailyReportResponse reportResponse) {
//        try {
//            if (reportResponse != null) {
//                if (reportResponse.getStatusCode().equalsIgnoreCase("200")
//                        && reportResponse.getDailyReportData() != null &&
//                        reportResponse.getDailyReportData().size() > 0) {
//
//                    long totalPop = 0, popPer = 0;
//
//                    for (int x = 0; x < reportResponse.getDailyReportData().size(); x++) {
//                        totalPop = totalPop + Long.valueOf(reportResponse.getDailyReportData().get(x).getSvMobileRevisedTarget());
//                        popPer = popPer + Long.valueOf(reportResponse.getDailyReportData().get(x).getSvMobileRevisedTargetPercent());
//                    }
//
//                    binding.includedLayout.totalCount.setText(String.valueOf(totalPop));
//                    binding.includedLayout.popperCount.setText(String.valueOf(popPer));
//
//                    prepareAdapter(reportResponse);
//
//
//                } else {
//                    Utils.customErrorAlert(getActivity(), getResources().getString(R.string.app_name), getResources().getString(R.string.server_not));
//                }
//            } else {
//                Utils.customErrorAlert(getActivity(), getResources().getString(R.string.app_name),
//                        getResources().getString(R.string.server_not));
//            }
//        } catch (Resources.NotFoundException e) {
//            e.printStackTrace();
//        }
//    }
//
//    OTCEReportAdapter OTCEReportAdapter;
//
//    private void prepareAdapter(DailyReportResponse reportResponse) {
//
//        try {
//            Set<String> hashSet = new HashSet<>();
//            for (int x = 0; x < reportResponse.getDailyReportData().size(); x++) {
//                hashSet.add(reportResponse.getDailyReportData().get(x).getDistrictName());
//            }
//
//            if (reportResponse.getDailyReportData().size() > 0) {
//                ArrayList<DailyReportData> projectReportData = new ArrayList<>();
//                Iterator<String> iterator = hashSet.iterator();
//
//                while (iterator.hasNext()) {
//                    long totalPop = 0, popPer = 0;
//                    String distName = iterator.next();
//                    DailyReportData reportData = new DailyReportData();
//                    for (int z = 0; z < reportResponse.getDailyReportData().size(); z++) {
//
//                        if (distName == reportResponse.getDailyReportData().get(z).getDistrictName()) {
//
//                            totalPop = totalPop + Long.valueOf(reportResponse.getDailyReportData().get(z).getSvMobileRevisedTarget());
//                            popPer = popPer + Long.valueOf(reportResponse.getDailyReportData().get(z).getSvMobileRevisedTargetPercent());
//
//                            reportData.setDistrictName(reportResponse.getDailyReportData().get(z).getDistrictName());
//                            reportData.setCityName(reportResponse.getDailyReportData().get(z).getCityName());
//                        }
//
//                        reportData.setSvMobileRevisedTarget(String.valueOf(totalPop));
//                        reportData.setSvMobileRevisedTargetPercent(String.valueOf(popPer));
//
//                    }
//                    projectReportData.add(reportData);
//                }
//
//                if (projectReportData.size() > 0) {
//                    sortData(projectReportData);
//                    OTCEReportAdapter = new OTCEReportAdapter(reportResponse, projectReportData, getActivity(), getActivity());
//                    RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getActivity(), LinearLayoutManager.VERTICAL, false);
//                    binding.projectRV.setLayoutManager(mLayoutManager);
//                    binding.projectRV.setAdapter(OTCEReportAdapter);
//                }
//
//            }
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
//
//
//    }
//
//    private Menu mMenu;
//    private SearchView searchView = null;
//
//    @Override
//    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
//        super.onPrepareOptionsMenu(menu);
//        inflater.inflate(R.menu.search_menu, menu);
//        mMenu = menu;
//
//        mMenu.findItem(R.id.action_view).setVisible(false);
//        mMenu.findItem(R.id.action_logout).setVisible(false);
//        mMenu.findItem(R.id.action_search).setVisible(true);
//
//        MenuItem menuItem = mMenu.findItem(R.id.action_search);
//        searchView = (SearchView) MenuItemCompat.getActionView(menuItem);
//
//        searchView.setQueryHint("Search by project");
//        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
//            @Override
//            public boolean onQueryTextSubmit(String s) {
//                searchView.clearFocus();
//                return false;
//            }
//
//            @Override
//            public boolean onQueryTextChange(String s) {
//                search(searchView);
//                return true;
//            }
//        });
//
//    }
//
//    private void search(SearchView searchView) {
//        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
//            @Override
//            public boolean onQueryTextSubmit(String query) {
//                return false;
//            }
//
//            @Override
//            public boolean onQueryTextChange(String newText) {
//                try {
//                    if (OTCEReportAdapter != null) {
//                        OTCEReportAdapter.getFilter().filter(newText);
//                    }
//                } catch (Exception e) {
//                    e.printStackTrace();
//                }
//                return true;
//            }
//        });
//    }
//
//    private void sortData(ArrayList<DailyReportData> projectReportData) {
//        Collections.sort(projectReportData, new Comparator<DailyReportData>() {
//            public int compare(DailyReportData lhs, DailyReportData rhs) {
//                return (lhs.getDistrictName().compareTo(rhs.getDistrictName()));
//            }
//        });
//    }
//
//
//}
