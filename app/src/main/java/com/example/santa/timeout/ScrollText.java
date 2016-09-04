package com.example.santa.timeout;

import android.content.Context;
import android.graphics.Color;
import android.util.AttributeSet;
import android.util.DisplayMetrics;
import android.util.Log;
import android.util.TypedValue;
import android.view.MotionEvent;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.TextView;

import java.util.LinkedList;
import java.util.List;

/**
 * Created by santa on 16/8/26.
 */
public class ScrollText extends ScrollView {
    private Context mContext;
    private LinearLayout mContainer;
    private LinearLayout.LayoutParams mDefLayoutParams;


    private int mTextColor = Color.WHITE;
    private int mBackgroundColor = Color.BLACK;
    private int mTextSize = 50;

    private List<String> mTextList;

    private int mHeight;

    public ScrollText(Context context) {
        this(context, null);
    }

    public ScrollText(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public ScrollText(Context context, AttributeSet attrs, int defStyleAttr) {
        this(context, attrs, defStyleAttr, 0);
    }

    public ScrollText(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr);
        mContext = context;

        setBackgroundColor(mBackgroundColor);
        DisplayMetrics dm = context.getResources().getDisplayMetrics();
        mTextSize = (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_SP, mTextSize, dm);

        mDefLayoutParams = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        mContainer = new LinearLayout(context);
        mContainer.setLayoutParams(mDefLayoutParams);
        mContainer.setOrientation(LinearLayout.VERTICAL);
        addView(mContainer);

        initHeight();
        initTexts();
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        setMeasuredDimension(MeasureSpec.makeMeasureSpec(getMeasuredWidth(), MeasureSpec.getMode(widthMeasureSpec)), MeasureSpec.makeMeasureSpec(mHeight, MeasureSpec.EXACTLY));
    }

    @Override
    public boolean onTouchEvent(MotionEvent ev) {
        return false;
    }

    public void setTextColor(int color) {
        mTextColor = color;
        int count = mContainer.getChildCount();
        for (int i = 0 ; i < count; i++) {
            TextView textView = (TextView) mContainer.getChildAt(i);
            textView.setTextColor(color);
        }
    }

    public void setTextSize(int textSize) {
        mTextSize = textSize;
        initHeight();
        int count = mContainer.getChildCount();
        for (int i = 0 ; i < count; i++) {
            TextView textView = (TextView) mContainer.getChildAt(i);
            textView.setTextSize(TypedValue.COMPLEX_UNIT_PX, textSize);
        }
    }

    private void initHeight() {
        TextView t = new TextView(mContext);
        t.setTextSize(TypedValue.COMPLEX_UNIT_PX, mTextSize);
        t.measure(MeasureSpec.makeMeasureSpec(0, MeasureSpec.UNSPECIFIED), MeasureSpec.makeMeasureSpec(0, MeasureSpec.UNSPECIFIED));
        mHeight = t.getMeasuredHeight();
    }

    private void initTexts(){
        mTextList = new LinkedList<>();
        for (int i = 0 ; i <= 9; i++) {
            addText(""+i);
            mTextList.add(""+i);
        }
    }

    public void addText(String text) {
        TextView textView = new TextView(mContext);
        textView.setLayoutParams(mDefLayoutParams);
        textView.setText(text);
        textView.setTextColor(mTextColor);
        textView.setTextSize(TypedValue.COMPLEX_UNIT_PX, mTextSize);
        mContainer.addView(textView);
    }

    public void scrollToText(String string) {
        for (int i = 0; i<mTextList.size(); i++) {
            if (mTextList.get(i).equals(string)) {
                smoothScrollTo(0, (getHeight()*i));
            }
        }
    }

    public String getCurText() {
        int index = getScrollY()/getHeight();
        return mTextList.get(index);
    }



}
