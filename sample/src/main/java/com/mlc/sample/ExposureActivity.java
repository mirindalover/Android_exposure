package com.mlc.sample;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import com.mlc.sample.adapter.ItemBean;
import com.mlc.sample.adapter.MainItemAdapter;

import java.util.ArrayList;
import java.util.List;

public class ExposureActivity extends AppCompatActivity implements AdapterView.OnItemClickListener {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        ListView mList = findViewById(R.id.m_list);

        MainItemAdapter adapter = new MainItemAdapter(this);
        List<ItemBean> data = initData();
        adapter.setData(data);
        mList.setAdapter(adapter);
        mList.setOnItemClickListener(this);
    }

    private List<ItemBean> initData() {
        ArrayList<ItemBean> data = new ArrayList<>();
        data.add(new ItemBean("ListView的曝光", ListViewExposureActivity.class));
        data.add(new ItemBean("RecyclerView的曝光", RecyclerViewExposureActivity.class));
        data.add(new ItemBean("ViewPager的曝光", ViewPagerExposureActivity.class));
        data.add(new ItemBean("ScrollView的曝光", ScrollViewExposureActivity.class));
        return data;
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        Object tag = view.getTag();
        if (tag != null && tag instanceof Class) {
            Intent intent = new Intent(this, (Class) tag);
            startActivity(intent);
        }
    }
}
