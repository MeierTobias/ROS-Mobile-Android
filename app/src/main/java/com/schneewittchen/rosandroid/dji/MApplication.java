package com.schneewittchen.rosandroid.dji;

import android.app.Application;
import android.content.Context;
import android.content.Intent;

import androidx.multidex.MultiDex;

import dji.sdk.base.BaseProduct;
import dji.sdk.camera.Camera;
import dji.sdk.products.Aircraft;
import dji.sdk.products.HandHeld;
import dji.sdk.sdkmanager.DJISDKManager;

public class MApplication extends Application {
    public static final String TAG = MApplication.class.getName();
    private static Application app = null;
    private static BaseProduct product;

    @Override
    protected void attachBaseContext(Context paramContext) {
        super.attachBaseContext(paramContext);
        MultiDex.install(this);
        com.secneo.sdk.Helper.install(this);
        app = this;
    }

    public static Application getInstance() {
        return MApplication.app;
    }

    public static synchronized BaseProduct getProductInstance() {
        product = DJISDKManager.getInstance().getProduct();
        return product;
    }

    public static boolean isAircraftConnected() {
        return getProductInstance() != null && getProductInstance() instanceof Aircraft;
    }

    public static synchronized Aircraft getAircraftInstance() {
        if (!isAircraftConnected()) {
            return null;
        }
        return (Aircraft) getProductInstance();
    }

    public static synchronized Camera getCameraInstance() {
        if (getProductInstance() == null) return null;
        Camera camera = null;
        if (getProductInstance() instanceof Aircraft) {
            camera = ((Aircraft) getProductInstance()).getCamera();
        } else if (getProductInstance() instanceof HandHeld) {
            camera = ((HandHeld) getProductInstance()).getCamera();
        }
        return camera;
    }
}
