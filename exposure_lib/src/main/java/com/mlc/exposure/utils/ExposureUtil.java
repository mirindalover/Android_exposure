package com.mlc.exposure.utils;

import android.support.annotation.Keep;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.AbsListView;

import com.mlc.exposure.listener.ExposureListenerForListView;
import com.mlc.exposure.listener.ExposureListenerForRecyclerView;
import com.mlc.exposure.listener.ExposureListenerForScrollView;


/**
 * Created by mulianchao on 2019/1/22.
 * <p>
 * 用于获取曝光的listener
 * 防止混淆后无法寻找对应的listener
 */
@Keep
public class ExposureUtil {

    public static ExposureListenerForListView createListViewListener(AbsListView listView) {
        return new ExposureListenerForListView(listView);
    }

    public static ExposureListenerForRecyclerView createRecyclerViewListener(RecyclerView recyclerView) {
        return new ExposureListenerForRecyclerView(recyclerView);
    }

    public static ExposureListenerForScrollView createScrollViewListener(View view) {
        return new ExposureListenerForScrollView(view);
    }


}
