package com.mlc.sample.adapter;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;


import com.mlc.sample.R;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by mulianchao on 2018/10/15.
 */

public class MainItemAdapter extends BaseAdapter {


    private final Context mContext;

    public MainItemAdapter(Context context) {
        mContext = context;
    }

    @Override
    public int getCount() {
        return mData.size();
    }

    @Override
    public Object getItem(int position) {
        return mData.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder viewHolder;
        if (convertView == null) {
            convertView = View.inflate(mContext, R.layout.adapter_main, null);
            viewHolder = new ViewHolder(convertView);
            convertView.setTag(R.id.view_holder,viewHolder);
        }else {
            viewHolder = (ViewHolder) convertView.getTag(R.id.view_holder);
        }
        ItemBean itemBean = mData.get(position);
        viewHolder.mText.setText(itemBean.name);
        convertView.setTag(itemBean.clazz);
        return convertView;
    }

    private List<ItemBean> mData = new ArrayList<>();

    public void setData(List<ItemBean> data) {
        if (data == null || data.isEmpty()) {
            return;
        }
        mData.clear();
        mData.addAll(data);
    }

    class ViewHolder {
        TextView mText;

        public ViewHolder(View item) {
            mText = item.findViewById(R.id.m_item_text);
        }
    }
}
