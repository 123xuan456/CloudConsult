package com.mtm.cloudconsult.mvp.model;

import android.app.Application;

import com.google.gson.Gson;
import com.jess.arms.di.scope.FragmentScope;
import com.jess.arms.integration.IRepositoryManager;
import com.jess.arms.mvp.BaseModel;
import com.mtm.cloudconsult.mvp.contract.TFriendContract;
import com.mtm.cloudconsult.mvp.model.api.CommonCache;
import com.mtm.cloudconsult.mvp.model.api.CommonService;
import com.mtm.cloudconsult.mvp.model.bean.GankIoDataBean;

import javax.inject.Inject;

import io.reactivex.Observable;
import io.reactivex.ObservableSource;
import io.reactivex.functions.Function;
import io.rx_cache2.DynamicKey;
import io.rx_cache2.EvictDynamicKey;


@FragmentScope
public class TFriendModel extends BaseModel implements TFriendContract.Model {
    @Inject
    Gson mGson;
    @Inject
    Application mApplication;

    @Inject
    public TFriendModel(IRepositoryManager repositoryManager) {
        super(repositoryManager);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        this.mGson = null;
        this.mApplication = null;
    }

    @Override
    public Observable<GankIoDataBean> getGankIoData(String mType, int currentPage, boolean isEvictCache) {
        return  Observable.just(mRepositoryManager.obtainRetrofitService(CommonService.class).getGankIoData(mType,currentPage,20))
                .flatMap(new Function<Observable<GankIoDataBean>, ObservableSource<GankIoDataBean>>() {
                    @Override
                    public ObservableSource<GankIoDataBean> apply(Observable<GankIoDataBean> observable) throws Exception {
                        return mRepositoryManager.obtainCacheService(CommonCache.class)
                                .getGankIoData(observable,new DynamicKey(mType+currentPage),new EvictDynamicKey(isEvictCache))
                                .map(listReply -> listReply.getData());
                    }
                });
    }
}