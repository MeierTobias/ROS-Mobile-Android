package com.schneewittchen.rosandroid.widgets.djiactivation;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Typeface;
import android.text.TextPaint;
import android.util.AttributeSet;
import android.view.MotionEvent;

import androidx.annotation.Nullable;

import com.schneewittchen.rosandroid.R;
import com.schneewittchen.rosandroid.dji.ActivationManager;
import com.schneewittchen.rosandroid.dji.MApplication;
import com.schneewittchen.rosandroid.model.entities.widgets.BaseEntity;
import com.schneewittchen.rosandroid.ui.views.widgets.PublisherWidgetView;
import com.schneewittchen.rosandroid.utility.Utils;
import com.schneewittchen.rosandroid.widgets.djicamera.DjiCameraView;
import com.schneewittchen.rosandroid.widgets.label.LabelEntity;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;

public class DjiActivationView extends PublisherWidgetView {
    public static final String TAG = DjiActivationView.class.getSimpleName();

    private TextPaint textPaint;
    private Paint linePaint;
    private float lineWidth;

    public DjiActivationView(Context context) {
        super(context);
        init(context);
    }

    public DjiActivationView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        init(context);
    }

    private void init(Context context) {
        ActivationManager.getInstance().setContext(context);

        EventBus.getDefault().register(this);

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
    public void setWidgetEntity(BaseEntity widgetEntity) {
        super.setWidgetEntity(widgetEntity);
        ActivationManager.getInstance().setConnectionEntity((DjiActivationEntity) widgetEntity);
        invalidate();
    }

    @Subscribe
    public void onConnectionStatusUpdateEvent(ActivationManager.ConnectionStatusUpdateEvent connectionStatusUpdateEvent){
        ActivationManager.getInstance().refreshStatus();
        this.publishViewData(new DjiActivationData((DjiActivationEntity) widgetEntity));
        invalidate();
    }

    /*@Override
    public void onDestroy(){
        EventBus.getDefault().unregister(this);
    }*/

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        if (super.onTouchEvent(event)) {
            return true;
        }
        invalidate();

        this.publishViewData(new DjiActivationData((DjiActivationEntity) widgetEntity));
        ActivationManager.getInstance().startRegistration();

        return true;
    }

    @Override
    public void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        DjiActivationEntity entity = (DjiActivationEntity) widgetEntity;
        float width = getWidth();
        float height = getHeight();

        float baseline = (textPaint.descent() + textPaint.ascent()) / 2f;
        float xPos = width / 2;
        float yPos1 = height / 3f - baseline;
        float yPos2 = height * 2f / 3f - baseline;

        canvas.save();
        canvas.drawText(entity.getConnectionStatus(), xPos, yPos1, textPaint);
        canvas.drawText(entity.getProductInfo(), xPos, yPos2, textPaint);
        canvas.drawLine(0, height - lineWidth / 2, width, height - lineWidth / 2, linePaint);
        canvas.restore();
    }
}
