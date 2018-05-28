package com.youngly.customview;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.view.ViewPropertyAnimator;
import android.view.ViewTreeObserver;
import android.view.animation.LinearInterpolator;

public class MainActivity extends AppCompatActivity {

    private MyView view;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ViewPropertyAnimator animate = findViewById(R.id.taiji).animate();
        animate.rotation(10000);
        animate.setInterpolator(new LinearInterpolator());
        animate.setDuration(120000);
        animate.start();

        view = findViewById(R.id.my_view);

        ((RadarView) findViewById(R.id.radarview)).setScore(33, 35, 37, 39, 35, 33);
        view.getViewTreeObserver().addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() {
            @Override
            public void onGlobalLayout() {
                Log.i("youngly1", "getTop =" + view.getTop() + ", getLeft = " + view.getLeft() + ", getRight =" +
                        view.getRight() + ", getBottom = " + view.getBottom());

                int[] locationWindow = new int[2];
                view.getLocationInWindow(locationWindow);

                int[] locationScreen = new int[2];
                view.getLocationOnScreen(locationScreen);
                Log.i("youngly1", "window = " + locationWindow[0] + ", " + locationWindow[1] +
                        ", screen = " + locationScreen[0] + ", " + locationScreen[1]);
            }
        });
    }

    public void gotoSecond(View view) {
        startActivity(new Intent(this, SecondActivity.class));
    }
}
