package com.mtm.cloudconsult.app.base;

import com.jess.arms.mvp.BasePresenter;
import com.jess.arms.mvp.IModel;
import com.jess.arms.mvp.IView;
import com.jess.arms.utils.RxLifecycleUtils;
import com.mtm.cloudconsult.app.api.CloudConstant;
import com.mtm.cloudconsult.app.utils.NetWorkUtils;

import java.util.List;

import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.annotations.NonNull;
import io.reactivex.schedulers.Schedulers;
import me.jessyan.rxerrorhandler.core.RxErrorHandler;
import me.jessyan.rxerrorhandler.handler.ErrorHandleSubscriber;
import me.jessyan.rxerrorhandler.handler.RetryWithDelay;

/**
 * 公共处理网络请求
 * @param <M>
 * @param <V>
 */
public abstract class BaseRecyclePresenter<M extends IModel, V extends IView> extends BasePresenter<M, V> {

    protected int pregPage = 15;
    public BaseRecyclePresenter(M model, V rootView) {
        this.mModel = model;
        this.mRootView = rootView;
        onStart();
    }

    public void setPregPage(int pregPage) {
        this.pregPage = pregPage;
    }

    public <T extends BaseEntityBean> void getDataList(Observable<List<T>> observable, List<BaseEntityBean> mData, BaseRecycleIView view, RxErrorHandler mErrorHandler, boolean pullToRefresh){
        observable
                .subscribeOn(Schedulers.io())
                .retryWhen(new RetryWithDelay(1, 2))//遇到错误时重试,第一个参数为重试几次,第二个参数为重试的间隔
                .doOnSubscribe(disposable -> {
                    if (pullToRefresh)
                        view.showLoading();//显示上拉刷新的进度条
                }).subscribeOn(AndroidSchedulers.mainThread())
                .observeOn(AndroidSchedulers.mainThread())
                .doAfterTerminate(() -> {
                    if (pullToRefresh){
                        view.hideLoading();//隐藏上拉刷新的进度条
                    }
                })
                .compose(RxLifecycleUtils.bindToLifecycle(mRootView))//使用Rxlifecycle,使Disposable和Activity一起销毁
                .subscribe(new ErrorHandleSubscriber<List<T>>(mErrorHandler) {
                    @Override
                    public void onError(@NonNull Throwable e) {
                        super.onError(e);
                        NetWorkUtils.onListError(e,view,pullToRefresh);
                    }
                    @Override
                    public void onNext(List<T> data) {
                        if (pullToRefresh) mData.clear();//如果是上拉刷新则清空列表
                        if(pullToRefresh){
                            view.showLoadSirView(CloudConstant.LoadSir.SUCCESS);
                            mData.addAll(data);
                            view.refreshUI(mData);
                            if(data==null || data.isEmpty()){
                                view.showLoadSirView(CloudConstant.LoadSir.EMPTY);
                            }
                        }else{
                            //mUsers.addAll(users);
                            boolean hasMore = true;
                            if(data==null || data.size()<pregPage){
                                hasMore = false;
                            }
                            view.loadMore(data,hasMore);
                        }
                    }
                });
    }

}
