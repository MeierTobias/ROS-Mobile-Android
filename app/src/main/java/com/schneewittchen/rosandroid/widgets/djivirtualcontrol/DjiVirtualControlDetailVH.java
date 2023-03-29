package com.schneewittchen.rosandroid.widgets.djivirtualcontrol;

import android.view.View;

import com.schneewittchen.rosandroid.model.entities.widgets.BaseEntity;
import com.schneewittchen.rosandroid.ui.views.details.SubscriberWidgetViewHolder;

import java.util.Collections;
import java.util.List;

public class DjiVirtualControlDetailVH extends SubscriberWidgetViewHolder {
    @Override
    protected void initView(View itemView) {

    }

    @Override
    protected void bindEntity(BaseEntity entity) {

    }

    @Override
    protected void updateEntity(BaseEntity entity) {

    }

    @Override
    public List<String> getTopicTypes() {
        return Collections.singletonList(geometry_msgs.Twist._TYPE);
    }
}
