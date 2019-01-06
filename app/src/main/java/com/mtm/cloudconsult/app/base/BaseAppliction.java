package com.mtm.cloudconsult.app.base;

import android.content.res.Configuration;
import android.content.res.Resources;

import com.jess.arms.base.BaseApplication;

/**
 * Created by 绍轩 on 2019/1/5.
 */

public class BaseAppliction extends BaseApplication {
    private static BaseAppliction baseAppliction;

    public static BaseAppliction getInstance() {
        return baseAppliction;
    }

    @SuppressWarnings("unused")
    @Override
    public void onCreate() {
        super.onCreate();
        baseAppliction = this;
        initTextSize();
    }

    /**
     * 使其系统更改字体大小无效
     */
    private void initTextSize() {
        Resources res = getResources();
        Configuration config = new Configuration();
        config.setToDefaults();
        res.updateConfiguration(config, res.getDisplayMetrics());
    }

}
