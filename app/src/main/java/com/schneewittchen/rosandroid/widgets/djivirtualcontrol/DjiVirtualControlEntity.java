package com.schneewittchen.rosandroid.widgets.djivirtualcontrol;

import com.schneewittchen.rosandroid.model.entities.widgets.SubscriberWidgetEntity;
import com.schneewittchen.rosandroid.model.repositories.rosRepo.message.Topic;

import geometry_msgs.Twist;

public class DjiVirtualControlEntity extends SubscriberWidgetEntity {

    public DjiVirtualControlEntity() {
        this.width = 4;
        this.height = 3;
        this.topic = new Topic("dji/cmd_vel", Twist._TYPE);
    }
}
