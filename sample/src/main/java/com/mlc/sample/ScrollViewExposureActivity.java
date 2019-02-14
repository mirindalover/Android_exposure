package com.mlc.sample;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;

import com.mlc.exposure.bean.ExposureBean;
import com.mlc.exposure.listener.ExposureListenerForScrollView;
import com.mlc.exposure.listener.OnExposureListener;
import com.mlc.exposure.utils.ExposureUtil;
import com.mlc.exposure.view.ExposureScrollView;

import java.util.List;

public class ScrollViewExposureActivity extends AppCompatActivity {

    private ExposureScrollView mScroll;
    private ExposureListenerForScrollView exposureListenerForScrollView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_scroll_view_exposure);
        initView();
    }

    private void initView() {
        View mText1 = findViewById(R.id.mText2);
        mText1.setTag(R.id.exposure_item, "text2");
        View mText2 = findViewById(R.id.mText6);
        mText2.setTag(R.id.exposure_item, "text6");
        View mImage1 = findViewById(R.id.image1);
        mImage1.setTag(R.id.exposure_item, "广告曝光");
        mScroll = findViewById(R.id.mScroll);

        exposureListenerForScrollView = ExposureUtil.createScrollViewListener(mScroll);
        exposureListenerForScrollView.setExposureListener(new OnExposureListener() {
            @Override
            public void onExposure(List<ExposureBean> exposureBeans) {
                for (ExposureBean temp : exposureBeans) {
                    Log.e("Exposure", "onExposure: " + temp);
                }
            }
        });
        mScroll.setOnScrollListener(exposureListenerForScrollView);
    }

    @Override
    protected void onResume() {
        super.onResume();
        exposureListenerForScrollView.setVisible(true);
        exposureListenerForScrollView.resetExposure();
    }

    @Override
    protected void onStop() {
        super.onStop();
        exposureListenerForScrollView.setVisible(false);
    }
}
