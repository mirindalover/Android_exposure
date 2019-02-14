package com.mlc.sample;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.mlc.exposure.bean.ExposureBean;
import com.mlc.exposure.listener.ExposureListenerForListView;
import com.mlc.exposure.listener.OnExposureListener;
import com.mlc.exposure.utils.ExposureUtil;

import java.util.ArrayList;
import java.util.List;

public class ListViewExposureActivity extends AppCompatActivity {

    private ExposureListenerForListView exposureListenerForListView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        ListView mList = findViewById(R.id.m_list);


        ArrayAdapter<String> data = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, initData());
        mList.setAdapter(data);

        exposureListenerForListView = ExposureUtil.createListViewListener(mList);
        exposureListenerForListView.setExposureListener(new OnExposureListener() {
            @Override
            public void onExposure(List<ExposureBean> exposureBeans) {
                for (ExposureBean temp : exposureBeans) {
                    Log.e("Exposure", "onExposure: " + temp);
                }
            }
        });
        mList.setOnScrollListener(exposureListenerForListView);
    }

    @Override
    protected void onResume() {
        super.onResume();
        exposureListenerForListView.setVisible(true);
        exposureListenerForListView.resetExposure();
    }

    @Override
    protected void onStop() {
        super.onStop();
        exposureListenerForListView.setVisible(false);
    }

    private List<String> initData() {
        ArrayList<String> strings = new ArrayList<>();
        for (int i = 0; i < 20; i++) {
            strings.add("item" + i);
        }
        return strings;
    }
}
