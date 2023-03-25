package com.schneewittchen.rosandroid.widgets.djiflightservice;

import android.view.View;
import android.widget.EditText;

import com.schneewittchen.rosandroid.R;
import com.schneewittchen.rosandroid.model.entities.widgets.BaseEntity;
import com.schneewittchen.rosandroid.ui.views.details.ServiceWidgetViewHolder;

public class DjiFlightServiceDetailVH extends ServiceWidgetViewHolder {
    public static final String TAG = DjiFlightServiceDetailVH.class.getSimpleName();

    private EditText labelTextText;

    @Override
    protected void initView(View view) {
        labelTextText = view.findViewById(R.id.labelText);
    }

    @Override
    protected void bindEntity(BaseEntity entity) {
    }

    @Override
    protected void updateEntity(BaseEntity entity) {
    }
}
