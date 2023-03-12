package com.schneewittchen.rosandroid.widgets.djicamera;

import android.view.View;
import android.widget.EditText;

import com.schneewittchen.rosandroid.model.entities.widgets.BaseEntity;
import com.schneewittchen.rosandroid.ui.views.details.PublisherWidgetViewHolder;

import java.util.Arrays;
import java.util.List;

import sensor_msgs.CompressedImage;
import sensor_msgs.Image;

public class DjiCameraDetailVH extends PublisherWidgetViewHolder {

    //public static final String TAG = DjiCameraDetailVH.class.getSimpleName();

    @Override
    protected void initView(View parentView) {

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
