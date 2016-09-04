package com.example.santa.timeout;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Color;
import android.util.AttributeSet;
import android.util.DisplayMetrics;
import android.util.Log;
import android.util.TypedValue;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.text.DecimalFormat;

/**
 * Created by santa on 16/8/26.
 */
public class TimeViewScroll extends LinearLayout {

    private ScrollText mHourHigh;
    private ScrollText mHourLow;
    private ScrollText mMinuteHigh;
    private ScrollText mMinuteLow;
    private ScrollText mSecHigh;
    private ScrollText mSecLow;

    private int mTextColor = Color.WHITE;
    private int mBackgroundColor = Color.BLACK;
    private int mTextSize = 50;
    private int mPaddingHorizontal = 4;

    private TimeoutManager mTimeoutManager;
    private DecimalFormat df = new DecimalFormat("00");

    private enum DIR{LEFT, MID, RIGHT};

    public TimeViewScroll(Context context) {
        this(context, null);
    }

    public TimeViewScroll(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public TimeViewScroll(Context context, AttributeSet attrs, int defStyleAttr) {
        this(context, attrs, defStyleAttr, 0);
    }

    public TimeViewScroll(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr);
        setOrientation(HORIZONTAL);

        DisplayMetrics dm = context.getResources().getDisplayMetrics();
        mTextSize = (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_SP, mTextSize, dm);
        mPaddingHorizontal = (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, mPaddingHorizontal, dm);

        TypedArray array = context.obtainStyledAttributes(attrs, R.styleable.TimeViewScroll);
        mTextColor = array.getColor(R.styleable.TimeViewScroll_tvs_textColor, mTextColor);
        mBackgroundColor = array.getColor(R.styleable.TimeViewScroll_tvs_backgroundColor, mBackgroundColor);
        mTextSize = array.getDimensionPixelSize(R.styleable.TimeViewScroll_tvs_textSize, mTextSize);
        mPaddingHorizontal = array.getDimensionPixelSize(R.styleable.TimeViewScroll_tvs_textPaddingHorizantal, mPaddingHorizontal);
        array.recycle();


        LayoutParams layoutParams = new LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        mHourHigh = createScrollText(context, layoutParams, mTextColor, mBackgroundColor, mTextSize, DIR.LEFT);
        mHourLow = createScrollText(context, layoutParams, mTextColor, mBackgroundColor, mTextSize, DIR.RIGHT);
        createSpace(context, layoutParams);

        mMinuteHigh = createScrollText(context, layoutParams, mTextColor, mBackgroundColor, mTextSize, DIR.LEFT);
        mMinuteLow = createScrollText(context, layoutParams, mTextColor, mBackgroundColor, mTextSize, DIR.RIGHT);
        createSpace(context, layoutParams);

        mSecHigh = createScrollText(context, layoutParams, mTextColor, mBackgroundColor, mTextSize, DIR.LEFT);
        mSecLow = createScrollText(context, layoutParams, mTextColor, mBackgroundColor, mTextSize, DIR.RIGHT);

        startTime(33, 44, 55);
    }

    private void createSpace(Context context, LayoutParams layoutParams) {
        TextView textView = new TextView(context);
        textView.setLayoutParams(layoutParams);
        textView.setTextSize(TypedValue.COMPLEX_UNIT_PX, mTextSize);
        textView.setTextColor(Color.BLACK);
        textView.setText(":");
        addView(textView);
    }

    private ScrollText createScrollText(Context context, LayoutParams layoutParams, int textColor,
                                  int backgroundColor, int textSize, DIR dir) {
        ScrollText scrollText = new ScrollText(context);
        scrollText.setLayoutParams(layoutParams);
        scrollText.setTextSize(textSize);
        scrollText.setTextColor(textColor);
        scrollText.setBackgroundColor(backgroundColor);
        addView(scrollText);
        if (dir == DIR.LEFT) {
            scrollText.setPadding(mPaddingHorizontal, 0, 0, 0);
        } else if (dir == DIR.RIGHT) {
            scrollText.setPadding(0, 0, mPaddingHorizontal, 0);
        }
        return scrollText;
    }



    public void startTime(int hour, int minutes, int second) {

        if (null == mTimeoutManager) {
            mTimeoutManager = new TimeoutManager(hour, minutes, second, new TimeoutManager.OnTimeRunListener() {
                @Override
                public void onTimeRun(int hour, int minute, int second) {
                    setTime(df.format(hour), df.format(minute), df.format(second));
                }
            });
        } else {
            mTimeoutManager.resetTime(hour, minutes, second);
        }
    }


    private void setTime(String hour, String minute, String second) {
        goTime(hour, mHourHigh, mHourLow);
        goTime(minute, mMinuteHigh, mMinuteLow);
        goTime(second, mSecHigh, mSecLow);
    }

    private void goTime(String time, ScrollText high, ScrollText low) {

        String timeHigh = time.substring(0, 1);
        String timeLow = time.substring(1, 2);
        if (!high.getCurText().equals(timeHigh)) {
            high.scrollToText(timeHigh);
        }
        if (!low.getCurText().equals(timeLow)) {
            low.scrollToText(timeLow);
        }

    }

}
