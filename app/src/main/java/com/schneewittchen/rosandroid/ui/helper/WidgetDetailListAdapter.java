package com.schneewittchen.rosandroid.ui.helper;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.AsyncListDiffer;
import androidx.recyclerview.widget.DiffUtil;
import androidx.recyclerview.widget.RecyclerView;

import com.schneewittchen.rosandroid.R;
import com.schneewittchen.rosandroid.widgets.base.BaseDetailViewHolder;
import com.schneewittchen.rosandroid.widgets.base.BaseEntity;
import com.schneewittchen.rosandroid.widgets.base.DetailListener;

import java.lang.reflect.Constructor;
import java.util.List;


/**
 * TODO: Description
 *
 * @author Nico Studt
 * @version 1.0.3
 * @created on 24.01.20
 * @updated on 02.04.20
 * @modified by
 */
public class WidgetDetailListAdapter extends RecyclerView.Adapter<BaseDetailViewHolder> implements DetailListener{

    private DetailListener detailListener;
    private AsyncListDiffer<BaseEntity> mDiffer;


    public WidgetDetailListAdapter() {
        mDiffer = new AsyncListDiffer<>(this, diffCallback);
    }


    @NonNull
    @Override
    public BaseDetailViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int position) {
        BaseEntity entity = this.getItem(position);
        Class<? extends BaseDetailViewHolder> viewHolderClazz = entity.getDetailViewHolderType();
        
        try {
            Constructor<? extends BaseDetailViewHolder> cons  = viewHolderClazz
                                                    .getConstructor(View.class, DetailListener.class);
            LayoutInflater inflator = LayoutInflater.from(parent.getContext());
            View itemView = inflator.inflate(R.layout.widget_detail_base, parent, false);

            return cons.newInstance(itemView, detailListener);

        } catch (Exception e) {
            e.printStackTrace();
        }

        return null;
    }

    @Override
    public void onBindViewHolder(@NonNull BaseDetailViewHolder holder, int position) {
        BaseEntity entity = this.getItem(position);

        LayoutInflater inflator = LayoutInflater.from(holder.itemView.getContext());
        holder.detailContend.removeView(holder.detailContend.getChildAt(1));

        int detailContentLayout = entity.getWidgetDetailViewId();
        inflator.inflate(detailContentLayout, holder.detailContend, true);

        holder.update(entity);
    }

    @Override
    public int getItemViewType(int position) {
        return position;
    }

    @Override
    public int getItemCount() {
        return mDiffer.getCurrentList().size();
    }


    public BaseEntity getItem(int position) {
        return mDiffer.getCurrentList().get(position);
    }

    public void setWidgets(List<BaseEntity> newWidgets){
        mDiffer.submitList(newWidgets);
    }

    public void setChangeListener(DetailListener detailListener) {
        this.detailListener = detailListener;
    }


    @Override
    public void onDetailsChanged(BaseEntity widgetId) {
        if(detailListener != null) {
            this.detailListener.onDetailsChanged(widgetId);
        }
    }


    private DiffUtil.ItemCallback<BaseEntity> diffCallback = new DiffUtil.ItemCallback<BaseEntity>() {
        @Override
        public boolean areItemsTheSame(BaseEntity oldItem, BaseEntity newItem) {
            return oldItem.id == newItem.id;
        }

        @Override
        public boolean areContentsTheSame(BaseEntity oldItem, BaseEntity newItem) {
            return oldItem.equals(newItem) && oldItem.equalContent(newItem);
        }
    };
}