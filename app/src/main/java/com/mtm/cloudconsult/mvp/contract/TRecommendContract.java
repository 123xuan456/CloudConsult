package com.mtm.cloudconsult.mvp.contract;

import com.jess.arms.mvp.IModel;
import com.jess.arms.mvp.IView;
import com.mtm.cloudconsult.mvp.model.bean.AndroidBean;
import com.mtm.cloudconsult.mvp.model.bean.FrontpageBean;
import com.mtm.cloudconsult.mvp.model.bean.GankIoDayBean;

import java.util.ArrayList;
import java.util.List;

import io.reactivex.Observable;


public interface TRecommendContract {
    //对于经常使用的关于UI的方法可以定义到IView中,如显示隐藏进度条,和显示文字消息
    interface View extends IView {
        //轮播图
        void showBannerView(ArrayList<String> mBannerImages, ArrayList<FrontpageBean.ResultBannerBean.FocusBean.ResultBeanX> result);

        void showListView(ArrayList<List<AndroidBean>> lists);
    }

    //Model层定义接口,外部只需关心Model返回的数据,无需关心内部细节,即是否使用缓存
    interface Model extends IModel {
        Observable<FrontpageBean> showBannerPage(int dynamicKey,boolean updat);

        Observable<GankIoDayBean> getGankIoDay(String year, String month, String day);
    }
}
