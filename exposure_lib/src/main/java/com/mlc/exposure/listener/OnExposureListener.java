package com.mlc.exposure.listener;

import com.mlc.exposure.bean.ExposureBean;

import java.util.List;

/**
 * Created by mulianchao on 2019/1/24.
 */

public interface OnExposureListener {

    void onExposure(List<ExposureBean> exposureBeans);
}
