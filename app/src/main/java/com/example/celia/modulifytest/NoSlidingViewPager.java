package com.example.celia.modulifytest;

import android.content.Context;
import android.support.v4.view.ViewPager;
import android.util.AttributeSet;
import android.view.MotionEvent;

public class NoSlidingViewPager extends ViewPager {
    public NoSlidingViewPager(Context context, AttributeSet attrs) {
        super(context, attrs);
    }
    public NoSlidingViewPager(Context context) {
        super(context);
    }
    @Override
    public boolean onInterceptTouchEvent(MotionEvent arg0) {
        return false;
    }
    public boolean onTouchEvent(MotionEvent arg0) {
        return false;
    }

    @Override
    public void setCurrentItem(int item) {
        super.setCurrentItem(item);
        getAdapter().notifyDataSetChanged();
    }
}
