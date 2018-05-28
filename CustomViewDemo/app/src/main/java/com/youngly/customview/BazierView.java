package com.youngly.customview;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.Point;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;

/**
 * Created by youngly on 2018/4/18.
 * E-mail: youngly.yang@gometech.com.cn
 */

public class BazierView extends View {

    Paint pointPaint, bazierLinePaint;
    Point beginPoint, endPoint, controlPoint;

    public BazierView(Context context) {
        super(context);
    }

    public BazierView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        initPaint();
        initPoint();
    }

    private void initPoint() {
        beginPoint = new Point(-300, 300);
        endPoint = new Point(300, -300);
        controlPoint = new Point(300, 300);
    }

    private void initPaint() {
        pointPaint = new Paint();
        pointPaint.setColor(Color.GRAY);
        bazierLinePaint = new Paint();
        bazierLinePaint.setStyle(Paint.Style.STROKE);
        bazierLinePaint.setColor(Color.RED);
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        controlPoint.x = (int) event.getX() - getWidth() / 2;
        controlPoint.y = (int) event.getY() - getHeight() / 2;
        invalidate();
        return true;
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        canvas.translate(getWidth() / 2, getHeight() / 2);

        canvas.drawLine(beginPoint.x, beginPoint.y, controlPoint.x, controlPoint.y, pointPaint);
        canvas.drawLine(controlPoint.x, controlPoint.y, endPoint.x, endPoint.y, pointPaint);

        pointPaint.setColor(Color.BLACK);
        pointPaint.setStrokeWidth(10);
        pointPaint.setStrokeCap(Paint.Cap.ROUND);
        canvas.drawPoint(beginPoint.x, beginPoint.y, pointPaint);
        canvas.drawPoint(controlPoint.x, controlPoint.y, pointPaint);
        canvas.drawPoint(endPoint.x, endPoint.y, pointPaint);

        Path path = new Path();
        path.moveTo(beginPoint.x, beginPoint.y);
        path.quadTo(controlPoint.x, controlPoint.y, endPoint.x, endPoint.y);
        canvas.drawPath(path, bazierLinePaint);
    }
}
