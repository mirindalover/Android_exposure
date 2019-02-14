package com.mlc.exposure.bean;

import android.view.View;

/**
 * Created by mulianchao on 2019/1/23.
 */

public class ExposureBean {

    public Object itemData;

    public View itemView;

    public int position;


    public ExposureBean(Object itemData, View itemView, int position) {
        this.itemData = itemData;
        this.itemView = itemView;
        this.position = position;
    }

    @Override
    public String toString() {
        return "ExposureBean{" +
                "itemData=" + itemData +
                ", itemView=" + itemView +
                ", position=" + position +
                '}';
    }
}
