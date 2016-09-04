package com.example.santa.timeout;

import android.animation.Animator;
import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.animation.ValueAnimator;
import android.content.Context;
import android.util.AttributeSet;
import android.view.animation.AnticipateOvershootInterpolator;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.LinkedList;
import java.util.List;

/**
 * Created by santa on 16/8/26.
 */
public class TimeViewFlicker extends TimeViewComm {

    public TimeViewFlicker(Context context) {
        super(context);
    }

    public TimeViewFlicker(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public TimeViewFlicker(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    public TimeViewFlicker(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
    }

    @Override
    protected void setTime(String hour, String minute, String second) {
        List<Animator> animators = new LinkedList<>();
        if (isChanged(hour, mHours)) {
            ValueAnimator scaleX = ObjectAnimator.ofFloat(mHours, "ScaleX", 1f, 0.7f, 1f);
            ValueAnimator scaleY = ObjectAnimator.ofFloat(mHours, "ScaleY", 1f, 0.7f, 1f);
            animators.add(scaleX);
            animators.add(scaleY);
        }
        if (isChanged(minute, mMinutes)) {
            ValueAnimator scaleX = ObjectAnimator.ofFloat(mMinutes, "ScaleX", 1f, 0.7f, 1f);
            ValueAnimator scaleY = ObjectAnimator.ofFloat(mMinutes, "ScaleY", 1f, 0.7f, 1f);
            animators.add(scaleX);
            animators.add(scaleY);
        }
        if (isChanged(second, mSeconds)) {
            ValueAnimator scaleX = ObjectAnimator.ofFloat(mSeconds, "ScaleX", 1f, 0.7f, 1f);
            ValueAnimator scaleY = ObjectAnimator.ofFloat(mSeconds, "ScaleY", 1f, 0.7f, 1f);
            animators.add(scaleX);
            animators.add(scaleY);
        }

        AnimatorSet animatorSet = new AnimatorSet();
        animatorSet.playTogether(animators);
        animatorSet.setInterpolator(new AnticipateOvershootInterpolator());
        animatorSet.setDuration(500);
        animatorSet.start();
        super.setTime(hour, minute, second);

    }

    private boolean isChanged(String time, TextView timeView) {
        return !timeView.getText().equals(time);
    }


}
