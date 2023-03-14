package com.schneewittchen.rosandroid.widgets.djiactivation;

import com.schneewittchen.rosandroid.model.entities.widgets.BaseEntity;
import com.schneewittchen.rosandroid.model.repositories.rosRepo.node.BaseData;

import org.ros.internal.message.Message;
import org.ros.node.topic.Publisher;

import std_msgs.Bool;

public class DjiActivationData extends BaseData {

    public boolean activated;

    public DjiActivationData(boolean activated){
        this.activated = activated;
    }

    @Override
    public Message toRosMessage(Publisher<Message> publisher, BaseEntity widget) {
        std_msgs.Bool message = (Bool) publisher.newMessage();
        message.setData(activated);
        return message;
    }
}
