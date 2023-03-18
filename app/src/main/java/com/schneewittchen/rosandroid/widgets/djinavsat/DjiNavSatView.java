package com.schneewittchen.rosandroid.widgets.djinavsat;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Typeface;
import android.text.TextPaint;
import android.util.AttributeSet;
import android.view.MotionEvent;

import androidx.annotation.Nullable;

import com.schneewittchen.rosandroid.R;
import com.schneewittchen.rosandroid.model.entities.widgets.BaseEntity;
import com.schneewittchen.rosandroid.ui.views.widgets.PublisherWidgetView;
import com.schneewittchen.rosandroid.utility.Utils;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;

public class DjiNavSatView extends PublisherWidgetView {
    public static final String TAG = DjiNavSatView.class.getSimpleName();

    private TextPaint textPaint;
    private Paint linePaint;
    private float lineWidth;

    private double wgs84_long;
    private double wgs84_lat;
    private double wgs84_alt;

    public DjiNavSatView(Context context) {
        super(context);
        init(context);
    }

    public DjiNavSatView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        init(context);
    }

    private void init(Context context) {
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
        invalidate();
    }

    /*@Override
    public void onDestroy(){
        EventBus.getDefault().unregister(this);
    }*/

    @Subscribe
    public void onDjiNavSatUpdateEvent(DjiNavSatUpdateEvent eventData) {
        wgs84_long = eventData.wgs84_long;
        wgs84_lat = eventData.wgs84_lat;
        wgs84_alt = eventData.wgs84_alt;
        this.publishViewData(new DjiNavSatData(eventData.wgs84_long, eventData.wgs84_lat, eventData.wgs84_alt));
        invalidate();
    }

    @Override
    public void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        float width = getWidth();
        float height = getHeight();

        float baseline = (textPaint.descent() + textPaint.ascent()) / 2f;
        float xPos = width / 2;
        float yPos1 = height / 4f - baseline;
        float yPos2 = height * 2f / 4f - baseline;
        float yPos3 = height * 3f / 4f - baseline;

        canvas.save();
        canvas.drawText(String.format("Long: %.5f", wgs84_long), xPos, yPos1, textPaint);
        canvas.drawText(String.format("Lat: %.5f", wgs84_lat), xPos, yPos2, textPaint);
        canvas.drawText(String.format("Alt: %.5f", wgs84_alt), xPos, yPos3, textPaint);
        canvas.drawLine(0, height - lineWidth / 2, width, height - lineWidth / 2, linePaint);
        canvas.restore();
    }
}
