package com.schneewittchen.rosandroid.widgets.djiflightservice;

import android.util.Log;

import com.schneewittchen.rosandroid.dji.MApplication;
import com.schneewittchen.rosandroid.dji.ModuleVerificationUtil;
import com.schneewittchen.rosandroid.model.entities.widgets.IServiceModel;

import dji.common.error.DJIError;
import dji.common.flightcontroller.virtualstick.FlightCoordinateSystem;
import dji.common.flightcontroller.virtualstick.RollPitchControlMode;
import dji.common.flightcontroller.virtualstick.VerticalControlMode;
import dji.common.flightcontroller.virtualstick.YawControlMode;
import dji.common.util.CommonCallbacks;
import dji.sdk.flightcontroller.FlightController;

public class DjiFlightServiceModel implements IServiceModel {
    private static final String TAG = DjiFlightServiceModel.class.getName();

    private FlightController flightController = null;

    public void runMethodByCommand(String cmd) {
        switch (cmd) {
            case "take_off":
                takeOffMethod();
                break;
            case "land":
                landMethod();
                break;
            case "enable_virtual_control":
                enableVirtualControlMethod();
                break;
            case "disable_virtual_control":
                disableVirtualControlMethod();
                break;
            default:
                Log.i(TAG, "Service not found");
        }
    }

    public void takeOffMethod() {
        Log.d(TAG, "Take-off command received");
        initParams();
        flightController.startTakeoff(new CommonCallbacks.CompletionCallback() {
            @Override
            public void onResult(DJIError djiError) {
                if (djiError.getErrorCode() == 0) {
                    Log.d(TAG, "Take-off command completed");
                } else {
                    Log.w(TAG, djiError.getDescription());
                }
                // pubResult.publish(pubResult.newMessage());
            }
        });
    }

    public void landMethod() {
        Log.d(TAG, "Landing command received");
        initParams();
        flightController.startLanding(new CommonCallbacks.CompletionCallback() {
            @Override
            public void onResult(DJIError djiError) {
                if (djiError.getErrorCode() == 0) {
                    Log.d(TAG, "Landing command completed");
                } else {
                    Log.w(TAG, djiError.getDescription());
                }
                // pubResult.publish(pubResult.newMessage());
            }
        });
    }

    public void enableVirtualControlMethod() {
        Log.d(TAG, "EnableVirtualControl command received");
        initParams();
        flightController.setVirtualStickModeEnabled(true, new CommonCallbacks.CompletionCallback() {
            @Override
            public void onResult(DJIError djiError) {
                flightController.setVirtualStickAdvancedModeEnabled(true);
                if (djiError.getErrorCode() == 0) {
                    Log.d(TAG, "EnableVirtualControl command completed");
                } else {
                    Log.w(TAG, djiError.getDescription());
                }
                // pubResult.publish(pubResult.newMessage());
            }
        });
    }

    public void disableVirtualControlMethod() {
        initParams();
        flightController.setVirtualStickModeEnabled(false, new CommonCallbacks.CompletionCallback() {
            @Override
            public void onResult(DJIError djiError) {
                if (djiError.getErrorCode() == 0) {
                    Log.d(TAG, "DisableVirtualControl command completed");
                } else {
                    Log.w(TAG, djiError.getDescription());
                }
                // pubResult.publish(pubResult.newMessage());
            }
        });
    }

    private void initParams() {
        if (flightController == null) {
            if (ModuleVerificationUtil.isFlightControllerAvailable()) {
                flightController = MApplication.getAircraftInstance().getFlightController();
            } else {
                Log.w(TAG, "Flight controller not available");
                return;
            }
        }
        flightController.setVerticalControlMode(VerticalControlMode.VELOCITY);
        flightController.setRollPitchControlMode(RollPitchControlMode.VELOCITY);
        flightController.setYawControlMode(YawControlMode.ANGULAR_VELOCITY);
        flightController.setRollPitchCoordinateSystem(FlightCoordinateSystem.BODY);
    }
}
