package com.mtm.cloudconsult.mvp.model.api;

import com.mtm.cloudconsult.mvp.model.bean.FrontpageBean;
import com.mtm.cloudconsult.mvp.model.bean.GankIoDataBean;
import com.mtm.cloudconsult.mvp.model.bean.GankIoDayBean;
import com.mtm.cloudconsult.mvp.model.bean.WanAndroidBannerBean;
import com.mtm.cloudconsult.mvp.model.bean.movie.MovieBean;
import com.mtm.cloudconsult.mvp.model.bean.movie.MovieCelebrity;
import com.mtm.cloudconsult.mvp.model.bean.movie.MovieHttpRequest;
import com.mtm.cloudconsult.mvp.model.bean.movie.MoviePhotoRequest;
import com.mtm.cloudconsult.mvp.model.bean.movie.MovieUsBoxRequest;

import io.reactivex.Observable;
import retrofit2.http.GET;
import retrofit2.http.Headers;
import retrofit2.http.Path;
import retrofit2.http.Query;

import static com.mtm.cloudconsult.app.api.Api.DOUBAN_DOMAIN_NAME;
import static com.mtm.cloudconsult.app.api.Api.GANK_DOMAIN_NAME;
import static com.mtm.cloudconsult.app.api.Api.TING_DOMAIN_NAME;
import static me.jessyan.retrofiturlmanager.RetrofitUrlManager.DOMAIN_NAME_HEADER;

/**
 * Created by MTM on 2019/1/4.
 *
 * @author QSX
 */
public interface CommonService {
    /**
     * 首页轮播图
     */
    @Headers({DOMAIN_NAME_HEADER + TING_DOMAIN_NAME})
    @GET("/ting?from=android&version=5.8.1.0&channel=ppzs&operator=3&method=baidu.ting.plaza.index&cuid=89CF1E1A06826F9AB95A34DC0F6AAA14")
    Observable<FrontpageBean> getFrontpage();


    /**
     * 玩安卓轮播图
     */
    @GET("banner/json")
    Observable<WanAndroidBannerBean> getWanAndroidBanner();

    /**
     * 分类数据: http://gank.io/api/data/数据类型/请求个数/第几页
     * 数据类型： 福利 | Android | iOS | 休息视频 | 拓展资源 | 前端 | all
     * 请求个数： 数字，大于0
     * 第几页：数字，大于0
     * eg: http://gank.io/api/data/Android/10/1
     */
    @Headers({DOMAIN_NAME_HEADER + GANK_DOMAIN_NAME})
    @GET("/api/data/{type}/{pre_page}/{page}")
    Observable<GankIoDataBean> getGankIoData(@Path("type") String id, @Path("page") int page, @Path("pre_page") int pre_page);

    /**
     * 每日数据： http://gank.io/api/day/年/月/日
     * eg:http://gank.io/api/day/2015/08/06
     */
    @Headers({DOMAIN_NAME_HEADER + GANK_DOMAIN_NAME})
    @GET("/api/day/{year}/{month}/{day}")
    Observable<GankIoDayBean> getGankIoDay(@Path("year") String year, @Path("month") String month, @Path("day") String day);



    /**
     * 正在上映
     *https://api.douban.com/v2/movie/in_theaters?apikey=0b2bdeda43b5688921839c8ecb20399b&city=%E5%8C%97%E4%BA%AC&start=0&count=100&client=somemessage&udid=dddddddddddddddddddddd
     * @param apikey 固定值 0b2bdeda43b5688921839c8ecb20399b
     * @param city 城市名称 上海/北京
     * @param start 分页使用，表示第几页
     * @param count 分页使用，表示数量
     * @return
     */
    @Headers({DOMAIN_NAME_HEADER+ DOUBAN_DOMAIN_NAME})
    @GET("/v2/movie/{type}")
    Observable<MovieHttpRequest> getHotPlayMovies(@Path("type") String type, @Query("apikey") String apikey, @Query("city") String city, @Query("start") int start, @Query("count") int count);

