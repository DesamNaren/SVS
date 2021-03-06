package com.cgg.streetvendor.adapter;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.LinearLayout;

import androidx.annotation.NonNull;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.RecyclerView;

import com.cgg.streetvendor.R;
import com.cgg.streetvendor.databinding.DailyUlbReportItemBinding;
import com.cgg.streetvendor.source.reposnse.reports.DailyReportData;
import com.cgg.streetvendor.ui.view.DailyReportDetailsActivity;
import com.cgg.streetvendor.util.Utils;

import java.util.ArrayList;

public class DailyULBReportAdapter extends RecyclerView.Adapter<DailyULBReportAdapter.ItemViewHolder> implements Filterable {


    private ArrayList<DailyReportData> projectReportData;
    private ArrayList<DailyReportData> mFilteredList;
    private Context context;

    public DailyULBReportAdapter(ArrayList<DailyReportData> projectReportData, Context context) {
        this.projectReportData = projectReportData;
        mFilteredList = projectReportData;
        this.context = context;
    }

    @NonNull
    @Override
    public ItemViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        DailyUlbReportItemBinding listItemBinding = DataBindingUtil.inflate(
                LayoutInflater.from(viewGroup.getContext()),
                R.layout.daily_ulb_report_item, viewGroup, false);

        return new ItemViewHolder(listItemBinding);

    }

    @Override
    public void onBindViewHolder(@NonNull final ItemViewHolder itemViewHolder, final int position) {
        try {
            DailyReportData dailyReportData = mFilteredList.get(position);
            itemViewHolder.listItemBinding.preDayTv.setText(context.getString(R.string.no_of_svs_prev_day) + Utils.getPreviousDate());
            itemViewHolder.listItemBinding.todayTv.setText(context.getString(R.string.no_of_svs_today) + Utils.getCurrentDate());

            itemViewHolder.listItemBinding.setDailyReportData(dailyReportData);

            itemViewHolder.bind(dailyReportData);

            itemViewHolder.listItemBinding.shareIV.setVisibility(View.GONE);
            itemViewHolder.listItemBinding.shareIV.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    LinearLayout abstractView = itemViewHolder.listItemBinding.dataLl;
                    Utils.takeSCImage(context, abstractView,
                            mFilteredList.get(position).getDistrictName() + "_" +
                                    mFilteredList.get(position).getCityName() + "_Daily Report ULB Data");
                }
            });

            itemViewHolder.listItemBinding.absrtractLl.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(context, DailyReportDetailsActivity.class);
                    intent.putExtra("DAILY_REPORT_DISTRICT",
                            mFilteredList.get(position).getDistrictName());
                    intent.putExtra("DAILY_REPORT_ULB",
                            mFilteredList.get(position).getCityName());
                    intent.putExtra("DAILY_REPORT_DISTRICT_ID",
                            mFilteredList.get(position).getDistrictId());
                    intent.putExtra("DAILY_REPORT_ULB_ID",
                            mFilteredList.get(position).getCityId());
                    intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);
                    context.startActivity(intent);
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

        DailyUlbReportItemBinding listItemBinding;

        ItemViewHolder(DailyUlbReportItemBinding listItemBinding) {
            super(listItemBinding.getRoot());
            this.listItemBinding = listItemBinding;
        }

        void bind(Object obj) {
            listItemBinding.executePendingBindings();
        }
    }
}
