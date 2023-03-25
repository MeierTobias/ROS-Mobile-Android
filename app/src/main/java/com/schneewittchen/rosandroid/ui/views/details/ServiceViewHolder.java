package com.schneewittchen.rosandroid.ui.views.details;

import android.view.KeyEvent;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.widget.TextView;

import com.google.android.material.textfield.TextInputEditText;
import com.schneewittchen.rosandroid.R;
import com.schneewittchen.rosandroid.model.entities.widgets.BaseEntity;
import com.schneewittchen.rosandroid.utility.Utils;
import com.schneewittchen.rosandroid.viewmodel.DetailsViewModel;

import java.util.List;

public class ServiceViewHolder implements IBaseViewHolder, TextView.OnEditorActionListener {

    public static final String TAG = ServiceViewHolder.class.getSimpleName();
    private final DetailViewHolder parentViewHolder;
    public DetailsViewModel viewModel;


    public ServiceViewHolder(DetailViewHolder parentViewHolder) {
        this.parentViewHolder = parentViewHolder;
    }


    @Override
    public void baseInitView(View widgetView) {
    }

    @Override
    public void baseBindEntity(BaseEntity entity) {
        String topicName = entity.topic.name;
        String messageType = entity.topic.type;
    }

    @Override
    public void baseUpdateEntity(BaseEntity entity) {
    }


    @Override
    public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
        switch (actionId) {
            case EditorInfo.IME_ACTION_DONE:
            case EditorInfo.IME_ACTION_NEXT:
            case EditorInfo.IME_ACTION_PREVIOUS:
                Utils.hideSoftKeyboard(v);
                v.clearFocus();
                parentViewHolder.forceWidgetUpdate();
                return true;
        }

        return false;
    }
}
