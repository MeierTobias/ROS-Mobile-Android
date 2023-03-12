package com.schneewittchen.rosandroid.widgets.djicamera;

import com.schneewittchen.rosandroid.model.entities.widgets.PublisherWidgetEntity;
import com.schneewittchen.rosandroid.model.repositories.rosRepo.message.Topic;

import sensor_msgs.Image;

public class DjiCameraEntity extends PublisherWidgetEntity{
    int colorScheme;
    boolean drawBehind;
    boolean useTimeStamp;

    public DjiCameraEntity() {
        this.width = 8;
        this.height = 6;
        this.topic = new Topic("dji_camera/image_raw", Image._TYPE);
    }
}
