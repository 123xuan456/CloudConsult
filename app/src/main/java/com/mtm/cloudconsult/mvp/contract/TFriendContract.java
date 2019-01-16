package com.mtm.cloudconsult.mvp.contract;

import com.jess.arms.mvp.IModel;
import com.mtm.cloudconsult.mvp.model.bean.GankIoDataBean;

import java.util.ArrayList;

import io.reactivex.Observable;


public interface TFriendContract {
    //对于经常使用的关于UI的方法可以定义到IView中,如显示隐藏进度条,和显示文字消息
    interface View extends BaseRecyIView {
        void setGankIoData(GankIoDataBean bean);
        //没有更多数据
        void showListNoMoreLoading();
        //获取数据出错
        void showListError();

        void setImageList(ArrayList<ArrayList<String>> arrayLists);
    }

    //Model层定义接口,外部只需关心Model返回的数据,无需关心内部细节,即是否使用缓存
    interface Model extends IModel {
        Observable<GankIoDataBean> getGankIoData(String mType, int currentPage, boolean isEvictCache);
    }
}
