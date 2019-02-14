package com.mlc.exposure.listener;

import android.os.Handler;
import android.os.Looper;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.mlc.exposure.bean.ExposureBean;
import com.mlc.exposure.manager.ExposureManager;
import com.mlc.exposure.rule.ExposureRuleForRecyclerView;
import com.mlc.exposure.rule.IExposureRuleForRecyclerView;
import com.mlc.exposure.utils.ArrayUtils;

import java.util.ArrayList;

/**
 * Created by mulianchao on 2019/1/24.
 */

public class ExposureListenerForRecyclerView extends RecyclerView.OnScrollListener {

    private final RecyclerView mRecyclerView;
    private final ExposureManager mManager;

    private Handler mHandler = new Handler(Looper.getMainLooper());
    private Handler mExposureHandler = new Handler(Looper.getMainLooper());


    private static final int FIND_EXPOSURE_DELAY_TIME = 300;
    private boolean mVisible = true;
    private IExposureRuleForRecyclerView mRule;
    private OnExposureListener mListener;

    public ExposureListenerForRecyclerView(RecyclerView view) {
        mRecyclerView = view;
        mManager = new ExposureManager();
        mRule = ExposureRuleForRecyclerView.getInstance();
    }

    @Override
    public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
        switch (newState) {
            case RecyclerView.SCROLL_STATE_IDLE:
                findScreenVisibleViewsAndNotify(recyclerView);
                break;
        }
    }

    private void findScreenVisibleViewsAndNotify(RecyclerView recyclerView) {
        if (mVisible) {
            RecyclerView.LayoutManager layoutManager = recyclerView.getLayoutManager();
            if (layoutManager instanceof LinearLayoutManager) {
                LinearLayoutManager linearLayoutManager = (LinearLayoutManager) layoutManager;
                int firstVisibleItem = linearLayoutManager.findFirstVisibleItemPosition();
                int lastVisibleItem = linearLayoutManager.findLastVisibleItemPosition();
                this.fixVisibleViewAndNotify(recyclerView, firstVisibleItem, lastVisibleItem);
            }
        }
    }

    private void fixVisibleViewAndNotify(RecyclerView recyclerView, int firstVisibleItem, int lastVisibleItem) {
        ArrayList<ExposureBean> result ;
        result = new ArrayList<ExposureBean>();
        //使用规则获取真正的可见item
        int[] realVisibleItems = mRule.getRealVisibleItems(recyclerView, firstVisibleItem, lastVisibleItem);
        int realFirstVisibleItem = realVisibleItems[0];
        lastVisibleItem = realVisibleItems[1];

        int listChildCount = recyclerView.getChildCount();
        RecyclerView.Adapter adapter = recyclerView.getAdapter();
        Object itemData;
        for (int position = realFirstVisibleItem; position <= lastVisibleItem; ++position) {
            int childViewIndex = position - firstVisibleItem;
            if (childViewIndex < listChildCount) {
                View itemView = recyclerView.getChildAt(childViewIndex);

                if (adapter != null && adapter instanceof IRecyclerViewAdapter) {
                    itemData = ((IRecyclerViewAdapter) adapter).getItemData(position);
                } else {
                    itemData = null;
                }
                if (mManager.addResource(mRule.createItemID(itemData, position))) {
                    result.add(new ExposureBean(itemData, itemView, position));
                }
            }
        }
        notifyExposure(result);
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
                findScreenVisibleViewsAndNotify(mRecyclerView);
            }
        }, FIND_EXPOSURE_DELAY_TIME);
    }

    /**
     * 用于设置recyclerView的可见性，默认可见
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
    public void setExposureRule(IExposureRuleForRecyclerView exposureRule) {
        mRule = exposureRule;
    }
}
