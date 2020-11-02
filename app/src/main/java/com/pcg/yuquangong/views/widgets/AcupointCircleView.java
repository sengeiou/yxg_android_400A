package com.pcg.yuquangong.views.widgets;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.View;

public class AcupointCircleView extends View {

    private Paint paint = new Paint();

    private int mCircleX;
    private int mCircleY;
    private int mCircleRadius;

    public AcupointCircleView(Context context) {
        super(context);
    }

    public AcupointCircleView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }

    public AcupointCircleView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    public void setCordinator(int circleX, int circleY, int radius) {
        mCircleX = circleX;
        mCircleY = circleY;
        mCircleRadius = radius;
        postInvalidate();
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        paint.setColor(Color.RED);
        canvas.drawCircle(mCircleX, mCircleY, mCircleRadius, paint);
    }

}
