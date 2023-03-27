package com.schneewittchen.rosandroid.widgets.djiflightservice;

import android.util.Log;

import com.schneewittchen.rosandroid.model.entities.widgets.IServiceModel;

public class DjiFlightServiceModel implements IServiceModel {
    private static final String TAG = DjiFlightServiceModel.class.getName();

    public void runMethodByCommand(String cmd) {
        switch (cmd) {
            case "take_off":
                takeOffMethod();
                break;
            case "land":
                landMethod();
                break;
            default:
                Log.i(TAG, "Service not found");
        }
    }

    public void takeOffMethod() {
        Log.d(TAG, "Take-off command received");
    }

    public void landMethod() {
        Log.d(TAG, "Land command received");
    }
}
