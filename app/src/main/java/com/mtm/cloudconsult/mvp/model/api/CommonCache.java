package com.mtm.cloudconsult.mvp.model.api;

import com.mtm.cloudconsult.mvp.model.bean.FrontpageBean;

import java.util.concurrent.TimeUnit;

import io.reactivex.Observable;
import io.rx_cache2.DynamicKey;
import io.rx_cache2.EvictProvider;
import io.rx_cache2.LifeCache;

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
    Observable<FrontpageBean> getFrontpage(Observable<FrontpageBean> commonService, DynamicKey idLastUserQueried, EvictProvider evictProvider);
}
