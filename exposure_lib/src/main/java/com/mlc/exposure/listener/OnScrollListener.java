package com.mlc.exposure.listener;

import android.view.View;

/**
 * Created by mulianchao on 2019/1/28.
 */

public interface OnScrollListener {


    /**
     * The view is not scrolling. Note navigating the list using the trackball counts as
     * being in the idle state since these transitions are not animated.
     */
    int SCROLL_STATE_IDLE = 0;

    /**
     * The user is scrolling using touch, and their finger is still on the screen
     */
    int SCROLL_STATE_TOUCH_SCROLL = 1;

    /**
     * The user had previously been scrolling using touch and had performed a fling. The
     * animation is now coasting to a stop
     */
    int SCROLL_STATE_FLING = 2;


    void onScrollStateChanged(View view, int scrollState);
}
