package com.schneewittchen.rosandroid.widgets.djiflightservice;

import android.util.Log;

import com.schneewittchen.rosandroid.model.entities.widgets.ServiceWidgetEntity;
import com.schneewittchen.rosandroid.model.repositories.rosRepo.message.Topic;

public class DjiFlightServiceEntity extends ServiceWidgetEntity {

    public static void takeOffMethod() {
        Log.d("Test", "Take-off command received and processed");
    }

    public DjiFlightServiceEntity() throws NoSuchMethodException {
        // widget properties
        this.width = 4;
        this.height = 2;
        this.topic = new Topic("service", std_msgs.Empty._TYPE);
        this.command = "takeoff";
        this.commandMethodRef = this.getClass().getMethod("takeOffMethod");
    }
}
