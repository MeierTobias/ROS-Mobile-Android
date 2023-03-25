package com.schneewittchen.rosandroid.ui.views.widgets;

import android.content.Context;
import android.util.AttributeSet;

import androidx.annotation.Nullable;

public abstract class ServiceWidgetView extends WidgetView implements IServiceView {

    public static String TAG = ServiceWidgetView.class.getSimpleName();

    public ServiceWidgetView(Context context) {
        super(context);
    }

    public ServiceWidgetView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }

}
