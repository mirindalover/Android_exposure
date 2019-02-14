package com.mlc.exposure.rule;

import android.view.View;

import com.mlc.exposure.R;


/**
 * Created by mulianchao on 2019/1/23.
 * <p>
 * 可以自己配置的曝光规则
 */

public class ExposureRuleForScrollView extends BaseExposureRule implements IExposureRuleForScrollView {

    private static ExposureRuleForScrollView mInstance = new ExposureRuleForScrollView();

    private ExposureRuleForScrollView() {
    }

    public static ExposureRuleForScrollView getInstance() {
        return mInstance;
    }

    @Override
    public Object getExposureTag(View view) {
        return view.getTag(R.id.exposure_item);
    }
}
