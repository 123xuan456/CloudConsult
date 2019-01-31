package com.mtm.cloudconsult.app.base;

import android.app.Activity;
import android.support.v7.widget.RecyclerView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.jess.arms.mvp.IView;

import java.util.List;

/**
 * recycle 接口基类
 */
public interface BaseRecycleIView<T> extends IView {
    Activity getActivity();
    //设置页面状态切换
    void showLoadSirView(int status);
    //重新加载页面数据
    void onViewReload();
    //刷新列表
    void refreshUI(List<T> data);
    //更新
    void updateUI(T data);
    //删除
    void deleteUI(T data);
    //请求出错
    void onError(String error);
    //加载更多
    void loadMore(List<T> data, boolean hasMore);
    // 下拉刷新
    void onDataRefresh();
    // 上拉加载
    void onDataLoadMore();
    //
    int getReHeaderView();

    int getReFooterView();
    //是否开启下拉刷新
    boolean enableRefresh();
    //是否开启下拉刷新
    boolean enableMore();
    //
    BaseQuickAdapter<T, BaseViewHolder> getAdapter();
    //列表样式
    RecyclerView.LayoutManager getLayoutManager();


}
