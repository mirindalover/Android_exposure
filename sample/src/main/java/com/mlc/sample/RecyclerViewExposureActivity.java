package com.mlc.sample;

import android.content.Context;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;


import com.mlc.exposure.bean.ExposureBean;
import com.mlc.exposure.listener.ExposureListenerForRecyclerView;
import com.mlc.exposure.listener.IRecyclerViewAdapter;
import com.mlc.exposure.listener.OnExposureListener;
import com.mlc.exposure.utils.ExposureUtil;
import com.mlc.sample.adapter.RecyclerDivider;

import java.util.ArrayList;
import java.util.List;

public class RecyclerViewExposureActivity extends AppCompatActivity {

    private ExposureListenerForRecyclerView exposureListenerForRecyclerView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recycler_view_exposure);

        RecyclerView mRecycler = findViewById(R.id.m_recycler);

        mRecycler.setLayoutManager(new LinearLayoutManager(this));

        mRecycler.addItemDecoration(new RecyclerDivider(this, LinearLayoutManager.HORIZONTAL));
        MyAdapter myAdapter = new MyAdapter(this);
        mRecycler.setAdapter(myAdapter);
        myAdapter.setData(initData());


        exposureListenerForRecyclerView = ExposureUtil.createRecyclerViewListener(mRecycler);
        mRecycler.setOnScrollListener(exposureListenerForRecyclerView);

        exposureListenerForRecyclerView.setExposureListener(new OnExposureListener() {
            @Override
            public void onExposure(List<ExposureBean> exposureBeans) {
                for (ExposureBean temp : exposureBeans) {
                    Log.e("Exposure", "onExposure: " + temp);
                }
            }
        });
    }

    @Override
    protected void onResume() {
        super.onResume();
        exposureListenerForRecyclerView.setVisible(true);
        exposureListenerForRecyclerView.resetExposure();
    }

    @Override
    protected void onStop() {
        super.onStop();
        exposureListenerForRecyclerView.setVisible(false);
    }

    private List<String> initData() {
        ArrayList<String> strings = new ArrayList<>();
        for (int i = 0; i < 20; i++) {
            strings.add("item" + i);
        }
        return strings;
    }


    static class MyAdapter extends RecyclerView.Adapter<MyViewHolder> implements IRecyclerViewAdapter {

        private final Context mContext;

        private List<String> mData = new ArrayList<>();

        public MyAdapter(Context context) {
            mContext = context;
        }


        @Override
        public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            View inflate = View.inflate(mContext, android.R.layout.simple_list_item_1, null);
            return new MyViewHolder(inflate);
        }

        @Override
        public void onBindViewHolder(MyViewHolder holder, int position) {
            holder.text.setText(mData.get(position));
        }

        @Override
        public int getItemCount() {
            return mData.size();
        }

        public void setData(List<String> strings) {
            mData.clear();
            mData.addAll(strings);
            notifyDataSetChanged();
        }

        @Override
        public Object getItemData(int position) {
            return mData.get(position);
        }
    }

    static class MyViewHolder extends RecyclerView.ViewHolder {

        private final TextView text;

        public MyViewHolder(View itemView) {
            super(itemView);
            text = itemView.findViewById(android.R.id.text1);
        }
    }


}
