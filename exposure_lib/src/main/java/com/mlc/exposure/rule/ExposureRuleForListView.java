package com.mlc.exposure.rule;

import android.widget.AbsListView;

/**
 * Created by mulianchao on 2019/1/23.
 * <p>
 * 可以自己配置的曝光规则
 */

public class ExposureRuleForListView extends BaseExposureRule implements IExposureRuleForListView {

    private static ExposureRuleForListView mInstance = new ExposureRuleForListView();

    private ExposureRuleForListView() {
    }

    public static ExposureRuleForListView getInstance() {
        return mInstance;
    }

    @Override
    public int[] getRealVisibleItems(AbsListView listView, int startPosition, int endPosition) {
        return new int[]{startPosition, endPosition};
    }
}
