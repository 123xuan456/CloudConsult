/*
 * Copyright 2017 JessYan
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.mtm.cloudconsult.app.config;

import android.app.Application;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;

import com.jess.arms.base.delegate.AppLifecycles;
import com.jess.arms.di.module.GlobalConfigModule;
import com.jess.arms.http.log.FormatPrinter;
import com.jess.arms.http.log.RequestInterceptor;
import com.jess.arms.integration.ConfigModule;
import com.jess.arms.utils.ArmsUtils;
import com.mtm.cloudconsult.BuildConfig;
import com.mtm.cloudconsult.app.api.Api;
import com.squareup.leakcanary.RefWatcher;

import java.util.List;
import java.util.concurrent.TimeUnit;

import me.jessyan.progressmanager.ProgressManager;
import me.jessyan.retrofiturlmanager.RetrofitUrlManager;
import okhttp3.MediaType;
import okhttp3.Request;
import timber.log.Timber;

/**
 * ================================================
 * App 的全局配置信息在此配置,需要将此实现类声明到 AndroidManifest 中
 * ConfigModule 的实现类可以有无数多个, 在 Application 中只是注册回调, 并不会影响性能 (多个 ConfigModule 在多 Module 环境下尤为受用)
 * ConfigModule 接口的实现类对象是通过反射生成的, 这里会有些性能损耗
 *
 * @see <a href="https://github.com/JessYanCoding/MVPArms/wiki">请配合官方 Wiki 文档学习本框架 (Arms 的文档除了 MVP 部分, 其他的文档内容 Art 和 Arms 都可以共用)</a>
 * @see <a href="https://github.com/JessYanCoding/MVPArms/wiki/Issues">常见 Issues, 踩坑必看!</a>
 * Created by JessYan on 12/04/2017 17:25
 * <a href="mailto:jess.yan.effort@gmail.com">Contact me</a>
 * <a href="https://github.com/JessYanCoding">Follow me</a>
 * ================================================
 */
public final class GlobalConfiguration implements ConfigModule {
    @Override
    public void applyOptions(@NonNull Context context, @NonNull GlobalConfigModule.Builder builder) {
        if (!BuildConfig.LOG_DEBUG) { //Release 时, 让框架不再打印 Http 请求和响应的信息
            builder.printHttpLogLevel(RequestInterceptor.Level.NONE);
        }
        //想支持多 BaseUrl, 以及运行时动态切换任意一个 BaseUrl, 请使用 https://github.com/JessYanCoding/RetrofitUrlManager
        //若觉得框架默认的打印格式并不能满足自己的需求, 可自行扩展自己理想的打印格式 (以下只是简单实现)
        builder.baseurl(Api.API_GANKIO)
                .formatPrinter(new FormatPrinter() {
                    @Override
                    public void printJsonRequest(Request request, String bodyString) {
                        Timber.i("printJsonRequest:" + bodyString);
                    }

                    @Override
                    public void printFileRequest(Request request) {
                        Timber.i("printFileRequest:" + request.url().toString());
                    }

                    @Override
                    public void printJsonResponse(long chainMs, boolean isSuccessful, int code,
                                                  String headers, MediaType contentType, String bodyString,
                                                  List<String> segments, String message, String responseUrl) {
                        Timber.i("printJsonResponse:" + bodyString);
                    }

                    @Override
                    public void printFileResponse(long chainMs, boolean isSuccessful, int code, String headers,
                                                  List<String> segments, String message, String responseUrl) {
                        Timber.i("printFileResponse:" + responseUrl);
                    }
                })
                //这里提供一个全局处理 Http 请求和响应结果的处理类, 可以比客户端提前一步拿到服务器返回的结果, 可以做一些操作, 比如 Token 超时后, 重新获取 Token
                .globalHttpHandler(new GlobalHttpHandlerImpl(context))
                //用来处理 RxJava 中发生的所有错误, RxJava 中发生的每个错误都会回调此接口
                //RxJava 必须要使用 ErrorHandleSubscriber (默认实现 Subscriber 的 onError 方法), 此监听才生效
                .responseErrorListener(new ResponseErrorListenerImpl())
                .gsonConfiguration((context1, gsonBuilder) -> {//这里可以自己自定义配置 Gson 的参数
                    gsonBuilder
                            .serializeNulls()//支持序列化值为 null 的参数
                            .enableComplexMapKeySerialization();//支持将序列化 key 为 Object 的 Map, 默认只能序列化 key 为 String 的 Map
                })
                .okhttpConfiguration((context1, okhttpBuilder) -> {//这里可以自己自定义配置 Okhttp 的参数
//                    okhttpBuilder.sslSocketFactory(); //支持 Https, 详情请百度
                    okhttpBuilder.writeTimeout(10, TimeUnit.SECONDS);
                    //使用一行代码监听 Retrofit／Okhttp 上传下载进度监听,以及 Glide 加载进度监听, 详细使用方法请查看 https://github.com/JessYanCoding/ProgressManager
                    ProgressManager.getInstance().with(okhttpBuilder);
                    //让 Retrofit 同时支持多个 BaseUrl 以及动态改变 BaseUrl, 详细使用方法请查看 https://github.com/JessYanCoding/RetrofitUrlManager
                    RetrofitUrlManager.getInstance().with(okhttpBuilder);
                });
    }

    @Override
    public void injectAppLifecycle(@NonNull Context context, @NonNull List<AppLifecycles> lifecycles) {
        lifecycles.add(new AppLifecyclesImpl());
    }

    @Override
    public void injectActivityLifecycle(@NonNull Context context, @NonNull List<Application.ActivityLifecycleCallbacks> lifecycles) {
        //ActivityLifecycleCallbacks 中的所有方法都会在 Activity (包括三方库) 的对应生命周期中被调用, 所以在对应的方法中可以扩展一些自己需要的逻辑
        //可以根据不同的逻辑添加多个实现类
        lifecycles.add(new ActivityLifecycleCallbacksImpl());
    }

    @Override
    public void injectFragmentLifecycle(Context context, List<FragmentManager.FragmentLifecycleCallbacks> lifecycles) {
        lifecycles.add(new FragmentManager.FragmentLifecycleCallbacks() {

            @Override
            public void onFragmentCreated(FragmentManager fm, Fragment f, Bundle savedInstanceState) {
                // 在配置变化的时候将这个 Fragment 保存下来,在 Activity 由于配置变化重建时重复利用已经创建的 Fragment。
                // https://developer.android.com/reference/android/app/Fragment.html?hl=zh-cn#setRetainInstance(boolean)
                // 如果在 XML 中使用 <Fragment/> 标签,的方式创建 Fragment 请务必在标签中加上 android:id 或者 android:tag 属性,否则 setRetainInstance(true) 无效
                // 在 Activity 中绑定少量的 Fragment 建议这样做,如果需要绑定较多的 Fragment 不建议设置此参数,如 ViewPager 需要展示较多 Fragment
                f.setRetainInstance(true);
            }

            @Override
            public void onFragmentDestroyed(FragmentManager fm, Fragment f) {
                ((RefWatcher) ArmsUtils
                        .obtainAppComponentFromContext(f.getActivity())
                        .extras()
                        .get("Keep=" + RefWatcher.class.getName()))
                        .watch(f);
            }
        });
    }
}
