package com.schneewittchen.rosandroid.widgets.djinavsat;

import com.schneewittchen.rosandroid.model.entities.widgets.BaseEntity;
import com.schneewittchen.rosandroid.model.repositories.rosRepo.node.BaseData;

import org.ros.internal.message.Message;
import org.ros.node.topic.Publisher;

public class DjiNavSatData extends BaseData {

    private double wgs84_long;
    private double wgs84_lat;
    private double wgs84_alt;


    public DjiNavSatData(double lo, double la, double alt) {
        wgs84_long = lo;
        wgs84_lat = la;
        wgs84_alt = alt;
    }

    @Override
    public Message toRosMessage(Publisher<Message> publisher, BaseEntity widget) {
        sensor_msgs.NavSatFix message = (sensor_msgs.NavSatFix) publisher.newMessage();
        message.setLongitude(wgs84_long);
        message.setLatitude(wgs84_lat);
        message.setAltitude(wgs84_alt);
        return message;
    }

}
