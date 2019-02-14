package com.mlc.exposure.listener;

/**
 * Created by mulianchao on 2019/1/24.
 * RecyclerView的Adapter来实现，用来获取item的数据
 */

public interface IRecyclerViewAdapter {
    /**
     * 根据position获取item的数据
     */
    Object getItemData(int position);
}
