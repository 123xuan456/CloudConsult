package com.mtm.cloudconsult.mvp.model;

import android.app.Application;

import com.google.gson.Gson;
import com.jess.arms.di.scope.FragmentScope;
import com.jess.arms.integration.IRepositoryManager;
import com.jess.arms.mvp.BaseModel;
import com.mtm.cloudconsult.mvp.contract.TRecommendContract;
import com.mtm.cloudconsult.mvp.model.api.CommonCache;
import com.mtm.cloudconsult.mvp.model.api.CommonService;
import com.mtm.cloudconsult.mvp.model.bean.FrontpageBean;
import com.mtm.cloudconsult.mvp.model.bean.GankIoDayBean;

import javax.inject.Inject;

import io.reactivex.Observable;
import io.reactivex.ObservableSource;
import io.reactivex.functions.Function;


@FragmentScope
public class TRecommendModel extends BaseModel implements TRecommendContract.Model {
    @Inject
    Gson mGson;
    @Inject
    Application mApplication;

    @Inject
    public TRecommendModel(IRepositoryManager repositoryManager) {
        super(repositoryManager);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        this.mGson = null;
        this.mApplication = null;
    }

    @Override
    public Observable<FrontpageBean> showBannerPage(int dynamicKey,boolean update) {
//        return mRepositoryManager.obtainRetrofitService(CommonService.class).getFrontpage();
        //读取缓存
        return  Observable.just(mRepositoryManager.obtainRetrofitService(CommonService.class).getFrontpage())
                .flatMap(new Function<Observable<FrontpageBean>, ObservableSource<FrontpageBean>>() {
                    @Override
                    public ObservableSource<FrontpageBean> apply(Observable<FrontpageBean> todoListObservable) throws Exception {
                        return mRepositoryManager.obtainCacheService(CommonCache.class)
                                .getFrontpage(todoListObservable)
//                                .getFrontpage(todoListObservable,new DynamicKey(dynamicKey),new EvictDynamicKey(update))
                                .map(listReply -> listReply.getData());
                    }
                });
    }

    @Override
    public Observable<GankIoDayBean> getGankIoDay(String year, String month, String day) {
        return  mRepositoryManager.obtainRetrofitService(CommonService.class).getGankIoDay(year, month, day);
//        return  Observable.just(mRepositoryManager.obtainRetrofitService(CommonService.class).getGankIoDay(year, month, day))
//                .flatMap(new Function<Observable<GankIoDayBean>, ObservableSource<GankIoDayBean>>() {
//                    @Override
//                    public ObservableSource<GankIoDayBean> apply(Observable<GankIoDayBean> observable) throws Exception {
//                        return mRepositoryManager.obtainCacheService(CommonCache.class)
//                                .getGankIoDayBean(observable,new DynamicKey(year+month+day),new EvictDynamicKey(true))
//                                .map(listReply -> listReply.getData());
//                    }
//                });
    }
}