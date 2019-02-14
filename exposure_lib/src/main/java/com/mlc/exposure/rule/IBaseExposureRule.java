package com.mlc.exposure.rule;

/**
 * Created by mulianchao on 2019/1/24.
 * 规则Base
 */

public interface IBaseExposureRule {

    /**
     * @param itemData item的数据
     * @param position position
     * @return ItemID的规则(唯一id)
     */
    String createItemID(Object itemData, int position);
}
