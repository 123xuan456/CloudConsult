package com.mtm.cloudconsult.mvp.model.api;

import com.mtm.cloudconsult.mvp.model.bean.FrontpageBean;

import io.reactivex.Observable;
import retrofit2.http.GET;
import retrofit2.http.Headers;

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
    @GET("ting?from=android&version=5.8.1.0&channel=ppzs&operator=3&method=baidu.ting.plaza.index&cuid=89CF1E1A06826F9AB95A34DC0F6AAA14")
    Observable<FrontpageBean> getFrontpage();
}
