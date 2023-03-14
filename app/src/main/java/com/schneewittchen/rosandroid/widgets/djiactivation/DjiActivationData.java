package com.schneewittchen.rosandroid.widgets.djiactivation;

import com.schneewittchen.rosandroid.model.entities.widgets.BaseEntity;
import com.schneewittchen.rosandroid.model.repositories.rosRepo.node.BaseData;

import org.ros.internal.message.Message;
import org.ros.node.topic.Publisher;

import std_msgs.Bool;

public class DjiActivationData extends BaseData {

    private String status;

    public DjiActivationData(DjiActivationEntity entity){

        status = entity.getConnectionStatus() + ";" + entity.getProductInfo();
    }

    @Override
    public Message toRosMessage(Publisher<Message> publisher, BaseEntity widget) {
        std_msgs.String message = (std_msgs.String) publisher.newMessage();
        message.setData(status);
        return message;
    }
}
