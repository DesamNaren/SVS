package com.cgg.streetvendor.ui;

import android.content.Context;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.RecyclerView;

import com.cgg.streetvendor.R;
import com.cgg.streetvendor.databinding.ItemMemberBinding;
import com.cgg.streetvendor.interfaces.MemberInterface;

import java.util.List;


public class MemberAdapter extends RecyclerView.Adapter<MemberAdapter.ItemHolder> {
    private Context context;
    private List<FamilyInfo> list;
    private MemberInterface memberInterface;

    MemberAdapter(Context context, List<FamilyInfo> list) {
        this.context = context;
        this.list = list;
        memberInterface = (MemberInterface) context;
    }

    @NonNull
    @Override
    public ItemHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        ItemMemberBinding listItemBinding = DataBindingUtil.inflate(
                LayoutInflater.from(parent.getContext()),
                R.layout.item_member, parent, false);

        return new ItemHolder(listItemBinding);
    }

    @Override
    public void onBindViewHolder(@NonNull final ItemHolder holder, final int i) {
        final FamilyInfo dataModel = list.get(i);
        holder.listItemBinding.setMemData(dataModel);

//        if (!TextUtils.isEmpty(dataModel.getFgender())) {
//            if (dataModel.getFgender().equals("Male")) {
//                holder.listItemBinding.ivMale.setColorFilter(context.getResources().getColor(android.R.color.holo_blue_dark));
//                holder.listItemBinding.ivFemale.setColorFilter(context.getResources().getColor(android.R.color.darker_gray));
//            } else {
//                holder.listItemBinding.ivMale.setColorFilter(context.getResources().getColor(android.R.color.darker_gray));
//                holder.listItemBinding.ivFemale.setColorFilter(context.getResources().getColor(android.R.color.holo_blue_dark));
//            }
//        }else{
//            holder.listItemBinding.ivMale.setColorFilter(context.getResources().getColor(android.R.color.darker_gray));
//            holder.listItemBinding.ivFemale.setColorFilter(context.getResources().getColor(android.R.color.darker_gray));
//        }
        holder.bind(dataModel);

        holder.listItemBinding.btnEditMember.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                memberInterface.editMember(dataModel, i);
            }
        });holder.listItemBinding.btnDeleteMember.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                memberInterface.removeMember(i);
            }
        });
    }

    @Override
    public int getItemCount() {
        return list != null && list.size() > 0 ? list.size() : 0;
    }

    class ItemHolder extends RecyclerView.ViewHolder {


        ItemMemberBinding listItemBinding;

        ItemHolder(ItemMemberBinding listItemBinding) {
            super(listItemBinding.getRoot());
            this.listItemBinding = listItemBinding;
        }

        void bind(Object obj) {
            listItemBinding.executePendingBindings();
        }

    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public int getItemViewType(int position) {
        return position;
    }
}
