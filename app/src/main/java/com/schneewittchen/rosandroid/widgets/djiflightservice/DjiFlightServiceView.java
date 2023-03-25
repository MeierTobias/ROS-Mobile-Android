package com.schneewittchen.rosandroid.widgets.djiflightservice;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Typeface;
import android.text.TextPaint;
import android.util.AttributeSet;

import androidx.annotation.Nullable;

import com.schneewittchen.rosandroid.R;
import com.schneewittchen.rosandroid.model.entities.widgets.BaseEntity;
import com.schneewittchen.rosandroid.ui.views.widgets.ServiceWidgetView;
import com.schneewittchen.rosandroid.utility.Utils;


public class DjiFlightServiceView extends ServiceWidgetView {
    public static final String TAG = DjiFlightServiceView.class.getSimpleName();

    private TextPaint textPaint;
    private Paint linePaint;
    private float lineWidth;

    public DjiFlightServiceView(Context context) {
        super(context);
        init(context);
    }

    public DjiFlightServiceView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        init(context);
    }

    private void init(Context context) {
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
    public void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        float width = getWidth();
        float height = getHeight();

        float baseline = (textPaint.descent() + textPaint.ascent()) / 2f;
        float xPos = width / 2;
        float yPos = height / 2f - baseline;

        canvas.save();
        canvas.drawText("Service", xPos, yPos, textPaint);
        canvas.drawLine(0, height - lineWidth / 2, width, height - lineWidth / 2, linePaint);
        canvas.restore();
    }
}
