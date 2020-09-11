package com.cgg.streetvendor.ui;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.LinearLayout;

import androidx.annotation.NonNull;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.RecyclerView;

import com.cgg.streetvendor.R;
import com.cgg.streetvendor.databinding.DailyDistrictReportDetailsItemBinding;
import com.cgg.streetvendor.source.reposnse.reports.DailyReportData;
import com.cgg.streetvendor.util.Utils;

import java.util.ArrayList;

public class DailyDistrictDetailsReportAdapter extends RecyclerView.Adapter<DailyDistrictDetailsReportAdapter.ItemViewHolder> implements Filterable {


    private ArrayList<DailyReportData> projectReportData;
    private ArrayList<DailyReportData> mFilteredList;
    private Context context;
    private Activity activity;
    private static int currentPosition;


    public DailyDistrictDetailsReportAdapter(ArrayList<DailyReportData> projectReportData, Context context, Activity activity) {
        this.projectReportData = projectReportData;
        mFilteredList = projectReportData;
        this.context = context;
        this.activity = activity;
        currentPosition = 0;
    }

    @NonNull
    @Override
    public ItemViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        DailyDistrictReportDetailsItemBinding listItemBinding = DataBindingUtil.inflate(
                LayoutInflater.from(viewGroup.getContext()),
                R.layout.daily_district_report_details_item, viewGroup, false);

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

            itemViewHolder.listItemBinding.contentLl.setVisibility(View.GONE);

            if (currentPosition == position) {
                //creating an animation
                Animation slideDown = AnimationUtils.loadAnimation(context, R.anim.nav_default_enter_anim);

                //toggling visibility
                itemViewHolder.listItemBinding.contentLl.setVisibility(View.VISIBLE);

                //adding sliding effect
                itemViewHolder.listItemBinding.contentLl.startAnimation(slideDown);
            }

            itemViewHolder.listItemBinding.title.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    //getting the position of the item to expand it
                    currentPosition = position;

                    //reloding the list
                    notifyDataSetChanged();
                }

            });

            itemViewHolder.listItemBinding.shareIV.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    LinearLayout abstractView = itemViewHolder.listItemBinding.dataLl;
                    Utils.takeSCImage(activity, abstractView,
                            mFilteredList.get(position).getDistrictName()
                                    +"_"
                                    +mFilteredList.get(position).getCityName()
                                    + "_Daily Report Complete Data");
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

        DailyDistrictReportDetailsItemBinding listItemBinding;

        ItemViewHolder(DailyDistrictReportDetailsItemBinding listItemBinding) {
            super(listItemBinding.getRoot());
            this.listItemBinding = listItemBinding;
        }

        void bind(Object obj) {
            listItemBinding.executePendingBindings();
        }
    }
}
