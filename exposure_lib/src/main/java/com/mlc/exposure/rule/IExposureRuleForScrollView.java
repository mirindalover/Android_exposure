package com.mlc.exposure.rule;

import android.view.View;

/**
 * Created by mulianchao on 2019/1/23.
 * 规则接口
 */

public interface IExposureRuleForScrollView extends IBaseExposureRule {

    /**
     * 获取曝光的tag
     */
    Object getExposureTag(View view);

}
