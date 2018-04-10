package com.hencoder.hencoderpracticedraw1.practice;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.View;

public class Practice10HistogramView extends View {

    Paint whitePaint = new Paint();
    Path path = new Path(); // 初始化 Path 对象
    Paint greenPaint = new Paint();

    public Practice10HistogramView(Context context) {
        super(context);
    }

    public Practice10HistogramView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }

    public Practice10HistogramView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

//        综合练习
//        练习内容：使用各种 Canvas.drawXXX() 方法画直方图

//        path.addCircle(300, 300, 200, Path.Direction.CW);
//        canvas.drawPath(path, paint);
        whitePaint.setAntiAlias(true);
        whitePaint.setColor(Color.WHITE);
        whitePaint.setStyle(Paint.Style.STROKE);
        whitePaint.setStrokeWidth(2);
        path.moveTo(100, 100);
        path.lineTo(100, 600);
        path.lineTo(1000, 600);
        canvas.drawPath(path, whitePaint);

        whitePaint.setStrokeWidth(0);
        whitePaint.setTextSize(28);
        canvas.drawText("Froyo", 130, 630, whitePaint);

        greenPaint.setAntiAlias(true);
        greenPaint.setColor(Color.GREEN);
        canvas.drawRect(120, 595, 210, 600, greenPaint);
    }
}
