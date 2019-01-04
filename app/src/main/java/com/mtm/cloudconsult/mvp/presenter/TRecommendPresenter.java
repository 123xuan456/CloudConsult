package com.mtm.cloudconsult.mvp.presenter;

import android.app.Application;

import com.jess.arms.di.scope.FragmentScope;
import com.jess.arms.http.imageloader.ImageLoader;
import com.jess.arms.integration.AppManager;
import com.jess.arms.mvp.BasePresenter;
import com.jess.arms.utils.LogUtils;
import com.mtm.cloudconsult.mvp.contract.TRecommendContract;
import com.mtm.cloudconsult.mvp.model.bean.FrontpageBean;

import javax.inject.Inject;

import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Action;
import io.reactivex.functions.Consumer;
import io.reactivex.schedulers.Schedulers;
import me.jessyan.rxerrorhandler.core.RxErrorHandler;
import me.jessyan.rxerrorhandler.handler.ErrorHandleSubscriber;


@FragmentScope
public class TRecommendPresenter extends BasePresenter<TRecommendContract.Model, TRecommendContract.View> {
    @Inject
    RxErrorHandler mErrorHandler;
    @Inject
    Application mApplication;
    @Inject
    ImageLoader mImageLoader;
    @Inject
    AppManager mAppManager;

    @Inject
    public TRecommendPresenter(TRecommendContract.Model model, TRecommendContract.View rootView) {
        super(model, rootView);
    }
    /**
     * 数据请求-轮播图
     */
    public void showBannerPage() {
        Observable<FrontpageBean> requestInfo = mModel.showBannerPage();
        requestInfo.subscribeOn(Schedulers.io())
                //在执行任务之前 do some thing ...
                .doOnSubscribe(new Consumer<Disposable>() {
                    @Override
                    public void accept(Disposable disposable) throws Exception {
                    }
                })
                //doOnSubscribe 切换在主线程
                .subscribeOn(AndroidSchedulers.mainThread())
                //在主线程回调
                .observeOn(AndroidSchedulers.mainThread())
                //任务结束 do some thing ...
                .doFinally(new Action() {
                    @Override
                    public void run() throws Exception {
                        if (mRootView != null) {
                            mRootView.hideLoading();
                        }
                    }
                })
                .subscribe(new ErrorHandleSubscriber<FrontpageBean>(mErrorHandler) {
                    @Override
                    public void onNext(FrontpageBean frontpageBean) {
                        //加载异常判断
                        LogUtils.warnInfo(frontpageBean.getError_code()+"");
                    }

                    @Override
                    public void onError(Throwable t) {
                        super.onError(t);
                    }
                });
    }



    @Override
    public void onDestroy() {
        super.onDestroy();
        this.mErrorHandler = null;
        this.mAppManager = null;
        this.mImageLoader = null;
        this.mApplication = null;
    }
}
