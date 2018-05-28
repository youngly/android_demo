package com.youngly.customview;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.PathMeasure;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.View;

/**
 * Created by youngly on 2018/4/23.
 * E-mail: youngly.yang@gometech.com.cn
 */

public class SegmentView extends View {
    public SegmentView(Context context) {
        super(context);
    }

    public SegmentView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        Paint paint = new Paint();
        paint.setStyle(Paint.Style.STROKE);
//        paint.setStrokeWidth(4);
//
//        canvas.translate(getWidth() / 2, getHeight() / 2);
//        Path path = new Path();
//        path.addRect(-200, -200, 200, 200, Path.Direction.CW);
//
//        PathMeasure pathMeasure = new PathMeasure(path, false);
//        Path dst = new Path();
//        pathMeasure.getSegment(200, 600, dst, true);
//
//        canvas.drawPath(dst, paint);

        canvas.translate(getWidth() / 2, getWidth() / 2);          // 平移坐标系

        Path path = new Path();                                     // 创建Path并添加了一个矩形
        path.addRect(-200, -200, 200, 200, Path.Direction.CW);

        Path dst = new Path();                                      // 创建用于存储截取后内容的 Path

        PathMeasure measure = new PathMeasure(path, false);         // 将 Path 与 PathMeasure 关联

// 截取一部分存入dst中，并使用 moveTo 保持截取得到的 Path 第一个点的位置不变
        measure.getSegment(200, 600, dst, true);

        canvas.drawPath(dst, paint);
    }
}
