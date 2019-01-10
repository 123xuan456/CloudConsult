package com.mtm.cloudconsult.app.base;

import android.content.Context;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.os.Build;
import android.os.StrictMode;
import android.support.annotation.RequiresApi;
import android.util.Log;

import com.jess.arms.base.BaseApplication;
import com.tencent.bugly.Bugly;
import com.tencent.bugly.beta.Beta;

/**
 * Created by 绍轩 on 2019/1/5.
 */

public class BaseAppliction extends BaseApplication {
    private static BaseAppliction baseAppliction;

    public static BaseAppliction getInstance() {
        return baseAppliction;
    }

    @Override
    protected void attachBaseContext(Context base) {
        super.attachBaseContext(base);
        // 安装tinker热部署
        Beta.installTinker();
    }

    @RequiresApi(api = Build.VERSION_CODES.GINGERBREAD)
    @Override
    public void onCreate() {
        super.onCreate();
        baseAppliction = this;
        initTextSize();
        /**
         * 初始化热更新
         */
        initBuglyTinker();

    }

    @RequiresApi(api = Build.VERSION_CODES.GINGERBREAD)
    private void initBuglyTinker() {
        /**
         * Bugly热更新
         */
        // 设置是否开启热更新能力，默认为true
            Beta.enableHotfix = true;
            // 设置是否自动下载补丁
            Beta.canAutoDownloadPatch = true;
            // 设置是否提示用户重启
            Beta.canNotifyUserRestart = false;
            // 设置是否自动合成补丁
            Beta.canAutoPatch = true;
            StrictMode.setThreadPolicy(new StrictMode.ThreadPolicy.Builder().permitAll().build());
            StrictMode.setVmPolicy(new StrictMode.VmPolicy.Builder().detectAll().penaltyLog().build());
            /**
             * 补丁回调接口，可以监听补丁接收、下载、合成的回调
             */
//        Beta.betaPatchListener = new BetaPatchListener() {
//            @Override
//            public void onPatchReceived(String patchFileUrl) {
//                Toast.makeText(getInstance(), patchFileUrl, Toast.LENGTH_SHORT).show();
//            }
//
//            @Override
//            public void onDownloadReceived(long savedLength, long totalLength) {
//                Toast.makeText(getInstance(), String.format(Locale.getDefault(),
//                        "%s %d%%",
//                        Beta.strNotificationDownloading,
//                        (int) (totalLength == 0 ? 0 : savedLength * 100 / totalLength)), Toast.LENGTH_SHORT).show();
//            }
//
//            @Override
//            public void onDownloadSuccess(String patchFilePath) {
//                Toast.makeText(getInstance(), patchFilePath, Toast.LENGTH_SHORT).show();
////                Beta.applyDownloadedPatch();
//            }
//
//            @Override
//            public void onDownloadFailure(String msg) {
//                Toast.makeText(getInstance(), msg, Toast.LENGTH_SHORT).show();
//            }
//
//            @Override
//            public void onApplySuccess(String msg) {
//                Toast.makeText(getInstance(), msg, Toast.LENGTH_SHORT).show();
//            }
//
//            @Override
//            public void onApplyFailure(String msg) {
//                Toast.makeText(getInstance(), msg, Toast.LENGTH_SHORT).show();
//            }
//
//            @Override
//            public void onPatchRollback() {
//                Toast.makeText(getInstance(), "onPatchRollback", Toast.LENGTH_SHORT).show();
//            }
//        };
            // 设置开发设备，默认为false，下发范围指定为“全量设备”
            // true 表示下发范围是开发设备
            Bugly.setIsDevelopmentDevice(getInstance(), false);
            long start = System.currentTimeMillis();
            // 这里实现SDK初始化，appId替换成你的在Bugly平台申请的appId,调试时将第三个参数设置为true
            Bugly.init(getInstance(), "dbcd0e5879", true);
            long end = System.currentTimeMillis();
            Log.e("init time--->", end - start + "ms");


        /**
         * TinkerPatch 热更新
         */
//        if (BuildConfig.TINKER_ENABLE) {
//            ApplicationLike tinkerApplicationLike = TinkerPatchApplicationLike.getTinkerPatchApplicationLike();
//            // 初始化TinkerPatch SDK
//            TinkerPatch.init(tinkerApplicationLike)
//                    .reflectPatchLibrary()
//                    .setPatchRollbackOnScreenOff(true)
//                    .setPatchRestartOnSrceenOff(true)
//                    .setFetchPatchIntervalByHours(3);
//            // 获取当前的补丁版本
//            Log.d("", "当前版本： " + TinkerPatch.with().getPatchVersion());
//            // fetchPatchUpdateAndPollWithInterval 与 fetchPatchUpdate(false)
//            // 不同的是，会通过handler的方式去轮询
//            TinkerPatch.with().fetchPatchUpdateAndPollWithInterval();
//        }
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
