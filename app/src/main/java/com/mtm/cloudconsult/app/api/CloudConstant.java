package com.mtm.cloudconsult.app.api;


/**
 * 依赖库的常量
 */
public class CloudConstant {

    // 热映缓存
    public static String ONE_HOT_MOVIE = "one_hot_movie";
    // 保存每日推荐轮播图url
    public static String BANNER_PIC = "banner_pic";
    // 保存每日推荐轮播图的跳转数据
    public static String BANNER_PIC_DATA = "banner_pic_data";
    // 保存每日推荐recyclerview内容
    public static String EVERYDAY_CONTENT = "everyday_content";
    // 干货订制类别
    public static String GANK_CALA = "gank_cala";
    // 是否登录
    public static String IS_LOGIN = "is_login";
    // 是否第一次收藏网址
    public static String IS_FIRST_COLLECTURL = "isFirstCollectUrl";

    public static final String WECHAT_KEY = "4d180a8abece50b0ab967050205fd6bc";
    public static final String MOVIE_KEY = "0b2bdeda43b5688921839c8ecb20399b";
    public class LoadSir {
        //成功
        public static final int SUCCESS = 1001;
        //失败
        public static final int ERROR = 1002;
        //数据为空
        public static final int EMPTY = 1003;
        //加载中
        public static final int LOADING = 1004;
        //超时
        public static final int TIMEOUT = 1005;
        //未开启网络
        public static final int NO_NETWORK = 1006;
    }

    /*Movie*/
    public static final int  MOVIE_LIST_DEFAULT = 1401; //列表
    public static final int MOVIE_PHOTOS_LIST = 1402;
    public static final int MOVIE_COMMENT_DEFAULT = 1403;
    public static final int MOVIE_COMMENT_REVIEW = 1404;
    public static final int MOVIE_LIST_US_BOX = 1405;
    public static final int MOVIE_LIST_SEARCH_TAG = 1406;
    public static final int MOVIE_LIST_SEARCH_QUERY = 1407;
    public static final int MOVIE_SEARCH_TYPE = 1408;

}
