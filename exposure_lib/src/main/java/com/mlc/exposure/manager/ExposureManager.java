package com.mlc.exposure.manager;


import java.util.ArrayList;
import java.util.List;

/**
 * Created by mulianchao on 2019/1/23.
 */

public class ExposureManager {

    private List<String> mExposureIDs = new ArrayList<>();

    public boolean addResource(String resource) {
        if (mExposureIDs.contains(resource)) {
            return false;
        }
        mExposureIDs.add(resource);
        return true;
    }


    public void resetExposure() {
        mExposureIDs.clear();
    }

}
