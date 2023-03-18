package com.schneewittchen.rosandroid.widgets.djinavsat;

import android.view.View;
import android.widget.EditText;

import com.schneewittchen.rosandroid.R;
import com.schneewittchen.rosandroid.model.entities.widgets.BaseEntity;
import com.schneewittchen.rosandroid.ui.views.details.PublisherWidgetViewHolder;

import java.util.Collections;
import java.util.List;

public class DjiNavSatDetailVH extends PublisherWidgetViewHolder {
    public static final String TAG = DjiNavSatDetailVH.class.getSimpleName();

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
        return Collections.singletonList(sensor_msgs.NavSatFix._TYPE);
    }
}
