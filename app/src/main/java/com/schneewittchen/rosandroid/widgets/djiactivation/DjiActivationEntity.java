package com.schneewittchen.rosandroid.widgets.djiactivation;

import com.schneewittchen.rosandroid.model.entities.widgets.PublisherWidgetEntity;
import com.schneewittchen.rosandroid.model.repositories.rosRepo.message.Topic;

import std_msgs.Bool;

public class DjiActivationEntity extends PublisherWidgetEntity {

    public String text;

    public DjiActivationEntity(){
        this.width = 2;
        this.height = 2;
        this.topic = new Topic("dji_activation/status", Bool._TYPE);
        this.text = "Label";
        this.immediatePublish = true;
    }
}
