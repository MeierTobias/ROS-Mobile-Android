package com.schneewittchen.rosandroid.widgets.lockorientation;

import android.view.View;

import com.schneewittchen.rosandroid.model.entities.widgets.BaseEntity;
import com.schneewittchen.rosandroid.ui.views.details.SilentWidgetViewHolder;

public class LockOrientationDetailVH extends SilentWidgetViewHolder {


    @Override
    public void initView(View view) {
    }

    @Override
    protected void bindEntity(BaseEntity entity) {

    }

    @Override
    protected void updateEntity(BaseEntity entity) {
        LockOrientationEntity switchEntity = (LockOrientationEntity) entity;
    }
}
