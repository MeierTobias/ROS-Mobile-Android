package com.schneewittchen.rosandroid.dji;

import android.Manifest;
import android.app.Activity;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.pm.PackageManager;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Handler;
import android.os.Looper;
import android.util.Log;
import android.widget.Toast;

import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import com.schneewittchen.rosandroid.R;
import com.schneewittchen.rosandroid.ui.activity.MainActivity;
import com.schneewittchen.rosandroid.widgets.djiactivation.DjiActivationEntity;

import org.greenrobot.eventbus.EventBus;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicBoolean;

import dji.common.error.DJIError;
import dji.common.error.DJISDKError;
import dji.common.realname.AppActivationState;
import dji.common.util.CommonCallbacks;
import dji.log.DJILog;
import dji.sdk.base.BaseComponent;
import dji.sdk.base.BaseProduct;
import dji.sdk.products.Aircraft;
import dji.sdk.realname.AppActivationManager;
import dji.sdk.sdkmanager.DJISDKInitEvent;
import dji.sdk.sdkmanager.DJISDKManager;
import dji.sdk.sdkmanager.LDMModule;
import dji.sdk.sdkmanager.LDMModuleType;

public class ActivationManager {
    private static final String TAG = ActivationManager.class.getName();

    // general attributes
    private Context mContext;
    private Handler mHandler;
    private DjiActivationEntity mEntity;
    private static BaseProduct mProduct;

    // permission attributes
    private static final int REQUEST_PERMISSION_CODE = 12345;
    private static final String[] REQUIRED_PERMISSION_LIST = new String[]{
            android.Manifest.permission.VIBRATE, // Gimbal rotation
            android.Manifest.permission.INTERNET, // API requests
            android.Manifest.permission.ACCESS_WIFI_STATE, // WIFI connected products
            android.Manifest.permission.ACCESS_COARSE_LOCATION, // Maps
            android.Manifest.permission.ACCESS_NETWORK_STATE, // WIFI connected products
            android.Manifest.permission.ACCESS_FINE_LOCATION, // Maps
            android.Manifest.permission.CHANGE_WIFI_STATE, // Changing between WIFI and USB connection
            // android.Manifest.permission.WRITE_EXTERNAL_STORAGE, // Log files
            android.Manifest.permission.BLUETOOTH, // Bluetooth connected products
            android.Manifest.permission.BLUETOOTH_ADMIN, // Bluetooth connected products
            // android.Manifest.permission.READ_EXTERNAL_STORAGE, // Log files
            android.Manifest.permission.READ_PHONE_STATE, // Device UUID accessed upon registration
            Manifest.permission.RECORD_AUDIO // Speaker accessory
    };

    // registration attributes
    private AtomicBoolean isRegistrationInProgress = new AtomicBoolean(false);
    private BaseComponent.ComponentListener mDJIComponentListener = new BaseComponent.ComponentListener() {
        @Override
        public void onConnectivityChange(boolean isConnected) {
            Log.d(TAG, "onComponentConnectivityChanged: " + isConnected);
            notifyStatusChange();
        }
    };
    private int lastProcess = -1;

    // Connection attributes
    private static final int MSG_INFORM_ACTIVATION = 1;
    private static final int ACTIVATION_DELAY_TIME = 3000;
    private AtomicBoolean hasAppActivationListenerStarted = new AtomicBoolean(false);
    private AppActivationState.AppActivationStateListener appActivationStateListener;
    public class ConnectionStatusUpdateEvent{}

    public ActivationManager(Context context) {
        mContext = context;

        //Initialize DJI SDK Manager
        mHandler = new Handler(Looper.getMainLooper());
    }

    public void setEntity(DjiActivationEntity entity) {
        mEntity = entity;
    }


    public void startRegistration() {
        if (checkAndRequestPermissions()) {
            startSDKRegistration();
        }
        else {
            mEntity.setProductInfo("Info: missing permissions");
        }
    }

