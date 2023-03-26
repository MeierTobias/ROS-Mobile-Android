package com.schneewittchen.rosandroid.widgets.djiflightservice;

import android.util.Log;

import com.schneewittchen.rosandroid.model.entities.widgets.ServiceWidgetEntity;
import com.schneewittchen.rosandroid.model.repositories.rosRepo.message.ServiceData;
import com.schneewittchen.rosandroid.model.repositories.rosRepo.message.Topic;

import java.util.ArrayList;

public class DjiFlightServiceEntity extends ServiceWidgetEntity {

    public static void takeOffMethod() {
        Log.d("DjiFlightServiceEntity", "Take-off command received");
    }

    public static void landMethod() {
        Log.d("DjiFlightServiceEntity", "Land command received");
    }

    public DjiFlightServiceEntity() throws NoSuchMethodException {
        super();
        initProperties();
    }

    public DjiFlightServiceEntity(DjiFlightServiceEntity entity) throws NoSuchMethodException {
        super(entity);
        initProperties();
    }

    private void initProperties() throws NoSuchMethodException {
        this.width = 4;
        this.height = 2;
        this.topic = new Topic("service", std_msgs.Empty._TYPE);
        this.commandNamespace = "dji/services/flight_control";
        this.serviceCollection.add(new ServiceData("take_off",
                this.getClass().getMethod("takeOffMethod")));
        this.serviceCollection.add(new ServiceData("land",
                this.getClass().getMethod("landMethod")));
    }
}
