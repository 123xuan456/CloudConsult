package com.mtm.cloudconsult.mvp.model;

import android.app.Application;

import com.google.gson.Gson;
import com.jess.arms.di.scope.FragmentScope;
import com.jess.arms.integration.IRepositoryManager;
import com.jess.arms.mvp.BaseModel;
import com.mtm.cloudconsult.mvp.contract.TRecommendContract;
import com.mtm.cloudconsult.mvp.model.api.CommonService;
import com.mtm.cloudconsult.mvp.model.bean.FrontpageBean;

import javax.inject.Inject;

import io.reactivex.Observable;


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
    public Observable<FrontpageBean> showBannerPage() {
        return mRepositoryManager.obtainRetrofitService(CommonService.class).getFrontpage();

//        return  Observable.just(mRepositoryManager.obtainRetrofitService(CommonService.class).getFrontpage())
//                .flatMap(new Function<Observable<FrontpageBean>, ObservableSource<FrontpageBean>>() {
//                    @Override
//                    public ObservableSource<FrontpageBean> apply(Observable<FrontpageBean> todoListObservable) throws Exception {
//                        return mRepositoryManager.obtainCacheService(CommonCache.class)
//                                .getFrontpage(todoListObservable,new DynamicKey(1),new EvictDynamicKey(true))
//                                .map(listReply -> listReply.getData());
//                    }
//                });
    }
}