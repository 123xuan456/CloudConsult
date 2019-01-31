package com.mtm.cloudconsult.mvp.contract;

import com.jess.arms.mvp.IModel;
import com.mtm.cloudconsult.app.base.BaseEntityBean;
import com.mtm.cloudconsult.app.base.BaseRecycleIView;

import java.util.List;

import io.reactivex.Observable;


public interface MovieDetailContract {
    //对于经常使用的关于UI的方法可以定义到IView中,如显示隐藏进度条,和显示文字消息
    interface View extends BaseRecycleIView {


    }

    //Model层定义接口,外部只需关心Model返回的数据,无需关心内部细节,即是否使用缓存
    interface Model extends IModel {
        Observable<List<BaseEntityBean>> getMovieDetail(String subjectId, String apikey, String city) ;
        Observable<List<BaseEntityBean>> getCelebrity(String subjectId,String apikey,MovieDetailContract.View view);
    }
}
