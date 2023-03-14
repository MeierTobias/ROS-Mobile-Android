package com.schneewittchen.rosandroid.widgets.djiactivation;

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
import com.schneewittchen.rosandroid.dji.ActivationManager;
import com.schneewittchen.rosandroid.model.entities.widgets.BaseEntity;
import com.schneewittchen.rosandroid.ui.views.widgets.PublisherWidgetView;
import com.schneewittchen.rosandroid.utility.Utils;
import com.schneewittchen.rosandroid.widgets.djicamera.DjiCameraView;
import com.schneewittchen.rosandroid.widgets.label.LabelEntity;

public class DjiActivationView extends PublisherWidgetView {
    public static final String TAG = DjiActivationView.class.getSimpleName();

    private TextPaint textPaint;
    private Paint linePaint;
    private float lineWidth;
    private boolean activated = false;

    private ActivationManager activationManager;

    public DjiActivationView(Context context) {
        super(context);
        activationManager = new ActivationManager(context);
        init();
    }

    public DjiActivationView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        activationManager = new ActivationManager(context);
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
    public void setWidgetEntity(BaseEntity widgetEntity) {
        super.setWidgetEntity(widgetEntity);
        invalidate();
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        if (super.onTouchEvent(event)) {
            return true;
        }

        this.activated = !this.activated;
        this.publishViewData(new DjiActivationData(this.activated));
        invalidate();

        activationManager.startRegistration();

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
        float yPos = height / 2f - baseline;

        canvas.save();
        canvas.drawText(entity.text, xPos, yPos, textPaint);
        canvas.drawLine(0, height - lineWidth / 2, width, height - lineWidth / 2, linePaint);
        canvas.restore();
    }
}
