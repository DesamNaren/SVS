package com.cgg.streetvendor.ui;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Filter;
import android.widget.Filterable;

import androidx.annotation.NonNull;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.RecyclerView;

import com.cgg.streetvendor.R;
import com.cgg.streetvendor.databinding.DailyDistrictReportItemBinding;
import com.cgg.streetvendor.source.reposnse.reports.DailyReportData;
import com.cgg.streetvendor.util.Utils;

import java.util.ArrayList;

public class DailyDistrictReportAdapter extends RecyclerView.Adapter<DailyDistrictReportAdapter.ItemViewHolder> implements Filterable {


    private ArrayList<DailyReportData> projectReportData;
    private ArrayList<DailyReportData> mFilteredList;
    private Context context;
    private Activity activity;

    public DailyDistrictReportAdapter(ArrayList<DailyReportData> projectReportData, Context context, Activity activity) {
        this.projectReportData = projectReportData;
        mFilteredList = projectReportData;
        this.context = context;
        this.activity = activity;
    }

    @NonNull
    @Override
    public ItemViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        DailyDistrictReportItemBinding listItemBinding = DataBindingUtil.inflate(
                LayoutInflater.from(viewGroup.getContext()),
                R.layout.daily_district_report_item, viewGroup, false);

        return new ItemViewHolder(listItemBinding);

    }

    @Override
    public void onBindViewHolder(@NonNull final ItemViewHolder itemViewHolder, final int position) {
        try {

            DailyReportData dailyReportData = mFilteredList.get(position);
            itemViewHolder.listItemBinding.preDayTv.setText(context.getString(R.string.no_of_svs_prev_day) + Utils.getPreviousDate());
            itemViewHolder.listItemBinding.todayTv.setText(context.getString(R.string.no_of_svs_prev_day) + Utils.getCurrentDate());
            itemViewHolder.listItemBinding.setDailyReportData(dailyReportData);

            itemViewHolder.bind(dailyReportData);

//            itemViewHolder.listItemBinding.shareIV.setOnClickListener(new View.OnClickListener() {
//                @Override
//                public void onClick(View view) {
////                    LinearLayout abstractView =itemViewHolder.listItemBinding.dataLl;
////                    Utilities.takeSCImage(activity, abstractView ,
////                            mFilteredList.get(position).getProjectName() + "_Project Data");
//                }
//            });


            itemViewHolder.listItemBinding.dataLl.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    // new Activity by passing DistName
                }
            });


        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    @Override
    public Filter getFilter() {
        return new Filter() {
            @Override
            protected FilterResults performFiltering(CharSequence charSequence) {
                String charString = charSequence.toString();
                if (charString.isEmpty()) {
                    mFilteredList = projectReportData;
                } else {
                    ArrayList<DailyReportData> filteredList = new ArrayList<>();
                    for (DailyReportData otData : projectReportData) {
                        if (otData.getDistrictName().toLowerCase().contains(charString.toLowerCase())) {
                            filteredList.add(otData);
                        }
                    }
                    mFilteredList = filteredList;
                }
                FilterResults filterResults = new FilterResults();
                filterResults.values = mFilteredList;
                return filterResults;
            }

            @Override
            protected void publishResults(CharSequence charSequence, FilterResults filterResults) {
                mFilteredList = (ArrayList<DailyReportData>) filterResults.values;
                notifyDataSetChanged();
                getFilteredData();
            }
        };
    }

    public ArrayList<DailyReportData> getFilteredData() {
        return mFilteredList;
    }

    @Override
    public int getItemCount() {
        return mFilteredList == null ? 0 : mFilteredList.size();
    }


    class ItemViewHolder extends RecyclerView.ViewHolder {

        DailyDistrictReportItemBinding listItemBinding;

        ItemViewHolder(DailyDistrictReportItemBinding listItemBinding) {
            super(listItemBinding.getRoot());
            this.listItemBinding = listItemBinding;
        }

        void bind(Object obj) {
            listItemBinding.executePendingBindings();
        }
    }
}
