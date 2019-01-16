package com.mtm.cloudconsult.mvp.presenter;

import android.annotation.SuppressLint;
import android.app.Application;

import com.jess.arms.di.scope.FragmentScope;
import com.jess.arms.http.imageloader.ImageLoader;
import com.jess.arms.integration.AppManager;
import com.jess.arms.mvp.BasePresenter;
import com.mtm.cloudconsult.app.api.CloudConstant;
import com.mtm.cloudconsult.mvp.contract.TFriendContract;
import com.mtm.cloudconsult.mvp.model.bean.GankIoDataBean;

import java.util.ArrayList;

import javax.inject.Inject;

import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Action;
import io.reactivex.functions.Consumer;
import io.reactivex.functions.Function;
import io.reactivex.schedulers.Schedulers;
import me.jessyan.rxerrorhandler.core.RxErrorHandler;
import me.jessyan.rxerrorhandler.handler.ErrorHandleSubscriber;


@FragmentScope
public class TFriendPresenter extends BasePresenter<TFriendContract.Model, TFriendContract.View> {
    @Inject
    RxErrorHandler mErrorHandler;
    @Inject
    Application mApplication;
    @Inject
    ImageLoader mImageLoader;
    @Inject
    AppManager mAppManager;
    /**
     * 图片url集合
     */
    private ArrayList<String> imgList = new ArrayList<>();
    /**
     * 图片标题集合，用于保存图片时使用
     */
    private ArrayList<String> imageTitleList = new ArrayList<>();
    /**
     * 传递给Activity数据集合
     */
    private ArrayList<ArrayList<String>> allList = new ArrayList<>();
    private int mPage = 1;

    @Inject
    public TFriendPresenter(TFriendContract.Model model, TFriendContract.View rootView) {
        super(model, rootView);
    }

    public void loadCustomData(boolean isEvictCache) {
        Observable<GankIoDataBean> requestInfo = mModel.getGankIoData("福利", mPage, isEvictCache);
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
                            handleImageList(gankIoDataBean);
                            mRootView.showLoadSirView(CloudConstant.LoadSir.SUCCESS);
                        }
                    }

                    @Override
                    public void onError(Throwable t) {
                        super.onError(t);
                        if (mRootView != null) {
                            if (mPage > 1) {
                                mPage--;
                            } else if (mPage < 1) {
                                mRootView.showLoadSirView(CloudConstant.LoadSir.ERROR);
                            } else {
                                mRootView.showLoadSirView(CloudConstant.LoadSir.SUCCESS);
                            }
                            mRootView.showListError();
                        }
                    }
                });

    }

    /**
     * 异步处理用于图片详情显示的数据
     *
     * @param gankIoDataBean 原数据
     */
    @SuppressLint("CheckResult")
    private void handleImageList(GankIoDataBean gankIoDataBean) {
        Observable.just(gankIoDataBean)
                .subscribeOn(Schedulers.io())
                .map(new Function<GankIoDataBean, ArrayList<ArrayList<String>>>() {
                    @Override
                    public ArrayList<ArrayList<String>> apply(GankIoDataBean gankIoDataBean) throws Exception {
                        imgList.clear();
                        imageTitleList.clear();
                        for (int i = 0; i < gankIoDataBean.getResults().size(); i++) {
                            imgList.add(gankIoDataBean.getResults().get(i).getUrl());
                            imageTitleList.add(gankIoDataBean.getResults().get(i).getDesc());
                        }
                        allList.clear();
                        allList.add(imgList);
                        allList.add(imageTitleList);
                        return allList;
                    }
                })
                //doOnSubscribe 切换在主线程
                .subscribeOn(AndroidSchedulers.mainThread())
                //在主线程回调
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Consumer<ArrayList<ArrayList<String>>>() {
                    @Override
                    public void accept(ArrayList<ArrayList<String>> arrayLists) throws Exception {
                        if (mRootView != null) {
                            mRootView.setImageList(arrayLists);
                        }
                    }

                }, new Consumer<Throwable>() {
                    @Override
                    public void accept(Throwable throwable) throws Exception {
                        throwable.getMessage();
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
