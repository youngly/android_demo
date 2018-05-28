package com.youngly.customview;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.PointF;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.View;

/**
 * Created by youngly on 2018/4/17.
 * E-mail: youngly.yang@gometech.com.cn
 */

public class RadarView extends View {

    PointF first, second, third, forth, fifth, sixth;
    int firstScore, secondScore, thirdScore, forthScore, fifthScore, sixthScore;
    Paint paint;
    int defaultLong = 50;
    float angle = (float) (Math.PI * 2 / 6);
    private Paint bgPaint;


    public RadarView(Context context) {
        super(context);
    }

    public RadarView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        initPaint();
        initPoint();
    }

    public void setScore(int firstScore, int secondScore, int thirdScore, int forthScore, int fifthScore, int sixthScore) {
        this.firstScore = firstScore * 3;
        this.secondScore = secondScore * 3;
        this.thirdScore = thirdScore * 3;
        this.forthScore = forthScore * 3;
        this.fifthScore = fifthScore * 3;
        this.sixthScore = sixthScore * 3;
        invalidate();
    }

    private void initPaint() {
        paint = new Paint();
        paint.setStyle(Paint.Style.STROKE);

        bgPaint = new Paint();
        bgPaint.setColor(Color.parseColor("#447FFFAA"));
    }

    private void initPoint() {
        first = new PointF(defaultLong, 0);
        second = new PointF(((float) (defaultLong * Math.cos(angle))), -((float) (defaultLong * Math.sin(angle))));
        third = new PointF(-((float) (defaultLong * Math.cos(angle))), -((float) (defaultLong * Math.sin(angle))));
        forth = new PointF(-defaultLong, 0);
        fifth = new PointF(((float) (-defaultLong * Math.cos(angle))), ((float) (defaultLong * Math.sin(angle))));
        sixth = new PointF(((float) (defaultLong * Math.cos(angle))), ((float) (defaultLong * Math.sin(angle))));
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        canvas.translate(getWidth() / 2, getHeight() / 2);
        for (int i = 1; i < 5; i++) {
            Path path = new Path();
            path.moveTo(first.x * i, first.y * i);
            path.lineTo(second.x * i, second.y * i);
            path.lineTo(third.x * i, third.y * i);
            path.lineTo(forth.x * i, forth.y * i);
            path.lineTo(fifth.x * i, fifth.y * i);
            path.lineTo(sixth.x * i, sixth.y * i);
            path.close();
            canvas.drawPath(path, paint);
        }

        Path colorPath = new Path();
        colorPath.moveTo(firstScore, 0);
        colorPath.lineTo(((float) (secondScore * Math.cos(angle))), -((float) (secondScore * Math.sin(angle))));
        colorPath.lineTo(-((float) (thirdScore * Math.cos(angle))), -((float) (thirdScore * Math.sin(angle))));
        colorPath.lineTo(-forthScore, 0);
        colorPath.lineTo(-((float) (fifthScore * Math.cos(angle))), ((float) (fifthScore * Math.sin(angle))));
        colorPath.lineTo(((float) (sixthScore * Math.cos(angle))), ((float) (sixthScore * Math.sin(angle))));
        colorPath.close();
        canvas.drawPath(colorPath, bgPaint);
    }
}
