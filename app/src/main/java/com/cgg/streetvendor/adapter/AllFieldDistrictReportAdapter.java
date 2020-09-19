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
import com.cgg.streetvendor.databinding.AllFieldDistrictReportItemBinding;
import com.cgg.streetvendor.source.reposnse.reports.AllFieldReportData;
import com.cgg.streetvendor.ui.view.AllFieldReportDetailsActivity;
import com.cgg.streetvendor.util.Utils;

import java.util.ArrayList;

public class AllFieldDistrictReportAdapter extends RecyclerView.Adapter<AllFieldDistrictReportAdapter.ItemViewHolder> implements Filterable {


    private ArrayList<AllFieldReportData> projectReportData;
    private ArrayList<AllFieldReportData> mFilteredList;
    private Context context;

    public AllFieldDistrictReportAdapter(ArrayList<AllFieldReportData> projectReportData, Context context) {
        this.projectReportData = projectReportData;
        mFilteredList = projectReportData;
        this.context = context;
    }

    @NonNull
    @Override
    public ItemViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        AllFieldDistrictReportItemBinding listItemBinding = DataBindingUtil.inflate(
                LayoutInflater.from(viewGroup.getContext()),
                R.layout.all_field_district_report_item, viewGroup, false);

        return new ItemViewHolder(listItemBinding);

    }

    @Override
    public void onBindViewHolder(@NonNull final ItemViewHolder itemViewHolder, final int position) {
        try {

            AllFieldReportData dailyReportData = mFilteredList.get(position);

            itemViewHolder.listItemBinding.setAllFieldData(dailyReportData);

            itemViewHolder.bind(dailyReportData);

            itemViewHolder.listItemBinding.absrtractLl.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(context, AllFieldReportDetailsActivity.class);
                    intent.putExtra("DAILY_REPORT_DISTRICT",
                            mFilteredList.get(position).getDistrictName());
                    intent.putExtra("DAILY_REPORT_ULB", "");
                    intent.putExtra("DAILY_REPORT_DISTRICT_ID",
                            mFilteredList.get(position).getDistrictId());
                    intent.putExtra("DAILY_REPORT_ULB_ID", "");
                    intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);
                    context.startActivity(intent);

                }
            });
            itemViewHolder.listItemBinding.shareIV.setVisibility(View.GONE);
            itemViewHolder.listItemBinding.shareIV.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    LinearLayout abstractView =itemViewHolder.listItemBinding.dataLl;
                    Utils.takeSCImage(context, abstractView ,
                            mFilteredList.get(position).getDistrictName() + "_All Fields Report District Data");
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
                    ArrayList<AllFieldReportData> filteredList = new ArrayList<>();
                    for (AllFieldReportData otData : projectReportData) {
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
                mFilteredList = (ArrayList<AllFieldReportData>) filterResults.values;
                notifyDataSetChanged();
                getFilteredData();
            }
        };
    }

    public ArrayList<AllFieldReportData> getFilteredData() {
        return mFilteredList;
    }

    @Override
    public int getItemCount() {
        return mFilteredList == null ? 0 : mFilteredList.size();
    }


    class ItemViewHolder extends RecyclerView.ViewHolder {

        AllFieldDistrictReportItemBinding listItemBinding;

        ItemViewHolder(AllFieldDistrictReportItemBinding listItemBinding) {
            super(listItemBinding.getRoot());
            this.listItemBinding = listItemBinding;
        }

        void bind(Object obj) {
            listItemBinding.executePendingBindings();
        }
    }
}
