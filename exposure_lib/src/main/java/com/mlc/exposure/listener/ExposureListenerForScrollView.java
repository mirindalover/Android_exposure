package com.mlc.exposure.listener;

import android.graphics.Rect;
import android.os.Handler;
import android.os.Looper;
import android.support.v4.util.ArrayMap;
import android.view.View;
import android.view.ViewGroup;

import com.mlc.exposure.bean.ExposureBean;
import com.mlc.exposure.manager.ExposureManager;
import com.mlc.exposure.rule.ExposureRuleForScrollView;
import com.mlc.exposure.rule.IExposureRuleForScrollView;
import com.mlc.exposure.utils.ArrayUtils;

import java.util.ArrayList;
import java.util.Map;

/**
 * Created by mulianchao on 2019/1/23.
 */

public class ExposureListenerForScrollView implements OnScrollListener {

    private final ExposureManager mManager;
    private final View mView;

    private Handler mHandler = new Handler(Looper.getMainLooper());
    private Handler mExposureHandler = new Handler(Looper.getMainLooper());


    private static final int FIND_EXPOSURE_DELAY_TIME = 300;
    private boolean mVisible = true;
    private IExposureRuleForScrollView mRule;
    private OnExposureListener mListener;

    public ExposureListenerForScrollView(View view) {
        mView = view;
        mManager = new ExposureManager();
        mRule = ExposureRuleForScrollView.getInstance();
    }


    private void findScreenVisibleViewsAndNotify(View view) {
        ArrayList<ExposureBean> result;
        if (mVisible) {
            result = new ArrayList<ExposureBean>();

            Map<String, ExposureBean> currentVisibleViewMap = new ArrayMap<String, ExposureBean>();
            wrapVisibleViews(view, currentVisibleViewMap);

            for (String temp : currentVisibleViewMap.keySet()) {
                if (mManager.addResource(temp)) {
                    result.add(currentVisibleViewMap.get(temp));
                }
            }
            notifyExposure(result);
        }
    }

    /**
     * 把所有可见的view，根据rule放入currentVisibleViewMap
     */
    private void wrapVisibleViews(View view, Map<String, ExposureBean> currentVisibleViewMap) {
        if (hasExposureTag(view)) {
            wrapExposureCurrentView(view, currentVisibleViewMap);
        }

        if (view instanceof ViewGroup) {
            ViewGroup group = (ViewGroup) view;
            int childCount = group.getChildCount();
            for (int i = 0; i < childCount; i++) {//寻找所有子类的带TAG
                wrapVisibleViews(group.getChildAt(i), currentVisibleViewMap);
            }
        }
    }

    private void wrapExposureCurrentView(View view, Map<String, ExposureBean> currentVisibleViewMap) {
        boolean isWindowChange = view.hasWindowFocus();
        boolean exposureValid = checkExposureViewDimension(view);
        boolean needExposureProcess = isWindowChange && exposureValid;
        if (!needExposureProcess) {
            return;
        }
        currentVisibleViewMap.put(mRule.createItemID(view, 0), new ExposureBean(mRule.getExposureTag(view), view, 0));
    }

    private boolean checkExposureViewDimension(View view) {//TODO 可在这里添加曝光的面积规则
        Rect GlobalVisibleRect = new Rect();
        return view.getGlobalVisibleRect(GlobalVisibleRect);
    }

    private boolean hasExposureTag(View view) {
        return mRule.getExposureTag(view) != null;
    }

    private void notifyExposure(final ArrayList<ExposureBean> result) {
        if (mListener == null || ArrayUtils.isEmpty(result)) {
            return;
        }
        mExposureHandler.post(new Runnable() {
            @Override
            public void run() {
                mListener.onExposure(result);
            }
        });
    }

    /**
     * 设置曝光的监听
     */
    public void setExposureListener(OnExposureListener listener) {
        mListener = listener;
    }

    /**
     * 重置曝光
     * <p>
     * 调用场景:onResume、接口请求完毕
     */
    public void resetExposure() {
        mManager.resetExposure();
        mHandler.removeCallbacksAndMessages(null);
        mHandler.postDelayed(new Runnable() {
            @Override
            public void run() {
                findScreenVisibleViewsAndNotify(mView);
            }
        }, FIND_EXPOSURE_DELAY_TIME);
    }

    /**
     * 用于设置View的可见性，默认可见
     * <p>
     * <p>
     * 调用场景：Fragment或者Activity不可见时设置不可见，防止曝光初始化并没有在可见的情况下(通过接口的请求数据初始化的曝光)
     * <p>
     * <p>
     * 原因：view的attach方法不能准确判定是否可见
     *
     * @param visible 可见
     */
    public void setVisible(boolean visible) {
        mVisible = visible;
    }

    /**
     * 设置曝光的规则
     */
    public void setExposureRule(IExposureRuleForScrollView exposureRule) {
        mRule = exposureRule;
    }

    @Override
    public void onScrollStateChanged(View view, int scrollState) {
        switch (scrollState) {
            case SCROLL_STATE_IDLE:
                findScreenVisibleViewsAndNotify(view);
                break;
        }
    }
}
