package com.mlc.exposure.view;

import android.content.Context;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.widget.ScrollView;

import com.mlc.exposure.listener.OnScrollListener;
import com.mlc.exposure.manager.ScrollHandler;


/**
 * Created by mulianchao on 2019/1/28.
 */

public class ExposureScrollView extends ScrollView {


    public ExposureScrollView(Context context) {
        super(context);
    }

    public ExposureScrollView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public ExposureScrollView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    private ScrollHandler mHandleScroll = new ScrollHandler(this);

    @Override
    public boolean onTouchEvent(MotionEvent ev) {
        mHandleScroll.handleTouchEvent(ev);
        return super.onTouchEvent(ev);
    }

    @Override
    public boolean onInterceptTouchEvent(MotionEvent ev) {
        mHandleScroll.handleInterceptTouchEvent(ev);
        return super.onInterceptTouchEvent(ev);
    }

    @Override
    protected void onScrollChanged(int l, int t, int oldl, int oldt) {
        super.onScrollChanged(l, t, oldl, oldt);
        mHandleScroll.handleScrollChanged();
    }

    public void setOnScrollListener(OnScrollListener scrollListener) {
       mHandleScroll.setOnScrollListener(scrollListener);
    }

}
