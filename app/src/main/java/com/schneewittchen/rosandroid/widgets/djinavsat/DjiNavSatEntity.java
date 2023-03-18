package com.schneewittchen.rosandroid.widgets.djinavsat;

import com.schneewittchen.rosandroid.dji.MApplication;
import com.schneewittchen.rosandroid.dji.ModuleVerificationUtil;
import com.schneewittchen.rosandroid.model.entities.widgets.PublisherWidgetEntity;
import com.schneewittchen.rosandroid.model.repositories.rosRepo.message.Topic;

import org.greenrobot.eventbus.EventBus;

import java.util.Timer;
import java.util.TimerTask;

import dji.common.flightcontroller.LocationCoordinate3D;
import dji.common.flightcontroller.virtualstick.FlightCoordinateSystem;
import dji.common.flightcontroller.virtualstick.RollPitchControlMode;
import dji.common.flightcontroller.virtualstick.VerticalControlMode;
import dji.common.flightcontroller.virtualstick.YawControlMode;
import dji.sdk.flightcontroller.FlightController;

public class DjiNavSatEntity extends PublisherWidgetEntity {

    private Timer t = new Timer();
    private TimerTask tt;
    private FlightController flightController = null;

    public DjiNavSatEntity() {
        // widget properties
        this.width = 4;
        this.height = 2;
        this.topic = new Topic("dji/navsatfix", sensor_msgs.NavSatFix._TYPE);
        this.immediatePublish = false;
        this.publishRate = 1f;

        // setup timer
        tt = new TimerTask() {
            @Override
            public void run() {
                // get the coordinates from the flight controller
                if (MApplication.isAircraftConnected()) {
                    LocationCoordinate3D loc = MApplication.getAircraftInstance().getFlightController().getState().getAircraftLocation();
                    EventBus.getDefault().post(new DjiNavSatUpdateEvent(
                            MApplication.getAircraftInstance()
                                    .getFlightController()
                                    .getState()
                                    .getAircraftLocation()
                                    .getLongitude(),
                            MApplication.getAircraftInstance()
                                    .getFlightController()
                                    .getState()
                                    .getAircraftLocation()
                                    .getLatitude(),
                            MApplication.getAircraftInstance()
                                    .getFlightController()
                                    .getState()
                                    .getAircraftLocation()
                                    .getAltitude()
                    ));
                }
            }
        };
        t.schedule(tt, 0, (long) (1000f / this.publishRate));
    }
}
