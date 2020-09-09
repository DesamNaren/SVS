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

import java.util.ArrayList;

public class DailyULBReportAdapter extends RecyclerView.Adapter<DailyULBReportAdapter.ItemViewHolder> implements Filterable {


    private ArrayList<DailyReportData> projectReportData;
    private ArrayList<DailyReportData> mFilteredList;
    private Context context;
    private Activity activity;

    public DailyULBReportAdapter(ArrayList<DailyReportData> projectReportData, Context context, Activity activity) {
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
            itemViewHolder.listItemBinding.title.setText(mFilteredList.get(position).getCityName());
            itemViewHolder.listItemBinding.totalCount.setText
                    (String.valueOf(mFilteredList.get(position).getSvMobileRevisedTarget()));
            itemViewHolder.listItemBinding.popperCount.setText
                    (String.valueOf(mFilteredList.get(position).getSvMobileRevisedTargetPercent()));

            itemViewHolder.listItemBinding.shareIV.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
//                    LinearLayout abstractView =itemViewHolder.listItemBinding.dataLl;
//                    Utilities.takeSCImage(activity, abstractView ,
//                            mFilteredList.get(position).getProjectName() + "_Project Data");
                }
            });


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
                        if (otData.getCityName().toLowerCase().contains(charString.toLowerCase())) {
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
    }
}
