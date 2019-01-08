package com.mtm.cloudconsult.mvp.model.api;

import com.mtm.cloudconsult.mvp.model.bean.FrontpageBean;
import com.mtm.cloudconsult.mvp.model.bean.GankIoDayBean;

import java.util.concurrent.TimeUnit;

import io.reactivex.Observable;
import io.rx_cache2.DynamicKey;
import io.rx_cache2.EvictDynamicKey;
import io.rx_cache2.LifeCache;
import io.rx_cache2.Reply;

/**
 * Created by MTM on 2019/1/4.
 *  缓存数据
 * @author QSX
 */
public interface CommonCache {

    /**
     * 轮播图
     */
    @LifeCache(duration = 1, timeUnit = TimeUnit.DAYS)
    Observable<Reply<FrontpageBean>> getFrontpage(Observable<FrontpageBean> commonService);
    /**
     *
     */
    @LifeCache(duration = 1, timeUnit = TimeUnit.DAYS)
    Observable<Reply<GankIoDayBean>> getGankIoDayBean(Observable<GankIoDayBean> commonService, DynamicKey dynamicKey, EvictDynamicKey evictDynamicKey);
}
