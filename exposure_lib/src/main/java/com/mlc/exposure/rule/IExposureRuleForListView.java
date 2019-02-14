package com.mlc.exposure.rule;

import android.widget.AbsListView;

/**
 * Created by mulianchao on 2019/1/23.
 * 规则接口
 */

public interface IExposureRuleForListView extends IBaseExposureRule {

    /**
     * 获取真正可见的position
     *
     * @return 长度为2的数组，1.start 2.end
     */
    int[] getRealVisibleItems(AbsListView listView, int startPosition, int endPosition);
}
