package com.schneewittchen.rosandroid.widgets.djiactivation;

import android.view.View;
import android.widget.EditText;

import com.schneewittchen.rosandroid.R;
import com.schneewittchen.rosandroid.model.entities.widgets.BaseEntity;
import com.schneewittchen.rosandroid.ui.views.details.PublisherWidgetViewHolder;

import java.util.Arrays;
import java.util.List;

import sensor_msgs.CompressedImage;
import sensor_msgs.Image;

public class DjiActivationDetailVH extends PublisherWidgetViewHolder {
    public static final String TAG = DjiActivationDetailVH.class.getSimpleName();

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

    @Override
    public List<String> getTopicTypes() {
        return Arrays.asList(Image._TYPE, CompressedImage._TYPE);
    }
}
