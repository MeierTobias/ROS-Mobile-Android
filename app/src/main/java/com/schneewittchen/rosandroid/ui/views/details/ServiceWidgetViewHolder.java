package com.schneewittchen.rosandroid.ui.views.details;

import android.view.View;

import com.schneewittchen.rosandroid.model.entities.widgets.BaseEntity;
import com.schneewittchen.rosandroid.viewmodel.DetailsViewModel;

import java.util.List;


public abstract class ServiceWidgetViewHolder extends DetailViewHolder {

    private final WidgetViewHolder widgetViewHolder;
    private final ServiceViewHolder serviceViewHolder;


    public ServiceWidgetViewHolder() {
        this.widgetViewHolder = new WidgetViewHolder(this);
        this.serviceViewHolder = new ServiceViewHolder(this);
    }



    @Override
    public void setViewModel(DetailsViewModel viewModel) {
        super.setViewModel(viewModel);
        serviceViewHolder.viewModel = viewModel;
    }

    public void baseInitView(View view) {
        widgetViewHolder.baseInitView(view);
        serviceViewHolder.baseInitView(view);
    }

    public void baseBindEntity(BaseEntity entity) {
        widgetViewHolder.baseBindEntity(entity);
        serviceViewHolder.baseBindEntity(entity);
    }

    public void baseUpdateEntity(BaseEntity entity) {
        widgetViewHolder.baseUpdateEntity(entity);
        serviceViewHolder.baseUpdateEntity(entity);
    }
}
