package com.mtm.cloudconsult.mvp.presenter;

import android.app.Application;

import com.jess.arms.di.scope.ActivityScope;
import com.jess.arms.http.imageloader.ImageLoader;
import com.jess.arms.integration.AppManager;
import com.mtm.cloudconsult.app.base.BaseRecyclePresenter;
import com.mtm.cloudconsult.mvp.contract.MovieDetailContract;

import java.util.ArrayList;

import javax.inject.Inject;

import me.jessyan.rxerrorhandler.core.RxErrorHandler;


@ActivityScope
public class MovieDetailPresenter extends BaseRecyclePresenter<MovieDetailContract.Model, MovieDetailContract.View> {
    @Inject
    RxErrorHandler mErrorHandler;
    @Inject
    Application mApplication;
    @Inject
    ImageLoader mImageLoader;
    @Inject
    AppManager mAppManager;

    @Inject
    public MovieDetailPresenter(MovieDetailContract.Model model, MovieDetailContract.View rootView) {
        super(model, rootView);
    }
    public void getMovieDetail(String subjectId,boolean pullToRefresh) {
        getDataList(mModel.getMovieDetail(subjectId, "0b2bdeda43b5688921839c8ecb20399b", "北京"), new ArrayList<>(), mRootView, mErrorHandler, pullToRefresh);
    }

    public void getCelebrity(String subjectId,boolean pullToRefresh) {
        getDataList(mModel.getCelebrity(subjectId,"0b2bdeda43b5688921839c8ecb20399b",mRootView), new ArrayList<>(), mRootView, mErrorHandler, pullToRefresh);

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
