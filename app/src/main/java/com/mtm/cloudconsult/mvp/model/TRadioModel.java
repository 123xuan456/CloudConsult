package com.mtm.cloudconsult.mvp.model;

import android.app.Application;

import com.google.gson.Gson;
import com.jess.arms.di.scope.FragmentScope;
import com.jess.arms.integration.IRepositoryManager;
import com.jess.arms.mvp.BaseModel;
import com.mtm.cloudconsult.mvp.contract.TRadioContract;
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
public class TRadioModel extends BaseModel implements TRadioContract.Model {
    @Inject
    Gson mGson;
    @Inject
    Application mApplication;

    @Inject
    public TRadioModel(IRepositoryManager repositoryManager) {
        super(repositoryManager);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        this.mGson = null;
        this.mApplication = null;
    }

    @Override
    public Observable<GankIoDataBean> getGankIoData(String type,int currentPage, boolean isEvictCache) {
        return  Observable.just(mRepositoryManager.obtainRetrofitService(CommonService.class).getGankIoData(type,currentPage,20))
                .flatMap(new Function<Observable<GankIoDataBean>, ObservableSource<GankIoDataBean>>() {
                    @Override
                    public ObservableSource<GankIoDataBean> apply(Observable<GankIoDataBean> observable) throws Exception {
                        return mRepositoryManager.obtainCacheService(CommonCache.class)
                                .getGankIoData(observable,new DynamicKey(type+currentPage),new EvictDynamicKey(isEvictCache))
                                .map(listReply -> listReply.getData());
                    }
                });


    }
}