    private boolean checkAndRequestPermissions() {
        // Check for permissions
        List<String> missingPermission = new ArrayList<>();
        for (String eachPermission : REQUIRED_PERMISSION_LIST) {
            if (ContextCompat.checkSelfPermission(mContext, eachPermission) != PackageManager.PERMISSION_GRANTED) {
                missingPermission.add(eachPermission);
            }
        }
        // Request for missing permissions
        if (missingPermission.isEmpty()) {
            return true;
        } else if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            ActivityCompat.requestPermissions((Activity) mContext,
                    missingPermission.toArray(new String[missingPermission.size()]),
                    REQUEST_PERMISSION_CODE);
            return false;
        }
        return false;
    }

    private void startSDKRegistration() {
        if (isRegistrationInProgress.compareAndSet(false, true)) {
            AsyncTask.execute(new Runnable() {
                @Override
                public void run() {
                    ToastUtils.setResultToToast("Registering, pls wait...");

                    DJISDKManager.getInstance().getLDMManager().setModuleNetworkServiceEnabled(
                            new LDMModule.Builder().moduleType(
                                    LDMModuleType.FIRMWARE_UPGRADE).enabled(false).build());

                    DJISDKManager.getInstance().registerApp(mContext.getApplicationContext(), new DJISDKManager.SDKManagerCallback() {
                        @Override
                        public void onRegister(DJIError djiError) {
                            if (djiError == DJISDKError.REGISTRATION_SUCCESS) {
                                DJILog.e("App registration", DJISDKError.REGISTRATION_SUCCESS.getDescription());
                                DJISDKManager.getInstance().startConnectionToProduct();
                                ToastUtils.setResultToToast("SDK Registered Successfully.");
                                notifyStatusChange();
                            } else {
                                ToastUtils.setResultToToast("SDK Registration Failed. Please check the bundle ID and your network connectivity." + djiError.getDescription());
                            }
                            Log.v(TAG, djiError.getDescription());
                        }

                        @Override
                        public void onProductDisconnect() {
                            Log.d(TAG, "onProductDisconnect");
                            notifyStatusChange();
                        }

                        @Override
                        public void onProductConnect(BaseProduct baseProduct) {
                            Log.d(TAG, String.format("onProductConnect newProduct:%s", baseProduct));
                            notifyStatusChange();
                        }

                        @Override
                        public void onProductChanged(BaseProduct baseProduct) {
                            notifyStatusChange();
                        }

                        @Override
                        public void onComponentChange(BaseProduct.ComponentKey componentKey,
                                                      BaseComponent oldComponent,
                                                      BaseComponent newComponent) {
                            if (newComponent != null) {
                                newComponent.setComponentListener(mDJIComponentListener);

                                if (componentKey == BaseProduct.ComponentKey.FLIGHT_CONTROLLER) {
                                    showDBVersion();
                                }
                            }
                            Log.d(TAG,
                                    String.format("onComponentChange key:%s, oldComponent:%s, newComponent:%s",
                                            componentKey,
                                            oldComponent,
                                            newComponent));

                            notifyStatusChange();
                        }

                        @Override
                        public void onInitProcess(DJISDKInitEvent djisdkInitEvent, int i) {

                        }

                        @Override
                        public void onDatabaseDownloadProgress(long current, long total) {
                            int process = (int) (100 * current / total);
                            if (process == lastProcess) {
                                return;
                            }
                            lastProcess = process;
                            if (process % 25 == 0) {
                                ToastUtils.setResultToToast("DB load process : " + process);
                            } else if (process == 0) {
                                ToastUtils.setResultToToast("DB load begin");
                            }
                        }
                    });
                }
            });
        }
    }

    private void notifyStatusChange() {
        EventBus.getDefault().post(new ConnectionStatusUpdateEvent());
    }

    public void refreshStatus() {
        mProduct = MApplication.getProductInstance();
        Log.d(TAG, "mProduct: " + (mProduct == null ? "null" : "unnull"));
        if (null != mProduct) {
            if (mProduct.isConnected()) {
                String str = mProduct instanceof Aircraft ? "DJIAircraft" : "DJIHandHeld";
                mEntity.setConnectionStatus("Status: " + str + " connected");
                if (mProduct instanceof Aircraft) {
                    addAppActivationListenerIfNeeded();
                }

                if (null != mProduct.getModel()) {
                    mEntity.setProductInfo("" + mProduct.getModel().getDisplayName());
                } else {
                    mEntity.setProductInfo("Product Information");
                }
            } else if (mProduct instanceof Aircraft) {
                Aircraft aircraft = (Aircraft) mProduct;
                if (aircraft.getRemoteController() != null && aircraft.getRemoteController().isConnected()) {
                    mEntity.setConnectionStatus("Status: Only RC connected");
                    mEntity.setProductInfo("Product Information");
                }
            }
        } else {
            mEntity.setConnectionStatus("Status: No Product Connected");
            mEntity.setProductInfo("Product Information");
        }
    }

    private void showDBVersion() {
        mHandler.postDelayed(new Runnable() {
            @Override
            public void run() {
                DJISDKManager.getInstance().getFlyZoneManager().getPreciseDatabaseVersion(new CommonCallbacks.CompletionCallbackWith<String>() {
                    @Override
                    public void onSuccess(String s) {
                        ToastUtils.setResultToToast("db load success ! version : " + s);
                    }

                    @Override
                    public void onFailure(DJIError djiError) {
                        ToastUtils.setResultToToast("db load failure ! get version error : " + djiError.getDescription());
                    }
                });
            }
        }, 3000);
    }

    private void addAppActivationListenerIfNeeded() {
        if (AppActivationManager.getInstance().getAppActivationState() != AppActivationState.ACTIVATED) {
            sendDelayMsg(MSG_INFORM_ACTIVATION, ACTIVATION_DELAY_TIME);
            if (hasAppActivationListenerStarted.compareAndSet(false, true)) {
                appActivationStateListener = new AppActivationState.AppActivationStateListener() {

                    @Override
                    public void onUpdate(AppActivationState appActivationState) {
                        if (mHandler != null && mHandler.hasMessages(MSG_INFORM_ACTIVATION)) {
                            mHandler.removeMessages(MSG_INFORM_ACTIVATION);
                        }
                        if (appActivationState != AppActivationState.ACTIVATED) {
                            sendDelayMsg(MSG_INFORM_ACTIVATION, ACTIVATION_DELAY_TIME);
                        }
                    }
                };
                AppActivationManager.getInstance().addAppActivationStateListener(appActivationStateListener);
            }
        }
    }

    private void sendDelayMsg(int msg, long delayMillis) {
        if (mHandler == null) {
            return;
        }

        if (!mHandler.hasMessages(msg)) {
            mHandler.sendEmptyMessageDelayed(msg, delayMillis);
        }
    }
}
