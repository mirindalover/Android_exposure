package com.mlc.exposure.rule;

/**
 * Created by mulianchao on 2019/1/25.
 */

public class BaseExposureRule implements IBaseExposureRule {
    @Override
    public String createItemID(Object itemData, int position) {
        if (itemData == null) {
            return String.valueOf(position);
        }
        return String.valueOf(itemData.hashCode()) + position;
    }
}