    /**
     * 口碑榜
     *https://api.douban.com/v2/movie/in_theaters?apikey=0b2bdeda43b5688921839c8ecb20399b&city=%E5%8C%97%E4%BA%AC&start=0&count=100&client=somemessage&udid=dddddddddddddddddddddd
     * @param apikey 固定值 0b2bdeda43b5688921839c8ecb20399b
     * @return
     */
    @Headers({DOMAIN_NAME_HEADER+ DOUBAN_DOMAIN_NAME})
    @GET("/v2/movie/weekly")
    Observable<MovieHttpRequest> getWeekly(@Query("apikey") String apikey);

    /**
     * 北美票房榜
     *https://api.douban.com/v2/movie/in_theaters?apikey=0b2bdeda43b5688921839c8ecb20399b&city=%E5%8C%97%E4%BA%AC&start=0&count=100&client=somemessage&udid=dddddddddddddddddddddd
     * @param apikey 固定值 0b2bdeda43b5688921839c8ecb20399b
     * @return
     */
    @Headers({DOMAIN_NAME_HEADER+ DOUBAN_DOMAIN_NAME})
    @GET("/v2/movie/{type}")
    Observable<MovieUsBoxRequest> getUsBox(@Path("type") String type, @Query("apikey") String apikey);

    /**
     * Top250
     *https://api.douban.com/v2/movie/in_theaters?apikey=0b2bdeda43b5688921839c8ecb20399b&city=%E5%8C%97%E4%BA%AC&start=0&count=100&client=somemessage&udid=dddddddddddddddddddddd
     * @param apikey 固定值 0b2bdeda43b5688921839c8ecb20399b
     * @param start 分页使用，表示第几页
     * @param count 分页使用，表示数量
     * @return
     */
    @Headers({DOMAIN_NAME_HEADER+ DOUBAN_DOMAIN_NAME})
    @GET("/v2/movie/top250")
    Observable<MovieHttpRequest> getTop250(@Query("apikey") String apikey, @Query("start") int start, @Query("count") int count);


    /**
     *http://api.douban.com/v2/movie/subject/26865690?apikey=0b2bdeda43b5688921839c8ecb20399b&city=%E5%8C%97%E4%BA%AC&client=something&udid=dddddddddddddddddddddd
     * @param apikey 固定值 0b2bdeda43b5688921839c8ecb20399b
     * @param city 城市名称 上海/北京
     * @return
     */
    @Headers({DOMAIN_NAME_HEADER+ DOUBAN_DOMAIN_NAME})
    @GET("/v2/movie/subject/{subjectId}")
    Observable<MovieBean> getMovieDetail(@Path("subjectId")String subjectId, @Query("apikey") String apikey, @Query("city") String city);

    /**
     * 电影剧照
     *https://api.douban.com//v2/movie/celebrity/1054395?apikey=0b2bdeda43b5688921839c8ecb20399b
     * @param apikey 固定值 0b2bdeda43b5688921839c8ecb20399b
     * @return
     */
    @Headers({DOMAIN_NAME_HEADER+ DOUBAN_DOMAIN_NAME})
    @GET("/v2/movie/subject/{Id}/{type}")
    Observable<MoviePhotoRequest> getMoviePhotos(@Path("Id")String id, @Path("type")String type, @Query("apikey") String apikey, @Query("start") int start, @Query("count") int count);

    /**
     * 获取影人信息
     *https://api.douban.com//v2/movie/celebrity/1054395?apikey=0b2bdeda43b5688921839c8ecb20399b
     * @param apikey 固定值 0b2bdeda43b5688921839c8ecb20399b
     * @return
     */
    @Headers({DOMAIN_NAME_HEADER+ DOUBAN_DOMAIN_NAME})
    @GET("/v2/movie/celebrity/{Id}")
    Observable<MovieCelebrity> getCelebrity(@Path("Id")String id, @Query("apikey") String apikey);

    /**
     * 电影搜索
     *https://api.douban.com//v2/movie/celebrity/1054395?apikey=0b2bdeda43b5688921839c8ecb20399b
     * @param apikey 固定值 0b2bdeda43b5688921839c8ecb20399b
     * @return
     */
    @Headers({DOMAIN_NAME_HEADER+ DOUBAN_DOMAIN_NAME})
    @GET("/v2/movie/search")
    Observable<MovieHttpRequest> getMovieSearch(@Query("tag")String tag, @Query("q")String q, @Query("apikey") String apikey, @Query("start") int start, @Query("count") int count);






}
