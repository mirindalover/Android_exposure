package com.mlc.exposure.manager;

import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.view.MotionEvent;
import android.view.View;

import com.mlc.exposure.listener.OnScrollListener;

import static com.mlc.exposure.listener.OnScrollListener.SCROLL_STATE_FLING;
import static com.mlc.exposure.listener.OnScrollListener.SCROLL_STATE_IDLE;
import static com.mlc.exposure.listener.OnScrollListener.SCROLL_STATE_TOUCH_SCROLL;


/**
 * Created by mulianchao on 2019/1/28.
 * 判断滑动的状态
 */

public class ScrollHandler {

    private static final int CHECK_SCROLL_STOP_DELAY_MILLIS = 80;
    private static final int MSG_SCROLL = 1;
    private final View mView;

    private boolean mIsTouched;
    private int mScrollState;


    private final Handler mHandler = new Handler(Looper.getMainLooper(), new Handler.Callback() {

        private int mLastY = Integer.MIN_VALUE;

        @Override
        public boolean handleMessage(Message msg) {
            if (msg.what == MSG_SCROLL) {
                final int scrollY = mView.getScrollY();
                if (mLastY == scrollY) {
                    mLastY = Integer.MIN_VALUE;
                    setScrollState(SCROLL_STATE_IDLE);
                } else {
                    mLastY = scrollY;
                    restartCheckStopTiming();
                }
                return true;
            }
            return false;
        }
    });
    private OnScrollListener mScrollListener;

    public ScrollHandler(View view) {
        mView = view;
    }

    private void setScrollState(int scrollState) {
        if (mScrollState != scrollState) {
            mScrollState = scrollState;
            if (mScrollListener != null) {
                mScrollListener.onScrollStateChanged(mView, scrollState);
            }
        }
    }


    /**
     * 手指抬起后，检查是否真正停止
     */
    private void restartCheckStopTiming() {
        mHandler.removeMessages(MSG_SCROLL);
        mHandler.sendEmptyMessageDelayed(MSG_SCROLL, CHECK_SCROLL_STOP_DELAY_MILLIS);
    }

    /**
     * 根据 onTouchEvent来处理逻辑
     */
    public void handleTouchEvent(MotionEvent ev) {
        switch (ev.getAction()) {
            case MotionEvent.ACTION_UP:
            case MotionEvent.ACTION_CANCEL:
                mIsTouched = false;
//                Log.e("测试", "setScrollState:  UP");
                restartCheckStopTiming();
                break;
        }
    }

    /**
     * onScrollChanged时调用
     */
    public void handleScrollChanged() {
//        Log.e("测试", "setScrollState:  handleScrollChanged");
        if (mIsTouched) {
            setScrollState(SCROLL_STATE_TOUCH_SCROLL);
        } else {
            setScrollState(SCROLL_STATE_FLING);
            restartCheckStopTiming();
        }
    }

    /**
     * 根据 onInterceptTouchEvent来处理逻辑
     */
    public void handleInterceptTouchEvent(MotionEvent ev) {
        switch (ev.getAction()) {
            case MotionEvent.ACTION_DOWN:
                mIsTouched = true;
//                Log.e("测试", "setScrollState:  DOWN");
                break;
        }
    }

    public void setOnScrollListener(OnScrollListener scrollListener) {
        mScrollListener = scrollListener;
    }
}
