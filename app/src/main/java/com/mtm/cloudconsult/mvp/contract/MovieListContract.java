package com.mtm.cloudconsult.mvp.contract;

import com.jess.arms.mvp.IModel;
import com.mtm.cloudconsult.app.base.BaseEntityBean;
import com.mtm.cloudconsult.app.base.BaseRecycleIView;

import java.util.List;

import io.reactivex.Observable;


public interface MovieListContract {
    //对于经常使用的关于UI的方法可以定义到IView中,如显示隐藏进度条,和显示文字消息
    interface View<T> extends BaseRecycleIView<T> {



    }

    //Model层定义接口,外部只需关心Model返回的数据,无需关心内部细节,即是否使用缓存
    interface Model extends IModel {
        Observable<List<BaseEntityBean>> getHotPlayMovies(String type, String apikey, String city, int start, int count);
        Observable<List<BaseEntityBean>> getMoviePhotos(String apikey, String id,String type, int start,int count);
        Observable<List<BaseEntityBean>> getMovieUsBoxRequest(String type,String apikey);
        Observable<List<BaseEntityBean>> getMovieSearch(String tag,String q,String apikey,int start,int count);
    }
}
