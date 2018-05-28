package com.hencoder.hencoderpracticedraw5;

import android.animation.ObjectAnimator;
import android.os.Bundle;
import android.support.annotation.LayoutRes;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.view.animation.FastOutSlowInInterpolator;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewStub;
import android.view.animation.LinearInterpolator;

import com.hencoder.hencoderpracticedraw5.practice.Practice01AfterOnDrawView;

import static android.animation.ValueAnimator.RESTART;

public class PageFragment extends Fragment {
    @LayoutRes int sampleLayoutRes;
    @LayoutRes int practiceLayoutRes;

    public static PageFragment newInstance(@LayoutRes int sampleLayoutRes, @LayoutRes int practiceLayoutRes) {
        PageFragment fragment = new PageFragment();
        Bundle args = new Bundle();
        args.putInt("sampleLayoutRes", sampleLayoutRes);
        args.putInt("practiceLayoutRes", practiceLayoutRes);
        fragment.setArguments(args);
        return fragment;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_page, container, false);

        ViewStub sampleStub = (ViewStub) view.findViewById(R.id.sampleStub);
        sampleStub.setLayoutResource(sampleLayoutRes);
        sampleStub.inflate();

        ViewStub practiceStub = (ViewStub) view.findViewById(R.id.practiceStub);
        practiceStub.setLayoutResource(practiceLayoutRes);
        practiceStub.inflate();

        if (practiceLayoutRes == R.layout.practice_after_on_draw) {
            Practice01AfterOnDrawView animationView = (Practice01AfterOnDrawView) view.findViewById(R.id.one);
            ObjectAnimator animator = ObjectAnimator.ofFloat(animationView, "progress", 0, 60);
            animator.setInterpolator(new LinearInterpolator());
            animator.start();
        }

        return view;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Bundle args = getArguments();
        if (args != null) {
            sampleLayoutRes = args.getInt("sampleLayoutRes");
            practiceLayoutRes = args.getInt("practiceLayoutRes");
        }
    }
}
