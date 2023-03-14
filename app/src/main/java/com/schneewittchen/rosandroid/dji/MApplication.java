package com.schneewittchen.rosandroid.dji;

import android.app.Application;
import android.content.Context;

import androidx.multidex.MultiDex;

public class MApplication extends Application {
    private static Application app = null;
    public static final String FLAG_CONNECTION_CHANGE = "dji_sdk_connection_change";
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
}
