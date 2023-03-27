package com.schneewittchen.rosandroid.widgets.djiflightservice;

import com.schneewittchen.rosandroid.model.entities.widgets.ServiceWidgetEntity;
import com.schneewittchen.rosandroid.model.repositories.rosRepo.message.Topic;
import com.schneewittchen.rosandroid.utility.Constants;

import java.util.ArrayList;

public class DjiFlightServiceEntity extends ServiceWidgetEntity {


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
        this.commands.add("take_off");
        this.commands.add("land");

        String className = this.getClass().getSimpleName().substring(0, this.getClass().getSimpleName().length() - 6);
        this.serviceModelPath = String.format(Constants.SERVICE_FORMAT,
                className.toLowerCase(), className);
    }
}
