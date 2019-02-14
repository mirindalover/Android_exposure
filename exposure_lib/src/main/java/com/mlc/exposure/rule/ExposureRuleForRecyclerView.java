package com.mlc.exposure.rule;

import android.support.v7.widget.RecyclerView;

/**
 * Created by mulianchao on 2019/1/23.
 * <p>
 * 可以自己配置的曝光规则
 */

public class ExposureRuleForRecyclerView extends BaseExposureRule implements IExposureRuleForRecyclerView {

    private static ExposureRuleForRecyclerView mInstance = new ExposureRuleForRecyclerView();

    private ExposureRuleForRecyclerView() {
    }

    public static ExposureRuleForRecyclerView getInstance() {
        return mInstance;
    }

    @Override
    public int[] getRealVisibleItems(RecyclerView listView, int startPosition, int endPosition) {
        return new int[]{startPosition, endPosition};
    }
}
