package com.mlc.sample;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.mlc.exposure.manager.ExposureManager;

import java.util.ArrayList;
import java.util.List;

/**
 * ViewPager自己管理曝光即可
 */
public class ViewPagerExposureActivity extends AppCompatActivity {

    private ExposureManager exposureManager;
    private ViewPager mViewPage;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_pager_exposure);

        mViewPage = findViewById(R.id.m_viewPage);


        MyPagerAdapter pagerAdapter = new MyPagerAdapter();
        mViewPage.setAdapter(pagerAdapter);

        mViewPage.setOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                if (exposureManager.addResource(String.valueOf(position))) {
                    reportExposure(position);
                }
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });

        pagerAdapter.setViews(initView());

        exposureManager = new ExposureManager();

        mViewPage.setCurrentItem(0);
    }

    @Override
    protected void onResume() {
        super.onResume();
        exposureManager.resetExposure();

        int position = mViewPage.getCurrentItem();
        exposureManager.addResource(String.valueOf(position));
        reportExposure(position);
    }

    private void reportExposure(int position) {
        Log.e("Exposure", "viewPager: " + position);
    }

    private List<View> initView() {
        ArrayList<View> views = new ArrayList<>();
        for (int i = 0; i < 5; i++) {
            TextView textView = new TextView(this);
            textView.setText("item" + i);
            views.add(textView);
        }
        return views;
    }

    static class MyPagerAdapter extends PagerAdapter {

        private List<View> mViews = new ArrayList<>();

        @Override
        public int getCount() {
            return mViews.size();
        }

        @Override
        public boolean isViewFromObject(@NonNull View view, @NonNull Object object) {
            return view == object;
        }

        @NonNull
        @Override
        public Object instantiateItem(@NonNull ViewGroup container, int position) {
            container.addView(mViews.get(position));
            return mViews.get(position);
        }

        @Override
        public void destroyItem(@NonNull ViewGroup container, int position, @NonNull Object object) {
            View view = mViews.get(position);
            container.removeView(view);
        }

        public void setViews(List<View> views) {
            mViews.clear();
            mViews.addAll(views);
            notifyDataSetChanged();
        }
    }
}
