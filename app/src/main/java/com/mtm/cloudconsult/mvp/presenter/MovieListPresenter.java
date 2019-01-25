package com.mtm.cloudconsult.mvp.presenter;

import android.app.Application;

import com.jess.arms.di.scope.FragmentScope;
import com.jess.arms.http.imageloader.ImageLoader;
import com.jess.arms.integration.AppManager;
import com.mtm.cloudconsult.app.api.CloudConstant;
import com.mtm.cloudconsult.app.base.BaseEntityBean;
import com.mtm.cloudconsult.app.base.BaseRecyclePresenter;
import com.mtm.cloudconsult.mvp.contract.MovieListContract;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import me.jessyan.rxerrorhandler.core.RxErrorHandler;


@FragmentScope
public class MovieListPresenter extends BaseRecyclePresenter<MovieListContract.Model, MovieListContract.View> {
    @Inject
    RxErrorHandler mErrorHandler;
    @Inject
    Application mApplication;
    @Inject
    ImageLoader mImageLoader;
    @Inject
    AppManager mAppManager;

    private List<BaseEntityBean> mDatas;
    private int page;
    @Inject
    public MovieListPresenter(MovieListContract.Model model, MovieListContract.View rootView) {
        super(model, rootView);
        mDatas = new ArrayList<>();
        pregPage = 15;
    }

    /**
     * 列表数据
     * @param type
     * @param pullToRefresh
     */
    public void getData(String type,boolean pullToRefresh) {
        if(pullToRefresh){
            page = 1;
        }else{
            page++;
        }
        int start = (page-1)*pregPage;
        getDataList(mModel.getHotPlayMovies(type, CloudConstant.MOVIE_KEY,"北京",start,pregPage), mDatas, mRootView, mErrorHandler, pullToRefresh);
    }
    public void getMovieUsBoxRequest(String type) {
        getDataList(mModel.getMovieUsBoxRequest(type,CloudConstant.MOVIE_KEY), mDatas, mRootView, mErrorHandler, true);
    }
    public void getMovieTypeList(String subjectId,String type, boolean pullToRefresh) {
        if("photos".equals(type)){
            setPregPage(21);
        }
        if(pullToRefresh){
            page = 1;
        }else{
            page++;
        }
        int start = (page-1)*pregPage;
        getDataList(mModel.getMoviePhotos(CloudConstant.MOVIE_KEY,subjectId,type,start,pregPage), mDatas, mRootView, mErrorHandler, pullToRefresh);
    }
    public void getMovieSearchTag(String type,boolean pullToRefresh) {
        if(pullToRefresh){
            page = 1;
        }else{
            page++;
        }
        int start = (page-1)*pregPage;
        getDataList(mModel.getMovieSearch(type,"",CloudConstant.MOVIE_KEY,start,pregPage), mDatas, mRootView, mErrorHandler, pullToRefresh);
    }
    public void getMovieSearchQuery(String type,boolean pullToRefresh) {
        if(pullToRefresh){
            page = 1;
        }else{
            page++;
        }
        int start = (page-1)*pregPage;
        getDataList(mModel.getMovieSearch("",type,CloudConstant.MOVIE_KEY,start,pregPage), mDatas, mRootView, mErrorHandler, pullToRefresh);
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
