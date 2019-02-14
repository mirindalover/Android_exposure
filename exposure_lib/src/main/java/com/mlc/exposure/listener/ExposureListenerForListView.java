package com.mlc.exposure.listener;

import android.os.Handler;
import android.os.Looper;
import android.view.View;
import android.widget.AbsListView;

import com.mlc.exposure.bean.ExposureBean;
import com.mlc.exposure.manager.ExposureManager;
import com.mlc.exposure.rule.ExposureRuleForListView;
import com.mlc.exposure.rule.IExposureRuleForListView;
import com.mlc.exposure.utils.ArrayUtils;

import java.util.ArrayList;


/**
 * Created by mulianchao on 2019/1/23.
 * <p>
 * TODO 后续考虑使用AOP模式不侵入app的开发
 */

public class ExposureListenerForListView implements AbsListView.OnScrollListener {

    private final AbsListView mListView;
    private int mFirstVisibleItem;
    private int mVisibleItemCount;
    private final ExposureManager mManager;

    private Handler mHandler = new Handler(Looper.getMainLooper());
    private Handler mExposureHandler = new Handler(Looper.getMainLooper());


    private static final int FIND_EXPOSURE_DELAY_TIME = 300;
    private boolean mVisible = true;
    private IExposureRuleForListView mRule;
    private OnExposureListener mListener;

    public ExposureListenerForListView(AbsListView view) {
        mListView = view;
        mManager = new ExposureManager();
        mRule = ExposureRuleForListView.getInstance();
    }

    @Override
    public void onScrollStateChanged(AbsListView view, int scrollState) {
        switch (scrollState) {
            case SCROLL_STATE_IDLE:
                findScreenVisibleViewsAndNotify(view, mFirstVisibleItem, mVisibleItemCount);
                break;
        }
    }

    @Override
    public void onScroll(AbsListView view, int firstVisibleItem, int visibleItemCount, int totalItemCount) {
        mFirstVisibleItem = firstVisibleItem;
        mVisibleItemCount = visibleItemCount;
    }


    private void findScreenVisibleViewsAndNotify(AbsListView view, int firstVisibleItem, int visibleItemCount) {
        fixVisibleViewAndNotify(view, firstVisibleItem, firstVisibleItem + visibleItemCount - 1);
    }

    private void fixVisibleViewAndNotify(AbsListView listView, int firstVisibleItem, int lastVisibleItem) {
        ArrayList<ExposureBean> result;
        if (mVisible) {
            result = new ArrayList<ExposureBean>();
            //使用规则获取真正的可见item
            int[] realVisibleItems = mRule.getRealVisibleItems(listView, firstVisibleItem, lastVisibleItem);
            firstVisibleItem = realVisibleItems[0];
            lastVisibleItem = realVisibleItems[1];

            int listChildCount = listView.getChildCount();
            int firstVisiblePosition = listView.getFirstVisiblePosition();
            for (int position = firstVisibleItem; position <= lastVisibleItem; ++position) {
                int childViewIndex = position - firstVisiblePosition;
                if (childViewIndex < listChildCount) {
                    View itemView = listView.getChildAt(childViewIndex);
                    Object itemData = listView.getItemAtPosition(position);
                    if (mManager.addResource(mRule.createItemID(itemData, position))) {
                        result.add(new ExposureBean(itemData, itemView, position));
                    }
                }
            }
            notifyExposure(result);
        }
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
                fixVisibleViewAndNotify(mListView, mListView.getFirstVisiblePosition(), mListView.getLastVisiblePosition());
            }
        }, FIND_EXPOSURE_DELAY_TIME);
    }

    /**
     * 用于设置listView的可见性，默认可见
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
    public void setExposureRule(IExposureRuleForListView exposureRule) {
        mRule = exposureRule;
    }

}
