package com.schneewittchen.rosandroid.widgets.djicamera;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.RectF;
import android.util.AttributeSet;
import android.view.MotionEvent;

import androidx.annotation.Nullable;

import com.schneewittchen.rosandroid.R;
import com.schneewittchen.rosandroid.ui.views.widgets.PublisherWidgetView;

public class DjiCameraView extends PublisherWidgetView {
    public static final String TAG = DjiCameraView.class.getSimpleName();
    //private final RectF imageRect = new RectF();
    private Paint borderPaint;
    private Paint paintBackground;
    private float cornerWidth;
    //private CameraData data;

    public DjiCameraView(Context context) {
        super(context);
        init();
    }

    public DjiCameraView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    private void init() {
        this.cornerWidth = 0; //Utils.dpToPx(getContext(), 8);

        borderPaint = new Paint();
        borderPaint.setColor(getResources().getColor(R.color.borderColor));
        borderPaint.setStyle(Paint.Style.STROKE);
        borderPaint.setStrokeWidth(8);

        // Background color
        paintBackground = new Paint();
        paintBackground.setColor(Color.argb(100, 0, 0, 0));
        paintBackground.setStyle(Paint.Style.FILL);
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        if (this.editMode) {
            return super.onTouchEvent(event);
        }

        switch (event.getActionMasked()) {
            case MotionEvent.ACTION_UP:
            case MotionEvent.ACTION_DOWN:
                this.publishViewData(new DjiCameraData());
                invalidate();
                break;
            default:
                return false;
        }

        return true;
    }

    @Override
    public void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        canvas.drawPaint(paintBackground);

        // Define image size based on the Bitmap width and height
        float leftViz = 0F;
        float topViz = 0F;
        float widthViz = getWidth();
        float heightViz = getHeight();

        float width = widthViz;
        float height = heightViz;
        float left = leftViz;
        float top = topViz;

        // Draw Border
        canvas.drawRoundRect(leftViz, topViz, widthViz, heightViz, cornerWidth, cornerWidth, borderPaint);
    }
}
