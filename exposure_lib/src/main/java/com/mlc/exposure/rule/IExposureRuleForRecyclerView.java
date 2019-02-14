package com.mlc.exposure.rule;

import android.support.v7.widget.RecyclerView;

/**
 * Created by mulianchao on 2019/1/23.
 * 规则接口
 */

public interface IExposureRuleForRecyclerView extends IBaseExposureRule {

    /**
     * 获取真正可见的position
     *
     * @return 长度为2的数组，1.start 2.end
     */
    int[] getRealVisibleItems(RecyclerView listView, int startPosition, int endPosition);
}
