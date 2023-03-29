package com.schneewittchen.rosandroid.widgets.djivirtualcontrol;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Typeface;
import android.text.TextPaint;
import android.util.AttributeSet;
import android.util.Log;

import androidx.annotation.Nullable;

import com.schneewittchen.rosandroid.R;
import com.schneewittchen.rosandroid.dji.MApplication;
import com.schneewittchen.rosandroid.dji.ModuleVerificationUtil;
import com.schneewittchen.rosandroid.ui.views.widgets.SubscriberWidgetView;
import com.schneewittchen.rosandroid.utility.Utils;

import org.ros.internal.message.Message;

import dji.common.error.DJIError;
import dji.common.flightcontroller.virtualstick.FlightControlData;
import dji.common.flightcontroller.virtualstick.FlightCoordinateSystem;
import dji.common.flightcontroller.virtualstick.RollPitchControlMode;
import dji.common.flightcontroller.virtualstick.VerticalControlMode;
import dji.common.flightcontroller.virtualstick.YawControlMode;
import dji.common.util.CommonCallbacks;
import dji.sdk.flightcontroller.FlightController;
import geometry_msgs.Twist;

public class DjiVirtualControlView extends SubscriberWidgetView {

    public static final String TAG = DjiVirtualControlView.class.getSimpleName();

    private float yVel = 0f;
    private float xVel = 0f;
    private float yawVel = 0f;
    private float zVel = 0f;

    private TextPaint textPaint;
    private Paint linePaint;
    private float lineWidth;

    private FlightController flightController = null;
    private boolean flightControllerInitialized = false;

    public DjiVirtualControlView(Context context) {
        super(context);
        init();
    }

    public DjiVirtualControlView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    private void init() {
        lineWidth = Utils.dpToPx(getContext(), 2);

        linePaint = new Paint();
        linePaint.setColor(getResources().getColor(R.color.colorAccent));
        linePaint.setStyle(Paint.Style.STROKE);
        linePaint.setStrokeWidth(lineWidth);

        textPaint = new TextPaint();
        textPaint.setColor(Color.WHITE);
        textPaint.setTextAlign(Paint.Align.CENTER);
        textPaint.setTypeface(Typeface.create(Typeface.DEFAULT, Typeface.BOLD));
        textPaint.setTextSize(20 * getResources().getDisplayMetrics().density);


    }

    @Override
    public void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        float width = getWidth();
        float height = getHeight();

        float baseline = (textPaint.descent() + textPaint.ascent()) / 2f;
        float xPos = width / 2;
        float yPos = height / 5f;

        canvas.save();
        canvas.drawText(String.format("X vel: %.3f m/s", xVel), xPos, yPos * 3 - baseline, textPaint);
        canvas.drawText(String.format("Y vel: %.3f m/s", yVel), xPos, yPos * 2 - baseline, textPaint);
        canvas.drawText(String.format("Z vel: %.3f m/s", zVel), xPos, yPos - baseline, textPaint);
        canvas.drawText(String.format("Yaw vel: %.3f °/s", yawVel), xPos, yPos * 4 - baseline, textPaint);
        canvas.drawLine(0, height - lineWidth / 2, width, height - lineWidth / 2, linePaint);
        canvas.restore();
    }

    @Override
    public void onNewMessage(Message message) {
        super.onNewMessage(message);

        Twist vel_cmd = (Twist) message;
        xVel = (float) vel_cmd.getLinear().getX();
        yVel = (float) vel_cmd.getLinear().getY();
        zVel = (float) vel_cmd.getLinear().getZ();
        yawVel = (float) (vel_cmd.getAngular().getZ() * 180 / Math.PI);

        // write to drone
        if (!flightControllerInitialized) {
            if (!initParams()) {
                return;
            }
        }

        if (ModuleVerificationUtil.isFlightControllerAvailable()) {
            if (flightController.isVirtualStickControlModeAvailable()) {
                flightController.sendVirtualStickFlightControlData(
                        new FlightControlData(
                                yVel,
                                xVel,
                                yawVel,
                                zVel),
                        new CommonCallbacks.CompletionCallback() {
                            @Override
                            public void onResult(DJIError djiError) {

                            }
                        });
            }
        } else {
            flightControllerInitialized = false;
        }

        this.invalidate();
    }

    private boolean initParams() {
        if (ModuleVerificationUtil.isFlightControllerAvailable()) {
            flightController = MApplication.getAircraftInstance().getFlightController();
            flightController.setVerticalControlMode(VerticalControlMode.VELOCITY);
            flightController.setRollPitchControlMode(RollPitchControlMode.VELOCITY);
            flightController.setYawControlMode(YawControlMode.ANGULAR_VELOCITY);
            flightController.setRollPitchCoordinateSystem(FlightCoordinateSystem.BODY);
            flightControllerInitialized = true;
            return true;
        } else {
            Log.w(TAG, "Flight controller not available");
            flightControllerInitialized = false;
            return false;
        }

    }
}
