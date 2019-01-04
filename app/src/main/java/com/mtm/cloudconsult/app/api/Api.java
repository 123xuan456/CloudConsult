package com.mtm.cloudconsult.app.api;

/**
 * 配置请求地址以及相关信息
 *
 * @author MtmWp
 */

public interface Api {
    // gankio、豆瓣、（轮播图）
     String API_GANKIO = "https://gank.io/api/";
     String API_DOUBAN = "Https://api.douban.com/";
     String API_TING = "https://tingapi.ting.baidu.com/v1/restserver/";
     String API_FIR = "http://api.fir.im/apps/";
     String API_WAN_ANDROID = "http://www.wanandroid.com/";
     String API_QSBK = "http://m2.qiushibaike.com/";

     String GANK_DOMAIN_NAME = "gank";
     String DOUBAN_DOMAIN_NAME = "douban";
     String TING_DOMAIN_NAME = "ting";
     String FIR_DOMAIN_NAME = "fir";
     String WAN_ANDROID_DOMAIN_NAME = "wan_android";
     String QSBK_DOMAIN_NAME = "qsbk";
}