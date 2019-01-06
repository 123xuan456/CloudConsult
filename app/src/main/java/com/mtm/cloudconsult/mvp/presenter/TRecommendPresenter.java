package com.mtm.cloudconsult.mvp.presenter;

import android.app.Application;

import com.jess.arms.di.scope.FragmentScope;
import com.jess.arms.http.imageloader.ImageLoader;
import com.jess.arms.integration.AppManager;
import com.jess.arms.mvp.BasePresenter;
import com.mtm.cloudconsult.mvp.contract.TRecommendContract;
import com.mtm.cloudconsult.mvp.model.bean.FrontpageBean;

import java.util.ArrayList;

import javax.inject.Inject;

import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
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
    private ArrayList<String> mBannerImages;
    @Inject
    public TRecommendPresenter(TRecommendContract.Model model, TRecommendContract.View rootView) {
        super(model, rootView);
    }
    /**
     * 数据请求-轮播图
     *
     */
    public void showBannerPage(int dynamicKey,boolean updat) {
        Observable<FrontpageBean> requestInfo = mModel.showBannerPage(dynamicKey,updat);
        requestInfo.observeOn(AndroidSchedulers.mainThread()).subscribeOn(Schedulers.io())
                .subscribe(new ErrorHandleSubscriber<FrontpageBean>(mErrorHandler) {
                    @Override
                    public void onNext(FrontpageBean bean) {

                        if (mBannerImages == null) {
                            mBannerImages = new ArrayList<String>();
                        } else {
                            mBannerImages.clear();
                        }
                        if (bean != null && bean.getResult() != null && bean.getResult().getFocus() != null && bean.getResult().getFocus().getResult() != null) {
                            final ArrayList<FrontpageBean.ResultBannerBean.FocusBean.ResultBeanX> result = (ArrayList<FrontpageBean.ResultBannerBean.FocusBean.ResultBeanX>) bean.getResult().getFocus().getResult();
                            if (result != null && result.size() > 0) {
                                for (int i = 0; i < result.size(); i++) {
                                    //获取所有图片
                                    mBannerImages.add(result.get(i).getRandpic());
                                }
                                if (mRootView!=null){
                                    mRootView.showBannerView(mBannerImages, result);
                                }
                            }
                        }
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
