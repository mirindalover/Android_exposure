## 曝光

> 支持的类型可见 ExposureUtil

### ListView

使用方法见ListViewExposureActivity

```java
//创建listView滑动监听
ExposureListenerForListView exposureListenerForListView = ExposureUtil.createListViewListener(listView);
//将listView的滑动监听交给ExposureListenerForListView
listView.setOnScrollListener(exposureListenerForListView);
```
监听listView的滑动，来处理曝光逻辑
```java
exposureListenerForListView.setExposureListener(new OnExposureListener() {
    @Override
    public void onExposure(List<ExposureBean> exposureBeans) {
        //上报
    }
});
```
监听曝光,上报埋点

### RecyclerView

使用方法见RecyclerViewExposureActivity

```java
ExposureListenerForRecyclerView exposureListenerForRecyclerView = ExposureUtil.createRecyclerViewListener(mRecycler);
mRecycler.setOnScrollListener(exposureListenerForRecyclerView);
```
监听RecyclerView的滑动，处理曝光逻辑

```java
exposureListenerForRecyclerView.setExposureListener(new OnExposureListener() {
    @Override
    public void onExposure(List<ExposureBean> exposureBeans) {
        //上报
    }
});
```
监听曝光,上报埋点

### ScrollView

使用方法见ScrollViewExposureActivity

- 在对应的想要曝光的view上设置tag(默认为R.id.exposure_item)，也可以通过自定义Rule实现

```java
ExposureListenerForScrollView exposureListenerForScrollView = ExposureUtil.createScrollViewListener(mScroll);
mScroll.setOnScrollListener(exposureListenerForScrollView);
```
监听ScrollView的滑动，处理曝光逻辑

> 由于ScrollView没有滑动状态的监听，需要自己实现滑动状态的改变监听

> ScrollView推荐使用ExposureScrollView(处理了滑动的监听)

```java
exposureListenerForScrollView.setExposureListener(new OnExposureListener() {
    @Override
    public void onExposure(List<ExposureBean> exposureBeans) {
        //上报
    }
});
```
监听曝光，上报埋点

### 曝光其他设置

- 推荐在生命周期中设置view的可见性，达到上报的准确性

- 推荐在对应生命周期重置曝光 

```java
@Override
protected void onResume() {
    super.onResume();
    exposureListener.setVisible(true);
    exposureListenerForListView.resetExposure();
}

@Override
protected void onStop() {
    super.onStop();
    exposureListener.setVisible(false);
}
```

### 扩展方法

- 可自定义规则:通过setExposureRule()来自定义










