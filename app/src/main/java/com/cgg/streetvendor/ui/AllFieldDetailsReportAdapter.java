package com.cgg.streetvendor.ui;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
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
import com.cgg.streetvendor.databinding.AllFieldReportDetailsItemBinding;
import com.cgg.streetvendor.source.reposnse.reports.AllFieldReportData;
import com.cgg.streetvendor.util.Utils;

import java.util.ArrayList;

public class AllFieldDetailsReportAdapter extends RecyclerView.Adapter<AllFieldDetailsReportAdapter.ItemViewHolder> implements Filterable {


    private ArrayList<AllFieldReportData> projectReportData;
    private ArrayList<AllFieldReportData> mFilteredList;
    private Context context;
    private Activity activity;
    private static int currentPosition;


    public AllFieldDetailsReportAdapter(ArrayList<AllFieldReportData> projectReportData, Context context, Activity activity) {
        this.projectReportData = projectReportData;
        mFilteredList = projectReportData;
        this.context = context;
        this.activity = activity;
        currentPosition = 0;
    }

    @NonNull
    @Override
    public ItemViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        AllFieldReportDetailsItemBinding listItemBinding = DataBindingUtil.inflate(
                LayoutInflater.from(viewGroup.getContext()),
                R.layout.all_field_report_details_item, viewGroup, false);

        return new ItemViewHolder(listItemBinding);

    }

    @Override
    public void onBindViewHolder(@NonNull final ItemViewHolder itemViewHolder, final int position) {
        try {

            AllFieldReportData dailyReportData = mFilteredList.get(position);
            itemViewHolder.listItemBinding.setAllReportData(dailyReportData);

            itemViewHolder.bind(dailyReportData);

            itemViewHolder.listItemBinding.dataLl.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(context, AllFieldReportDetailsActivity.class);
                    intent.putExtra("DAILY_REPORT_DISTRICT",
                            mFilteredList.get(position).getDistrictName());
                    intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);
                    context.startActivity(intent);
                }
            });

            itemViewHolder.listItemBinding.shareIV.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    LinearLayout abstractView = itemViewHolder.listItemBinding.dataLl;
                    Utils.takeSCImage(activity, abstractView,
                            mFilteredList.get(position).getDistrictName()
                                    +"_"
                                    + mFilteredList.get(position).getCityName()
                                    + "_All Field Report Complete Data");
                }
            });

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
                    ArrayList<AllFieldReportData> filteredList = new ArrayList<>();
                    for (AllFieldReportData otData : projectReportData) {
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

        AllFieldReportDetailsItemBinding listItemBinding;

        ItemViewHolder(AllFieldReportDetailsItemBinding listItemBinding) {
            super(listItemBinding.getRoot());
            this.listItemBinding = listItemBinding;
        }

        void bind(Object obj) {
            listItemBinding.executePendingBindings();
        }
    }
}
