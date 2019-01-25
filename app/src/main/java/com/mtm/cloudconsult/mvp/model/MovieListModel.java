package com.mtm.cloudconsult.mvp.model;

import android.app.Application;

import com.google.gson.Gson;
import com.jess.arms.di.scope.FragmentScope;
import com.jess.arms.integration.IRepositoryManager;
import com.jess.arms.mvp.BaseModel;
import com.mtm.cloudconsult.app.api.AdapterConstant;
import com.mtm.cloudconsult.app.base.BaseEntityBean;
import com.mtm.cloudconsult.mvp.contract.MovieListContract;
import com.mtm.cloudconsult.mvp.model.api.CommonCache;
import com.mtm.cloudconsult.mvp.model.api.CommonService;
import com.mtm.cloudconsult.mvp.model.bean.movie.MovieBean;
import com.mtm.cloudconsult.mvp.model.bean.movie.MovieComment;
import com.mtm.cloudconsult.mvp.model.bean.movie.MovieHttpRequest;
import com.mtm.cloudconsult.mvp.model.bean.movie.MoviePhotoRequest;
import com.mtm.cloudconsult.mvp.model.bean.movie.MovieUsBoxRequest;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import io.reactivex.Observable;
import io.reactivex.ObservableSource;
import io.reactivex.annotations.NonNull;
import io.reactivex.functions.Function;
import io.rx_cache2.DynamicKey;
import io.rx_cache2.EvictDynamicKey;


@FragmentScope
public class MovieListModel extends BaseModel implements MovieListContract.Model {
    @Inject
    Gson mGson;
    @Inject
    Application mApplication;

    @Inject
    public MovieListModel(IRepositoryManager repositoryManager) {
        super(repositoryManager);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        this.mGson = null;
        this.mApplication = null;
    }

    @Override
    public Observable<List<BaseEntityBean>> getHotPlayMovies(String type, String apikey, String city, int start, int count) {
        Observable<List<BaseEntityBean>> observable = mRepositoryManager.obtainRetrofitService(CommonService.class)
                .getHotPlayMovies(type,apikey,city,start,count).flatMap(new Function<MovieHttpRequest, ObservableSource<List<BaseEntityBean>>>() {
                    @Override
                    public ObservableSource<List<BaseEntityBean>> apply(@NonNull MovieHttpRequest listReply) throws Exception {
                        List<BaseEntityBean> BaseEntityBeans = new ArrayList<BaseEntityBean>();
                        if (listReply != null && listReply.getSubjects() != null && listReply.getSubjects().size() > 0) {
                            BaseEntityBeans.addAll(listReply.getSubjects());
                        }
                        return Observable.just(BaseEntityBeans);
                    }
                });
        return mRepositoryManager.obtainCacheService(CommonCache.class).getListDataCache(observable, new DynamicKey("getHotPlayMovies"+type+apikey+city+start+count), new EvictDynamicKey(false));
    }

    @Override
    public Observable<List<BaseEntityBean>> getMovieUsBoxRequest(String type,String apikey) {
        Observable<MovieUsBoxRequest> observable = mRepositoryManager.obtainCacheService(CommonCache.class).getMovieUsBoxRequest(mRepositoryManager.obtainRetrofitService(CommonService.class)
                .getUsBox(type,apikey), new DynamicKey("getMovieTypeList"+apikey), new EvictDynamicKey(false));
        return observable.flatMap(new Function<MovieUsBoxRequest, ObservableSource<List<BaseEntityBean>>>() {
            @Override
            public ObservableSource<List<BaseEntityBean>> apply(@NonNull MovieUsBoxRequest listReply) throws Exception {
                List<BaseEntityBean> BaseEntityBeans = new ArrayList<BaseEntityBean>();
                if(listReply!=null){
                    if (listReply.getSubjects() != null && listReply.getSubjects().size() > 0) {
                        for (int i=0;i<listReply.getSubjects().size();i++){
                            MovieBean subject = listReply.getSubjects().get(i).getSubject();
                            BaseEntityBeans.add(subject);
                        }
                    }
                }
                return Observable.just(BaseEntityBeans);
            }
        });
    }

    @Override
    public Observable<List<BaseEntityBean>> getMoviePhotos(String apikey, String id,String type, int start,int count) {
        Observable<MoviePhotoRequest> observable = mRepositoryManager.obtainCacheService(CommonCache.class).getMoviePhotoRequest(mRepositoryManager.obtainRetrofitService(CommonService.class)
                .getMoviePhotos(id,type,apikey,start,count), new DynamicKey("getMovieTypeList"+apikey+id+type+start+count), new EvictDynamicKey(false));

        return observable.flatMap(new Function<MoviePhotoRequest, ObservableSource<List<BaseEntityBean>>>() {
            @Override
            public ObservableSource<List<BaseEntityBean>> apply(@NonNull MoviePhotoRequest listReply) throws Exception {
                List<BaseEntityBean> BaseEntityBeans = new ArrayList<BaseEntityBean>();
                if(listReply!=null){
                    if (listReply.getPhotos() != null && listReply.getPhotos().size() > 0) {
                        BaseEntityBeans.addAll(listReply.getPhotos());
                    }
                    if (listReply.getReviews() != null && listReply.getReviews().size() > 0) {
                        BaseEntityBeans.addAll(listReply.getReviews());
                    }
                    if (listReply.getComments() != null && listReply.getComments().size() > 0) {
                        for (int i=0;i<listReply.getComments().size();i++){
                            MovieComment comment = listReply.getComments().get(i);
                            comment.setItemtype(AdapterConstant.ITME_MOVIE_COMMENT_DEFAULT);
                            BaseEntityBeans.add(comment);
                        }
                    }
                }
                return Observable.just(BaseEntityBeans);
            }
        });
    }
    @Override
    public Observable<List<BaseEntityBean>> getMovieSearch(String tag,String q,String apikey,int start,int count) {
        Observable<List<BaseEntityBean>> observable = mRepositoryManager.obtainRetrofitService(CommonService.class)
                .getMovieSearch(tag,q,apikey,start,count).flatMap(new Function<MovieHttpRequest, ObservableSource<List<BaseEntityBean>>>() {
                    @Override
                    public ObservableSource<List<BaseEntityBean>> apply(@NonNull MovieHttpRequest listReply) throws Exception {
                        List<BaseEntityBean> BaseEntityBeans = new ArrayList<BaseEntityBean>();
                        if (listReply != null && listReply.getSubjects() != null && listReply.getSubjects().size() > 0) {
                            BaseEntityBeans.addAll(listReply.getSubjects());
                        }
                        return Observable.just(BaseEntityBeans);
                    }
                });
        return mRepositoryManager.obtainCacheService(CommonCache.class).getListDataCache(observable, new DynamicKey("getMovieSearch"+q+apikey+tag+start+count), new EvictDynamicKey(false));
    }
}