package com.mtm.cloudconsult.mvp.presenter;

import android.app.Application;

import com.jess.arms.di.scope.FragmentScope;
import com.jess.arms.http.imageloader.ImageLoader;
import com.jess.arms.integration.AppManager;
import com.jess.arms.mvp.BasePresenter;
import com.mtm.cloudconsult.app.api.CloudConstant;
import com.mtm.cloudconsult.app.utils.SPUtils;
import com.mtm.cloudconsult.mvp.contract.TRadioContract;
import com.mtm.cloudconsult.mvp.model.bean.GankIoDataBean;

import javax.inject.Inject;

import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Action;
import io.reactivex.functions.Consumer;
import io.reactivex.schedulers.Schedulers;
import me.jessyan.rxerrorhandler.core.RxErrorHandler;
import me.jessyan.rxerrorhandler.handler.ErrorHandleSubscriber;

import static com.mtm.cloudconsult.app.api.CloudConstant.GANK_CALA;


@FragmentScope
public class TRadioPresenter extends BasePresenter<TRadioContract.Model, TRadioContract.View> {
    @Inject
    RxErrorHandler mErrorHandler;
    @Inject
    Application mApplication;
    @Inject
    ImageLoader mImageLoader;
    @Inject
    AppManager mAppManager;
    private int mPage = 1;
    @Inject
    public TRadioPresenter(TRadioContract.Model model, TRadioContract.View rootView) {
        super(model, rootView);
    }

    public void loadCustomData(boolean isEvictCache) {
        String type = SPUtils.getString(GANK_CALA, "全部");
        if ("全部".equals(type)) {
            type = "all";
        } else if ("IOS".equals(type)) {
            type = "iOS";
        }
        Observable<GankIoDataBean> requestInfo = mModel.getGankIoData(type,mPage, isEvictCache);
        requestInfo.subscribeOn(Schedulers.io())
                //在执行任务之前 do some thing ...
                .doOnSubscribe(new Consumer<Disposable>() {
                    @Override
                    public void accept(Disposable disposable) throws Exception {
                        if (mRootView != null) {
//                            mRootView.showLoadSirView(CloudConstant.LoadSir.LOADING);
                        }
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

                    }
                })
                .subscribe(new ErrorHandleSubscriber<GankIoDataBean>(mErrorHandler) {
                    @Override
                    public void onNext(GankIoDataBean gankIoDataBean) {
                        if (mRootView != null) {
                            if (mPage == 1) {
                                if (gankIoDataBean == null || gankIoDataBean.getResults() == null || gankIoDataBean.getResults().size() <= 0) {
                                    //获取数据为空
                                    mRootView.showLoadSirView(CloudConstant.LoadSir.ERROR);
                                    return;
                                }
                            } else {
                                if (gankIoDataBean == null || gankIoDataBean.getResults() == null || gankIoDataBean.getResults().size() <= 0) {
                                    //没有更多数据
                                    mRootView.showListNoMoreLoading();
                                    mRootView.showLoadSirView(CloudConstant.LoadSir.SUCCESS);
                                    return;
                                }
                            }
                            mRootView.setGankIoData(gankIoDataBean);
                            mRootView.showLoadSirView(CloudConstant.LoadSir.SUCCESS);
                        }
                    }

                    @Override
                    public void onError(Throwable t) {
                        super.onError(t);
                        if (mRootView != null) {
                            if (mPage > 1) {
                                mPage--;
                            }else if (mPage < 1){
                                mRootView.showLoadSirView(CloudConstant.LoadSir.ERROR);
                            }else {
                                mRootView.showLoadSirView(CloudConstant.LoadSir.SUCCESS);
                            }
                            mRootView.showListError();
                        }
                    }
                });

    }
    public int getPage() {
        return mPage;
    }

    public void setPage(int mPage) {
        this.mPage = mPage;
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
