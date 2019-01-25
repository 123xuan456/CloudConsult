package com.mtm.cloudconsult.mvp.model.api;

import com.mtm.cloudconsult.app.base.BaseEntityBean;
import com.mtm.cloudconsult.mvp.model.bean.FrontpageBean;
import com.mtm.cloudconsult.mvp.model.bean.GankIoDataBean;
import com.mtm.cloudconsult.mvp.model.bean.GankIoDayBean;
import com.mtm.cloudconsult.mvp.model.bean.movie.MovieBean;
import com.mtm.cloudconsult.mvp.model.bean.movie.MovieCelebrity;
import com.mtm.cloudconsult.mvp.model.bean.movie.MovieHttpRequest;
import com.mtm.cloudconsult.mvp.model.bean.movie.MoviePhotoRequest;
import com.mtm.cloudconsult.mvp.model.bean.movie.MovieUsBoxRequest;

import java.util.List;
import java.util.concurrent.TimeUnit;

import io.reactivex.Observable;
import io.rx_cache2.DynamicKey;
import io.rx_cache2.EvictDynamicKey;
import io.rx_cache2.EvictProvider;
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
     * 每日数据： http://gank.io/api/day/年/月/日
     */
    @LifeCache(duration = 1, timeUnit = TimeUnit.DAYS)
    Observable<Reply<GankIoDayBean>> getGankIoDayBean(Observable<GankIoDayBean> commonService, DynamicKey dynamicKey, EvictDynamicKey evictDynamicKey);
    /**
     * 分类数据: http://gank.io/api/data/数据类型/请求个数/第几页
     * 数据类型： 福利 | Android | iOS | 休息视频 | 拓展资源 | 前端 | all
     * 请求个数： 数字，大于0
     * 第几页：数字，大于0
     */
    @LifeCache(duration = 2, timeUnit = TimeUnit.HOURS)
    Observable<Reply<GankIoDataBean>> getGankIoData(Observable<GankIoDataBean> commonService, DynamicKey dynamicKey, EvictDynamicKey evictDynamicKey);



    @LifeCache(duration = 2, timeUnit = TimeUnit.HOURS)
    Observable<MovieBean> getMovieBean(Observable<MovieBean> users, DynamicKey idLastUserQueried, EvictProvider evictProvider);



    @LifeCache(duration = 2, timeUnit = TimeUnit.HOURS)
    Observable<MovieCelebrity> getMovieCelebrity(Observable<MovieCelebrity> users, DynamicKey idLastUserQueried, EvictProvider evictProvider);
    @LifeCache(duration = 2, timeUnit = TimeUnit.HOURS)
    Observable<MoviePhotoRequest> getMoviePhotoRequest(Observable<MoviePhotoRequest> users, DynamicKey idLastUserQueried, EvictProvider evictProvider);

    @LifeCache(duration = 2, timeUnit = TimeUnit.HOURS)
    Observable<MovieHttpRequest> getMovieHttpRequest(Observable<MovieHttpRequest> users, DynamicKey idLastUserQueried, EvictProvider evictProvider);

    @LifeCache(duration = 2, timeUnit = TimeUnit.HOURS)
    Observable<MovieUsBoxRequest> getMovieUsBoxRequest(Observable<MovieUsBoxRequest> users, DynamicKey idLastUserQueried, EvictProvider evictProvider);


    @LifeCache(duration = 2, timeUnit = TimeUnit.HOURS)
    Observable<List<BaseEntityBean>> getListDataCache(Observable<List<BaseEntityBean>> users, DynamicKey idLastUserQueried, EvictProvider evictProvider);




}
