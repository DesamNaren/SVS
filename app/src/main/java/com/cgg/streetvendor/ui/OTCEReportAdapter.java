//package com.cgg.streetvendor.ui;
//
//import android.app.Activity;
//import android.content.Context;
//import android.view.LayoutInflater;
//import android.view.View;
//import android.view.ViewGroup;
//import android.widget.Filter;
//import android.widget.Filterable;
//import android.widget.LinearLayout;
//
//import androidx.annotation.NonNull;
//import androidx.databinding.DataBindingUtil;
//import androidx.recyclerview.widget.LinearLayoutManager;
//import androidx.recyclerview.widget.RecyclerView;
//
//import com.cgg.streetvendor.R;
//import com.cgg.streetvendor.databinding.OtSubItemBinding;
//import com.cgg.streetvendor.source.reposnse.reports.DailyReportData;
//import com.cgg.streetvendor.source.reposnse.reports.DailyReportResponse;
//
//import java.util.ArrayList;
//import java.util.Collections;
//import java.util.Comparator;
//import java.util.HashSet;
//import java.util.Iterator;
//import java.util.Set;
//
//
//public class OTCEReportAdapter extends RecyclerView.Adapter<OTCEReportAdapter.ItemViewHolder> implements Filterable {
//
//
//    private ArrayList<DailyReportData> t_projectReportData;
//    private DailyReportResponse reportResponse;
//    private ArrayList<DailyReportData> mFilteredList;
//    private Context context;
//    ArrayList<DailyReportData> subReportData;
//    Activity activity;
//
//    public OTCEReportAdapter(DailyReportResponse reportResponse, ArrayList<DailyReportData> t_projectReportData, Context context, Activity activity) {
//        this.reportResponse = reportResponse;
//        this.t_projectReportData = t_projectReportData;
//        mFilteredList = t_projectReportData;
//        this.context = context;
//        this.activity = activity;
//    }
//
//    @NonNull
//    @Override
//    public ItemViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
//        OtSubItemBinding listItemBinding = DataBindingUtil.inflate(
//                LayoutInflater.from(viewGroup.getContext()),
//                R.layout.ot_sub_item, viewGroup, false);
//
//        return new ItemViewHolder(listItemBinding);
//    }
//
//    @Override
//    public void onBindViewHolder(@NonNull final ItemViewHolder itemViewHolder, final int position) {
//        try {
//
//            itemViewHolder.listItemBinding.extradetailsRv.setVisibility(View.GONE);
//
//            itemViewHolder.listItemBinding.title.setText(mFilteredList.get(position).getDistrictName());
//            itemViewHolder.listItemBinding.totalCount.setText(mFilteredList.get(position).getSvMobileRevisedTarget());
//            itemViewHolder.listItemBinding.notStCount.setText(mFilteredList.get(position).getSvMobileRevisedTargetPercent());
//
//            itemViewHolder.listItemBinding
//                    .shareIV.setOnClickListener(new View.OnClickListener() {
//                @Override
//                public void onClick(View view) {
////                    LinearLayout abstractView = itemViewHolder.dataLl;
////                    Utilities.takeSCImage(activity, abstractView,
////                            mFilteredList.get(position).getUnitName() + "_Unit Data");
//                }
//            });
//
//
//            itemViewHolder.listItemBinding.title.setOnClickListener(new View.OnClickListener() {
//                @Override
//                public void onClick(View view) {
//                    try {
//
//
//                        if (reportResponse.getDailyReportData().size() > 0) {
//
//                            ArrayList<DailyReportData> projectReportData = new ArrayList<>();
//                            subReportData = new ArrayList<>();
//                            DailyReportData reportData = null;
//
//                            for (int z = 0; z < reportResponse.getDailyReportData().size(); z++) {
//                                if (mFilteredList.get(position).getDistrictName().equals(reportResponse.getDailyReportData()
//                                        .get(z).getDistrictName())) {
//                                    reportData = reportResponse.getDailyReportData().get(z);
//                                    projectReportData.add(reportData);
//                                }
//                            }
//
//
//                            if (projectReportData.size() > 0) {
//
//                                Set<String> hashSet = new HashSet<>();
//                                for (int x = 0; x < projectReportData.size(); x++) {
//                                    hashSet.add(projectReportData.get(x).getDistrictName());
//                                }
//
//                                Iterator<String> iterator = hashSet.iterator();
//
//                                while (iterator.hasNext()) {
//                                    int tanks = 0, tanksTobeFed = 0, tsCnt = 0, techSanOts = 0, tenders = 0, agreements = 0, nomination = 0;
//                                    int notSta = 0, inPro = 0, completed = 0, total = 0;
//                                    int projectId = iterator.next();
//                                    reportData = new ReportData();
//
//
//                                    for (int z = 0; z < projectReportData.size(); z++) {
//                                        if (projectId == projectReportData.get(z).getProjectId()) {
//                                            tanks = tanks + projectReportData.get(z).getTanks();
//                                            tanksTobeFed = tanksTobeFed + projectReportData.get(z).getTanks_to_be_fed();
//                                            tsCnt = tsCnt + projectReportData.get(z).getTechsanctions();
//                                            techSanOts = techSanOts + projectReportData.get(z).getTechsancots();
//                                            tenders = tenders + projectReportData.get(z).getTenders();
//                                            nomination = nomination + projectReportData.get(z).getNominations();
//                                            agreements = agreements + projectReportData.get(z).getAgreements();
//
//                                            notSta = notSta + projectReportData.get(z).getNotStarted();
//                                            inPro = inPro + projectReportData.get(z).getInProgress();
//                                            completed = completed + projectReportData.get(z).getCompleted();
//
//                                            total = total + projectReportData.get(z).getOts();
//                                            reportData.setUnitId(projectReportData.get(z).getUnitId());
//                                            reportData.setUnitName(projectReportData.get(z).getUnitName());
//                                            reportData.setProjectId(projectReportData.get(z).getProjectId());
//                                            reportData.setProjectName(projectReportData.get(z).getProjectName());
//
//                                        }
//
//                                        reportData.setTanks(tanks);
//                                        reportData.setTanks_to_be_fed(tanksTobeFed);
//                                        reportData.setTechsanctions(tsCnt);
//                                        reportData.setTechsancots(techSanOts);
//                                        reportData.setTenders(tenders);
//                                        reportData.setAgreements(agreements);
//                                        reportData.setNotStarted(notSta);
//                                        reportData.setInProgress(inPro);
//                                        reportData.setCompleted(completed);
//                                        reportData.setTotal(total);
//                                        reportData.setNominations(nomination);
//
//                                    }
//
//                                    subReportData.add(reportData);
//
//
//                                    if (subReportData.size() > 0) {
//
//                                        sortData(subReportData);
//
//                                        OTExpandedAdapter dashboardSubAdapter = new OTExpandedAdapter(subReportData, context, activity);
//                                        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false);
//                                        itemViewHolder.extradetailsRv.setLayoutManager(mLayoutManager);
//                                        itemViewHolder.extradetailsRv.setAdapter(dashboardSubAdapter);
//                                    }
//
//                                }
//                            }
//                        }
//
//                        OTExpandedAdapter dashboardSubAdapter = new OTExpandedAdapter(subReportData, context, activity);
//                        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false);
//                        itemViewHolder.extradetailsRv.setLayoutManager(mLayoutManager);
//                        itemViewHolder.extradetailsRv.setAdapter(dashboardSubAdapter);
//
//                        if (itemViewHolder.extradetailsRv.getVisibility() == View.GONE) {
//                            itemViewHolder.extradetailsRv.setVisibility(View.VISIBLE);
//                            itemViewHolder.title.setCompoundDrawablesWithIntrinsicBounds(0, 0, R.drawable.up_arrow, 0);
//                        } else {
//                            itemViewHolder.extradetailsRv.setVisibility(View.GONE);
//                            itemViewHolder.title.setCompoundDrawablesWithIntrinsicBounds(0, 0, R.drawable.down_arrow, 0);
//                        }
//
//                    } catch (Exception e) {
//                        e.printStackTrace();
//                    }
//                }
//            });
//
//        } catch (
//                Exception e) {
//            e.printStackTrace();
//        }
//    }
//
//    private void sortData(ArrayList<DailyReportData> reportData) {
//        Collections.sort(reportData, new Comparator<DailyReportData>() {
//            public int compare(DailyReportData lhs, DailyReportData rhs) {
//                return (lhs.getDistrictName().compareTo(rhs.getDistrictName()));
//            }
//        });
//    }
//
//    @Override
//    public Filter getFilter() {
//        return new Filter() {
//            @Override
//            protected FilterResults performFiltering(CharSequence charSequence) {
//                String charString = charSequence.toString();
//                if (charString.isEmpty()) {
//                    mFilteredList = t_projectReportData;
//                } else {
//                    ArrayList<DailyReportData> filteredList = new ArrayList<>();
//                    for (DailyReportData otData : t_projectReportData) {
//                        if (otData.getDistrictName().toLowerCase().contains(charString.toLowerCase())) {
//                            filteredList.add(otData);
//                        }
//                    }
//                    mFilteredList = filteredList;
//                }
//                FilterResults filterResults = new FilterResults();
//                filterResults.values = mFilteredList;
//                return filterResults;
//            }
//
//            @Override
//            protected void publishResults(CharSequence charSequence, FilterResults filterResults) {
//                mFilteredList = (ArrayList<DailyReportData>) filterResults.values;
//                notifyDataSetChanged();
//                getFilteredData();
//            }
//        };
//    }
//
//    public ArrayList<DailyReportData> getFilteredData() {
//        return mFilteredList;
//    }
//
//    @Override
//    public int getItemCount() {
//        return mFilteredList == null ? 0 : mFilteredList.size();
//    }
//
//
//    class ItemViewHolder extends RecyclerView.ViewHolder {
//        OtSubItemBinding listItemBinding;
//
//        ItemViewHolder(OtSubItemBinding listItemBinding) {
//            super(listItemBinding.getRoot());
//            this.listItemBinding = listItemBinding;
//        }
//    }
//}
